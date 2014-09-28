package content

import comments.*

import java.util.Date;

/**
 * Content is the superclass of Event, ,Offer, Article, Guide.
 * Contains whatever they share in common, and is actually used to avoid redundant code and also optimise our queries execution time and complexity.
 * 
 *@TODO A Content must belong to a user (and to a Category type?)
 *@TODO Consider what other fields we need for the Content (Images for example byte[] image)
 *@TODO Think if we need some more constrains here
 *@TODO Implement a method for calculating the rating that will be displayed on the user.
 *@TODO Think if by sorting the content here make our queries perform better.
 *
 *@author 2go IT.
 *
 */
class Content {
	
	/** The title of the content */
	String title
	
	/** The description of the content */
	String description
	
	/** The date that the content was created. (Automatically set to new Date()) */
	Date dateCreated
	
	/** The location of the Content */
	String address
	
	/** The rating of the content. It will be calculated through a method */
	double rating	//0 to 5
	
	/** A counter of the views of the content views */
	int views=0
	
	/** The Users comment of this content */
	static hasMany = [comments: Comment]
	
	/** The Category and the category Type that this event belongs */
	static belongsTo = [category :Category,categoryType:CategoryType] // ,user :User,

	/** The constrains of the Content record fields */
	static constraints = { 
		title blank: false, nullable:false
		description blank: false, nullable:false ,maxSize:5000 
		address blank: false, nullable:false
		}

	/** This method is used to test the creation of a Content */
	String toString(){
		"$title,$description "
	}
	
	/** This method is called from the Content (Event-Offer-Article-Guide) Controller to increase its view by one  */
	def addToViews(){
		views++
	}
}
