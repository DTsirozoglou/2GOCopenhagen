package tag_lib

/**
 * This grails tag is created in order to output in the html the star image that corresponds to the given Rating.
 * 
 * @TODO Change the images of the stars (Waiting for star images)
 * 
 * @author 2go IT
 *
 */
class RatingTagLib {
	
	/**
	 * Takes as an attribute a double from 0 to 5 (the Rating of the Content) and outputs in the html the 
	 * corresponding star image of the rating.
	 */
	def ratingToStars = { attrs ->
		
		def rating = attrs.rating
		
		//half star
		if (rating <0.75) {
			out <<"<img id='image1'	src='${resource(dir: 'images/rating', file: '1.png')}'/>"
		}
		//one star
		else if (rating >=0.75 && rating <=1.24 ) {
			out <<"<img id='image1'	src='${resource(dir: 'images/rating', file: '2.png')}'/>"
		}
		//one and a half stars
		else if (rating >1.24 && rating <=1.74 ) {
			out <<"<img id='image1'	src='${resource(dir: 'images/rating', file: '3.png')}'/>"
		}
		//two stars
		else if (rating >1.74 && rating <=2.24 ) {
			out <<"<img id='image1'	src='${resource(dir: 'images/rating', file: '4.png')}'/>"
		}
		//two and a half stars
		else if (rating >2.24 && rating <=2.74 ) {
			out <<"<img id='image1'	src='${resource(dir: 'images/rating', file: '5.png')}'/>"
		}
		//three stars
		else if (rating >2.74 && rating <=3.24 ) {
			out <<"<img id='image1'	src='${resource(dir: 'images/rating', file: '6.png')}'/>"
		}
		//three and a half stars
		else if (rating >3.24 && rating <=3.74 ) {
			out <<"<img id='image1'	src='${resource(dir: 'images/rating', file: '7.png')}'/>"
		}
		//four stars
		else if (rating >3.74 && rating <=4.24 ) {
			out <<"<img id='image1'	src='${resource(dir: 'images/rating', file: '8.png')}'/>"
		}
		//four and a half stars
		else if (rating >4.24 && rating <=4.74 ) {
			out <<"<img id='image1'	src='${resource(dir: 'images/rating', file: '9.png')}'/>"
		}
		//five stars
		else {
			out <<"<img id='image1'	src='${resource(dir: 'images/rating', file: '10.png')}'/>"
		}
		
	}
	
}
