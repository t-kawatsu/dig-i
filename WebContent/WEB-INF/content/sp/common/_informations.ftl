<#list informations as row>
<li class="js-cl clearfix <#if deletable!false>c-dc</#if>">
  <div class="clearfix fl" style="width:95%;">
    <div class="c-item-i">
      <a href="${url('/information/read/' + row.id)}">
      <#if row.imageUrl?? >
        <img src="${informationImage(row.id, 's')}" alt="" width="48" height="48" />
      <#else>
        <img src="${assets('no-image.png', 'images')}" alt="no image" width="48" height="48" />
      </#if>
      </a>
    </div>
	<div>
		<p class="bold c-item-d-t"><a href="${url('/information/read/${row.id?c}')}">${cutStr(row.title, 23)?html}</a></p>
		<#-- <@my.date date=row.publishedAt /> -->
		<div class="c-color-g">
			<@my.resourceIcon resourceType=row.resource.type />
			${cutStr(row.resource.title, 13)?html}
        </div>
	</div>
  </div>
  <div class="c-item-d-l icon-chevron-right"></div>
  <#if deletable!false>
    <a class="btn-d icon-remove-sign" href="${url('/user-favorite/delete-ajax/information-id/${row.id?c}')}" >削除</a>
  </#if>
</li>
</#list>

<#if noMoreItems!false>
<script type="text/javascript">
    $('.btn-more').hide();
</script>
</#if>
