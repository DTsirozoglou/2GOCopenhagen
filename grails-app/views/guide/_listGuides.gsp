<g:if test="${paginate == null}">
			<div id="SideBarBottomTitle" style="margin-bottom:20px;"><p class="btn_sidebar_title">WHAT'S HOT</p></div>
</g:if>

<div class="row">
	<g:each in="${guideInstanceList}" status="i" var="guideInstance">
		<div class="col-md-6 col-lg-6 ">
					<div id="content">
						<div id="guideimage">
							<img data-location="${guideInstance.id}" data-location2="${guideInstance.category.id - 6}" data-location3="${guideInstance.location.title}"
								class="singleGuideInstance"	src="${resource(dir: 'images', file: 'noma.jpg')}"/>
						</div>
				
						<div id="guidedescription">
							
								<h1>${fieldValue(bean: guideInstance, field: "title")}<br><br></h1>
								
								<p>${fieldValue(bean: guideInstance, field: "address")}<p>
								<p> Price range: <br><br></p>
								<g:ratingToStars rating="${guideInstance.rating}"/>
							
						</div>
					</div>
				</div>	
	</g:each>
</div>

<!--  Pagination -->
<div id="#paginationLine">
	<util:remotePageScroll controller="guide" action="paginate" total="${guideInstanceCount}"
	                         update="container"/>
</div>
