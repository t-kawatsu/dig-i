<title>${resource.title + " | " + resource.type}</title>

<link href="${assets('resource.css', 'css')}?_=20130415" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
  <div class=" c-gw-c mb-8">
    <div class="c-wrap-c mb-8" style="padding:0px;">
      <div class="r-m">
        <h2 class="r-t mb-8">
	      <#if resource.imageUrl?? >
            <img class="r-i" src="${resource.imageUrl}" />
	      </#if>
	      ${resource.title?html}
        </h2>
        <div>
	      <@my.resourceIcon resourceType=resource.type />
		  <@my.date date=resource.lastBuildedAt />
	    </div> 
        <p class="r-d">${resource.description?html}</p>
      </div>
      
      <ul class="r-ctrl clearfix r-ctrl-menu">
		<#include '../user-favorite-resource/_favorite.ftl' />
        <li class="r-ctrl-menu-border-r"><a href="#">&nbsp;</a></li>
        <li><a class="icon-external-link" href="${resource.link}" target="_blank">サイトへ</a></li>
      </ul>
    </div>
  </div>
  
  <div class="c-gw-c mb-8">
	<#assign moreUrl = url('/resource/read-more-ajax/id/${resource.id?c}') />
	<#assign noMoreItems = noMoreItems>
	<#include "../common/_information-items.ftl">
  </div>
</section>
