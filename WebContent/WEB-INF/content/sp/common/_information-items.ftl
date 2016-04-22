<#if !informations?? || informations?size == 0>
<p class="c-no-items c-wrap-c mb-8">登録はありません。</p>
<#else>
<ul class="c-items">
<#include "_informations.ftl">
</ul>
<#if !noMoreItems!false>
<div class="btn-more" data-result-container-class="c-items" >
    <a class="icon-chevron-down" href="${moreUrl}">もっとみる</a>
</div>
</#if>
</#if>
