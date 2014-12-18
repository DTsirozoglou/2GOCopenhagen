package content

import grails.converters.JSON
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import org.codehaus.groovy.runtime.*;

import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
/**
 * The controller of article includes all the actions that a user can perform when navigating to the Articles and Single Article pages.
 * 
 * @TODO Change the criteria dates from the starting date that is now. This will be decided when we discuss for the Articles creation. 
 * 
 * @author 2go IT.
 *
 */
class ArticleController {

	/** The service that we will use to get the latitude and the longitude of the Article address */
	def geocoderService

	/**
	 * The category that is selected from the index.gsp, if any
	 * The default selected category  is set to -1 in order to display all the Events.
	 * 
	 */
	static selectedCategory = 0



	/** The Date that is currently selected on the calendar */
	static tempdate= new Date()
	static dateSelectedOnCalendar = DateGroovyMethods.format(tempdate, 'MM/dd/yyyy')


	/**
	 * Index Action is called to display the index.gsp with the _ListEvents template or with the _SingleEvent template
	 * Calculates events to be shown in the Events page or in Single Event page (depending from the call)
	 * Also queries to get all relevant information and content for the side bars
	 * 
	 * @return a map with the results of all queries to index.gsp
	 */
	def index() {

		


		if (params.singleArticleCall.equals(null)){//Index action is called to list all the events

			//max articles displayed
			params.max = 3 //Math.min(max ?: 7, 7)
			def cat

			// The list of the Events to be displayed
			def results

	
			if(!params.category.equals(null)){//Index action is called with a selected category
				if (params.category.equals('0')){
					selectedCategory = 0
					def c = Article.createCriteria()
					results = c.list(params) {
						order("dateCreated", "asc")
					}
					
				}
				else{
					selectedCategory = params.category
					dateSelectedOnCalendar = DateGroovyMethods.format(tempdate, 'MM/dd/yyyy')
					cat = Category.get(selectedCategory.toInteger() + 16)
					//Create criteria on Event to query later
					def c = Article.createCriteria()
	
					results = c.list(params) {
						eq("category",cat)
						order("dateCreated", "asc")
					}
				}
			}
			else{
				dateSelectedOnCalendar = DateGroovyMethods.format(tempdate, 'MM/dd/yyyy')
				selectedCategory = 0  // Return to default..(all categories included)
				//Create criteria on Event to query later
				def c = Article.createCriteria()

				results = c.list(params) {
					ge("dateCreated", new Date(dateSelectedOnCalendar) )
					order("dateCreated", "asc")
				}
			}
			def mostPopular = Content.list(max: 11, offset: 0,sort: "views", order: "desc")
			def newPlaces = Guide.list(max: 4, offset: 0,sort: "dateCreated", order: "asc")
			def newEvents = Event.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
			def newOffers = Offer.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
			def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")

			// return calculated results to index.gsp
			[articleInstance:null,dateSelectedOnCalendar:dateSelectedOnCalendar,articleInstanceList: results,articleInstanceCount:results.getTotalCount() ,offersInstanceList: newOffers,
				eventsInstanceList: newEvents,newStuffInstanceList: newStuff,selectedCategory:selectedCategory,guideInstanceList: newPlaces,popularInstanceList:mostPopular]
		}

		else {//Index action is called from a Single Event from the sidebars

						def articleid = params.singleArticleCall.toInteger()
						def articleInstance = Article.get(articleid)
			
						selectedCategory = articleInstance.category.id-16 //The active category of this Event
						
						// We increase the views of this Event by one
						articleInstance.addToViews()

						//max articles displayed
						params.max = 13 //Math.min(max ?: 7, 7)
						
						selectedCategory = articleInstance.category.id-16
						def cat = Category.get(selectedCategory.toInteger() + 16)
						def categoryTitle = cat.categoryName
						def c = Article.createCriteria()
						
										def	results = c.list(params) {
											ne("id", articleInstance.id)
											eq("category",cat)
											order("dateCreated", "asc")
										}
										
						[articleInstance: articleInstance,selectedCategory:selectedCategory,articleInstanceList: results,articleCategoryTitle: categoryTitle]
			//			def mostPopular = Content.list(max: 15, offset: 0,sort: "views", order: "desc")
			//			def newPlaces = Guide.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
			//			def newOffers = Offer.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
			//			def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
			//
			//			[eventInstance:eventInstance,dateSelectedOnCalendar:dateSelectedOnCalendar,offersInstanceList: newOffers,
			//				newStuffInstanceList: newStuff, selectedCategory:selectedCategory,eventLocation:eventLocation,guideInstanceList: newPlaces,popularInstanceList:mostPopular]
		}
	}


	/**
	 * Calculates the content of the Left SideBar of the Events Page
	 * 
	 * @return A map with the results of all queries rendered to _leftSideBarContent.gsp
	 */
	def leftSideBarContent(){

		def mostPopular = Content.list(max: 11, offset: 0,sort: "views", order: "desc")
		def newPlaces = Guide.list(max: 4, offset: 0,sort: "dateCreated", order: "asc")
		def newEvents = Event.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newOffers = Offer.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")

		render(template:'leftSideBarContent', model:  [offersInstanceList: newOffers, eventsInstanceList: newEvents,newStuffInstanceList: newStuff,guideInstanceList: newPlaces,popularInstanceList:mostPopular])
	}

	/**
	 * Calculates the content of the Right SideBar of the Events Page
	 *
	 * @return A map with the results of all queries rendered to _rightSideBarContent.gsp
	 */
	def rightSideBarContent(){

		def newPlaces = Guide.list(max: 4, offset: 0,sort: "dateCreated", order: "asc")
		def newEvents = Event.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newOffers = Offer.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		
		render(template:'rightSideBarContent', model:  [offersInstanceList: newOffers, eventsInstanceList: newEvents,newStuffInstanceList: newStuff,guideInstanceList: newPlaces])
	}


	/**
	 * Calculates the content of the Single Article Page
	 * Use the geocoderService to find the latitude and longitude of the location of the Article
	 * 
	 * @param articleInstance The Article Instance that will be displayed
	 * 
	 * @return A map with the results of all queries rendered to _rightSideBarContent.gsp
	 */
	def showSingleArticle(Article articleInstance){

		// We increase the views of this Event by one
		articleInstance.addToViews()

		selectedCategory = articleInstance.category.id-16

		render(template: 'singleArticle', model:  [articleInstance: articleInstance,selectedCategory:selectedCategory])
	}


	/**
	 * Calculates the content of the Left SideBar of the Single Event Page
	 *
	 * @return A map with the results of all queries rendered to _leftSideBarContentForSingleEvent.gsp
	 */
	def leftSideBarContentForSingleArticle(Article articleInstance){
		
		//max articles displayed
		params.max = 13 //Math.min(max ?: 7, 7)
		
		selectedCategory = articleInstance.category.id-16
		def cat = Category.get(selectedCategory.toInteger() + 16)
		def categoryTitle = cat.categoryName
		def c = Article.createCriteria()
		
						def	results = c.list(params) {
							ne("id", articleInstance.id)
							eq("category",cat)
							order("dateCreated", "asc")
						}
		

		render(template: 'leftSideBarContentForSingleArticle', model:  [articleInstanceList: results,articleCategoryTitle: categoryTitle])
	}

	/**
	 * Calculates the content of the Right SideBar of the Single Event Page
	 *
	 * @return A map with the results of all queries rendered to _rightSideBarContentForSingleEvent.gsp
	 */
//	def rightSideBarContentForSingleEvent(){
//
//		def newPlaces = Guide.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
//		def newOffers = Offer.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
//		def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
//
//		render(template:'rightSideBarContentForSingleEvent', model:  [offersInstanceList: newOffers,newStuffInstanceList: newStuff,guideInstanceList: newPlaces])
//	}

	/**
	 * Used to support pagination. Calculates how many events and which to be displayed in the next p[age section. Called inside _listEvents.gsp. 
	 * @return calculates which and how many events to be displayed per page.
	 */
	def paginate(){
		params.max = 3 //Math.min(max ?: 7, 7)

		switch (selectedCategory.toInteger()) {
			// No Category is Selected
			case 0:
				def c = Article.createCriteria()
				def results = c.list(params) {
					order("dateCreated", "asc")
				}
				render(template: 'listArticles', model:  [articleInstanceList: results,articleInstanceCount:results.getTotalCount(),paginate:0])
				break
			// A category is highlighted
			default:

				def c = Article.createCriteria()
				def cat = Category.get(selectedCategory.toInteger() + 16)
				def results = c.list(params) {
					eq("category",cat)
					ge("dateCreated", new Date(dateSelectedOnCalendar))
					order("dateCreated", "asc")
				}
				render(template: 'listArticles', model:  [articleInstanceList: results,articleInstanceCount:results.getTotalCount(),paginate:0])
				break
		}
	}

	/**
	 * Called from the calendar of the Event page and returns all the events after the date selected and category selected
	 * @return a map with the events after the date selected on calendar
	 */
	def findArticlesByCategory()  {
		
		//max events displayed
		params.max = 3 //Math.min(max ?: 7, 7)
		// The list of the Events to be displayed
		def results

		if (params.category.equals('0')){
			selectedCategory = 0
			def c = Article.createCriteria()
			results = c.list(params) {
				ge("dateCreated", new Date(dateSelectedOnCalendar))
				order("dateCreated", "asc")
			}
			
		}
		else{
			def cat
	
			selectedCategory = params.category
	
			cat = Category.get(selectedCategory.toInteger() + 16)
			//Create criteria on Event to query later
			def c = Article.createCriteria()
				results = c.list(params) {
					eq("category",cat)
					order("dateCreated", "asc")
				}
		}

		render(template: 'listArticles', model:  [articleInstanceList: results,articleInstanceCount:results.getTotalCount()])
	}
}