<title>マイページ</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">

    <h2 class="c-sh icon-tags c-sh-h-icon">登録ワード</h2>
	<#assign deletable = true />
	<#include "../common/_user-tag-items.ftl" />
	<#if dispUserTagsNum < cntUserTags >
    <div class="btn u-menu-btn-wrap">
        <a href="${url('/user-tag/index')}">登録ワード一覧へ</a>
    </div>
	<#else>
    <div class="btn u-menu-btn-wrap">
        <a href="${url('/user-tag/create')}">ワードを登録する</a>
    </div>
	</#if>

    <h2 class="c-sh c-sh-h-icon icon-heart-empty">お気に入り情報</h2>
	<#if cntUserFavorite < 1 >
    <p class="c-no-items">
        登録はありません。<br />
        ※情報画面から「お気に入り」ボタンを押すとお気に入り登録出来ます。
    </p>
	<#else>
    <div class="btn u-menu-btn-wrap">
		<a class="icon-list" href="${url('/user-favorite/index')}">お気に入り情報一覧へ</a>
	</div>
	</#if>

    <h2 class="c-sh c-sh-h-icon icon-heart">お気に入りRSS</h2>
	<#if cntUserFavoriteResource < 1 >
    <p class="c-no-items">
        登録はありません。<br />
        ※RSS情報一覧画面から「お気に入り」ボタンを押すとお気に入り登録出来ます。
    </p>
	<#else>
    <div class="btn u-menu-btn-wrap">
		<a class="icon-list" href="${url('/user-favorite-resource/index')}">お気に入りRSS一覧へ</a>
	</div>
	</#if>

    <h2 class="c-sh c-sh-h-icon icon-wrench">ユーザー情報変更</h2>
    <div class="btn u-menu-btn-wrap">
		<a class="icon-wrench" href="${url('/user/update', 'true', 'true')}">ユーザー情報を変更する</a>
	</div>

	<#--
	<#if currentUser.facebookUser >
    <h2 class="c-sh c-sh-h-icon icon-facebook-sign">Facebookから趣味/興味情報を取得する</h2>
    <div class="btn u-menu-btn-wrap">
		<a class="icon-facebook-sign" href="${url('/fb-user/import-favorite')}">趣味/興味を取得</a>
	</div>
	</#if>
	-->

	<#if !currentUser.facebookUser >
    <h2 class="c-sh c-sh-h-icon icon-facebook-sign">Facebookアカウントの同期</h2>
    <p>アカウントを同期すると、次回からFacebookアカウントでもログインできるようになります。</p>
    <p class="c-err-d">既にFacebookアカウントでユーザー登録をされている場合はそのアカウントの登録データは失われてしまいます。</p>
    <div class="btn u-menu-btn-wrap">
		<a class="icon-facebook-sign" href="${url('/fb-user/merge')}">Facebookアカウントを同期する</a>
	</div>
	</#if>

</section>
