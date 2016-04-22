<title>ブックマーク一覧</title>

<section class="c-s">
  <div class=" c-gw-c mb-8">
    <#if pager.items?? && 0 < pager.items?size>
	  <#include "../common/_paginator.ftl" />
	  <#assign informations = pager.items />
	  <#assign noMoreItems = true />
	  <#assign deletable = true />
	  <#include "../common/_information-items.ftl" />
      <div class="mt-8">
	    <#include "../common/_paginator.ftl" />
      </div>
    <#else>
      <p class="c-no-items c-wrap-c mb-8">登録はありません。</p>
    </#if>
  </div>
</section>
