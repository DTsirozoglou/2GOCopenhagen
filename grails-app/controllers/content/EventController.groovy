package content

import grails.converters.JSON
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import org.codehaus.groovy.runtime.*;
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])

/**
 * The controller of event includes all the actions that a user can perform when navigating to the Events and Single Event pages.
 * 
 * @TODO Change the criteria dates from the starting date that is now. This will be decided when we discuss for the Events creation. 
 * 
 * @author 2go IT.
 *
 */
class EventController {

	/** The service that we will use to get the latitude and the longitude of the Event address */
	def geocoderService

	/**
	 * The category that is selected from the index.gsp, if any
	 * The default selected category  is set to -1 in order to display all the Events.
	 * 
	 * NONE(-1), MUSIC(0), ARTS_CALTURE(1), SPORTS(2),
	 * NETWORKING(3), CHARITIES(4), FREE(5)
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

		//max events displayed
		params.max = 6 //Math.min(max ?: 7, 7)


		if (params.singleEventCall.equals(null)){//Index action is called to list all the events

			def cat

			// The list of the Events to be displayed
			def results

			if (!params.date.equals(null)) {//Index action is called with a selected date
				if(params.date.equals('-1')){
					dateSelectedOnCalendar = DateGroovyMethods.format(tempdate, 'MM/dd/yyyy')
		
				}
				else{
				dateSelectedOnCalendar = DateGroovyMethods.format(new Date(params.date), 'MM/dd/yyyy') 
				}
				if  (params.category.equals('0')) {//Index action is called with a selected date and selected category
					selectedCategory = 0
					//Create criteria on Event to query later
					def c = Event.createCriteria()

					results = c.list(params) {
						ge("startDate", new Date(dateSelectedOnCalendar) )
						order("startDate", "asc")
					}
				}
				else{
					selectedCategory = params.category

					cat = Category.get(selectedCategory)
					//Create criteria on Event to query later
					def c = Event.createCriteria()

					results = c.list(params) {
						eq("category",cat)
						ge("startDate", new Date(dateSelectedOnCalendar) )
						order("startDate", "asc")
					}
				}

			}
			else{
				dateSelectedOnCalendar = DateGroovyMethods.format(tempdate, 'MM/dd/yyyy')
				selectedCategory = 0   // Return to default..(all categories included)
				//Create criteria on Event to query later
				def c = Event.createCriteria()

				results = c.list(params) {
					ge("startDate", new Date(dateSelectedOnCalendar) )
					order("startDate", "asc")
				}
			}
			def newPlaces = Guide.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
			def newOffers = Offer.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
			def newArticles = Article.list(max: 4, offset: 0,sort: "dateCreated", order: "asc")
			def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
			def recommended = Event.list(max: 5, offset: 0,sort: "rating", order: "desc")

			// return calculated results to index.gsp
			[eventInstance:null,dateSelectedOnCalendar:dateSelectedOnCalendar,eventInstanceList: results,eventInstanceCount:results.getTotalCount() ,offersInstanceList: newOffers,
				articlesInstanceList: newArticles,newStuffInstanceList: newStuff,selectedCategory:selectedCategory, recommendedInstanceList: recommended,guideInstanceList: newPlaces]
		}

		else {//Index action is called from a Single Event from the sidebars

			def eventid = params.singleEventCall.toInteger()
			def eventInstance = Event.get(eventid)

			dateSelectedOnCalendar =  DateGroovyMethods.format(eventInstance.getStartDate(), 'MM/dd/yyyy')

			selectedCategory = eventInstance.category.id //The active category of this Event

			// We increase the views of this Event by one
			eventInstance.addToViews()

			//Use the geocoderService to find the latitude and longitude of the location of the Event
			def eventLocation = geocoderService.geocodeAddress(eventInstance.getAddress())

			def mostPopular = Content.list(max: 15, offset: 0,sort: "views", order: "desc")
			def newPlaces = Guide.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
			def newOffers = Offer.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
			def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")

			[eventInstance:eventInstance,dateSelectedOnCalendar:dateSelectedOnCalendar,offersInstanceList: newOffers,
				newStuffInstanceList: newStuff, selectedCategory:selectedCategory,eventLocation:eventLocation,guideInstanceList: newPlaces,popularInstanceList:mostPopular]
		}
	}


	/**
	 * Calculates the content of the Left SideBar of the Events Page
	 * 
	 * @return A map with the results of all queries rendered to _leftSideBarContent.gsp
	 */
	def leftSideBarContent(){

		def newPlaces = Guide.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newOffers = Offer.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		def newArticles = Article.list(max: 4, offset: 0,sort: "dateCreated", order: "asc")
		def recommended = Event.list(max: 5, offset: 0,sort: "rating", order: "desc")

		render(template:'leftSideBarContent', model:  [articlesInstanceList: newArticles,recommendedInstanceList: recommended,
			offersInstanceList: newOffers,newStuffInstanceList: newStuff,guideInstanceList: newPlaces])
	}

	/**
	 * Calculates the content of the Right SideBar of the Events Page
	 *
	 * @return A map with the results of all queries rendered to _rightSideBarContent.gsp
	 */
	def rightSideBarContent(){

		def newPlaces = Guide.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newOffers = Offer.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")

		render(template:'rightSideBarContent', model:  [offersInstanceList: newOffers,newStuffInstanceList: newStuff,guideInstanceList: newPlaces])
	}


	/**
	 * Calculates the content of the Single Event Page
	 * Use the geocoderService to find the latitude and longitude of the location of the Event
	 * 
	 * @param eventInstance The Event Instance that will be displayed
	 * 
	 * @return A map with the results of all queries rendered to _rightSideBarContent.gsp
	 */
	def showSingleEvent(Event eventInstance){

		// We increase the views of this Event by one
		eventInstance.addToViews()

		selectedCategory = eventInstance.category.id
		dateSelectedOnCalendar =  DateGroovyMethods.format(eventInstance.getStartDate(), 'MM/dd/yyyy')

		//Use the geocoderService to find the latitude and longitude of the location of the Event
		def eventLocation = geocoderService.geocodeAddress(eventInstance.getAddress())

		render(template: 'singleEvent', model:  [eventInstance: eventInstance, eventLocation:eventLocation,selectedCategoryOfSingleEvent:selectedCategory,dateSelectedOnCalendar:dateSelectedOnCalendar])
	}


	/**
	 * Calculates the content of the Left SideBar of the Single Event Page
	 *
	 * @return A map with the results of all queries rendered to _leftSideBarContentForSingleEvent.gsp
	 */
	def leftSideBarContentForSingleEvent(){

		def mostPopular = Content.list(max: 15, offset: 0,sort: "views", order: "desc")
		def newPlaces = Guide.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		def newOffers = Offer.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")

		render(template: 'leftSideBarContentForSingleEvent', model:  [offersInstanceList: newOffers,newStuffInstanceList: newStuff,guideInstanceList: newPlaces,popularInstanceList:mostPopular])
	}

	/**
	 * Calculates the content of the Right SideBar of the Single Event Page
	 *
	 * @return A map with the results of all queries rendered to _rightSideBarContentForSingleEvent.gsp
	 */
	def rightSideBarContentForSingleEvent(){

		def newPlaces = Guide.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		def newOffers = Offer.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")

		render(template:'rightSideBarContentForSingleEvent', model:  [offersInstanceList: newOffers,newStuffInstanceList: newStuff,guideInstanceList: newPlaces])
	}

	/**
	 * Used to support pagination. Calculates how many events and which to be displayed in the next p[age section. Called inside _listEvents.gsp. 
	 * @return calculates which and how many events to be displayed per page.
	 */
	def paginate(){
		params.max = 6 //Math.min(max ?: 7, 7)

		switch (selectedCategory.toInteger()) {
			// No Category is Selected
			case 0:
				def c = Event.createCriteria()
				def results = c.list(params) {
					ge("startDate", new Date(dateSelectedOnCalendar))
					order("startDate", "asc")
				}
				render(template: 'listEvents', model:  [eventInstanceList: results,eventInstanceCount:results.getTotalCount()])
				break
			// A category is highlighted
			default:

				def c = Event.createCriteria()
				def cat = Category.get(selectedCategory)
				def results = c.list(params) {
					eq("category",cat)
					ge("startDate", new Date(dateSelectedOnCalendar))
					order("startDate", "asc")
				}
				render(template: 'listEvents', model:  [eventInstanceList: results,eventInstanceCount:results.getTotalCount()])
				break
		}
	}

	/**
	 * Called from the calendar of the Event page and returns all the events after the date selected and category selected
	 * @return a map with the events after the date selected on calendar
	 */
	def findEventsByCategoryAndSelectedDate()  {

		//max events displayed
		params.max = 6 //Math.min(max ?: 7, 7)

		def cat

		// The list of the Events to be displayed
		def results

		if(params.date.equals('0')){
			dateSelectedOnCalendar = DateGroovyMethods.format(tempdate, 'MM/dd/yyyy')
		}else {
			dateSelectedOnCalendar = DateGroovyMethods.format(new Date(params.date), 'MM/dd/yyyy') 
		}

		
		if  (params.category.equals('0')) {//Index action is called with a selected date and selected category
			selectedCategory = 0
			//Create criteria on Event to query later
			def c = Event.createCriteria()

			results = c.list(params) {
				ge("startDate", new Date(dateSelectedOnCalendar) )
				order("startDate", "asc")
			}
		}
		else{
			selectedCategory = params.category

			cat = Category.get(selectedCategory)
			//Create criteria on Event to query later
			def c = Event.createCriteria()

			results = c.list(params) {
				eq("category",cat)
				ge("startDate", new Date(dateSelectedOnCalendar) )
				order("startDate", "asc")
			}
		}

		render(template: 'listEvents', model:  [eventInstanceList: results,eventInstanceCount:results.getTotalCount()])
	}

//	/**
//	 * Called from the categories buttons of the Event page and returns all the events that belong to the corresponding category
//	 *
//	 * @return a map with the events of the corresponding category after the current date.
//	 */
//	def findEventsByCategory()  {
//		//max events displayed
//		params.max = 6 //Math.min(max ?: 7, 7)
//
//
//		dateSelectedOnCalendar = new Date().format('dd/mm/yy')
//		selectedCategory = params.category
//
//		def cat = Category.get(selectedCategory)
//
//		def c = Event.createCriteria()
//		def results = c.list(params) {
//			eq("category",cat)
//			ge("startDate", dateSelectedOnCalendar)
//			order("startDate", "asc")
//		}
//
//		render(template: 'listEvents', model:  [eventInstanceList: results,eventInstanceCount:results.getTotalCount()])
//	}

}