<title>${information.title}</title>

<link href="${assets('information.css', 'css')}?_=20130415" media="screen, print" rel="stylesheet" type="text/css" />

<section class="c-s">
  <div class=" c-gw-c mb-8">
    <div class="c-wrap-c mb-8" style="padding:0px;">
      <div class="i-d">
        <h2 class="clearfix">
	      <#if information.imageUrl??>
            <img class="i-i" src="${informationImage(information.id, 's')}" alt="" width="48" height="48" />
	      </#if>
	      ${information.title?html}
        </h2>
        <div class="i-r-t">
		  <@my.resourceIcon resourceType=information.resource.type />
		  ${information.resource.title?html}
          &nbsp;&nbsp;<@my.date date=information.publishedAt />
        </div>
      </div>

      <ul class="i-ctrl clearfix i-ctrl-menu">
		<#include '../user-favorite/_bookmark.ftl' />
        <li class="i-ctrl-menu-border-r"><a class="icon-list" href="${url('/resource/read/${information.resource.id?c}')}">情報一覧</a></li>
        <li><a class="icon-external-link" href="${information.link}" target="_blank">詳細</a></li>
      </ul>
    </div>
  </div>
  
  <div class="c-gw-c mb-8">
     <div class="i-ctrl-sws">
		  <#include "../common/_twitter-tweet.ftl">
		  <#include "../common/_facebook-like.ftl">
     </div>
  </div>
  
  <div class=" c-gw-c mb-8">
    <div class="c-wrap-c mb-8">
      <div class="i-body">
	    ${stripTags(information.description)?replace("\n", "<br/>")}
      </div>
    </div>
  </div>
</section>

<section class="c-s">
    <h3 class="c-sh bold">関連ワード</h3>
    <div class=" c-gw-c mb-8">
      <div class="c-wrap-c mb-8">
      <ul class="i-rws">
	    <#list relateWords as row>
        <li><a href="${url('/search/read')}?word=${row?url}" >${row?html}</a></li>
	    </#list>
      </ul>
      </div>
    </div>
</section>

<#include "../common/_twitter-root.ftl">
<#include "../common/_facebook-root.ftl">
