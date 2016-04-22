<div class=" c-gw-c mb-8">
<#if !userTags?? || userTags?size == 0>
<p class="c-no-items c-wrap-c mb-8">登録はありません。</p>
<#else>
<ul class="c-t-items">
<#list userTags as row>
	<#assign isUpdated = !row.lastReadedAt?? || row.lastReadedAt < row.informationUpdatedAt>
    <li class="js-cl clearfix <#if deletable!false>c-dc</#if> <#if isUpdated>updated</#if>">
        <a class="c-item-n" href="${url('/user-tag-information/index/user-tag-id/${row.id?c}')}">${row.tag.name?html}
		<br/><span style="font-size:12px"><@my.date date=row.informationUpdatedAt />更新</span></a>
        <div class="c-item-d-l icon-chevron-right"></div>
		<#if deletable!false>
        <a class="btn-d icon-remove-sign" href="${url('/user-tag/delete-ajax/id/${row.id?c}')}" >削除</a>
		</#if>
    </li>
</#list>
</ul>
</#if>
</div>