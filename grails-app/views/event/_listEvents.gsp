<div class="row">
	<g:each in="${eventInstanceList}" status="i" var="eventInstance">
		<div class="col-md-6 col-lg-4 ">
			<div id="content">
				<div id="eventimage">
					<img data-location="${eventInstance.id}" data-location2="${eventInstance.category.id}"
						class="singleEventInstance"
						src="${resource(dir: 'images', file: 'bieber.jpg')}" />
				</div>
				<div id="eventdescription">

					<h1>
						${fieldValue(bean: eventInstance, field: "title")}
					</h1>
					<h2>
						<g:formatDate date="${eventInstance.startDate}" />
					</h2>
					<p>
						${fieldValue(bean: eventInstance, field: "description").substring(0, 250)+'...'}
					</p>

				</div>
			</div>
		</div>
	</g:each>
</div>

<!--  Pagination -->
<div id="#paginationLine">
	<util:remotePageScroll controller="event" action="paginate"
		total="${eventInstanceCount}" update="container" />
</div>
