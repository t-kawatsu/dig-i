<#if !resources?? || resources?size == 0>
<p class="c-no-items c-wrap-c mb-8">登録はありません。</p>
<#else>
<ul class="c-items">
	<#list resources as row >
    <li class="js-cl clearfix <#if deletable!false>c-dc</#if>">
      <div class="clearfix fl" style="width:95%;">
        <div class="c-item-i">
		  <a href="${url('/resource/read/' + row.id )}">
		    <#if row.imageUrl?? >
		    <img src="${resourceImage(row.id, 's')}" alt="" width="48" height="48" />
		    <#else>
		    <img src="${assets('no-image.png', 'images')}" alt="no image" width="48" height="48" />
		    </#if>
		  </a>
        </div>
        <div>
            <p class="bold c-item-d-t">
            	<@my.resourceIcon resourceType=row.type />
            	<a href="${url('/resource/read/' + row.id?c )}">${cutStr(row.title, 23)?html}</a>
            </p>
			<div>
			  <@my.date date=row.lastBuildedAt />
			</div>
	    </div>
	  </div>
      <div class="c-item-d-l icon-chevron-right"></div>
	  <#if deletable!false>
        <a class="btn-d icon-remove-sign" href="${url('/user-favorite-resource/delete-ajax/resource-id/' + row.id?c )}" >削除</a>
	  </#if>
    </li>   
	</#list>
</ul>   
</#if>
