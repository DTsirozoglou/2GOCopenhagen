package content

import java.util.Date;

/** Describes the Offers in 2gocopenhagen. Each Offer must have a title,
 * a description, a picture,rating, contact information, comments.For more information refer to the mock-ups.
 * Each Offer should belong to a category and to a type of a category.
 *
 *@TODO Consider what other fields we need for the Offer
 *@TODO Think if we need some constrains here
 *
 * @author 2go IT.
 *
 */

class Offer extends Content {
	
	
	/** The starting date of the Event */
	Date startDate
	Locations location

	/** The constrains of the Event record fields */
	static constraints = {
		startDate blank: false, nullable:false
	}

}
