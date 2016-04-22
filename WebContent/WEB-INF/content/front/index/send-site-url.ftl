<section id="t-fm-msg-send-con">

<form action="${url('/index/send-site-url-ajax', 'true', 'true')}#t-fm-msg-send-con" method="POST" class="t-fm-msg-send ajax-form">
  <p style="line-height:27px;">
    入力されたメールアドレス宛にメールを送信いたします。<br/>
    メールに記載された本サイトのURLにスマートフォンからアクセスして下さい。
  </p>
  <div class="t-input-mailaddress-con">
    <@s.textfield name="mailForm.account" maxlength="100" size="40" placeholder="@より前" /><span style="margin:0px 3px;">@</span><@s.select key="mailForm.domain" list="mailForm.domains" />
    <@s.fielderror />
  </div>

  <div class="btn t-btn-send-app-url">
    <input type="submit" class="icon-envelope btn-submit" value="携帯にサイトのURLを送る"/>
  </div>
</form>

</section>