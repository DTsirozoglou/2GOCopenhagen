/**
* JQuery Inline datepicker with a remote function call.
* When a date is selected on the datepicker then we perform three ajax calls and we update
* the container, the container_mobile, the SideBarBottomLeft and SideBarBottomRight divs of the index.gsp (Events)
*  
*/
$(function() {

	$("#datepicker, #datepicker_mobile").datepicker({

		showOtherMonths : true,
		onSelect : function(dateText, inst) {
			jQuery.ajax({
				type : 'POST',
				data : 'date=' + dateText,
				url : '/2GOCPH/guide/findEventsByDate',
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
				url : '/2GOCPH/guide/leftSideBarContent',
				success : function(data, textStatus) {
					jQuery('#SideBarBottomLeft').html(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
			jQuery.ajax({
				type : 'POST',
				data : 'date=' + dateText,
				url : '/2GOCPH/guide/rightSideBarContent',
				success : function(data, textStatus) {
					jQuery('#SideBarBottomRight').html(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
		}
	});
});

/**
* On document.ready , when a category button is clicked, we change its class to active ,
* we remove active class from the other category buttons and we reset the date of the calendar.
*  
*/
$(document).ready(function() {

	$(".btn-group > .btn").click(function() {
		// $(this).addClass("active").siblings().removeClass("active");
		$(".btn-group > .btn").removeClass("active");
		$(this).addClass("active");
		$("#datepicker, #datepicker_mobile").datepicker('setDate', new Date());
	});
	
	$(function() {
//		console.log("guide");
//	 	$('#ImageMap1').maphilight();
//	 	$('img[usemap]').rwdImageMaps();
	});
	

});



/**
* This function takes as a parameter a date as a String, parses this date to the datepicker's format date
* and then set the active date of the datepicker to this date. 
*  
*/
function setCalendarDate(dateSelectedOnCalendar) {

	var parsedDate = $.datepicker.parseDate('yy-mm-dd', dateSelectedOnCalendar);
	$("#datepicker, #datepicker_mobile").datepicker('setDate', parsedDate);
}

/**
* This function takes as a parameter the category id of the selected Event and set to active the corresponding
* category button and removes the active class from the other buttons.
*  
*/
function setActiveCategory(selectedCategoryOfSingleGuide) {
	// console.log(selectedCategoryOfSingleGuide);
	$(".btn-group > .btn").removeClass("active");
	
	if (selectedCategoryOfSingleGuide==9) 
		$(".btn_1").addClass("active");
	else if (selectedCategoryOfSingleGuide==10) 
		$(".btn_2").addClass("active");
	else if (selectedCategoryOfSingleGuide==11) 
		$(".btn_3").addClass("active");
	else if (selectedCategoryOfSingleGuide==12) 
		$(".btn_4").addClass("active");
	else if (selectedCategoryOfSingleGuide==13) 
		$(".btn_5").addClass("active");
}

/**
* This function takes as parameters the title,the latitude and the longitude of the selected Event 
* Creates a google map in the div element "article_map", put a mark to the requested lat and lng and
* centers the map on this mark.  
*/
function loadGoogleMap(lat, lng, title) {
	if (GBrowserIsCompatible()) {
		var map = new GMap2(document.getElementById("article_map"));
		map.disableScrollWheelZoom();
		//map.setOptions({draggable: false, zoomControl: false, disableDoubleClickZoom: true});
		

		map.setCenter(new GLatLng(lat, lng), 15);
		var marker = new GMarker(new GLatLng(lat, lng))
		marker.bindInfoWindowHtml(title)
		map.addOverlay(marker)
		
		//Enables or Disables Map Zooming on Click
		google.maps.event.addListener(map, 'click', function(event) {
			if(map.scrollWheelZoomEnabled())
		      	map.disableScrollWheelZoom();
			else
				map.enableScrollWheelZoom();
		        });
	}
}



/**
* This function takes as parameters the selected category depending on the button that was clicked
* and perform three ajax calls to update the container, the container_mobile, the SideBarBottomLeft 
* and SideBarBottomRight divs of the index.gsp (Events)
*/

function categoryButtonCall(category) {

	jQuery.ajax({
		type : 'POST',
		data : 'categ=' + category,
		url : '/2GOCPH/guide/findGuidesByCategory',
		success : function(data, textStatus) {
			jQuery('#container').html(data);
			jQuery('#container_mobile').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	jQuery.ajax({
		type : 'POST',
		data : 'categ=' + category,
		url : '/2GOCPH/guide/leftSideBarContent',
		success : function(data, textStatus) {
			jQuery('#SideBarBottomLeft').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	jQuery.ajax({
		type : 'POST',
		data : 'categ=' + category,
		url : '/2GOCPH/guide/rightSideBarContent',
		success : function(data, textStatus) {
			jQuery('#SideBarBottomRight').html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
		
}


/**
* This function takes as parameters the selected Guide Instance depending on the Guide that was clicked
* and perform three ajax calls to update the container, the container_mobile, the SideBarBottomLeft 
* and SideBarBottomRight divs of the index.gsp (Guide)
*/
function singleGuideCall(guideInstance) {
	
		jQuery.ajax({
			type : 'POST',
			data : 'id=' + guideInstance,
			url : '/2GOCPH/guide/showSingleGuide',
			success : function(data, textStatus) {
				jQuery('#container').html(data);
				jQuery('#container_mobile').html(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
		jQuery.ajax({
			type : 'POST',
			data : 'id=' + guideInstance,
			url : '/2GOCPH/guide/leftSideBarContentForSingleGuide',
			success : function(data, textStatus) {
				jQuery('#SideBarBottomLeft').html(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
		jQuery.ajax({
			type : 'POST',
			data : 'id=' + guideInstance,
			url : '/2GOCPH/guide/rightSideBarContentForSingleGuide',
			success : function(data, textStatus) {
				jQuery('#SideBarBottomRight').html(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
}