package content

import java.util.Date;

/** Describes the events in 2gocopenhagen. Each event must have a title,
 * a description, a picture, contact information, price, coordinates for Google Maps, comments. Furthermore the users should
 * be able to add the event to their calendar. For more information refer to the mock-ups.
 * Each event should belong to a category.
 *
 *
 * @TODO Apart from the things listed in the class consider implementing tags
 * @TODO When the user inserts the adress of the event we should create and keep in our database the lat and lng for the location 
 * @TODO Consider what other fields we need for the Event (end date)
 * @TODO Think if we need some constrains here
 * @TODO Think how to implement repeatable events 
 * 
 * @author 2go IT.
 *
 */

class Event extends Content{

	/** The starting date of the Event */
	Date startDate

	/** The constrains of the Event record fields */
	static constraints = {
		startDate blank: false, nullable:false
	}
}