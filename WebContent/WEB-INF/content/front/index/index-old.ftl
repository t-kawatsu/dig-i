<div class="t-sec-s clearfix">
  <img src="${assets('logo-200.png', 'images', 'common')}" alt="dig-i" width="200" height="134" class="fl"  />
  <p>
<s.text name="app.site.name" />(ディギー)はおすすめニュース配信<br/>サービスです。<br/>
様々なサイトのRSSや検索情報をもとに、あなたが興味を持つニュースや情報を日々みつけ配信します。
  </p>
</div>

<div class="t-sec-image">
<div class="t-sec-s clearfix">
  <p class="t-sec-image-c">まずはあなたが興味あるキーワードを登録しましょう！</p>
  <img src="${assets('lbl-tags.png', 'images')}" alt="ワード" width="115" height="125" class="t-img-tag" />
</div>

<div class="t-sec-s clearfix">
  <img src="${assets('lbl-service-image.png', 'images')}" alt="サービスイメージ" width="99" height="127" class="t-img-service" />
  <p class="t-p-2"><s.text name="app.site.name" />(ディギー)はさまざまな
情報サイトからあなたが興味ありそうな<br/>ニュースや情報を日々見つけ配信してくれます。
  </p>
</div>
</div>

<p class="t-notice-no-support">
<i class="icon-exclamation-sign" style="color:#FF7F50"></i>ご利用環境には対応されていません。<br/>
スマートフォン(iPhone, Android, Windows mobileなど)でご利用下さい。
</p>
<#if successSendMail!false > 
<p class="t-msg-send"><i class="icon-ok-sign"></i>送信しました</p>
<#else>
<form action="${url('send', 'true', 'true')}" method="POST" class="t-fm-msg-send">
  <p>ここからスマートフォンのメールアドレスを入力し送信すると<br/>入力されたメールアドレス宛にサイトのURLをメールで送信します。</p>
  <@s.fielderror />
  <@s.textfield name="mailForm.account" maxlength="100" size="40" /><span style="font-size:16px">@</span><@s.select key="mailForm.domain" list="mailForm.domains" />

<div class="btn t-btn-send-app-url">
  <a href="#" class="icon-envelope">メールでサイトのURLを送る</a>
  <script type="text/javascript">
    $('.t-btn-send-app-url a').on('click', function(e){
        e.preventDefault();
        $(this).parents('form').trigger('submit');
    });
  </script>
</div>
</form>
</#if>

<div class="social_widgets">
	<#include "../../sp/common/_twitter-tweet.ftl">
	<#include "../../sp/common/_facebook-like.ftl">
</div>

