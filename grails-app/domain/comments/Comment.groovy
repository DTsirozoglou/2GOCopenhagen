package comments

import java.util.Date;

/** 
 * Implements the comments for the website. A comment record must have an automatic date created, 
 * the text and the user that this comment belongs to.
 *
 * @TODO Consider adding comment rating and options for upvote and downvote a-la reddit.
 * @TODO Think if we need some constrains here
 * @TODO A Comment must belong to a user: static belongsTo = [user :User]
 *
 * @author 2go IT
 *
 */

class Comment {
	
	/** The date that the comment was created (it will be automatically inserted) */
	Date dateCreated

	/** The text of the comment */
	String commentText

    /** The constrains of the comment record fields */
    static constraints = {
		commentText blank: false
		dateCreated blank: false
	}
	
	/** This method is used to test the creation of a comment*/
	String toString(){
		"$commentText"
	}
}
