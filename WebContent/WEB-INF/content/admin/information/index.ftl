<#assign contents = "information" />

<#include '../common/_pager.ftl' />
<div class="table">
    <ol class="c-list-h tr">
        <li class="information-id th">id</li>
        <li class="information-resource-id th">resource-id</li>
        <li class="information-title th">title</li>
        <li class="information-link th">link</li>
        <li class="information-description th">description</li>
        <li class="information-image-url th">image-url</li>
        <li class="information-created-at th">created-at</li>
    </ol>
	<#list pager.items as row>
    <ol class="c-list-b tr">
        <li class="information-id td">${row.id}</li>
        <li class="information-resource-id td">${row.resourceId}</li>
        <li class="information-title td">${row.title?html}</li>
        <li class="information-link td">${row.link}</li>
        <li class="information-description td">${row.description?html}</li>
        <li class="information-image-url td">${row.imageUrl!""}</li>
        <li class="information-created-at td">${row.createdAt?string("yyyy年MM月dd日 H時")}</li>
    </ol>
	</#list>
</div>
