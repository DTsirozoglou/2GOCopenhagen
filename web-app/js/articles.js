
window.selectedCategory = 0;
window.selectedArticle = "";

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
function doPushState(category) {    
    var state = {selectedCategory:category},
    title = "Page title",
    path  = "?category=" + category;
    
    history.pushState(state, title, path);
};

/**
 * 
 */
$(document).on("click", 'button', function(e) {
    var category = $(this).attr('data-location');
    categoryButtonCall(category);
    doPushState(category);
});

/**
 * 
 */
$(document).on("click", '.singleArticleInstance', function(e) {
    var articleInstance = $(this).attr('data-location');
    var articleCategory = $(this).attr('data-location2');
    
    singleArticleCall(articleInstance);
    setActiveCategory(articleCategory - 16);
    var state = { selectedArticle: articleInstance,selectedCategory:articleCategory },
    title = "Page title",
    path  = "?singleArticleCall=" + articleInstance;

history.pushState(state, title, path);
});

/**
 * 
 */
$(window).on('popstate', function(event) {
    var state = event.originalEvent.state;
    if (state){
	    if (state.selectedCategory) {
	    		categoryButtonCall(state.selectedCategory);
    			setActiveCategory(state.selectedCategory);
	    }
	    else {
	    	singleArticleCall(state.selectedArticle);
	    	setActiveCategory(state.selectedCategory);
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
//
///**
// */
//function findArticlesByDateAndCategory(dateText,category) {
//	
//	window.selectedCategory = category;
//	window.selectedDate2 = dateText;
//	
//	jQuery.ajax({
//		type : 'POST',
//		data: {
//			date: dateText,
//			category: category
//		},
//		url : window.appContext +'/article/findArticlesByCategoryAndSelectedDate',
//		success : function(data, textStatus) {
//			jQuery('#container').html(data);
//			jQuery('#container_mobile').html(data);
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//		}
//	});
//	jQuery.ajax({
//		type : 'POST',
//		data : 'date=' + dateText,
//		url : window.appContext +'/article/leftSideBarContent',
//		success : function(data, textStatus) {
//			jQuery('#SideBarBottomLeft').html(data);
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//		}
//	});
//	jQuery.ajax({
//		type : 'POST',
//		data : 'date=' + dateText,
//		url : window.appContext +'/article/rightSideBarContent',
//		success : function(data, textStatus) {
//			jQuery('#SideBarBottomRight').html(data);
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//		}
//	});
//	
//}

/**
 * This function takes as parameters the selected category depending on the
 * button that was clicked and perform three ajax calls to update the container,
 * the container_mobile, the SideBarBottomLeft and SideBarBottomRight divs of
 * the index.gsp (Events)
 */

function categoryButtonCall(category) {

	window.selectedCategory = category;
	
	jQuery.ajax({
		type : 'POST',
		data : 'category=' + category,
		url : window.appContext +'/article/findArticlesByCategory',
		success : function(data, textStatus) {
			jQuery('#container').html(data);
			jQuery('#container_mobile').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	jQuery.ajax({
		type : 'POST',
		data : 'category=' + category,
		url : window.appContext +'/article/leftSideBarContent',
		success : function(data, textStatus) {
			jQuery('#SideBarBottomLeft').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	jQuery.ajax({
		type : 'POST',
		data : 'category=' + category,
		url : window.appContext +'/article/rightSideBarContent',
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
function singleArticleCall(articleInstance) {

	window.selectedArticle = articleInstance;
	
	jQuery.ajax({
		type : 'POST',
		data : 'id=' + articleInstance,
		url : window.appContext +'/article/showSingleArticle',
		success : function(data, textStatus) {
			jQuery('#container').html(data);
			jQuery('#container_mobile').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	jQuery.ajax({
		type : 'POST',
		data : 'id=' + articleInstance,
		url : window.appContext +'/article/leftSideBarContentForSingleArticle',
		success : function(data, textStatus) {
			jQuery('#SideBarBottomLeft').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
//	jQuery.ajax({
//		type : 'POST',
//		data : 'id=' + articleInstance,
//		url : window.appContext +'/article/rightSideBarContentForSingleArticle',
//		success : function(data, textStatus) {
//			jQuery('#SideBarBottomRight').html(data);
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//		}
//	});

}