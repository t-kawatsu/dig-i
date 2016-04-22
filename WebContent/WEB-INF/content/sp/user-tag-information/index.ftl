<title>${userTag.tag.name + " | ワード"}</title>

<link href="${assets('tag-information.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" />

<section class="c-s" >
  <div class="c-gw-c">
    <div class="c-wrap-c">
      <h2 class="icon-tag ti-t-name">${userTag.tag.name?html}</h2>
      <div class="ti-menu clearfix">
        <dl class="ti-register clearfix c-color-g">
            <dt>登録者数</dt>
            <dd><b>${cntTagRegisteredUsers?c}</b>人</dd>
        </dl>
      </div>
    </div>
  </div>
</section>
<section class="c-s">
	<h2 class="c-sh bold">${userTag.tag.name?html}&nbsp;に関する情報</h2>
	<div class=" c-gw-c mb-8">
	  <#assign sortUrl = url('/user-tag-information/read-type-ajax/user-tag-id/${userTag.id?c}') />
	  <#include "../common/_informations-sort-menu.ftl"/>
      <div class="js-result-con">
		<#assign moreUrl = url('/user-tag-information/more-ajax/user-tag-id/${userTag.id?c}/resource-type/${resourceType!""}') />
		<#include "../common/_information-items.ftl"/>
      </div>
    </div>
</section>
<section>
	<#if relateTags?? && 0 < relateTags?size>
    <h3 class="c-sh bold icon-tags">おすすめワード</h3>
    <div class=" c-gw-c mb-8">
	  <#assign tags = relateTags />
	  <#include "../common/_tag-items.ftl" />
	</div>
	</#if>
</section>
<#include "../common/_search.ftl" />
<#include "../common/_twitter-root.ftl" />
