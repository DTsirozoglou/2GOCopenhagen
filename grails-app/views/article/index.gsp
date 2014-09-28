<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="layout" content="main" />
<title>Articles</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/blitzer/jquery-ui.css"/>
<link rel="stylesheet" href="${resource(dir: 'css/css_custom', file: 'Article.css')}" />
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script type="text/javascript">
		window.appContext = "";
	    $( window ).ready(function() {
	    	window.appContext='${request.contextPath}';
		});
</script>
<script src="${resource(dir: 'js', file:'articles.js')}" type="text/javascript"></script>
</head>

<body>

	<!--  MAIN PAGE - MAIN SECTION -->
	<div id="mainsection">
		<!--  LEFT BAR CONTENT -->
		<div id="leftbar">

			<div id="SideBarTop">
				<g:render template="categoryButtons" />
			</div>
			
			<div id="SideBarBottomLeft">
				<g:if test="${articleInstance == null}" >
					<g:render template="leftSideBarContent" />
				</g:if>		
				<g:else>
					<g:render template="leftSideBarContentForSingleArticle" />
				</g:else>	
			</div>

		</div>

		<!--  EVENT CONTENT ON DESKTOP -->
		<div id="container">
			<g:if test="${articleInstance == null}" >
				<div id="SideBarBottomTitle" style="margin-bottom:20px;"><p class="btn_sidebar_title">WHAT'S HOT</p></div>
				<g:render template="listArticles" />
			</g:if>		
			<g:else >
				<g:render template="singleArticle" />
			</g:else>
		</div>

		<!--  RIGHT BAR CONTENT -->
		<div id="rightbar" class="visible-lg">

			<div id="SideBarBottomRightArticle">
				<g:if test="${articleInstance == null}" >
					<g:render template="rightSideBarContent" />
				</g:if>		
				<g:else>
<%--					<g:render template="rightSideBarContentForSingleArticle" />--%>
				</g:else>
			</div>

		</div>
	</div>
	<script type="text/javascript">
	    $( window ).ready(function() {
	    	setActiveCategory(${selectedCategory});
		});
</script>

</body>
</html>


