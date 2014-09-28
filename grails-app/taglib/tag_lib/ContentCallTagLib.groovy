package tag_lib

import content.*

/**
 * This grails tag is created in order figure the class of the Content and output in the html the corresponding link
 * 
 * @author 2go IT
 *
 */
class ContentCallTagLib {
	
	/**
	 * Creates a Link to the index action of the Content class. Has two attributes. The content class that is the class of the provided Content
	 * and the Id of the Content.Depending on the class , outputs a link for the index action of the class's controller. 
	 */
	def contentCall = { attrs ->

		def contentclass = attrs.contentClass
		def contnetId = attrs.contentID
		
		if (contentclass.instanceOf(Event))
			out <<"<a href='${createLink(controller: 'Event', action: 'index')}?singleEventCall=${contnetId}'> <img src='${resource(dir: 'images', file: 'bieber.jpg')}'/> </a>"
			
		else if (contentclass.instanceOf(Offer))
			out <<"<a href='${createLink(controller: 'Offer', action: 'index')}?singleOfferCall=${contnetId}'> <img src='${resource(dir: 'images', file: 'bieber.jpg')}'/> </a>"
		
		else if (contentclass.instanceOf(Article))
			out <<"<a href='${createLink(controller: 'Article', action: 'index')}?singleArticleCall=${contnetId}'> <img src='${resource(dir: 'images', file: 'bieber.jpg')}'/> </a>"
		
		else
			out <<"<a href='${createLink(controller: 'Guide', action: 'index')}?singleGuideCall=${contnetId}'> <img src='${resource(dir: 'images', file: 'bieber.jpg')}'/> </a>"
		
		}
}
