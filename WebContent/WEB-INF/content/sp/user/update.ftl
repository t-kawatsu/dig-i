<title>ユーザー情報変更</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
  <h2 class="c-sh">ユーザー情報</h2>
  <div class="c-gw-c mb-8"> 
    <dl class="u-cndt c-wrap-c">
        <dt class="bold icon-envelope">メールアドレス</dt>
        <dd class="clearfix">
            <div class="fl">${currentUser.mailAddress!""}</div>
            <div class="btn-s fr"><a class="icon-envelope" href="${url('/user/update-mail-address')}">変更</a></div>
        </dd>
		<#if currentUser.properUser>
        <dt class="bold icon-key">パスワード</dt>
        <dd class="clearfix">
        	<#if currentUser.password??>
            <div class="fl">
			${maskPassword(currentUser.password, '●')}<br />
            <p>※セキュリティのため一文字'●'に変換し表示されています。</p>
            </div>
            </#if>
            <div class="btn-s fr"><a class="icon-key" href="${url('/user/update-password')}">変更</a></div>
        </dd>
		</#if>
        <dt class="bold icon-bullhorn">メール送信設定</dt>
        <dd class="clearfix">
            <div class="fl">${languageSetting("C001", currentUser.mailNoticeFrequencyCode, 'ja')}</div>
            <div class="btn-s fr"><a class="icon-bullhorn" href="${url('/user/update-mail-notice-frequency-code')}">変更</a></div>
        </dd>
    </dl>
  </div>
</section>
