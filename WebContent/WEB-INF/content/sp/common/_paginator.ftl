<nav class="c-pager-c clearfix">
<div class="c-pager-cd">${pager.firstIndice}~${pager.lastIndice}&nbsp;/&nbsp;${pager.total}</div>
<ul class="clearfix">
<!-- 前のページへのリンク -->
<#if pager.hasPrevPage>
<li class="btn-prev"><a class="icon-chevron-left" href="${url()+'?page='+pager.prevPage}"><span class="">Prev</span></a></li>
<#else>
<li class="btn-prev btn-d-pager"><a class="icon-chevron-left" href="#" onclick="return false"><span class="">Prev</span></a></li>
</#if>
 
<!-- 次のページへのリンク -->
<#if pager.hasNextPage>
<li class="btn-next"><a class="icon-chevron-right-right" href="${url()+'?page='+pager.nextPage}"><span>Next</span></a></li>
<#else>
<li class="btn-next btn-d-pager"><a class="icon-chevron-right-right" href="#" onclick="return false"><span>Next</span></a></li>
</#if>
</ul>
</nav>
