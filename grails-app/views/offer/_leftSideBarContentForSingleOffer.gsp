<div id="SideBarBottomTitle"><p class="btn_sidebar_title">MOST POPULAR</p></div>

<!--  LEFT BAR ON DESKTOP -->
<div id="SideBarBottomContent" class="visible-lg">
	<g:each in="${popularInstanceList}" status="i" var="popularInstance">
		<div id="contentSideBar">
			<div id="eventimageSideBar">
				<g:contentCall contentClass="${popularInstance}" contentID="${fieldValue(bean: popularInstance, field: "id")}"/>
			</div>
			<div id="eventdescriptionSideBar">
				<p>Title : ${fieldValue(bean: popularInstance, field: "title")}</p>
				<p>Views : ${fieldValue(bean: popularInstance, field: "views")}</p>
			</div>
		</div>
	</g:each>
</div>

<!--  LEFT BAR ON MOBILE -->
<div id="SideBarBottomContent" class="hidden-lg">
	<g:each in="${popularInstanceList}" status="i" var="popularInstance">
		<div id="contentSideBar_mobile">
			<div id="eventimageSideBar_mobile">
				<g:contentCall contentClass="${popularInstance}" contentID="${fieldValue(bean: popularInstance, field: "id")}"/>
			</div>
			<div id="eventdescriptionSideBar_mobile">
				<p>Title : ${fieldValue(bean: popularInstance, field: "title")}</p>
				<p>Views : ${fieldValue(bean: popularInstance, field: "views")}</p>
			</div>
		</div>
	</g:each>
</div>

<!--  LEFT BAR ON MOBILE (RIGHT BAR ON DESKTOP) -->

<div id="SideBarBottomTitle2" class="hidden-lg"><p class="btn_sidebar_title">NEW STUFF</p></div>
<!--  LEFT BAR1 ON MOBILE -->
<div id="SideBarBottomContent" class="hidden-lg">
	<g:each in="${newStuffInstanceList}" status="i" var="newStuffInstance">
		<div id="contentSideBar_mobile">
			<div id="eventimageSideBar_mobile">
				<g:contentCall contentClass="${newStuffInstance}" contentID="${fieldValue(bean: newStuffInstance, field: "id")}"/>
			</div>
			<div id="eventdescriptionSideBar_mobile">
				<p>${fieldValue(bean: newStuffInstance, field: "title")}</p>
			</div>
		</div>
	</g:each>
</div>

<div id="SideBarBottomTitle2" class="hidden-lg"><p class="btn_sidebar_title">NEW EVENTS</p></div> 
<!--  LEFT BAR2 ON MOBILE -->
<div id="SideBarBottomContent" class="hidden-lg">
	<g:each in="${eventsInstanceList}" status="i" var="eventInstance">
		<div id="contentSideBar_mobile">
			<div id="eventimageSideBar_mobile">
				<a href="${createLink(controller: 'Event', action: 'index')}?singleEventCall=${eventInstance.id}"><img src="${resource(dir: 'images', file: 'bieber.jpg')}" /></a>
			</div>
			<div id="eventdescriptionSideBar_mobile">
				<p>${fieldValue(bean: eventInstance, field: "title")}</p>
			</div>
		</div>
	</g:each>
</div>

<div id="SideBarBottomTitle2" class="hidden-lg"><p class="btn_sidebar_title">NEW PLACES</p></div> 
<!--  LEFT BAR2 ON MOBILE -->
<div id="SideBarBottomContent" class="hidden-lg">
	<g:each in="${guideInstanceList}" status="i" var="guideInstance">
		<div id="contentSideBar_mobile">
			<div id="eventimageSideBar_mobile">
				<a href="${createLink(controller: 'Guide', action: 'index')}?singleGuideCall=${guideInstance.id}"><img src="${resource(dir: 'images', file: 'bieber.jpg')}" /></a>
			</div>
			<div id="eventdescriptionSideBar_mobile">
				<p> ${fieldValue(bean: guideInstance, field: "title")} </p>
			</div>
		</div>
	</g:each>
</div>

<div id="SideBarBottomTitle2" class="hidden-lg"><p class="btn_sidebar_title">NEW ARTICLES</p></div> 
<!--  LEFT BAR2 ON MOBILE -->
<div id="SideBarBottomContent" class="hidden-lg">
	<g:each in="${articleInstanceList}" status="i" var="articleInstance">
		<div id="contentSideBar_mobile">
			<div id="eventimageSideBar_mobile">
				<a href="${createLink(controller: 'Article', action: 'index')}?singleArticleCall=${articleInstance.id}"><img src="${resource(dir: 'images', file: 'bieber.jpg')}" /></a>
			</div>
			<div id="eventdescriptionSideBar_mobile">
				<p> ${fieldValue(bean: articleInstance, field: "title")} </p>
			</div>
		</div>
	</g:each>
</div>
