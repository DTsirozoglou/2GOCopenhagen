package socialMediaSecurity

import grails.plugin.springsecurity.userdetails.GormUserDetailsService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import users.User

class SpringSecuritySigninService extends GormUserDetailsService {

    void signIn(User user) {
        def authorities = loadAuthorities(user, user.username, true)
        def userDetails = createUserDetails(user, authorities)
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities))
		
		println  userDetails.authorities 
		println  userDetails
    }
}
