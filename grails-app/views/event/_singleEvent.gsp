

<div id="single_article">

	
	<div id="article_title">
		<h1>${fieldValue(bean: eventInstance, field: "title")} </h1>
	</div>
	
	<div id="article_ShareIcons">
		<p>Share Icons Section </p>
	</div>
	
	<div id="article_photo">
		<img
			src="${resource(dir: 'images/eventpage_images', file: 'shakkira.gif')}" />
	</div>

	<div id="article_extra_info" class="hidden-xs">
		
		<div id="article_extra_info_left">
			<h2>Event Venue</h2>
			<p>${fieldValue(bean: eventInstance, field: "address")}</p>
			<h2>Views</h2>
			<p>${fieldValue(bean: eventInstance, field: "views")}</p>
			<h2>Rating</h2>
			<g:ratingToStars rating="${eventInstance.rating}"/>
		</div>
		
		<div id="article_extra_info_middle"></div>
		
		<div id="article_extra_info_right">
			<h2>Contact e-mail</h2>
			<p>justin@hotmail.com</p>
			<h2>Contact telephone</h2>
			<p>70546756</p>
			<h2>Add to my Calendar</h2>
		</div>
	</div>

	<div id="article_extra_info_mobile" class="visible-xs">
		
		<div id="article_extra_info_left_mobile">
			<h2>Event Venue</h2>
			<p>${fieldValue(bean: eventInstance, field: "address")}</p>
			<h2>Views</h2>
			<p>${fieldValue(bean: eventInstance, field: "views")}</p>
			<h2>Rating</h2>
			<g:ratingToStars rating="${eventInstance.rating}"/>
		</div>
		
		<div id="article_extra_info_right">
			<h2>Contact e-mail</h2>
			<p>justin@hotmail.com</p>

			<h2>Contact telephone</h2>
			<p>70546756</p>

			<h2>Add to my Calendar</h2>
		</div>
	</div>

	<div id="article_description">

		<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
			do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
		<p>Ut enim ad minim veniam, quis nostrud exercitation ullamco
			laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
			dolor in reprehenderit in voluptate velit esse cillum dolore eu
			fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
			proident, sunt in culpa qui officia deserunt mollit anim id est
			laborum.</p>
		<p>Ut enim ad minim veniam, quis nostrud exercitation ullamco
			laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
			dolor in reprehenderit in voluptate velit esse cillum dolore eu
			fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
			proident, sunt in culpa qui officia deserunt mollit anim id est
			laborum.</p>
		<p>Ut enim ad minim veniam, quis nostrud exercitation ullamco
			laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
			dolor in reprehenderit in voluptate velit esse cillum dolore eu
			fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
			proident, sunt in culpa qui officia deserunt mollit anim id est
			laborum.</p>
		<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
			do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
		<p>Ut enim ad minim veniam, quis nostrud exercitation ullamco
			laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
			dolor in reprehenderit in voluptate velit esse cillum dolore eu
			fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
			proident, sunt in culpa qui officia deserunt mollit anim id est
			laborum.</p>
		<p>Ut enim ad minim veniam, quis nostrud exercitation ullamco
			laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
			dolor in reprehenderit in voluptate velit esse cillum dolore eu
			fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
			proident, sunt in culpa qui officia deserunt mollit anim id est
			laborum.</p>
		<p>Ut enim ad minim veniam, quis nostrud exercitation ullamco
			laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
			dolor in reprehenderit in voluptate velit esse cillum dolore eu
			fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
			proident, sunt in culpa qui officia deserunt mollit anim id est
			laborum.</p>
	</div>
	
	<div id="article_Like_Button">
		<p>Facebook Like </p>
	</div>

	<div id="article_map"></div>

    <script type="text/javascript">
	    $( window ).ready(function() {
	    	setCalendarDate('${dateSelectedOnCalendar}');
<%--	    	setActiveCategory(${selectedCategoryOfSingleOffer});--%>
<%--	    	setCalendarDate(new Date('${dateSelectedOnCalendar});--%>
<%--			$("#datepicker, #datepicker_mobile").datepicker('setDate', new Date('${dateSelectedOnCalendar}'));--%>
	    	loadGoogleMap(${eventLocation.lat},${eventLocation.lng},'${eventInstance.title}');
		});
    </script>
    
    <div id="article_Comments">
		<p>Comments Section </p>
	</div>
	
</div>
