package content

/** 
 * Implements the  categories of Content
 *
 * @TODO Think if we need some constrains here
 *
 * @author 2go IT
 *
 */

class Category {
	
	/** The name of the  Category */
	String categoryName
	
	/** An Event Category can have many Events and many Event category types  */
	static hasMany = [contents :Content,types :CategoryType]

	/** This method is used to test the creation of an event Category */
	String toString(){
		"$categoryName"
	}
}
