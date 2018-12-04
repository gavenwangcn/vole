<#macro paginate currentPage totalPage actionUrl urlParas="">
	<#if (totalPage <= 0) || (currentPage > totalPage)><#return></#if>
	<#local startPage = currentPage - 4>
	<#if (startPage < 1)><#local startPage = 1></#if>
	
	<#local endPage = currentPage + 4>
	<#if (endPage > totalPage)><#local endPage = totalPage></#if>
	
	<ul class="pagination pagination-sm no-margin pull-right">
			<#if (currentPage <= 8)>
				<#local startPage = 1>
			</#if>
			<#if ((totalPage - currentPage) < 8)>
				<#local endPage = totalPage>
			</#if>
			
			<#if (currentPage == 1)>
				 <li><a href="javascript:void(0)">&laquo;</a></li>
			<#else>
				 <li><a href="${actionUrl}#{currentPage - 1}${urlParas!}" >&laquo;</a></li>
			</#if>
			
			<#if (currentPage > 8)>
			     <li><a href="${actionUrl}#{1}${urlParas!}" >#{1}</a></li>
			     <li><a href="${actionUrl}#{2}${urlParas!}" >#{2}</a></li>
			     <li><a href="javascript:void(0)" class="page-more">...</a></li>
			</#if>
			
			<#list startPage..endPage as i>
				<#if currentPage == i>
				      <li class="active"><a href="javascript:void(0)" >#{i}</a></li>
				<#else>
					  <li><a href="${actionUrl}#{i}${urlParas!}">#{i}</a></li>
				</#if>
			</#list>
			
			<#if ((totalPage - currentPage) >= 8)>
				 <li><a href="javascript:void(0)" >...</a></li>
				 <li><a href="${actionUrl}#{totalPage - 1}${urlParas!}" >#{totalPage - 1}</a></li>
				 <li><a href="${actionUrl}#{totalPage}${urlParas!}" >#{totalPage}</a></li>
			</#if>
			<#if (currentPage == totalPage)>
				 <li><a href="javascript:void(0)">&raquo;</a></li>
			<#else>
				 <li><a href="${actionUrl}#{currentPage + 1}${urlParas!}" >&raquo;</a></li>
			</#if>
	</ul>
</#macro>