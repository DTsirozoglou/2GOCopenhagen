
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

<div id="SideBarBottomTitle2"><p class="btn_sidebar_title">NEW OFFERS</p></div> 

<!--  Right BAR2 ON Desktop -->
<div id="SideBarBottomContent" class="visible-lg">
	<g:each in="${offersInstanceList}" status="i" var="offerInstance">
		<div id="contentSideBar">
			<div id="eventimageSideBar">
				<a href="events_single.html" target="_blank"><img src="${resource(dir: 'images', file: 'bieber.jpg')}" /></a>
			</div>
			<div id="eventdescriptionSideBar">
				<p>${fieldValue(bean: offerInstance, field: "title")}</p>
			</div>
		</div>
	</g:each>
</div>

<div id="SideBarBottomTitle2"><p class="btn_sidebar_title">NEW PLACES</p></div> 

<!--  Right BAR2 ON Desktop -->
<div id="SideBarBottomContent" class="visible-lg">
	<g:each in="${guideInstanceList}" status="i" var="guideInstance">
		<div id="contentSideBar">
			<div id="eventimageSideBar">
				<a href="events_single.html" target="_blank"><img src="${resource(dir: 'images', file: 'bieber.jpg')}" /></a>
			</div>
			<div id="eventdescriptionSideBar">
				<p> ${fieldValue(bean: guideInstance, field: "title")} </p>
			</div>
		</div>
	</g:each>
</div>