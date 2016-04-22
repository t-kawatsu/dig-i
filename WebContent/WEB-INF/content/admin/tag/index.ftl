
<#include '../common/_pager.ftl' />
<div class="table">
    <ol class="c-list-h tr">
        <li class="tag-id th">id</li>
        <li class="tag-name th">name</li>
        <li class="tag-created-at th">created-at</li>
    </ol>
	<#list pager.items as row>
    <ol class="c-list-b tr">
        <li class="tag-id td">${row.id}</li>
        <li class="tag-name td">${row.name?html}</li>
        <li class="tag-created-at td">${row.createdAt?string("yyyy年MM月dd日 H時")}</li>
    </ol>
	</#list>
</div>
