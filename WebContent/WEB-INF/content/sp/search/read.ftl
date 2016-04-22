<title>${word!""}</title>

<link href="${assets('search.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
  <div class="c-gw-c">
    <div class="search_head">
        <h2 class="c-pt search_word icon-search"><b>${word!""?html}</b>&nbsp;の検索結果</h2>
    </div>
	<#assign moreUrl = url('/search/read-more-ajax') + "?word=" + word?url />
	<#include "../common/_information-items.ftl"/>
	<#if isLogined >
    <div class="btn mt-8 mb-8">
        <a href="${url('/user-tag/create')}?word=${word?url}">このワードを登録する</a>
    </div>
	</#if>
  </div>
</section>

<#include "../common/_search.ftl" />
