package oauth

import socialMediaSecurity.SpringSecuritySigninService
import socialMediaService.AuthService
import users.*

//TODO:What we must do when the user has already an account and then he tries to login with fb auth?
class AuthController {

    SpringSecuritySigninService springSecuritySigninService

    def onSuccess() {
        AuthService service = resolveService(params.provider)
        User user = service.getUser(session)

        User existingUser = User.findByOauthIdAndOauthProvider(user.oauthId, params.provider)

        if (!existingUser) {
            createUser(user)
            existingUser = user
        }

        springSecuritySigninService.signIn(existingUser)

        redirect(uri: ('/') - request.contextPath)
    }

    private def resolveService(provider) {
        def serviceName = "${ provider as String }AuthService"
        grailsApplication.mainContext.getBean(serviceName)
    }

    private createUser(user) {
        user.save(failOnError: true)

        Role role = Role.find {authority == "ROLE_USER"}
        UserRole.create(user, role, true)
    }
}
