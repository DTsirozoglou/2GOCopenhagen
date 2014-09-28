
<div id="SideBarBottomTitle"><p class="btn_sidebar_title">NEW STUFF</p></div>

<!--  Right BAR1 ON Desktop -->

<div id="SideBarBottomContent" class="visible-lg">
	<g:each in="${newStuffInstanceList}" status="i" var="newStuffInstance">
		<div id="contentSideBar">
			<div id="eventimageSideBar">
					<g:contentCall contentClass="${newStuffInstance}" contentID="${fieldValue(bean: newStuffInstance, field: "id")}"/>
			</div>
			<div id="eventdescriptionSideBar">
				<p> ${fieldValue(bean: newStuffInstance, field: "title")} </p>
			</div>
		</div>
	</g:each>
</div>


<div id="SideBarBottomTitle2" class="visible-lg"><p class="btn_sidebar_title">NEW EVENTS</p></div> 
<!--  LEFT BAR2 ON MOBILE -->
<div id="SideBarBottomContent" class="visible-lg">
	<g:each in="${eventsInstanceList}" status="i" var="eventInstance">
		<div id="contentSideBar">
			<div id="eventimageSideBar">
				<a href="${createLink(controller: 'Event', action: 'index')}?singleEventCall=${eventInstance.id}"><img src="${resource(dir: 'images', file: 'bieber.jpg')}" /></a>
			</div>
			<div id="eventdescriptionSideBar">
				<p>${fieldValue(bean: eventInstance, field: "title")}</p>
			</div>
		</div>
	</g:each>
</div>

<div id="SideBarBottomTitle2" class="visible-lg"><p class="btn_sidebar_title">NEW PLACES</p></div> 
<!--  LEFT BAR2 ON MOBILE -->
<div id="SideBarBottomContent" class="visible-lg">
	<g:each in="${guideInstanceList}" status="i" var="guideInstance">
		<div id="contentSideBar">
			<div id="eventimageSideBar">
				<a href="${createLink(controller: 'Guide', action: 'index')}?singleGuideCall=${guideInstance.id}"><img src="${resource(dir: 'images', file: 'bieber.jpg')}" /></a>
			</div>
			<div id="eventdescriptionSideBar">
				<p> ${fieldValue(bean: guideInstance, field: "title")} </p>
			</div>
		</div>
	</g:each>
</div>

<div id="SideBarBottomTitle2" class="visible-lg"><p class="btn_sidebar_title">NEW ARTICLES</p></div> 
<!--  LEFT BAR2 ON MOBILE -->
<div id="SideBarBottomContent" class="visible-lg">
	<g:each in="${articleInstanceList}" status="i" var="articleInstance">
		<div id="contentSideBar">
			<div id="eventimageSideBar">
				<a href="${createLink(controller: 'Article', action: 'index')}?singleArticleCall=${articleInstance.id}"><img src="${resource(dir: 'images', file: 'bieber.jpg')}" /></a>
			</div>
			<div id="eventdescriptionSideBar">
				<p> ${fieldValue(bean: articleInstance, field: "title")} </p>
			</div>
		</div>
	</g:each>
</div>