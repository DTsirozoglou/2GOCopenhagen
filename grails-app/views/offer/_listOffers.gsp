<div class="row">
	<g:each in="${offerInstanceList}" status="i" var="offerInstance">
		<div class="col-md-6 col-lg-6 ">
			<div id="content">
				<div id="eventimage">
					<img data-location="${offerInstance.id}" data-location2="${offerInstance.category.id - 11}" data-location3="${offerInstance.location.title}"
						class="singleOfferInstance"
						src="${resource(dir: 'images', file: 'bieber.jpg')}" />
				</div>
				<div id="eventdescription">

					<h1>
						${fieldValue(bean: offerInstance, field: "title")}
					</h1>
					<h2>
						<g:formatDate date="${offerInstance.startDate}" />
					</h2>
					<p>
						${offerInstance.description}
					</p>

				</div>
			</div>
		</div>
	</g:each>
</div>

<!--  Pagination -->
<div id="#paginationLine">
	<util:remotePageScroll controller="offer" action="paginate"
		total="${offerInstanceCount}" update="container" />
</div>

