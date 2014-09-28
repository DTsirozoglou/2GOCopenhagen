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

<div id="SideBarBottomTitle2" class="hidden-lg"><p class="btn_sidebar_title">NEW OFFERS</p></div> 
<!--  LEFT BAR2 ON MOBILE -->
<div id="SideBarBottomContent" class="hidden-lg">
	<g:each in="${offersInstanceList}" status="i" var="offerInstance">
		<div id="contentSideBar_mobile">
			<div id="eventimageSideBar_mobile">
				<a href="${createLink(controller: 'Offer', action: 'index')}?singleOfferCall=${offerInstance.id}"><img src="${resource(dir: 'images', file: 'bieber.jpg')}" /></a>
			</div>
			<div id="eventdescriptionSideBar_mobile">
				<p>${fieldValue(bean: offerInstance, field: "title")}</p>
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
