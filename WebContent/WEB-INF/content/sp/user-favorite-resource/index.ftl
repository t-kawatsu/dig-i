<title>お気に入り一覧</title>

<section class="c-s">
  <div class=" c-gw-c mb-8">
    <#if pager.items?? && 0 < pager.items?size>
	  <#include "../common/_paginator.ftl" />
	  <#assign resources = pager.items />
	  <#assign deletable = true />
	  <#include "../common/_resource-items.ftl" />
      <div class="mt-8">
	    <#include "../common/_paginator.ftl" />
      </div>
    <#else>
      <p class="c-no-items c-wrap-c mb-8">登録はありません。</p>
    </#if>
  </div>
    <div class="btn u-menu-btn-wrap">
        <a class="icon-rss" href="${url('/resource/index')}">RSS一覧</a>
    </div>
</section>
