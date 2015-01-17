package users

import content.Locations;

class Profile {

	def phoneNumberService
	def countrySelectorService
	
	String country
	String firstName
	String lastName
	String phoneNumber
	Locations location
	
	static belongsTo = [user:User]
    static constraints = {
		country(nullable: true, validator: {val, obj ->
			def allowedCountryCodes = obj.countrySelectorService.allowedCountryCodes()
			if(val !=null && !allowedCountryCodes.contains(val))
			   return "org.grails.plugins.countrySelector.country.notAllowed"
		 })
		phoneNumber (phoneNumber: true, nullable:true)
		location nullable:true
		firstName nullable: true
		lastName nullable: true
    }
	
	void setPhoneNumber(String val) {
		phoneNumber = phoneNumberService?.format(val) ?: val
	}
}
