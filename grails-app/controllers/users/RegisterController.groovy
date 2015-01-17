package users

import content.Locations;
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
		[command: new RegisterCommand(copy),location:Locations.list()]
	}
	
	def register(RegisterCommand command) {

		if (command.hasErrors()) {
			render view: 'index', model: [command: command]
			return
		}

		String salt = saltSource instanceof NullSaltSource ? null : command.username
		def user = lookupUserClass().newInstance(username: command.username,email: command.username,accountLocked: true, enabled: true)

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
	
}

class RegisterCommand extends grails.plugin.springsecurity.ui.RegisterCommand{
	
		String username
		String email
		String password
		String password2
		
		Locations location
	
		def grailsApplication
		def countrySelectorService
	
		static constraints = {
			username blank: false, email: true, validator: { value, command ->
				if (value) {
					def User = command.grailsApplication.getDomainClass(SpringSecurityUtils.securityConfig.userLookup.userDomainClassName).clazz
					if (User.findByUsername(value)) {
						return 'registerCommand.username.unique'
					}
				}
			}
			email nullable:true
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
