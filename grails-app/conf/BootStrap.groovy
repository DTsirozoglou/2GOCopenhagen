import content.*
import grails.buildtestdata.mixin.Build
import users.Requestmap
import users.Role
import users.User
import users.UserRole

class BootStrap {

	def init = { servletContext -> 
		// Create roles and users for testing user system:
		def adminRole = new Role(authority: 'ROLE_ADMIN').save(failOnError: true, flush:true)
		
		def userRole = new Role(authority: 'ROLE_USER').save(failOnError: true, flush:true)
		
		def testUser = new User(username: 'bob', password: 'password', email: "mail@mail.com").save(failOnError: true, flush:true)
		//testUser.encodePassword()
		UserRole.create(testUser, adminRole, true)
		
		assert User.count() == 1
		assert Role.count() == 2
		assert UserRole.count() == 1
		new Requestmap(url: '/*', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
		new Requestmap(url: '/2GOcopenhagen/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
		new Requestmap(url: '/login/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
		new Requestmap(url: '/event/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
		new Requestmap(url: '/offer/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
		new Requestmap(url: '/article/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
		new Requestmap(url: '/guide/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
		new Requestmap(url: '/profile/**', configAttribute: 'ROLE_USER').save()
		//new Requestmap(url: '/js/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
		//#new Requestmap(url: '/css/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
	    //new Requestmap(url: '/images/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
		
		//new Requestmap(url: '/logout/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
		//
		
		//new Requestmap(url: '/admin/**', configAttribute: 'ROLE_ADMIN').save()
		//new Requestmap(url: '/admin/role/**', configAttribute: 'ROLE_ADMIN').save()
		//new Requestmap(url: '/admin/user/**', configAttribute: 'ROLE_ADMIN').save()
		//new Requestmap(url: '/j_spring_security_switch_user',
			//			 configAttribute: 'ROLE_SWITCH_USER,IS_AUTHENTICATED_FULLY').save()
		// Create Sample Categories for Events (id 1-6)
		//assert Requestmap.count() == 3
		def category1 = new Category(categoryName:'Music').save()
		def category2 = new Category(categoryName:'Arts&Colture').save()
		def category3 = new Category(categoryName:'Sports').save()
		def category4 = new Category(categoryName:'Networking').save()
		def category5 = new Category(categoryName:'Charities').save()
		def category6 = new Category(categoryName:'Free').save()
		
		// Create Sample Categories for Guide (id 7-11)
		def category7 = new Category(categoryName: 'EatOut').save()
		def category8 = new Category(categoryName: 'Shops').save()
		def category9 = new Category(categoryName: 'Nightlife').save()
		def category10 = new Category(categoryName: 'See').save()
		def category11 = new Category(categoryName: 'Accommodation').save()
		
		// Create Sample Categories for Offers (id 12-16)
		def category12 = new Category(categoryName:'Shop').save()
		def category13 = new Category(categoryName: 'Drink').save()
		def category14 = new Category(categoryName: 'EatOut').save()
		def category15 = new Category(categoryName: 'Shops').save()
		def category16 = new Category(categoryName: 'Nightlife').save()
		
		// Create Sample Categories for Articles (id 17-21)
		def category17 = new Category(categoryName:'Arts').save()
		def category18 = new Category(categoryName: 'CityLife').save()
		def category19 = new Category(categoryName: 'Business').save()
		def category20 = new Category(categoryName: 'Academic').save()
		def category21 = new Category(categoryName: 'LeisureTime').save()
		
		// Create Sample Category Types
		
		def type = new CategoryType(typeofCategory:'Festival',category:category1).save()

		// Create the supported locations
		def location1= new Locations(title:'Great_CPH').save()
		def location2= new Locations(title:'Frederiksberg').save()
		def location3= new Locations(title:'Norrebro').save()
		def location4= new Locations(title:'Osterbro').save()
		def location5= new Locations(title:'Vesterbro').save()
		def location6= new Locations(title:'Syndhavnen').save()
		def location7= new Locations(title:'Amager_O').save()
		def location8= new Locations(title:'Amager_V').save()
		def location9= new Locations(title:'Christianshavn').save()
		def location10= new Locations(title:'Inner_City').save()
		
		// Create Sample Events

		new Event(title:'Sample Event1',	startDate:new Date('06/2/2014'),
		description:'''The WALL is the digital platform of Museum of Copenhagen, which gives street level access to a unique database of media provided by the citizens of Copenhagen and the museum.
		You can comment and debate the images on the WALL and you can augment the collection with your own photos or videos of Copenhagen. By the WALL or at the website of the museum Copenhagen.dk, you can upload your own personal maps of Copenhagen, images of personal landmarks and secret hideouts.''',
		rating: 0.5, address: 'Bispebjerg Parkalle 22',category:category1,categoryType:type).save()

		new Event(title:'Sample Event2',	startDate:new Date('11/2/2015'),
		description:'''The WALL is the digital platform of Museum of Copenhagen, which gives street level access to a unique database of media provided by the citizens of Copenhagen and the museum.
		You can comment and debate the images on the WALL and you can augment the collection with your own photos or videos of Copenhagen. By the WALL or at the website of the museum Copenhagen.dk, you can upload your own personal maps of Copenhagen, images of personal landmarks and secret hideouts.''',
		rating: 1, address: 'Bispebjerg Parkalle 22',category:category1,categoryType:type).save()

		new Event(title:'Sample Event3',	startDate:new Date('05/22/2014'),
		description:'''and debate the images on the WALL and you can augment the collection with your own photos or videos of Copenhagen. By the WALL or at the website of the museum Copenhagen.dk, you can upload your own personal maps of Copenhagen, images of personal landmarks and secret hideouts''',
		rating: 1.5, address: 'Bispebjerg Parkalle 22',category:category1,categoryType:type).save()

		new Event(title:'Sample Event4',	startDate:new Date('06/26/2014'),
		description:'''and debate the images on the WALL and you can augment the collection with your own photos or videos of Copenhagen. By the WALL or at the website of the museum Copenhagen.dk, you can upload your own personal maps of Copenhagen, images of personal landmarks and secret hideouts''',
		rating: 2, address: 'Bispebjerg Parkalle 22',category:category1,categoryType:type).save()

		new Event(title:'Sample Event5',	startDate:new Date('06/26/2014'),
		description:'''and debate the images on the WALL and you can augment the collection with your own photos or videos of Copenhagen. By the WALL or at the website of the museum Copenhagen.dk, you can upload your own personal maps of Copenhagen, images of personal landmarks and secret hideouts''',
		rating: 2.5, address: 'Bispebjerg Parkalle 22',category:category1,categoryType:type).save()

		new Event(title:'Sample Event6',	startDate:new Date('06/29/2014'),
		description:'''and debate the images on the WALL and you can augment the collection with your own photos or videos of Copenhagen. By the WALL or at the website of the museum Copenhagen.dk, you can upload your own personal maps of Copenhagen, images of personal landmarks and secret hideouts''',
		rating: 3, address: 'Bispebjerg Parkalle 22',category:category2,categoryType:type).save()

		new Event(title:'Sample Event7',  startDate:new Date('05/1/2014'),
		description:'''and debate the images on the WALL and you can augment the collection with your own photos or videos of Copenhagen. By the WALL or at the website of the museum Copenhagen.dk, you can upload your own personal maps of Copenhagen, images of personal landmarks and secret hideouts''',
		rating: 3.5, address: 'Bispebjerg Parkalle 22',category:category2,categoryType:type).save()

		new Event(title:'Sample Event8',	startDate:new Date('06/27/2014'),
		description:'''and debate the images on the WALL and you can augment the collection with your own photos or videos of Copenhagen. By the WALL or at the website of the museum Copenhagen.dk, you can upload your own personal maps of Copenhagen, images of personal landmarks and secret hideouts''',
		rating: 4, address: 'Bispebjerg Parkalle 22',category:category2,categoryType:type).save()

		new Event(title:'Sample Event9',	startDate:new Date('05/29/2014'),
		description:'''and debate the images on the WALL and you can augment the collection with your own photos or videos of Copenhagen. By the WALL or at the website of the museum Copenhagen.dk, you can upload your own personal maps of Copenhagen, images of personal landmarks and secret hideouts''',
		rating: 4.5, address: 'Bispebjerg Parkalle 22',category:category2,categoryType:type).save()

		new Event(title:'Sample Event10',	startDate:new Date('05/21/2018'),
		description:'''The WALL is the digital platform of Museum of Copenhagen, which gives street level access to a unique database of media provided by the citizens of Copenhagen and the museum. 
		You can comment and debate the images on the WALL and you can augment the collection with your own photos or videos of Copenhagen. By the WALL or at the website of the museum Copenhagen.dk, you can upload your own personal maps of Copenhagen, images of personal landmarks and secret hideouts.''',
		rating: 0, address: 'Bispebjerg Parkalle 22', category:category1,categoryType:type).save()
		
		// Create Sample Offers

		new Offer(title:'Sample Offer1', description:'''offer1 description''' , startDate:new Date('08/22/2015'), rating: 2.3, category:category12, categoryType:type,location:location1,address: 'Bispebjerg Parkalle 22').save()
		new Offer(title:'Sample Offer2', description:'''offer2 description''' ,startDate:new Date('07/22/2015'), rating: 3, category:category12, categoryType:type,location:location2,address: 'Bispebjerg Parkalle 22').save()
		new Offer(title:'Sample Offer3', description:'''offer3 description''' ,startDate:new Date('06/22/2015'), rating: 1.24, category:category12, categoryType:type,location:location4,address: 'Bispebjerg Parkalle 22').save()
		new Offer(title:'Sample Offer4', description:'''offer4 description''' ,startDate:new Date('07/22/2015'), rating: 4.51, category:category12, categoryType:type,location:location1,address: 'Bispebjerg Parkalle 22').save()
		new Offer(title:'Sample Offer5', description:'''offer5 description''' ,startDate:new Date('07/22/2015'), rating: 2.3, category:category12, categoryType:type,location:location1,address: 'Bispebjerg Parkalle 22').save()
		new Offer(title:'Sample Offer6', description:'''offer6 description''' ,startDate:new Date('08/22/2015'), rating: 2.7, category:category12, categoryType:type,location:location1,address: 'Bispebjerg Parkalle 22').save()
		new Offer(title:'Sample Offer7', description:'''offer7 description''' ,startDate:new Date('09/22/2015'), rating: 2.1, category:category12, categoryType:type,location:location2,address: 'Bispebjerg Parkalle 22').save()
		new Offer(title:'Sample Offer8', description:'''offer8 description''' ,startDate:new Date('09/22/2015'), rating: 2.6, category:category14, categoryType:type,location:location1,address: 'Bispebjerg Parkalle 22').save()
		new Offer(title:'Sample Offer9', description:'''offer9 description''' ,startDate:new Date('09/22/2015'), rating: 2.99, category:category16, categoryType:type,location:location1,address: 'Bispebjerg Parkalle 22').save()
		new Offer(title:'Sample Offer10', description:'''offer10 description''' ,startDate:new Date('09/22/2015'), rating: 2.76, category:category13,categoryType:type,location:location1,address: 'Bispebjerg Parkalle 22').save()
		new Offer(title:'Sample Offer11', description:'''offer11 description''' ,startDate:new Date('09/22/2015'), rating: 2.1, category:category12,categoryType:type,location:location3,address: 'Bispebjerg Parkalle 22').save()
		
		
		assert Offer.count() == 11
		// Create Sample Articles

		new Article(title:'Sample Article1',description:'''Article1 Description''',	rating: 2,category:category17,categoryType:type,address: 'Bispebjerg Parkalle 22').save()
		new Article(title:'Sample Article2',description:'''Article2 Description''',	rating: 2,category:category18,categoryType:type,address: 'Bispebjerg Parkalle 22').save()
		new Article(title:'Sample Article3',description:'''Article3 Description''',	rating: 2,category:category19,categoryType:type,address: 'Bispebjerg Parkalle 22').save()
		new Article(title:'Sample Article4',description:'''Article4 Description''',	rating: 2,category:category20,categoryType:type,address: 'Bispebjerg Parkalle 22').save()
		new Article(title:'Sample Article5',description:'''Article5 Description''',	rating: 2,category:category21,categoryType:type,address: 'Bispebjerg Parkalle 22').save()
		new Article(title:'Sample Article6',description:'''Article6 Description''',	rating: 2,category:category17,categoryType:type,address: 'Bispebjerg Parkalle 22').save()
		new Article(title:'Sample Article7',description:'''Article7 Description''',	rating: 2,category:category18,categoryType:type,address: 'Bispebjerg Parkalle 22').save()
		new Article(title:'Sample Article8',description:'''Article8 Description''',	rating: 2,category:category19,categoryType:type,address: 'Bispebjerg Parkalle 22').save()
		new Article(title:'Sample Article9',description:'''Article9 Description''',	rating: 2,category:category20,categoryType:type,address: 'Bispebjerg Parkalle 22').save()
		new Article(title:'Sample Article10',description:'''Article10 Description''',	rating: 2,category:category21,categoryType:type,address: 'Bispebjerg Parkalle 22').save()

		// Create Sample Guides
		
		new Guide(title:'Sample Guide1',description:'''Guide1 Description''', address: 'Bispebjerg Parkalle 22',	rating: 2,	category:category9,  categoryType:type,location:location3, startDate:new Date('08/22/2015')).save()
		new Guide(title:'Sample Guide2',description:'''Guide2 Description''', address: 'Bispebjerg Parkalle 22',	rating: 3,  category:category10, categoryType:type,location:location1, startDate:new Date('09/22/2015')).save()
		new Guide(title:'Sample Guide3',description:'''Guide3 Description''', address: 'Bispebjerg Parkalle 22',	rating: 5,	category:category11, categoryType:type,location:location1, startDate:new Date('05/22/2015')).save()
		new Guide(title:'Sample Guide4',description:'''Guide4 Description''', address: 'Bispebjerg Parkalle 22',	rating: 4.2,category:category8, categoryType:type,location:location2, startDate:new Date('07/22/2015')).save()
		new Guide(title:'Sample Guide5',description:'''Guide5 Description''', address: 'Bispebjerg Parkalle 22',	rating: 3,	category:category7, categoryType:type,location:location3, startDate:new Date('09/22/2015')).save()
		new Guide(title:'Sample Guide6',description:'''Guide6 Description''', address: 'Bispebjerg Parkalle 22',	rating: 1,	category:category10, categoryType:type,location:location1, startDate:new Date('09/22/2015')).save()
		new Guide(title:'Sample Guide7',description:'''Guide7 Description''', address: 'Bispebjerg Parkalle 22',	rating: 2,	category:category9,  categoryType:type,location:location1, startDate:new Date('08/23/2015')).save()
		new Guide(title:'Sample Guide8',description:'''Guide8 Description''', address: 'Bispebjerg Parkalle 22',	rating: 3,  category:category10, categoryType:type,location:location1, startDate:new Date('08/24/2015')).save()
		new Guide(title:'Sample Guide9',description:'''Guide9 Description''', address: 'Bispebjerg Parkalle 22',	rating: 5,	category:category11, categoryType:type,location:location1, startDate:new Date('08/26/2015')).save()
		new Guide(title:'Sample Guide10',description:'''Guide10 Description''', address: 'Bispebjerg Parkalle 22',	rating: 4.2,category:category7, categoryType:type,location:location3, startDate:new Date('08/27/2015')).save()
		new Guide(title:'Sample Guide11',description:'''Guide11 Description''', address: 'Bispebjerg Parkalle 22',	rating: 3,	category:category9, categoryType:type,location:location3, startDate:new Date('08/22/2015')).save()
		new Guide(title:'Sample Guide12',description:'''Guide12 Description''', address: 'Bispebjerg Parkalle 22',	rating: 1,	category:category10, categoryType:type,location:location3, startDate:new Date('08/12/2015')).save()
		new Guide(title:'Sample Guide13',description:'''Guide13 Description''', address: 'Bispebjerg Parkalle 22',	rating: 2,	category:category9,  categoryType:type,location:location2, startDate:new Date('08/30/2015')).save()
		new Guide(title:'Sample Guide14',description:'''Guide14 Description''', address: 'Bispebjerg Parkalle 22',	rating: 3,  category:category10, categoryType:type,location:location2, startDate:new Date('08/21/2015')).save()
		new Guide(title:'Sample Guide15',description:'''Guide15 Description''', address: 'Bispebjerg Parkalle 22',	rating: 5,	category:category11, categoryType:type,location:location2, startDate:new Date('08/25/2015')).save()
		new Guide(title:'Sample Guide16',description:'''Guide16 Description''', address: 'Bispebjerg Parkalle 22',	rating: 4.2,category:category8, categoryType:type,location:location3, startDate:new Date('08/27/2015')).save()
		new Guide(title:'Sample Guide17',description:'''Guide17 Description''', address: 'Bispebjerg Parkalle 22',	rating: 3,	category:category7, categoryType:type,location:location2, startDate:new Date('08/22/2015')).save()
		new Guide(title:'Sample Guide18',description:'''Guide18 Description''', address: 'Bispebjerg Parkalle 22',	rating: 1,	category:category10, categoryType:type,location:location3, startDate:new Date('08/21/2015')).save()

		assert Guide.count() == 18
	}
	def destroy = {
	}
}
