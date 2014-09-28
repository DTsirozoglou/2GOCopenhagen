package content

/**
 * Implements the types of the  categories of Content.
 *
 * @TODO Think if we need some constrains here
 * 
 * @author 2go IT
 *
 */

class CategoryType {
	
	/** The name of the type of the Event category */
	String typeofCategory
	
	/** The Event Category that this type belongs */
	static belongsTo = [category :Category]
	
	/** This method is used to test the creation of an event type */
	String toString(){
		"$typeofCategory"
	}
}
