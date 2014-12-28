package users

class User {

	transient springSecurityService

	def phoneNumberService
	def countrySelectorService
	
	String country
	
	String password
	String username
	String email
	
	String firstName
	String lastName
	String phoneNumber
	
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
		country(nullable: true, validator: {val, obj ->
			def allowedCountryCodes = obj.countrySelectorService.allowedCountryCodes()
			if(val !=null && !allowedCountryCodes.contains(val))
			   return "org.grails.plugins.countrySelector.country.notAllowed"
		 })
		email blank: false, unique: true, email: true
		phoneNumber (phoneNumber: true, nullable:true)
		password blank: false
		avatarUrl nullable: true
		firstName nullable: true
		lastName nullable: true
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

	void setPhoneNumber(String val) {
		phoneNumber = phoneNumberService?.format(val) ?: val
	}
	
	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}

}
