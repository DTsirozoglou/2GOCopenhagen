<div id="SideBarBottomTitle"><p class="btn_sidebar_title">More From ${articleCategoryTitle}</p></div>

<!--  LEFT BAR ON DESKTOP -->
<div id="SideBarBottomContent" class="visible-lg">
	<g:each in="${articleInstanceList}" status="i" var="articleInstance">
		<div id="contentSideBar">
			<div id="eventimageSideBar">
				<g:contentCall contentClass="${articleInstance}" contentID="${fieldValue(bean: articleInstance, field: "id")}"/>
			</div>
			<div id="eventdescriptionSideBar">
				<p>Title : ${fieldValue(bean: articleInstance, field: "title")}</p>
				<p>Views : ${fieldValue(bean: articleInstance, field: "views")}</p>
			</div>
		</div>
	</g:each>
</div>

<!--  LEFT BAR ON MOBILE -->
<div id="SideBarBottomContent" class="hidden-lg">
	<g:each in="${articleInstanceList}" status="i" var="articleInstance">
		<div id="contentSideBar_mobile">
			<div id="eventimageSideBar_mobile">
				<g:contentCall contentClass="${articleInstance}" contentID="${fieldValue(bean: articleInstance, field: "id")}"/>
			</div>
			<div id="eventdescriptionSideBar_mobile">
				<p>Title : ${fieldValue(bean: articleInstance, field: "title")}</p>
				<p>Views : ${fieldValue(bean: articleInstance, field: "views")}</p>
			</div>
		</div>
	</g:each>
</div>

