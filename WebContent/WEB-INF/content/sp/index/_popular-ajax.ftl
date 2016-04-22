<h1 class="c-sh bold icon-tags c-sh-h-icon">人気ワード</h1>
<#--
<#include "../common/_tag-items.ftl" />
-->
<ul class="t-ptags c-ots clearfix">
<#list userTags as row>
    <li><a class="icon-tag" href="${url('/tag-information/index/tag-id/${row.tagId}')}">${row.tag.name?html}</a></li>
</#list>
</ul>
<#assign noMoreItems = true />
<#include "../common/_information-items.ftl" />
