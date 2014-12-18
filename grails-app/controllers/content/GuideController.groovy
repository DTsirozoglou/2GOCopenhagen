package content
	
import grails.converters.JSON
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat

import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
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
	 * ALL(0), EAT OUT(1), SHOPS(2), NIGHTLIFE(3),
	 * SEE(4), ACCOMODATION(5)
	 */
	static selectedCategory = 0

	static selectedSuburb = 'All'

	/** The Date that is currently selected on the calendar */
	static dateSelectedOnCalendar = new Date()

	
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
	
			if (params.singleGuideCall.equals(null)){//Index action is called to list all the events
				
					def cat
		
					// The list of the Events to be displayed
					def results
					if (params.category.equals(null)){
						dateSelectedOnCalendar = new Date()
						selectedCategory = 0
						selectedSuburb = 'All'
						//Create criteria on Event to query later
						def c = Guide.createCriteria()
						results = c.list(params) {
							ge("startDate", dateSelectedOnCalendar )
							order("startDate", "asc")
						}
					}
					else if (params.suburb.equals("All")){//Index action is called with a selected category
						
						dateSelectedOnCalendar = new Date()
						selectedSuburb = params.suburb
						
						if (params.category.equals('0')){
							def c = Guide.createCriteria()
							selectedCategory = 0
							results = c.list(params) {
							ge("startDate", dateSelectedOnCalendar)
							order("startDate", "asc")
							}
						
						}
						else{
							
							selectedCategory = params.category
							
								cat = Category.get(selectedCategory.toInteger() + 6)
								def c = Guide.createCriteria()
								results = c.list(params) {
									eq("category",cat)
									ge("startDate", dateSelectedOnCalendar)
									order("startDate", "asc")
								}
						}
					
					}
					else if(params.category != null && params.category.toInteger() == 0){//Index action is called with a selected category
						selectedCategory = params.category
						selectedSuburb = params.suburb
						dateSelectedOnCalendar = new Date()
						def subur = Locations.findByTitle(selectedSuburb)
						//Create criteria on Event to query later
						def c = Guide.createCriteria()
		
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
						def c = Guide.createCriteria()
						cat = Category.get(selectedCategory.toInteger() + 6)
						results = c.list(params) {
							eq("location",subur)
							eq("category",cat)
							ge("startDate", dateSelectedOnCalendar )
							order("startDate", "asc")
						}
					}
					
					def newEvents = Event.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
					def newOffers = Offer.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
					def newArticles = Article.list(max: 4, offset: 0,sort: "dateCreated", order: "asc")
					def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
					def mostPopular = Content.list(max: 5, offset: 0,sort: "views", order: "desc")
						
					// return calculated results to index.gsp
					[guideInstance:null,selectedCategory:selectedCategory,selectedSuburb:selectedSuburb,
						guideInstanceList: results,
						guideInstanceCount:results.getTotalCount(),
						offersInstanceList: newOffers,
						articlesInstanceList: newArticles,
						newStuffInstanceList: newStuff,
						popularInstanceList:mostPopular,
						eventsInstanceList: newEvents]
			}
	
			else {//Index action is called from a Single Offer from the sidebars
				
							def guideid = params.singleGuideCall.toInteger()
							def guideInstance = Guide.get(guideid)
				
							dateSelectedOnCalendar = new Date()
				
							selectedCategory = guideInstance.category.id-6//The active category of this Event
							selectedSuburb= guideInstance.location.title
							// We increase the views of this Event by one
							guideInstance.addToViews()
							
							//Use the geocoderService to find the latitude and longitude of the location of the Offer
							def guideLocation = geocoderService.geocodeAddress(guideInstance.getAddress())
							
							def mostPopular = Content.list(max: 15, offset: 0,sort: "views", order: "desc")
							def newPlaces = Guide.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
							def newOffers = Offer.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
							def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
							
							[guideInstance:guideInstance,guideLocation:guideLocation,selectedSuburb:selectedSuburb,
								offersInstanceList: newOffers,selectedCategory:selectedCategory,
								newStuffInstanceList: newStuff,	guideLocation:guideLocation,
								guideInstanceList: newPlaces,popularInstanceList:mostPopular]
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
		def newArticles = Article.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")
		def mostPopular = Content.list(max: 5, offset: 0,sort: "views", order: "desc")
		

		
		render(template:'leftSideBarContent', 
				model: [offersInstanceList: newOffers,
						articlesInstanceList: newArticles,
						newStuffInstanceList: newStuff,
						popularInstanceList:mostPopular,
						eventsInstanceList: newEvents])
	}
	
	/**
	 * Calculates the content of the Right SideBar of the Guides Page
	 *
	 * @return A map with the results of all queries rendered to _rightSideBarContent.gsp
	 */
	def rightSideBarContent(){
		
		def newEvents = Event.list(max: 2, offset: 0,sort: "dateCreated", order: "asc")
		def newOffers = Offer.list(max: 3, offset: 0,sort: "dateCreated", order: "asc")
		def newStuff = Content.list(max: 5, offset: 0,sort: "dateCreated", order: "asc")		

		
		render(template:'rightSideBarContent', 
				model: [offersInstanceList: newOffers,
						newStuffInstanceList: newStuff,
						eventsInstanceList: newEvents])
		
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
		// We increase the views of this Event by one
		guideInstance.addToViews()

		selectedCategory = guideInstance.category.id-6
		selectedSuburb = guideInstance.location.title

		//Use the geocoderService to find the latitude and longitude of the location of the Event
		def guideLocation = geocoderService.geocodeAddress(guideInstance.getAddress())

		render(template: 'singleGuide', model:  [guideInstance: guideInstance, guideLocation:guideLocation,selectedCategoryOfSingleGuide:selectedCategory])
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
		if (selectedSuburb.equals("All")){
			switch (selectedCategory.toInteger()) {
				// No Category is Selected
				case 0:
					def c = Guide.createCriteria()
					def results = c.list(params) {
						ge("startDate", new Date())
						order("startDate", "asc")
					}
					render(template: 'listGuides', model:  [guideInstanceList: results,guideInstanceCount:results.getTotalCount(),paginate:0])
					break
				// A category is highlighted
				default:
					def cat = Category.get(selectedCategory.toInteger() + 6)
					def c = Guide.createCriteria()
					def results = c.list(params) {
						eq("category",cat)
						ge("startDate", new Date())
						order("startDate", "asc")
					}
					render(template: 'listGuides', model:  [guideInstanceList: results,guideInstanceCount:results.getTotalCount(),paginate:0])
					break
			}
		}
		else{
			
			def subur = Locations.findByTitle(selectedSuburb)
			switch (selectedCategory.toInteger()) {
				// No Category is Selected
				case 0:
					def c = Guide.createCriteria()
					def results = c.list(params) {
						eq("location", subur)
						ge("startDate", new Date())
						order("startDate", "asc")
					}
					render(template: 'listGuides', model:  [guideInstanceList: results,guideInstanceCount:results.getTotalCount(),paginate:0])
					break
				// A category is highlighted
				default:
					def cat = Category.get(selectedCategory.toInteger() + 6)
					def c = Guide.createCriteria()
					def results = c.list(params) {
						eq("location", subur)
						eq("category",cat)
						ge("startDate", new Date())
						order("startDate", "asc")
					}
					render(template: 'listGuides', model:  [guideInstanceList: results,guideInstanceCount:results.getTotalCount(),paginate:0])
					break
			}
			
		}
	}

	def findGuidesBySuburnAndCategory(){
		
		params.max = 6 //Math.min(max ?: 7, 7)
		dateSelectedOnCalendar = new Date()
		def cat
		def c
		def results
		selectedSuburb = params.suburb
		if (selectedSuburb.equals('All')){
			
			if (params.category.equals('0')){
				c = Guide.createCriteria()
				selectedCategory = 0
				results = c.list(params) {
				ge("startDate", dateSelectedOnCalendar)
				order("startDate", "asc")
				}
			}
			else{
				selectedCategory = params.category
				
				cat = Category.get(selectedCategory.toInteger() + 6)
				c = Guide.createCriteria()
				
				results = c.list(params) {
					eq("category",cat)
					ge("startDate", dateSelectedOnCalendar)
					order("startDate", "asc")
				}
			}
		}
		else{
			def subur = Locations.findByTitle(selectedSuburb)
			c = Guide.createCriteria()
			results
			if (params.category != null && params.category.toInteger() == 0){
					selectedCategory = 0
					results = c.list(params) {
					eq("location", subur )
					ge("startDate", dateSelectedOnCalendar)
					order("startDate", "asc")
				}
			}
			else{
				selectedCategory = params.category
				 cat = Category.get(selectedCategory.toInteger() + 6)
				
							results = c.list(params) {
							eq("location",subur)
							eq("category",cat)
							ge("startDate", dateSelectedOnCalendar)
							order("startDate", "asc")
						}
			}
		}
		render(template: 'listGuides', model:  [guideInstanceList: results,guideInstanceCount:results.getTotalCount()])
	}
}