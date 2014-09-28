package content

import java.util.Date;

/** Describes the Guides in 2gocopenhagen. Each Guide must have a title,
 * a description, a picture, contact information,rating,a venue and comments.For more information refer to the mock-ups.
 * Each Guide should belong to a category and to a type of a category.
 *
 *@TODO Consider what other fields we need for the guide
 *@TODO Think if we need some constrains here
 *
 * @author 2go IT.
 *
 */

class Guide extends Content{
	
	/** The location of the Event */
	String address

	/** The constrains of the Event record fields */
	static constraints = {
		address blank: false, nullable:false
	}

}

