package users

import content.Locations;

class User {

	transient springSecurityService
	Profile profile

	String password
	String username
	String email
	
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	String oauthId
	String oauthProvider
	String avatarUrl

	static transients = ['springSecurityService']
	static hasMany = [oAuthIDs: OAuthID]
	
	static constraints = {
		username blank: false, unique: true, email: true
		email blank: false, unique: true, email: true
		password blank: false
		profile unique: true,nullable: true
		avatarUrl nullable: true
		oauthProvider nullable: true
		oauthId nullable: true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
	
	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}
	
	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}

}
