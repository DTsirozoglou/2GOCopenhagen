package users

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.authentication.dao.NullSaltSource
import grails.plugin.springsecurity.ui.RegistrationCode
import groovy.text.SimpleTemplateEngine
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class RegisterController extends grails.plugin.springsecurity.ui.RegisterController {
	
	// override default value from base class
	static defaultAction = 'index'

	// override default value from base class
	static allowedMethods = [register: 'POST']

	def mailService
	def messageSource
	def saltSource
	
	def index() {
		def copy = [:] + (flash.chainedParams ?: [:])
		copy.remove 'controller'
		copy.remove 'action'
		[command: new RegisterCommand(copy)]
	}
	
	def register(RegisterCommand command) {

		println "lala2"
		println command.errors
		
		if (command.hasErrors()) {
			render view: 'index', model: [command: command]
			return
		}

		println "lala5"
		String salt = saltSource instanceof NullSaltSource ? null : command.username
		def user = lookupUserClass().newInstance(username: command.username,email: command.username, phoneNumber: command.phoneNumber,firstName:command.firstName,
				lastName:command.lastName, country:command.country,accountLocked: true, enabled: true)

		RegistrationCode registrationCode = springSecurityUiService.register(user, command.password, salt)
		if (registrationCode == null || registrationCode.hasErrors()) {
			// null means problem creating the user
			flash.error = message(code: 'spring.security.ui.register.miscError')
			flash.chainedParams = params
			redirect action: 'index'
			return
		}

		String url = generateLink('verifyRegistration', [t: registrationCode.token])

		def conf = SpringSecurityUtils.securityConfig
		def body = conf.ui.register.emailBody
		if (body.contains('$')) {
			body = evaluate(body, [user: user, url: url])
		}
		mailService.sendMail {
			to command.username
			from conf.ui.register.emailFrom
			subject conf.ui.register.emailSubject
			html body.toString()
		}
		render view: 'index', model: [emailSent: true]
	}
	
	def verifyRegistration() {
		
		println "lala2322"
				def conf = SpringSecurityUtils.securityConfig
				String defaultTargetUrl = conf.successHandler.defaultTargetUrl
		
				String token = params.t
		
				def registrationCode = token ? RegistrationCode.findByToken(token) : null
				if (!registrationCode) {
					flash.error = message(code: 'spring.security.ui.register.badCode')
					redirect uri: defaultTargetUrl
					return
				}
		
				def user
				// TODO to ui service
				RegistrationCode.withTransaction { status ->
					String usernameFieldName = SpringSecurityUtils.securityConfig.userLookup.usernamePropertyName
					
					user = lookupUserClass().findWhere((usernameFieldName): registrationCode.username)
					if (!user) {
						return
					}
					user.accountLocked = false
					user.save(flush:true)
					def UserRole = lookupUserRoleClass()
					def Role = lookupRoleClass()
					for (roleName in conf.ui.register.defaultRoleNames) {
						UserRole.create user, Role.findByAuthority(roleName)
					}
					registrationCode.delete()
				}
		
				if (!user) {
					flash.error = message(code: 'spring.security.ui.register.badCode')
					redirect uri: defaultTargetUrl
					return
				}
		
				springSecurityService.reauthenticate user.username
		
				flash.message = message(code: 'spring.security.ui.register.complete')
				redirect uri: conf.ui.register.postRegisterUrl ?: defaultTargetUrl
			}

	def forgotPassword() {
		
				if (!request.post) {
					// show the form
					return
				}
		
				String username = params.username
				if (!username) {
					flash.error = message(code: 'spring.security.ui.forgotPassword.username.missing')
					redirect action: 'forgotPassword'
					return
				}
		
				String usernameFieldName = SpringSecurityUtils.securityConfig.userLookup.usernamePropertyName
				def user = lookupUserClass().findWhere((usernameFieldName): username)
				if (!user) {
					flash.error = message(code: 'spring.security.ui.forgotPassword.user.notFound')
					redirect action: 'forgotPassword'
					return
				}
		
				def registrationCode = new RegistrationCode(username: user."$usernameFieldName")
				registrationCode.save(flush: true)
		
				String url = generateLink('resetPassword', [t: registrationCode.token])
		
				def conf = SpringSecurityUtils.securityConfig
				def body = conf.ui.forgotPassword.emailBody
				if (body.contains('$')) {
					body = evaluate(body, [user: user, url: url])
				}
				mailService.sendMail {
					to user.username
					from conf.ui.forgotPassword.emailFrom
					subject conf.ui.forgotPassword.emailSubject
					html body.toString()
				}
		
				[emailSent: true]
			}
		
			def resetPassword(ResetPasswordCommand command) {
		
				String token = params.t
		
				def registrationCode = token ? RegistrationCode.findByToken(token) : null
				if (!registrationCode) {
					flash.error = message(code: 'spring.security.ui.resetPassword.badCode')
					redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
					return
				}
		
				if (!request.post) {
					return [token: token, command: new ResetPasswordCommand()]
				}
		
				command.username = registrationCode.username
				command.validate()
		
				if (command.hasErrors()) {
					return [token: token, command: command]
				}
		
				String salt = saltSource instanceof NullSaltSource ? null : registrationCode.username
				RegistrationCode.withTransaction { status ->
					String usernameFieldName = SpringSecurityUtils.securityConfig.userLookup.usernamePropertyName
					def user = lookupUserClass().findWhere((usernameFieldName): registrationCode.username)
					user.password = springSecurityUiService.encodePassword(command.password, salt)
					user.save()
					registrationCode.delete()
				}
		
				springSecurityService.reauthenticate registrationCode.username
		
				flash.message = message(code: 'spring.security.ui.resetPassword.success')
		
				def conf = SpringSecurityUtils.securityConfig
				String postResetUrl = conf.ui.register.postResetUrl ?: conf.successHandler.defaultTargetUrl
				redirect uri: postResetUrl
			}
		
			protected String generateLink(String action, linkParams) {
				createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
						controller: 'register', action: action,
						params: linkParams)
			}
		
			protected String evaluate(s, binding) {
				new SimpleTemplateEngine().createTemplate(s).make(binding)
			}
		
			static final passwordValidator = { String password, command ->
				if (command.username && command.username.equals(password)) {
					return 'command.password.error.username'
				}
		
				if (!checkPasswordMinLength(password, command) ||
					!checkPasswordMaxLength(password, command) ||
					!checkPasswordRegex(password, command)) {
					return 'command.password.error.strength'
				}
			}
		
			static boolean checkPasswordMinLength(String password, command) {
				def conf = SpringSecurityUtils.securityConfig
		
				int minLength = conf.ui.password.minLength instanceof Number ? conf.ui.password.minLength : 8
		
				password && password.length() >= minLength
			}
		
			static boolean checkPasswordMaxLength(String password, command) {
				def conf = SpringSecurityUtils.securityConfig
		
				int maxLength = conf.ui.password.maxLength instanceof Number ? conf.ui.password.maxLength : 64
		
				password && password.length() <= maxLength
			}
		
			static boolean checkPasswordRegex(String password, command) {
				def conf = SpringSecurityUtils.securityConfig
		
				String passValidationRegex = conf.ui.password.validationRegex ?:
						'^.*(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$'
		
				password && password.matches(passValidationRegex)
			}
		
			static final password2Validator = { value, command ->
				if (command.password != command.password2) {
					return 'command.password2.error.mismatch'
				}
			}
}

class RegisterCommand extends grails.plugin.springsecurity.ui.RegisterCommand{
	
		String username
		String firstName
		String lastName
		String email
		String country
		String phoneNumber
		String password
		String password2
	
		def grailsApplication
		def countrySelectorService
	
		static constraints = {
			username blank: false, email: true, validator: { value, command ->
				if (value) {
					def User = command.grailsApplication.getDomainClass(SpringSecurityUtils.securityConfig.userLookup.userDomainClassName).clazz
					println "lala3"
					if (User.findByUsername(value)) {
						return 'registerCommand.username.unique'
					}
				}
			}
			email nullable:true
			firstName nullable:true
			lastName nullable:true
			country(nullable: true, validator: {val, obj ->
				def allowedCountryCodes = obj.countrySelectorService.allowedCountryCodes()
				if(val !=null && !allowedCountryCodes.contains(val))
				   return "org.grails.plugins.countrySelector.country.notAllowed"
			 })
			phoneNumber (phoneNumber: true, nullable:true)
			password blank: false, validator: RegisterController.passwordValidator
			password2 validator: RegisterController.password2Validator
		}
}

class ResetPasswordCommand extends grails.plugin.springsecurity.ui.ResetPasswordCommand{
	String username
	String password
	String password2

	static constraints = {
		password blank: false, validator: RegisterController.passwordValidator
		password2 validator: RegisterController.password2Validator
	}
}
