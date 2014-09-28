
<script>  
 $( window ).ready(function() {
	 var clock = document.getElementById("offer-timer")  
	    , targetDate = new Date(2014, 08, 01); // Jan 1, 2050;8  
	  
	  clock.innerHTML = countdown(targetDate).toString();  
	  setInterval(function(){  
	    clock.innerHTML = countdown(targetDate).toString();  
	  }, 1000);   
	});
</script>

<div id="single_article1">

	<div id="article_title1">
		<h1>
			${fieldValue(bean: offerInstance, field: "title")}
		</h1>
	</div>

	<div id="article_ShareIcons1">
		<p>Share Icons Section</p>
	</div>
	<div class="row">
		<div class="col-md-6 col-lg-6 ">
			<div id="article_photo1">
				<img
					src="${resource(dir: 'images/eventpage_images', file: 'shakkira.gif')}" />
			</div>
		</div>
		<div class="col-md-6 col-lg-6 ">
			<div id="content1">
				<div>The Offer</div>
				<div>Offer's price</div>
				<div>Discount</div>
				<div>Offers sold</div>

				<div>
					<ul class="nav">
						<li>Still Active For</li>
						<li><span id="offer-timer"></span></li>
					</ul>
				</div>
				<button class="btn btn-success btn-lg btn-block btnGetIt">Get
					it now!</button>

			</div>
		</div>
	</div>
	<div class="row " style="margin-top: 20px;">
		<div class="col-md-6 col-lg-6  ">
			<div>
				<h1>Basically</h1>
				<ul style="margin-left: 20px;">
					<li>contains1</li>
					<li>contains2</li>
					<li>contains3</li>
				</ul>

			</div>
		</div>
		<div class="col-md-6 col-lg-6 ">
			<div>
				<h1>Good to Know</h1>
				<ul style="margin-left: 20px;">
					<li>contains1</li>
					<li>contains2</li>
					<li>contains3</li>
				</ul>

			</div>
		</div>
	</div>



	<div id="article_description1">
		<h1>Description</h1>
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

	<div class="row" style="margin-bottom: 10px;">

		<div class="col-md-6 col-lg-6 ">
			<h1>Address</h1>
			<p>
				${fieldValue(bean: offerInstance, field: "address")}
			</p>
			<p>justin@hotmail.com</p>
		</div>

		<div class="col-md-6 col-lg-6 ">
			<h1>Opening Hours</h1>
				<p>Opening hour 1</p>
				<p>Opening hour 2</p>
		</div>
	</div>

	<div class="row" style="margin-bottom: 10px;">

		<div class="col-md-6 col-lg-6 ">
			<h1>Telephone</h1>
			<p>70546756</p>
		</div>

		<div class="col-md-6 col-lg-6 ">
			<h1>Rating</h1>
				<div id="content1">
					<g:ratingToStars rating="${offerInstance.rating}" />
				</div>

		</div>
	</div>


	<div class="row" style="margin-bottom: 10px;">

		<div class="col-md-6 col-lg-6 "  style="margin-bottom: 10px;">
				<button class="btn btn-info btn-lg">Send a message <span class="glyphicon glyphicon-envelope"></span></button>
		</div>

		<div class="col-md-6 col-lg-6 "  style="margin-bottom: 10px;">
			<button class="btn btn-primary btn-lg" style = "background-color:grey;">Go to their Page <span class="glyphicon glyphicon-arrow-right"></span></button>
		</div>
	</div>

	<%--	<div id="article_Like_Button1">--%>
	<%--		<p>Facebook Like </p>--%>
	<%--	</div>--%>

	<div id="article_map"></div>

	<script type="text/javascript">
	    $( window ).ready(function() {
	    	loadGoogleMap(${offerLocation.lat},${offerLocation.lng},'${offerInstance.title}');
		});
    </script>

	<div id="article_Comments1">
		<p>Comments Section</p>
	</div>

</div>
