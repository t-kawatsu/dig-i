<@compress single_line=true>
<!DOCTYPE html>
<html lang="ja" xmlns="http://www.w3.org/1999/xhtml" xml:lang="ja" xmlns:og="http://ogp.me/ns#" xmlns:fb="http://www.facebook.com/2008/fbml">
<head>
<meta charset="UTF-8" />
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<meta http-equiv="Content-Style-Type" content="text/css" >
<meta http-equiv="Content-Script-Type" content="text/javascript" >
<meta name="keywords" content="<@s.text name="app.site.name"/>,ディギー,ディギー,ディグアイ,おすすめニュース,おすすめ,RSS,情報,jogoj" >
<meta name="description" content="<@s.text name="app.site.name"/>(ディギー)はおすすめニュース配信サービスです。様々なサイトのRSSや検索情報をもとに、あなたが興味を持つニュースや情報をリコメンド配信します。" >
<meta name="author" content="jogoj Inc." >
<meta name="copyright" content="Copyright &amp;copy;jogoj Inc." >
<link href="${assets('jquery.fancybox.css', 'css', 'common')}" media="screen, print" rel="stylesheet" type="text/css" >
<link href="${assets('font-awesome.css', 'css', 'common')}" media="screen" rel="stylesheet" type="text/css" >
<link href="${assets('default.css', 'css', 'front')}?_=20130416" media="screen, print" rel="stylesheet" type="text/css" >
<link href="${assets('common.css', 'css', 'front')}?_=20130416" media="screen, print" rel="stylesheet" type="text/css" >
<link href="${assets('favicon.gif', 'images', 'common')}" rel="icon" type="image/gif" >
<link href="${assets('favicon.gif', 'images', 'common')}" rel="shortcut icon" type="image/gif" >
<link href="/" rel="index" title="トップ" >
<script type="text/javascript" src="${assets('jquery-1.7.2.min.js', 'js', 'common')}"></script>
<title><@s.text name="app.site.name"/> (ディギー) | おすすめニュース配信サービス</title>

<meta property="og:title" content="${title!"dig-i (ディギー)"?html} | おすすめニュース配信サービス"/>
<meta property="og:image" content="${url('', 'true')}${assets('icn-facebook-like.png', 'images', 'common')}"/>
<meta property="og:url" content="${url('', 'true')}"/>
<meta property="og:site_name" content="<@s.text name="app.site.name"/> (ディギー)"/>
<meta property="og:type" content="website"/>
<meta property="og:app_id" content="<@s.text name="app.facebook.appId"/>"/>

<meta name="google-site-verification" content="<@s.text name='app.google.siteVerificationMeta' />" />
</head>

<body>
  <div id="container">
    <div id="wrapper">
      <div id="contents">
        <div id="${pathinfo('controller')}-${pathinfo('action')}" class="${pathinfo('controller')}-con">
			${body}
        </div>
        <hr class="footer-sep" />
        <footer id="footer">
          <nav class="clearfix">
            <ul class="clearfix fl footer-nav">
              <li><a href="<@s.text name='app.company.url' />" target="_blank">運営会社</a></li>
              <li><a href="${url('/terms/index')}">利用規約</a></li>
              <li><a href="${url('/terms/privacy')}">プライバシー</a></li>
              <li><a href="${url('/inquiry/create-ajax', 'true', 'true')}" class="modal">お問い合わせ</a></li>
            </ul>
            <address class="fr">dig-i.com</address>
          </nav>
        </footer>
      </div>  
    </div>  
  </div>
  
  <script type="text/javascript" src="${assets('jquery.fancybox.pack.js', 'js', 'common')}" ></script>
  <script type="text/javascript" src="${assets('my.js', 'js')}?_=20130416"></script>

  <#include "../../sp/common/_twitter-root.ftl">
  <#include "../../sp/common/_facebook-root.ftl">
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