
window.selectedCategory = 0;
window.selectedEvent = "";
window.selectedDate2 = "";

/**
 * JQuery Inline datepicker with a remote function call. When a date is selected
 * on the datepicker then we perform three ajax calls and we update the
 * container, the container_mobile, the SideBarBottomLeft and SideBarBottomRight
 * divs of the index.gsp (Events)
 * 
 */
$(function() {

	$("#datepicker, #datepicker_mobile").datepicker({

		showOtherMonths : true,
		onSelect : function(dateText, inst) {
			
			window.selectedDate2 = dateText;
			findEventsByDateAndCategory(dateText,window.selectedCategory);
				 var state = { selectedDate: dateText, selectedCategory: window.selectedCategory},
			        title = "Page title",
			        path  = "?date=" + dateText +"&category=" + window.selectedCategory;
			    
			    history.pushState(state, title, path);
		}
	});
});

/**
 * On document.ready , when a category button is clicked, we change its class to
 * active , we remove active class from the other category buttons and we reset
 * the date of the calendar.
 * 
 */
$(document).ready(function() {

	$(".btn-group > .btn").click(function() {
		// $(this).addClass("active").siblings().removeClass("active");
		$(".btn-group > .btn").removeClass("active");
		$(this).addClass("active");
//		$("#datepicker, #datepicker_mobile").datepicker('setDate', new Date());
	});

});


/**
 * 
 */
function doPushState(category,datetext) {    
    var state = { selectedDate: datetext, selectedCategory:category},
    title = "Page title",
    path  = "?date=" + datetext +"&category=" + category;
    
    history.pushState(state, title, path);
};

/**
 * 
 */
$(document).on("click", 'button', function(e) {
    var category = $(this).attr('data-location');
    findEventsByDateAndCategory(window.selectedDate2, category);
    doPushState(category, window.selectedDate2);
});

/**
 * 
 */
$(document).on("click", '.singleEventInstance', function(e) {
    var eventInstance = $(this).attr('data-location');
    var eventCategory = $(this).attr('data-location2');
    
    singleEventCall(eventInstance);
    setActiveCategory(eventCategory);
    var state = { selectedEvent: eventInstance,selectedCategory:eventCategory },
    title = "Page title",
    path  = "?singleEventCall=" + eventInstance;

history.pushState(state, title, path);
});

/**
 * 
 */
$(window).on('popstate', function(event) {
    var state = event.originalEvent.state;
    if (state){
	    if (state.selectedDate) {
    			findEventsByDateAndCategory(state.selectedDate,state.selectedCategory);
    			$("#datepicker, #datepicker_mobile").datepicker('setDate', new Date(state.selectedDate));
    			setActiveCategory(state.selectedCategory);
    			window.selectedDate2 = state.selectedDate;
	    }
	    else {
	    	singleEventCall(state.selectedEvent);
	    	setActiveCategory(state.selectedCategory);
	    }
    }
    else{ window.location.reload(); }
});

/**
 * This function takes as a parameter a date as a String, parses this date to
 * the datepicker's format date and then set the active date of the datepicker
 * to this date.
 * 
 */
function setCalendarDate(dateSelectedOnCalendar) {
	window.selectedDate2 = dateSelectedOnCalendar;
	$("#datepicker, #datepicker_mobile").datepicker('setDate', dateSelectedOnCalendar);
}

/**
 * This function takes as a parameter the category id of the selected Event and
 * set to active the corresponding category button and removes the active class
 * from the other buttons.
 * 
 */
function setActiveCategory(selectedCategoryOfSingleArticle) {
	
	
	window.selectedCategory = selectedCategoryOfSingleArticle;
	// console.log(selectedCategoryOfSingleEvent);
	$(".btn-group > .btn").removeClass("active");

	if (selectedCategoryOfSingleArticle == 0)
		$(".btn_0").addClass("active");
	else if (selectedCategoryOfSingleArticle == 1)
		$(".btn_1").addClass("active");
	else if (selectedCategoryOfSingleArticle == 2)
		$(".btn_2").addClass("active");
	else if (selectedCategoryOfSingleArticle == 3)
		$(".btn_3").addClass("active");
	else if (selectedCategoryOfSingleArticle == 4)
		$(".btn_4").addClass("active");
	else if (selectedCategoryOfSingleArticle == 5)
		$(".btn_5").addClass("active");
	else if (selectedCategoryOfSingleArticle == 6)
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

/**
 */
function findEventsByDateAndCategory(dateText,category) {
	
	window.selectedCategory = category;
	window.selectedDate2 = dateText;
	
	jQuery.ajax({
		type : 'POST',
		data: {
			date: dateText,
			category: category
		},
		url : window.appContext +'/event/findEventsByCategoryAndSelectedDate',
		success : function(data, textStatus) {
			jQuery('#container').html(data);
			jQuery('#container_mobile').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	jQuery.ajax({
		type : 'POST',
		data : 'date=' + dateText,
		url : window.appContext +'/event/leftSideBarContent',
		success : function(data, textStatus) {
			jQuery('#SideBarBottomLeft').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	jQuery.ajax({
		type : 'POST',
		data : 'date=' + dateText,
		url : window.appContext +'/event/rightSideBarContent',
		success : function(data, textStatus) {
			jQuery('#SideBarBottomRight').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	
}

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
//	
//	jQuery.ajax({
//		type : 'POST',
//		data : 'category=' + category,
//		url : window.appContext +'/event/findEventsByCategory',
//		success : function(data, textStatus) {
//			jQuery('#container').html(data);
//			jQuery('#container_mobile').html(data);
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//		}
//	});
//	jQuery.ajax({
//		type : 'POST',
//		data : 'category=' + category,
//		url : window.appContext +'/event/leftSideBarContent',
//		success : function(data, textStatus) {
//			jQuery('#SideBarBottomLeft').html(data);
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//		}
//	});
//	jQuery.ajax({
//		type : 'POST',
//		data : 'category=' + category,
//		url : window.appContext +'/event/rightSideBarContent',
//		success : function(data, textStatus) {
//			jQuery('#SideBarBottomRight').html(data);
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//		}
//	});
//}

/**
 * This function takes as parameters the selected event Instance depending on
 * Event that was clicked and perform three ajax calls to update the container,
 * the container_mobile, the SideBarBottomLeft and SideBarBottomRight divs of
 * the index.gsp (Events)
 */
function singleEventCall(eventInstance) {

	window.selectedEvent = eventInstance;
	
	jQuery.ajax({
		type : 'POST',
		data : 'id=' + eventInstance,
		url : window.appContext +'/event/showSingleEvent',
		success : function(data, textStatus) {
			jQuery('#container').html(data);
			jQuery('#container_mobile').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	jQuery.ajax({
		type : 'POST',
		data : 'id=' + eventInstance,
		url : window.appContext +'/event/leftSideBarContentForSingleEvent',
		success : function(data, textStatus) {
			jQuery('#SideBarBottomLeft').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	jQuery.ajax({
		type : 'POST',
		data : 'id=' + eventInstance,
		url : window.appContext +'/event/rightSideBarContentForSingleEvent',
		success : function(data, textStatus) {
			jQuery('#SideBarBottomRight').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});

}