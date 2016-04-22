<#assign contents = "resource" />
<#include "../common/_contents-header.ftl" />

<#include '../common/_pager.ftl' />
<div class="table">
    <ol class="c-list-h tr">
        <li class="resource-id th">id</li>
        <li class="resource-title th">title</li>
        <li class="resource-description th">description</li>
        <li class="resource-last-builded-at th">last-builded-at</li>
        <li class="resource-type th">type</li>
        <li class="resource-status th">status</li>
        <li class="resource-cause-connect-cnt th">cause-connect-cnt</li>
    </ol>
	<#list pager.items as row>
    <ol class="c-list-b tr">
        <li class="resource-id td"><a href="${url('/resource/update/' + row.id)}">${row.id}</a></li>
        <li class="resource-title td"><a href="${row.link}">${row.title?html}</a></li>
        <li class="resource-description td">${row.description?html}</li>
        <li class="resource-last-builded-at td">${row.lastBuildedAt?string("yyyy年MM月dd日 H時")}</li>
        <li class="resource-type td">${row.type}</li>
        <li class="resource-status td">${row.status}</li>
        <li class="resource-cause-connect-cnt td">${row.causeConnectCnt}</li>
    </ol>
	</#list>
</div>
