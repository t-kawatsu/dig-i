<#if tags?size == 0>
<p class="c-no-items c-wrap-c mb-8">登録はありません。</p>
<#else>
<ul class="c-t-items">
<#list tags as row>
    <li class="js-cl clearfix">
        <a class="c-item-n" href="${url('/tag-information/index/tag-id/${row.id}')}">${row.name?html}</a>
        <br/><span style="font-size:12px"><@my.date date=row.createdAt /></span></a>
        <div class="c-item-d-l icon-chevron-right"></div>
    </li>
</#list>
</ul>
</#if>
