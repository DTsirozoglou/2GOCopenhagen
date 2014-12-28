	
<div id="headerSection">	
	<div id="topheader">
		<div class="navbar navbar-fixed-top navbar_custom">

		    <!-- Brand and toggle get grouped for better mobile display -->
		    <div class="navbar-header">
		        <button type="button"  data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
		            <span class="sr-only">Toggle navigation</span>
		            <span class="icon-bar" style="background-color:blue;"></span>
		            <span class="icon-bar" style="background-color:blue;"></span>
		            <span class="icon-bar" style="background-color:blue;"></span>
					<span class="icon-bar" style="background-color:blue;"></span>
		        </button>
		        <a href="#" class="navbar-brand">
					<img  src="${resource(dir: 'images/header_images', file: 'logo.png')}" />
				</a>
		    </div>
		    <!-- Collection of nav links, forms, and other content for toggling -->
		    
		    <div id="navbarCollapse" class="collapse navbar-collapse nav navbar-nav navbar-right">
		        <ul class="nav navbar-nav">
	        		
	        		<sec:ifAllGranted roles="ROLE_ADMIN">
						<li class="dropdown"><g:link class="navigationCircles  "
							controller="admin" action="showPanel"> <span
								class="glyphicon glyphicon-user"></span> Dashboard
						</g:link></li>
						<li class="dropdown"><g:remoteLink
								class="logout dropdown-toggle js-activated" controller="logout"
								method="post" asynchronous="false" onSuccess="location.reload()">
								<span class="glyphicon glyphicon-eye-open"></span> Logout </g:remoteLink></li>
					</sec:ifAllGranted>
			
					<sec:ifAllGranted roles="ROLE_USER">
			
						<li class="dropdown"><g:link class="navigationCircles  "
							controller="profile" action="profile"> <span
								class="glyphicon glyphicon-user"></span> Profile
						 </g:link></li>
						<li class="dropdown" id="menuLogin"><g:remoteLink
								class="logout dropdown-toggle js-activated" controller="logout"
								method="post" asynchronous="false" onSuccess="location.reload()">
								<span class="glyphicon glyphicon-eye-open"></span> Logout </g:remoteLink></li>
					</sec:ifAllGranted>
					
					<sec:ifNotLoggedIn>
						<li class="dropdown" id="menuLogin"><g:link controller= "login" method="post" asynchronous="false"> Login  </g:link></li>
					</sec:ifNotLoggedIn>
		        
		        
		        
		            <li class="hidden-xs " ><a class="home" href="${request.contextPath}/"><img  src="${resource(dir: 'images/header_images', file: 'home.png')}"  /></a></li>
					<li class="visible-xs" ><a class="homeMobile" href="${request.contextPath}/"><img  src="${resource(dir: 'images/header_images', file: 'home.png')}"  /></a></li>
					<li><a class="search"  href="">	<img class="hidden-xs" src="${resource(dir: 'images/header_images', file: 'search.png')}"  /><p class="visible-xs">Search</p></a></li>
		            <li><a class="socialmediaImg"  href="https://www.facebook.com/2gocopenhagen"><img class="hidden-xs" src="${resource(dir: 'images/header_images', file: 'facebook.png')}" /><p class="visible-xs">Facebook</p></a></li>
					<li><oauth:connect provider="twitter" id="twitter-connect-link"><img class="hidden-xs" src="${resource(dir: 'images/header_images', file: 'twitter.png')}"  /></oauth:connect></li>
					<li><a class="socialmediaImg" href=""><img class="hidden-xs" src="${resource(dir: 'images/header_images', file: 'google_plus.png')}"  /><p class="visible-xs">Google Plus</p></a></li>
		        </ul>
		    </div>	
			<!--  LINE DIVIDER -->
			<div id="linedivtop"> <img class="line"  src="${resource(dir: 'images/topmenu_images', file: 'line.png')} "/></div>			
		</div>
	</div>
		


		<!--  MAIN NAVIGATION WITH 4 CIRCLES FOR DESKTOP  -->
	<div id="topmenu" class="hidden-xs">
		
		<div id="events">
			<g:link class="navigationCircles" controller="event" action="index" >
     		<img src="${resource(dir: 'images/topmenu_images', file: '1.png')}" />
			</g:link>
		</div>
		
		<div id="offers">
			<g:link class="navigationCircles" controller="offer" action="index" >
     		<img src="${resource(dir: 'images/topmenu_images', file: '2.png')}"/>
			</g:link>
		</div>  
		
		<div id="articles">	
		 <g:link class="navigationCircles" controller="article" action="index" >
     		<img src="${resource(dir: 'images/topmenu_images', file: '3.png')}"/>
			</g:link>
		</div>  

		<div id="guide">
		 <g:link class="navigationCircles" controller="guide" action="index" >
     		<img src="${resource(dir: 'images/topmenu_images', file: '4.png')}"/>
			</g:link>
		</div>  
	 
	</div>
	
	<!--  MAIN NAVIGATION WITH 4 CIRCLES FOR MOBILE -->

	<div id="topmenu_mobile" class="visible-xs">
		
		<nav class="blocknav">
		
			 <a class="navigationCircles_mobile" href="${request.contextPath}/event/index"><img 
			   src="${resource(dir: 'images/topmenu_images', file: '1.png')}"  /></a>
			 <a  class="navigationCircles_mobile"  href="${request.contextPath}/offer/index"><img 
			   src="${resource(dir: 'images/topmenu_images', file: '2.png')}"/></a> 
			 <a class="navigationCircles_mobile" href="${request.contextPath}/article/index"><img 
			   src="${resource(dir: 'images/topmenu_images', file: '3.png')}"/></a> 
			 <a class="navigationCircles_mobile" href="${request.contextPath}/guide/index"><img 
			  src="${resource(dir: 'images/topmenu_images', file: '4.png')}" /></a>
		</nav>
	</div>


	<!--  LINE DIVIDER -->	
	<div id="linediv"> <img class="line"  src="${resource(dir: 'images/topmenu_images', file: 'line.png')} " /></div>
</div>
		