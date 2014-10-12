

window.selectedCategory = 0;
window.selectedEvent = "";
window.selectedSuburb = "All";

/**
 * On document.ready , when a category button is clicked, we change its class to
 * active , we remove active class from the other category buttons and we reset
 * the date of the calendar.
 * 
 */
$(document).ready(function() {

	$(".btn-group > .btn").click(function() {
		$(".btn-group > .btn").removeClass("active");
		$(this).addClass("active");
//		$(".btn-groupSvg > .btn").attr("class", "btn");
	});
	
	$(".btn-groupSvg > .btn").click(function() {
		
		var suburb = $(this).attr('data-location');
		setActiveSuburb(suburb); 
		mapCall(suburb,window.selectedCategory);
		var state = { selectedSuburb: suburb,selectedCategory:selectedCategory },
	        title = "Page title",
	        path  = "?suburb=" + suburb +"&category=" + selectedCategory;   
	    history.pushState(state, title, path);
	});

});

function setActiveSuburb(suburb) {

	
	window.selectedSuburb = suburb;
	if (suburb == "All"){
		$(".btn-groupSvg > .btn").attr("class", "btn");
	}
	else{
	
		$(".btn-groupSvg > .btn").attr("class", "btn");
		$("path#"+suburb).attr("class", "btn activepath");
	}
	
}

/**
 * 
 */
function doPushState(category) {
    var state = { selectedCategory: category,selectedSuburb: window.selectedSuburb },
        title = "Page title",
        path  = "?suburb=" +  window.selectedSuburb +"&category=" + category; 
    
    history.pushState(state, title, path);
};

/**
 * 
 */
$(document).on("click", 'button', function(e) {
    var category = $(this).attr('data-location');
    
    mapCall(window.selectedSuburb,category);
    doPushState(category);
});

/**
 * 
 */
$(document).on("click", '.singleGuideInstance', function(e) {
    var guideInstance = $(this).attr('data-location');
    var guideCategory = $(this).attr('data-location2');
    var guideSuburb = $(this).attr('data-location3');
    
    singleGuideCall(guideInstance);
    setActiveCategory(guideCategory);
    setActiveSuburb(guideSuburb);
    var state = { selectedGuide: guideInstance,selectedCategory: guideCategory,selectedSuburb:guideSuburb },
    title = "Page title",
    path  = "?singleGuideCall=" + guideInstance;

history.pushState(state, title, path);
});

/**
 * 
 */
$(window).on('popstate', function(event) {
    var state = event.originalEvent.state;
    if (state){
	    if (state.selectedGuide){
	    	singleGuideCall(state.selectedGuide);
	    	setActiveCategory(state.selectedCategory);
	        setActiveSuburb(state.selectedSuburb);
	    }
//	    else if ((state.selectedCategory)&&(!state.selectedSuburb)){
//	    	categoryButtonCall( state.selectedCategory );
//	    	setActiveCategory(state.selectedCategory);
//	    }
	    else {
	    	mapCall(state.selectedSuburb,state.selectedCategory);
	    	setActiveCategory(state.selectedCategory);
	    	setActiveSuburb(state.selectedSuburb); 
	    }
	    
    }
    else{ window.location.reload(); }
});

/**
 * This function takes as a parameter the category id of the selected Event and
 * set to active the corresponding category button and removes the active class
 * from the other buttons.
 * 
 */
function setActiveCategory(selectedCategoryOfSingleGuide) {
		
		
		window.selectedCategory = selectedCategoryOfSingleGuide;
	
		$(".btn-group > .btn").removeClass("active");

		if (selectedCategoryOfSingleGuide == 0)
			$(".btn_0").addClass("active");
		else if (selectedCategoryOfSingleGuide == 1)
			$(".btn_1").addClass("active");
		else if (selectedCategoryOfSingleGuide == 2)
			$(".btn_2").addClass("active");
		else if (selectedCategoryOfSingleGuide == 3)
			$(".btn_3").addClass("active");
		else if (selectedCategoryOfSingleGuide == 4)
			$(".btn_4").addClass("active");
		else if (selectedCategoryOfSingleGuide == 5)
			$(".btn_5").addClass("active");
		else if (selectedCategoryOfSingleGuide == 6)
			$(".btn_6").addClass("active");
		else $(".btn-group > .btn").removeClass("active");
}

/**
 * This function takes as parameters the title,the latitude and the longitude of
 * the selected Event Creates a google map in the div element "article_map", put
 * a mark to the requested lat and lng and centers the map on this mark.
 */
function loadGoogleMap(lat, lng, title) {
	if (GBrowserIsCompatible()) {
		var map = new GMap2(document.getElementById("article_map"));
		map.disableScrollWheelZoom();
		// map.setOptions({draggable: false, zoomControl: false,
		// disableDoubleClickZoom: true});

		map.setCenter(new GLatLng(lat, lng), 15);
		var marker = new GMarker(new GLatLng(lat, lng))
		marker.bindInfoWindowHtml(title)
		map.addOverlay(marker)

		// Enables or Disables Map Zooming on Click
		google.maps.event.addListener(map, 'click', function(event) {
			if (map.scrollWheelZoomEnabled())
				map.disableScrollWheelZoom();
			else
				map.enableScrollWheelZoom();
		});
	}
}
//
///**
// * This function takes as parameters the selected category depending on the
// * button that was clicked and perform three ajax calls to update the container,
// * the container_mobile, the SideBarBottomLeft and SideBarBottomRight divs of
// * the index.gsp (Events)
// */
//
//function categoryButtonCall(category) {
//
//	window.selectedCategory = category;
//	jQuery.ajax({
//		type : 'POST',
//		data : 'category=' + category,
//		url :  window.appContext +'/guide/findGuidesByCategory',
//		success : function(data, textStatus) {
//			jQuery('#container2').html(data);
//			jQuery('#container_mobile').html(data);
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//		}
//	});
//	jQuery.ajax({
//		type : 'POST',
//		data : 'category=' + category,
//		url : window.appContext +'/guide/leftSideBarContent',
//		success : function(data, textStatus) {
//			jQuery('#SideBarBottomLeft').html(data);
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//		}
//	});
//	jQuery.ajax({
//		type : 'POST',
//		data : 'category=' + category,
//		url : window.appContext +'/guide/rightSideBarContent',
//		success : function(data, textStatus) {
//			jQuery('#SideBarBottomRight').html(data);
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//		}
//	});
//}

function mapCall(suburb,category) {

	window.selectedCategory=category;
	window.selectedSuburb=suburb;
	jQuery.ajax({
		type : 'POST',
		data : {
			suburb: suburb,
			category: category
		},
		url : window.appContext + '/guide/findGuidesBySuburnAndCategory',
		success : function(data, textStatus) {
			jQuery('#container').html(data);
//			jQuery('#container_mobile').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	
	jQuery.ajax({
		type : 'POST',
		data : 'category=' + category,
		url : window.appContext +'/guide/leftSideBarContent',
		success : function(data, textStatus) {
			jQuery('#SideBarBottomLeft').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	jQuery.ajax({
		type : 'POST',
		data : 'category=' + category,
		url : window.appContext +'/guide/rightSideBarContent',
		success : function(data, textStatus) {
			jQuery('#SideBarBottomRight').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	
}

/**
 * This function takes as parameters the selected event Instance depending on
 * Event that was clicked and perform three ajax calls to update the container,
 * the container_mobile, the SideBarBottomLeft and SideBarBottomRight divs of
 * the index.gsp (Events)
 */
function singleGuideCall(guideInstance) {

	window.selectedEvent = guideInstance;
	
	jQuery.ajax({
		type : 'POST',
		data : 'id=' + guideInstance,
		url : window.appContext +'/guide/showSingleGuide',
		success : function(data, textStatus) {
			jQuery('#container').html(data);
//			jQuery('#container_mobile').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	jQuery.ajax({
		type : 'POST',
		data : 'id=' + guideInstance,
		url : window.appContext +'/guide/leftSideBarContentForSingleGuide',
		success : function(data, textStatus) {
			jQuery('#SideBarBottomLeft').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	jQuery.ajax({
		type : 'POST',
		data : 'id=' + guideInstance,
		url : window.appContext +'/guide/rightSideBarContentForSingleGuide',
		success : function(data, textStatus) {
			jQuery('#SideBarBottomRight').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});

}