
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

<div id="SideBarBottomTitle2"><p class="btn_sidebar_title">NEW EVENTS</p></div> 

<!--  Right BAR2 ON Desktop -->
<div id="SideBarBottomContent" class="visible-lg" >
	<g:each in="${eventsInstanceList}" status="i" var="eventInstance">
		<div id="contentSideBar">
			<div id="eventimageSideBar">
				<a href="${createLink(controller: 'Event', action: 'index')}?singleEventCall=${eventInstance.id}"><img src="${resource(dir: 'images', file: 'bieber.jpg')}" /></a>
			</div>
			<div id="eventdescriptionSideBar">
				<p>${fieldValue(bean: eventInstance, field: "title")} </p>
			</div>
		</div>
	</g:each>
</div>


<div id="SideBarBottomTitle2"><p class="btn_sidebar_title">NEW OFFERS</p></div> 

<!--  Right BAR2 ON Desktop -->
<div id="SideBarBottomContent" class="visible-lg" >
	<g:each in="${offersInstanceList}" status="i" var="offerInstance">
		<div id="contentSideBar">
			<div id="eventimageSideBar">
				<a href="${createLink(controller: 'Offer', action: 'index')}?singleOfferCall=${offerInstance.id}"><img src="${resource(dir: 'images', file: 'bieber.jpg')}" /></a>
			</div>
			<div id="eventdescriptionSideBar">
				<p>${fieldValue(bean: offerInstance, field: "title")}</p>
			</div>
		</div>
	</g:each>
</div>



