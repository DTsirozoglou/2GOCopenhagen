package socialMediaService

import uk.co.desirableobjects.oauth.scribe.OauthService
import org.scribe.model.Token
import users.User
import socialMediaSecurity.SpringSecuritySigninService

abstract class AuthService {

    OauthService oauthService

    Token getToken(session, provider) {
        String sessionKey = oauthService.findSessionKeyForAccessToken(provider)
        Token token = session[sessionKey]

        return token
    }

    abstract User getUser(session)
}
