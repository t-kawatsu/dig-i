<title>ワード登録</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
  <form action="#" method="GET" class="c-gw-c mb-8 c-wrap-c">
	<dl class="c-input-item c-gw-c">
	  <dt class="c-input-item-n">以下のワードで登録されます。</dt>
		<dd>
		<@s.textfield name="tagForm.name" placeholder="ワード" readonly=true />
		</dd>
	</dl>
	<div class="c-input-item">
		<div class="btn mb-8"><a href="${url('/user-tag/create-complete')}">登録する</a></div>
		<div class="btn"><a href="${url('/user-tag/create')}?word=${tagForm.name?url}">戻る</a></div>
	</div>
  </form>
  
  <#if proxTags?? && 0 < proxTags?size >
  <div class="c-gw-c">
      <div class="c-alert-d icon-exclamation-sign mb-8">もしかして？</div>    
      <ul class="c-t-items">
		<#list proxTags as row>
        <li class="clearfix">
            <div>${row.name?html}</div>
            <div class="ut-ptag-r-l"><a href="${url('/user-tag/create')}?word=${row.name?url}">このワードを登録する</a>
            </div>
        </li>
		</#list>
      </ul>
  </div>
  </#if>
</section>
