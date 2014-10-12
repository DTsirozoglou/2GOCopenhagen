package content

import grails.converters.JSON
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat


/**
 * The controller of event includes all the actions that a user can perform when navigating to the Events and Single Event pages.
 * 
 * @TODO Change the criteria dates from the starting date that is now. This will be decided when we discuss for the Events creation. 
 * 
 * @author 2go IT.
 *
 */
class OfferController {

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

	static selectedSuburb = 'All'

	/** The Date that is currently selected on the calendar */
	static dateSelectedOnCalendar = new Date()


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


		if (params.singleOfferCall.equals(null)){//Index action is called to list all the events

			def cat

			// The list of the Events to be displayed
			def results
			if (params.category.equals(null)){
				dateSelectedOnCalendar = new Date()
				selectedCategory = 0 
				selectedSuburb = 'All'
				//Create criteria on Event to query later
				def c = Offer.createCriteria()
				results = c.list(params) {
					ge("startDate", dateSelectedOnCalendar )
					order("startDate", "asc")
				}
				
			}
			else if (params.suburb.equals("All")){//Index action is called with a selected category
				
				
				dateSelectedOnCalendar = new Date()
				selectedSuburb = params.suburb
				
				
				if (params.category.equals('0')){
					def c = Offer.createCriteria()
					selectedCategory = 0
					results = c.list(params) {
					ge("startDate", dateSelectedOnCalendar)
					order("startDate", "asc")
					}
				
				}
				
				else{
					
					selectedCategory = params.category
					
						cat = Category.get(selectedCategory.toInteger() + 11)
						def c = Offer.createCriteria()
						results = c.list(params) {
							eq("category",cat)
							ge("startDate", dateSelectedOnCalendar)
							order("startDate", "asc")
						}
				}
			}
			
			else if(params.category.toInteger() == 0){//Index action is called with a selected category
				selectedCategory = params.category
				selectedSuburb = params.suburb
				dateSelectedOnCalendar = new Date()
				def subur = Locations.findByTitle(selectedSuburb)
				//Create criteria on Event to query later
				def c = Offer.createCriteria()

				results = c.list(params) {
					eq("location",subur)
					ge("startDate", new Date())
					order("startDate", "asc")
				}
			}
			else{
				dateSelectedOnCalendar = new Date()
				selectedCategory = params.category   // Return to default..(all categories included)
				selectedSuburb = params.suburb
				def subur = Locations.findByTitle(selectedSuburb)
				//Create criteria on Event to query later
				def c = Offer.createCriteria()
				cat = Category.get(selectedCategory.toInteger() + 11)
				results = c.list(params) {
					eq("location",subur)
					eq("category",cat)
					ge("startDate", dateSelectedOnCalendar )
					order("startDate", "asc")
				}
			}
			def newPlaces = Guide.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
			def newEvents = Event.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
			def newOffers = Offer.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
			def newArticles = Article.list(max: 4, offset: 0,sort: "dateCreated", order: "asc")
			def mostPopular = Content.list(max: 5, offset: 0,sort: "views", order: "desc")

			// return calculated results to index.gsp
			[offerInstance:null,offerInstanceList: results,offerInstanceCount:results.getTotalCount() ,offersInstanceList: newOffers,
				articlesInstanceList: newArticles,selectedCategory:selectedCategory,selectedSuburb:selectedSuburb,guideInstanceList: newPlaces,popularInstanceList:mostPopular,eventsInstanceList: newEvents]
		}

		else {//Index action is called from a Single Offer from the sidebars

			def offerid = params.singleOfferCall.toInteger()
			def offerInstance = Offer.get(offerid)

			dateSelectedOnCalendar = new Date()

			selectedCategory = offerInstance.category.id-11//The active category of this Event
			selectedSuburb= offerInstance.location.title
			// We increase the views of this Event by one
			offerInstance.addToViews()

			
			//Use the geocoderService to find the latitude and longitude of the location of the Offer
			def offerLocation = geocoderService.geocodeAddress(offerInstance.getAddress())
			
			def mostPopular = Content.list(max: 12, offset: 0,sort: "views", order: "desc")
			def newPlaces = Guide.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
			def newEvents = Event.list(max: 3, offset: 0,sort: "dateCreated", order: "asc")
			def newStuff = Content.list(max: 3, offset: 0,sort: "dateCreated", order: "asc")
			def newArticles = Article.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")

			[offerLocation:offerLocation,selectedSuburb:selectedSuburb,offerInstance:offerInstance,dateSelectedOnCalendar:dateSelectedOnCalendar,eventsInstanceList: newEvents,
				newStuffInstanceList: newStuff, selectedCategory:selectedCategory,guideInstanceList: newPlaces,popularInstanceList:mostPopular,articleInstanceList:newArticles]
		}
	}


	/**
	 * Calculates the content of the Left SideBar of the Offer Page
	 * 
	 * @return A map with the results of all queries rendered to _leftSideBarContent.gsp
	 */
	def leftSideBarContent(){

			def newPlaces = Guide.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
			def newEvents = Event.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
			def newOffers = Offer.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
			def newArticles = Article.list(max: 4, offset: 0,sort: "dateCreated", order: "asc")
			def mostPopular = Content.list(max: 5, offset: 0,sort: "views", order: "desc")

		render(template:'leftSideBarContent', model:  [offersInstanceList: newOffers,articlesInstanceList: newArticles,guideInstanceList: newPlaces,
			popularInstanceList:mostPopular,eventInstanceList: newEvents]		
		)
	}

	/**
	 * Calculates the content of the Right SideBar of the Offer Page
	 *
	 * @return A map with the results of all queries rendered to _rightSideBarContent.gsp
	 */
	def rightSideBarContent(){

		def newPlaces = Guide.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newEvents = Event.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newOffers = Offer.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")

		render(template:'rightSideBarContent', model:  [offersInstanceList: newOffers,eventsInstanceList: newEvents,guideInstanceList: newPlaces])
	}


	/**
	 * Calculates the content of the Single Offer Page
	 * Use the geocoderService to find the latitude and longitude of the location of the Event
	 * 
	 * @param eventInstance The Event Instance that will be displayed
	 * 
	 * @return A map with the results of all queries rendered to _rightSideBarContent.gsp
	 */
	def showSingleOffer(Offer offerInstance){

		// We increase the views of this Event by one
		offerInstance.addToViews()

		selectedCategory = offerInstance.category.id-11
		selectedSuburb = offerInstance.location.title

		//Use the geocoderService to find the latitude and longitude of the location of the Event
		def offerLocation = geocoderService.geocodeAddress(offerInstance.getAddress())

		render(template: 'singleOffer', model:  [offerInstance: offerInstance, offerLocation:offerLocation,selectedCategoryOfSingleOffer:selectedCategory])
	}


	/**
	 * Calculates the content of the Left SideBar of the Single Offer Page
	 *
	 * @return A map with the results of all queries rendered to _leftSideBarContentForSingleOffer.gsp
	 */
	def leftSideBarContentForSingleOffer(){

		def mostPopular = Content.list(max: 12, offset: 0,sort: "views", order: "desc")
		def newPlaces = Guide.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newEvents = Event.list(max: 3, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 3, offset: 0,sort: "dateCreated", order: "asc")
		def newArticles = Article.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")

		render(template: 'leftSideBarContentForSingleOffer', model:  [eventsInstanceList: newEvents,newStuffInstanceList: newStuff,guideInstanceList: newPlaces,articleInstanceList: newArticles,popularInstanceList:mostPopular])
	}

	/**
	 * Calculates the content of the Right SideBar of the Single Offer Page
	 *
	 * @return A map with the results of all queries rendered to _rightSideBarContentForSingleOffer.gsp
	 */
	def rightSideBarContentForSingleOffer(){

		def newPlaces = Guide.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newEvents = Event.list(max: 3, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 3, offset: 0,sort: "dateCreated", order: "asc")
		def newArticles = Article.list (max: 2, offset: 0,sort: "dateCreated", order: "asc")

		render(template:'rightSideBarContentForSingleOffer', model:  [eventsInstanceList: newEvents,newStuffInstanceList: newStuff,guideInstanceList: newPlaces,articleInstanceList: newArticles])
	}

	/**
	 * Used to support pagination. Calculates how many events and which to be displayed in the next p[age section. Called inside _listEvents.gsp. 
	 * @return calculates which and how many events to be displayed per page.
	 */
	def paginate(){
		params.max = 6 //Math.min(max ?: 7, 7)
		if (selectedSuburb.equals("All")){
			switch (selectedCategory.toInteger()) {
				// No Category is Selected
				case 0:
					def c = Offer.createCriteria()
					def results = c.list(params) {
						ge("startDate", new Date())
						order("startDate", "asc")
					}
					render(template: 'listOffers', model:  [offerInstanceList: results,offerInstanceCount:results.getTotalCount()])
					break
				// A category is highlighted
				default:
					def cat = Category.get(selectedCategory.toInteger() + 11)
					def c = Offer.createCriteria()
					def results = c.list(params) {
						eq("category",cat)
						ge("startDate", new Date())
						order("startDate", "asc")
					}
					render(template: 'listOffers', model:  [offerInstanceList: results,offerInstanceCount:results.getTotalCount()])
					break
			}
		}
		else{
			def subur = Locations.findByTitle(selectedSuburb)
			switch (selectedCategory.toInteger()) {
				// No Category is Selected
				case 0:
					def c = Offer.createCriteria()
					def results = c.list(params) {
						eq("location", subur)
						ge("startDate", new Date())
						order("startDate", "asc")
					}
					render(template: 'listOffers', model:  [offerInstanceList: results,offerInstanceCount:results.getTotalCount()])
					break
				// A category is highlighted
				default:
					def cat = Category.get(selectedCategory.toInteger() + 11)
					def c = Offer.createCriteria()
					def results = c.list(params) {
						eq("location", subur)
						eq("category",cat)
						ge("startDate", new Date())
						order("startDate", "asc")
					}
					render(template: 'listOffers', model:  [offerInstanceList: results,offerInstanceCount:results.getTotalCount()])
					break
			}
			
		}
		
	}

	/**
	 * Called from the map of the Offer page and returns all the offers after the current date, with the selected category and suburb
	 * @return a map with the offers with the above criteria
	 */
	def findOffersBySuburnAndCategory(){
		
		params.max = 6 //Math.min(max ?: 7, 7)
		dateSelectedOnCalendar = new Date()
		def cat
		def c
		def results
		selectedSuburb = params.suburb
		if (selectedSuburb.equals('All')){
			
			if (params.category.equals('0')){
				c = Offer.createCriteria()
				selectedCategory = 0
				results = c.list(params) {
				ge("startDate", dateSelectedOnCalendar)
				order("startDate", "asc")
				}
			
			}
			else{
				
				selectedCategory = params.category
				
					cat = Category.get(selectedCategory.toInteger() + 11)
					c = Offer.createCriteria()
					results = c.list(params) {
						eq("category",cat)
						ge("startDate", dateSelectedOnCalendar)
						order("startDate", "asc")
					}
			}
		}
		else{
			def subur = Locations.findByTitle(selectedSuburb)
			c = Offer.createCriteria()
			results
			if (params.category.toInteger() == 0){
					selectedCategory = 0
					results = c.list(params) {
					eq("location", subur )
					ge("startDate", dateSelectedOnCalendar)
					order("startDate", "asc")
				}
				
			}
			else{
				
				selectedCategory = params.category
				 cat = Category.get(selectedCategory.toInteger() + 11)
				
						    results = c.list(params) {
							eq("location",subur)
							eq("category",cat)
							ge("startDate", dateSelectedOnCalendar)
							order("startDate", "asc")
						}
			}
		}
		
		render(template: 'listOffers', model:  [offerInstanceList: results,offerInstanceCount:results.getTotalCount()])
		
	}
	

//	/**
//	 * Called from the categories buttons of the Event page and returns all the events that belong to the corresponding category
//	 *
//	 * @return a map with the events of the corresponding category after the current date.
//	 */
//	def findOffersByCategory()  {
//		//max events displayed
//		params.max = 6 //Math.min(max ?: 7, 7)
//
//
//		dateSelectedOnCalendar = new Date()
//		selectedSuburb = "All"
//		selectedCategory = params.category
//
//		def cat = Category.get(selectedCategory.toInteger() + 11)
//
//		def c = Offer.createCriteria()
//		def results = c.list(params) {
//			eq("category",cat)
//			ge("startDate", dateSelectedOnCalendar)
//			order("startDate", "asc")
//		}
//
//		render(template: 'listOffers', model:  [offerInstanceList: results,offerInstanceCount:results.getTotalCount()])
//	}

}