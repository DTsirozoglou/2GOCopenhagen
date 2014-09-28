					
<g:each status="category_id" var="i" in="${ ['All','Arts','City Life','Business','Academic','Leisure Time'] }">

	<div id="categoryButton" class="btn-group">
		<g:if test="${category_id == selectedCategoryOfSingleArticle}" >
			<g:set var="statusOfButton" value= "active" />
		</g:if>
		<g:else >
			<g:set var="statusOfButton" value= "" />
		</g:else>	
		<button data-location='${category_id}' class=" ${statusOfButton} btn btn-info btn_custom btn_${category_id} ">
			${i}
		</button>
		
		<button data-toggle="dropdown" class="btn dropdown-toggle dropdown_custom dropdown-${category_id}-custom"><span class="caret"></span></button>
		<ul class="dropdown-menu dropdown-${category_id}-menu">
			<li><a href="#">Festivals</a></li>
		</ul>
	</div>	

</g:each>
					
					
					
					
					
			
					