<title>ワード一覧</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
<#assign deletable = true />
<#include "../common/_user-tag-items.ftl" />
<div class="btn">
    <a href="${url('/user-tag/create')}">ワードを登録する</a>
</div>
<#if currentUser.isFacebookUser()>
<div class="c-gw-c mb-8 mt-8">
<div class="c-alert-d icon-exclamation-sign">Facebookからあなたが興味のあるワードを手軽に取得する事が出来ます。</div>
</div>
<div class="btn-fb mt-8"><a class="icon-facebook-sign" href="${url('/fb-user/import-favorite', 'true', 'true')}">Facebookから取得</a></div>
</#if>
</section>
