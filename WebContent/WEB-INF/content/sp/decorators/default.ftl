<@compress single_line=true>
<!DOCTYPE html>
<html lang="ja" xmlns="http://www.w3.org/1999/xhtml" xml:lang="ja" xmlns:og="http://ogp.me/ns#" xmlns:fb="http://www.facebook.com/2008/fbml">
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta name="keywords" content="<@s.text name="app.site.name"/>,ディギー,ディグアイ,おすすめニュース,おすすめ,RSS,情報,jogoj" />
<meta name="description" content="<@s.text name="app.site.name"/>(ディギー)はおすすめニュース配信サービスです。様々なサイトのRSSや検索情報をもとに、あなたにオススメのニュースや情報を配信します。" />
<meta name="author" content="jogoj Inc." />
<meta name="copyright" content="Copyright &amp;copy;jogoj Inc." />
<!-- // iphone 横幅: 縦持ち320px 横持ち480px -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<meta name="format-detection" content="telephone=no" /><!-- iphoneで自動でつく電話番号へのリンク動作の無効化 -->
<link href="${assets('font-awesome.css', 'css', 'common')}" media="screen" rel="stylesheet" type="text/css" />
<link href="${assets('default.css', 'css')}?_=20130413" media="screen, print" rel="stylesheet" type="text/css" />
<link href="${assets('common.css', 'css')}?_=20130415" media="screen, print" rel="stylesheet" type="text/css" />
<link href="${assets('favicon.gif', 'images', 'common')}" rel="icon" type="image/gif" />
<link href="${assets('favicon.gif', 'images', 'common')}" rel="shortcut icon" type="image/gif" />
<link href="${assets('apple-touch-icon.png', 'images')}" rel="apple-touch-icon" />
<link href="/" rel="index" title="トップ" />

<script type="text/javascript" src="${assets('jquery-1.7.2.min.js', 'js', 'common')}"></script>
<title>${cutStr(title, 15)?html} | <@s.text name="app.site.name"/> (ディギー)</title>

<meta property="og:title" content="${title!"dig-i (ディギー)"?html}"/>
<meta property="og:image" content="${url('', 'true')}${assets('icn-facebook-like.png', 'images', 'common')}"/>
<meta property="og:url" content="${url('', 'true')}"/>
<meta property="og:site_name" content="<@s.text name="app.site.name"/> (ディギー)"/>
<meta property="og:type" content="website"/>
<meta property="og:app_id" content="<@s.text name="app.facebook.appId"/>"/>

<meta name="google-site-verification" content="<@s.text name='app.google.siteVerificationMeta' />" />
</head>

<body <#if isLogined!false>logined="yes"</#if> >
  <div id="container">
    <div id="wrapper">
      <div id="header-con">
      <header id="header" class="clearfix">
        <div id="c-site-id" class="ch-border-r" ><a href="${url('/')}"><img src="${assets('lbl-top-01-logo.png', 'images')}" alt="dig-i" width="38" height="26" /></a></div>
        <nav id="c-site-nav">
        <ul class="c-h-menu clearfix">
			<#if isLogined!false>
            <li class="ch-border-l"><a href="${url('/user/update')}" class="icon-cogs">設定</a></li>
			<#else>
			<li class="ch-border-l"><a class="icon-home" href="${url('/user/login')}">ログイン</a></li>
			</#if>
        </ul>
        </nav>
        <h1 class="c-title">${cutStr(title, 9)?html}</h1>
      </header>
      <#if isLogined!false>
      <nav class="c-my-menu">
      	<ul class="clearfix">
        	<li class="<#if pathinfo('controller') == 'index'>on</#if> cm-border-r">
        		<a href="${url('/')}"><img src="${assets('icon-home.png', 'images')}" />トップ</a>
        	</li>
        	<li class="<#if pathinfo('controller') == 'user-tag'>on</#if> cm-border-r">
        		<a href="${url('/user-tag/index')}"><img src="${assets('icon-tag.png', 'images')}" />ワード</a>
        	</li>
        	<li class="<#if pathinfo('controller') == 'user-favorite-resource'>on</#if> cm-border-r">
        		<a href="${url('/user-favorite-resource/index')}"><img src="${assets('icon-favorite.png', 'images')}" />お気に入り</a>
        	</li>
        	<li class="<#if pathinfo('controller') == 'user-favorite'>on</#if> cm-border-r">
        		<a href="${url('/user-favorite/index')}"><img src="${assets('icon-bookmark.png', 'images')}" />ブックマーク</a>
        	</li>
      	</ul>
      </nav>
      </#if>
      </div>
      <div id="contents">
        <div id="${pathinfo('controller')}-${pathinfo('action')}" class="${pathinfo('controller')}-con">
			${body}
        </div>
      </div>
      <hr/>
      <footer id="footer" class="clearfix">
        <nav>
          <a class="link-ft" href="${url('/')}"><img src="${assets('lbl-top-01-logo.png', 'images')}" alt="<@s.text name="app.site.name"/>" width="57" height="39" /></a>
          <ul class="c-cl">
			<#if isLogined!false >
            <li><a href="${url('/user/logout')}">ログアウト</a></li>
			<#else>
            <li><a href="${url('/user/login')}">ログイン</a></li>
			</#if>
            <li><a href="${url('/request/create', 'true', 'true')}">ご意見・ご要望</a></li>
            <li><a href="${url('/help/index')}">ヘルプ</a></li>
          </ul>
          <ul class="c-cl">
            <li><a href="${url('/inquiry/create', 'true', 'true')}">お問い合わせ</a></li>
            <li><a href="${url('/terms/index')}">利用規約</a></li>
            <li><a href="${url('/terms/privacy')}">プライバシー</a></li>
          </ul>
          <hr/>
		  <div class="copyright">
            <a href="<@s.text name='app.company.url' />" target="_blank">Copyright &copy;jogoj inc.</a>
          </div>
        </nav>
      </footer>
    </div>
  </div>

  <script type="text/javascript" src="${assets('my.js', 'js')}?_=20130415" ></script>

  <script type="text/javascript">
    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-31427166-2']);
    _gaq.push(['_trackPageview']);

    (function() {
      var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
      ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
      var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
    })();
  </script>
</body>
</html>
</@compress>