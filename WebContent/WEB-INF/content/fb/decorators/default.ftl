<!DOCTYPE html>
<html lang="ja" xmlns="http://www.w3.org/1999/xhtml" xml:lang="ja" xmlns:og="http://ogp.me/ns#" xmlns:fb="http://www.facebook.com/2008/fbml">
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta name="keywords" content="<@s.text name="app.site.name"/>,ディギー,ディギー,ディグアイ,おすすめニュース,おすすめ,RSS,情報,jogoj" />
<meta name="description" content="<@s.text name="app.site.name"/>(ディギー)はおすすめニュース配信サービスです。様々なサイトのRSSや検索情報をもとに、あなたが興味を持つニュースや情報をリコメンド配信します。" />
<meta name="author" content="jogoj Inc." />
<meta name="copyright" content="Copyright &amp;copy;jogoj Inc." />
<link href="${assets('font-awesome.css', 'css', 'common')}" media="screen" rel="stylesheet" type="text/css" />
<link href="${assets('default.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" />
<link href="${assets('common.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" />
<link href="${assets('favicon.gif', 'images', 'common')}" rel="icon" type="image/gif" />
<link href="${assets('favicon.gif', 'images', 'common')}" rel="shortcut icon" type="image/gif" />
<link href="/" rel="index" title="トップ" />

<script type="text/javascript" src="${assets('jquery-1.7.2.min.js', 'js', 'common')}"></script>
<title><@s.text name="app.site.name"/> (ディギー)</title>

<meta property="og:title" content="${title!"dig-i (ディギー)"?html}"/>
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
      <header id="header" class="clearfix">
        <div id="c-site-id" class="ch-border-r" ><a href="${url('/', 'true')}"><img src="${assets('lbl-top-01-logo.png', 'images')}" alt="dig-i" width="38" height="26" /></a></div>
        <nav id="c-site-nav">
        <ul class="c-h-menu clearfix">
        </ul>
        </nav>
      </header>
      <div id="contents">
        <div id="${pathinfo('controller')}-${pathinfo('action')}" class="${pathinfo('controller')}-con">
			${body}
        </div>
      </div>
      <footer id="footer" class="clearfix">
        <nav>
        <a class="link-ft" href="${url('/')}"><img src="${assets('lbl-top-01-logo.png', 'images')}" alt="<@s.text name="app.site.name"/>" width="57" height="39" /></a>
        <ul class="c-cl">
            <li class="cf-border-r"><a href="${url('/request/create', 'true', 'true')}">ご意見・ご要望</a></li>
            <li class="cf-border-r"><a href="${url('/help/index', 'true')}">ヘルプ</a></li>
            <li><a href="${url('/inquiry/create', 'true', 'true')}">お問い合わせ</a></li>
        </ul>
        <ul class="c-cl">
            <li class="cf-border-r"><a href="${url('/terms/index')}">利用規約</a></li>
            <li class="cf-border-r"><a href="${url('/terms/privacy')}">プライバシー</a></li>
            <li><a href="<@s.text name='app.company.url' />" target="_blank">運営会社</a></li>
        </ul>
        </nav>
      </footer>
    </div>
    <div class="copyright">
      <div class="fl"><@s.text name="app.site.domain"/></div>
      <div class="fr">Copyright &copy;jogoj inc.</div>
      <div style="clear:both;"></div>
    </div>
  </div>

<script type="text/javascript" src="${assets('my.js', 'js')}" ></script>

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
