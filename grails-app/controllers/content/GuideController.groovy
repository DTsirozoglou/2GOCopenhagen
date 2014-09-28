package content
	
import grails.converters.JSON
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat


/**
 * The controller of Guide includes all the actions that a user can perform when navigating to the Guides and Single Guide pages.
 * 
 * @TODO 
 * 
 * @author 2go IT.
 *
 */
class GuideController {
	
	/** The service that we will use to get the latitude and the longitude of the Guide address */
	def geocoderService 

	/**
	 * The category that is selected from the index.gsp, if any
	 * The default selected category  is set to -1 in order to display all the Events.
	 * 
	 * NONE(-1), EAT OUT(0), SHOPS(1), NIGHTLIFE(2),
	 * SEE(3), ACCOMODATION(4)
	 */
	static selectedCategory = -1

	/** The Date that is currently selected on the calendar */
	static dateSelectedOnCalendar

	/**
	 * Index Action is called to display the index.gsp with the _ListGuides template or with the _SingleGuide template
	 * Calculates guides to be shown in the Guides -  Single Guide page (depending from the call)
	 * Also queries to get all relevant information and content for the side bars
	 * 
	 * @return a map with the results of all queries to index.gsp
	 */
	def index() {

			//max events displayed
			params.max = 6 //Math.min(max ?: 7, 7)
	
			
			if (params.singleEventCall.equals(null)){//Index action is called to list all the events
				
				dateSelectedOnCalendar = new Date()
				selectedCategory = -1  // Return to default..(all categories included)
				
				def newEvents = Event.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
				def newOffers = Offer.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
				def newArticles = Article.list(max: 4, offset: 0,sort: "dateCreated", order: "asc")
				def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
				def mostPopular = Content.list(max: 5, offset: 0,sort: "views", order: "desc")
		
				//Create criteria on Guide to query later
				def c = Guide.createCriteria()
				
				// The list of the Guides to be displayed
				//We need to fix that...and decide on which criteria we should work on
				def results = c.list(params) {}
				
				// return calculated results to index.gsp
				[eventInstance:null,
					dateSelectedOnCalendar:dateSelectedOnCalendar,
					guideInstanceList: results,
					guideInstanceCount:results.getTotalCount(),
					offersInstanceList: newOffers,
					articlesInstanceList: newArticles,
					newStuffInstanceList: newStuff,
					popularInstanceList:mostPopular,
					newEventsInstanceList: newEvents]
			}
	
			else {//Index action is called from a Single Event from the sidebars
				
				def eventid = params.singleEventCall.toInteger()
				def eventInstance = Event.get(eventid)
				
				dateSelectedOnCalendar = eventInstance.getStartDate()
				
				selectedCategory = eventInstance.category.id //The active category of this Event
				
				// We increase the views of this Event by one
				eventInstance.addToViews()
				
				//Use the geocoderService to find the latitude and longitude of the location of the Event
				def eventLocation = geocoderService.geocodeAddress(eventInstance.getAddress())
				
				def mostPopular = Content.list(max: 15, offset: 0,sort: "views", order: "desc")
				def newPlaces = Guide.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
				def newOffers = Offer.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
				def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
				
				[eventInstance:eventInstance,
					dateSelectedOnCalendar:dateSelectedOnCalendar,
					offersInstanceList: newOffers,
					newStuffInstanceList: newStuff, 
					selectedCategoryOfSingleEvent:selectedCategory,
					eventLocation:eventLocation,
					guideInstanceList: newPlaces,
					popularInstanceList:mostPopular]
			}
	}
	
	/**
	 * Calculates the content of the Left SideBar of the Guides Page
	 * 
	 * @return A map with the results of all queries rendered to _leftSideBarContent.gsp
	 */
	def leftSideBarContent(){
		
		def newEvents = Event.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newOffers = Offer.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newArticles = Article.list(max: 4, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		def mostPopular = Content.list(max: 5, offset: 0,sort: "views", order: "desc")
		

		
		render(template:'leftSideBarContent', 
				model: [offersInstanceList: newOffers,
						articlesInstanceList: newArticles,
						newStuffInstanceList: newStuff,
						popularInstanceList:mostPopular,
						newEventsInstanceList: newEvents])
	}
	

	
	/**
	 * Calculates the content of the Right SideBar of the Guides Page
	 *
	 * @return A map with the results of all queries rendered to _rightSideBarContent.gsp
	 */
	def rightSideBarContent(){
		
		def newEvents = Event.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newOffers = Offer.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")		

		
		render(template:'rightSideBarContent', 
				model: [offersInstanceList: newOffers,
						newStuffInstanceList: newStuff,
						newEventsInstanceList: newEvents])
		
		render(template:'rightSideBarContent', model:  [offersInstanceList: newOffers,newStuffInstanceList: newStuff,guideInstanceList: newPlaces])
	}


	/**
	 * Calculates the content of the Single Guide Page
	 * Use the geocoderService to find the latitude and longitude of the location of the place descibed in Guide
	 * 
	 * @param guideInstance The Guide Instance that will be displayed
	 * 
	 * @return A map with the results of all queries rendered to _rightSideBarContent.gsp
	 */
	def showSingleGuide(Guide guideInstance){
		
		selectedCategory = guideInstance.category.id
		
		//Use the geocoderService to find the latitude and longitude of the location of the Event
		def guideLocation = geocoderService.geocodeAddress(guideInstance.getAddress())
		
		render(template: 'singleGuide', model:  [guideInstance: guideInstance,
												guideLocation: guideLocation,
												selectedCategoryOfSingleGuide:selectedCategory])
	}
	
	
	/**
	 * Calculates the content of the Left SideBar of the Single Guide Page
	 *
	 * @return A map with the results of all queries rendered to _leftSideBarContentForSingleGuide.gsp
	 */
	def leftSideBarContentForSingleGuide(){
		
		def mostPopular = Content.list(max: 15, offset: 0,sort: "views", order: "desc")
		def newPlaces = Guide.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		def newOffers = Offer.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		
		render(template: 'leftSideBarContentForSingleGuide', 
			model:  [offersInstanceList: newOffers,
					newStuffInstanceList: newStuff,
					guideInstanceList: newPlaces,
					popularInstanceList:mostPopular])
	}
	
	/**
	 * Calculates the content of the Right SideBar of the Single Guide Page
	 *
	 * @return A map with the results of all queries rendered to _rightSideBarContentForSingleGuide.gsp
	 */
	def rightSideBarContentForSingleGuide(){
		
		def newPlaces = Guide.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		def newOffers = Offer.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		
		render(template:'rightSideBarContentForSingleGuide',
			 	model:  [offersInstanceList: newOffers,
						newStuffInstanceList: newStuff,
						guideInstanceList: newPlaces])
	}

	/**
	 * Used to support pagination. Calculates how many guides and which to be displayed in the next page section. Called inside _.gsp. 
	 * @return calculates which and how many guides to be displayed per page.
	 */
	def paginate(){
		params.max = 6 //Math.min(max ?: 7, 7)

		switch (selectedCategory) {
			// No Category is Selected
			case -1:
				def c = Guide.createCriteria()
				def results = c.list(params) {}
				render(template: 'listGuides', 
					model:  [guideInstanceList: results,
							guideInstanceCount:results.getTotalCount()])
				break
			// A category is highlighted
			default:

				def c = Guide.createCriteria()
				def results = c.list(params) {
					eq("category",selectedCategory)
				}
				render(template: 'listGuides', 
					model:  [guideInstanceList: results,
							guideInstanceCount:results.getTotalCount()])
				break
		}
	}

	/**
	 * Called from the calendar of the Event page and returns all the events after the date selected
	 * @return a map with the events after the date selected on calendar
	 */
	def findEventsByDate()  {
		
		params.max = 6 //Math.min(max ?: 7, 7)
		dateSelectedOnCalendar = new Date(params.date)
		switch (selectedCategory) {
			// No Category is Selected
			case -1:
				def c = Event.createCriteria()
				def results = c.list(params) {
					ge("startDate", dateSelectedOnCalendar)
					order("startDate", "asc")
				}
				render(template: 'listGuides', model:  [guideInstanceList: results,eventInstanceCount:results.getTotalCount()])
				break
			// A category is highlighted
			default:
				def cat = Category.get(selectedCategory)

				def c = Event.createCriteria()
				def results = c.list(params) {
					eq("category",cat)
					ge("startDate", dateSelectedOnCalendar)
					order("startDate", "asc")
				}
				render(template: 'listGuides', model:  [guideInstanceList: results,eventInstanceCount:results.getTotalCount()])
				break
		}
	}

	/**
	 * Called from the categories buttons of the Event page and returns all the events that belong to the corresponding category
	 *
	 * @return a map with the events of the corresponding category after the current date.
	 */
	def findGuidesByCategory()  {
		
		dateSelectedOnCalendar = new Date()
		selectedCategory = params.categ
		
		def cat = Category.get(selectedCategory)
		
		def c = Guide.createCriteria()
		def results = c.list(params) {
			eq("category",cat)
		}
		
		render(template: 'listGuides', 
			model:  [guideInstanceList: results,
					guideInstanceCount:results.getTotalCount()])
	}
}