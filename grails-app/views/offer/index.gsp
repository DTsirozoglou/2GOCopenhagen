<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="layout" content="main" />
<title>Offers</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/blitzer/jquery-ui.css" />
<link rel="stylesheet"
	href="${resource(dir: 'css/css_custom', file: 'Offer.css')}" />
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script type="text/javascript">
		window.appContext = "";
	    $( window ).ready(function() {
	    	window.appContext='${request.contextPath}';
		});
</script>
<script src="${resource(dir: 'js', file:'offers.js')}"
	type="text/javascript"></script>
	<script src="${resource(dir: 'js', file:'countdown.js')}"
	type="text/javascript"></script>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp"
	type="text/javascript"></script>

</head>

<body>

	<!--  MAIN PAGE - MAIN SECTION -->
	<div id="mainsection">
		<!--  LEFT BAR CONTENT -->
		<div id="leftbar">

			<div id="SideBarTop">
				<g:render template="categoryButtons" />
			</div>
			<div id="districtMap" class="hidden-lg">
				<g:render template="offersMap" />
			</div>
			<div id="SideBarBottomLeft">
				<g:if test="${offerInstance == null}">
					<g:render template="leftSideBarContent" />
				</g:if>
				<g:else>
					<g:render template="leftSideBarContentForSingleOffer" />
				</g:else>
			</div>

		</div>

		<!--  EVENT CONTENT ON DESKTOP -->
		<div id="container">
			<g:if test="${offerInstance == null}">
				<g:render template="listOffers" />
			</g:if>
			
			<g:else>
				<g:render template="singleOffer" />
			</g:else>
		</div>

		<!--  RIGHT BAR CONTENT -->
		<div id="rightbar" class="visible-lg">
			
			<div id="districtMap">
				<g:render template="offersMap" />
			</div>
			
			<div id="SideBarBottomRight">
				<g:if test="${offerInstance == null}">
					<g:render template="rightSideBarContent" />
				</g:if>
				<g:else>
					<g:render template="rightSideBarContentForSingleOffer" />
				</g:else>
			</div>

		</div>
	</div>
	<script type="text/javascript">
	    $( window ).ready(function() {
	    	setActiveCategory(${selectedCategory});
	    	setActiveSuburb("${selectedSuburb}");
		});
	</script>
</body>
</html>


