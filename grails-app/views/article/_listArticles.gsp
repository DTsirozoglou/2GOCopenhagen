
<div class="row">
	<g:each in="${articleInstanceList}" status="i" var="articleInstance">
		<div class="col-lg-12 ">
			<div id="content">
				<div id="eventimage">
					<img data-location="${articleInstance.id}" data-location2="${articleInstance.category.id}"
						class="singleArticleInstance"
						src="${resource(dir: 'images', file: 'bieber.jpg')}" />
				
					<div id="eventdescription">
	
						<h1>
							${fieldValue(bean: articleInstance, field: "title")}
						</h1>
						<h2>
							<g:formatDate date="${articleInstance.dateCreated}" />
						</h2>
						<p>
	<%--						${fieldValue(bean: articleInstance, field: "description").substring(0, 250)+'...'}--%>
						</p>
	
					</div>
				</div>
			</div>
		</div>
	</g:each>
</div>

<!--  Pagination -->
<div id="#paginationLine">
	<util:remotePageScroll controller="article" action="paginate"
		total="${articleInstanceCount}" update="container" />
</div>
