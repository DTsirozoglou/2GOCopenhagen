package socialMediaService

import users.User
import org.scribe.model.Token
import grails.converters.JSON
import users.Role
import users.UserRole

class FacebookAuthService extends AuthService {

    static userInfoUrl = 'https://graph.facebook.com/me'

    static provider = 'facebook'

    User getUser(session) {
        Token token = getToken(session, provider)

        def jsonUser = getParsedResponse(token)

        return buildUserFromResponse(jsonUser)
    }

    private getParsedResponse(Token token) {
        def response = oauthService.getFacebookResource(token, userInfoUrl)
        return JSON.parse(response.body)
    }

    private buildUserFromResponse(def jsonUser) {
		
        User user = new User()
        user.oauthId = jsonUser.id
        user.avatarUrl = "http://graph.facebook.com/${jsonUser.id}/picture"
        user.username = jsonUser.email //jsonUser.username ?:"${jsonUser.first_name}.${jsonUser.last_name}"
		user.email = jsonUser.email
		user.password = 'skata'
        user.enabled = true
        user.oauthProvider = provider

        return user
    }
}
