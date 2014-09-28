package services

import grails.transaction.Transactional

/**
 * This Service is Created in order to provide us the latitude and the longitude of the adress passes as parameter 
 * 
 * @author 2go IT
 *
 */
@Transactional
class GeocoderService {

	boolean transactional = false
	//http://maps.googleapis.com/maps/api/geocode/json?address=your_address&sensor=true_or_false
	
	/**
	 * This function takes an Address and performs a request to get its latitude and longitude  
	 * 
	 * @param address The address that we want to know its latitude and longitude
	 * 
	 * @return a result with the latitude and the longitude of the requested address.
	 */
	def geocodeAddress(String address) {
		
		def base = "http://maps.googleapis.com/maps/api/geocode/xml?"
		def qs = []
		qs << "address=" + URLEncoder.encode(address)
		qs << "sensor=true_or_false"

		def url = new URL(base + qs.join("&"))
      	def connection = url.openConnection()

		def resultPosition = [:]
		if(connection.responseCode == 200){
			def xml = connection.content.text
			def geocodeResponse = new XmlSlurper().parseText(xml)
			
			resultPosition.lat = geocodeResponse.result.geometry.location.lat as String
			resultPosition.lng = geocodeResponse.result.geometry.location.lng as String
		}
		
		else{
			log.error("GeocoderService.geocodeAirport FAILED")
			log.error(url)
			log.error(connection.responseCode)
			log.error(connection.responseMessage)
		}      
		return resultPosition
	}
}
