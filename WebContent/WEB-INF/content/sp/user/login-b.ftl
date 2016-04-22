<title>ログイン</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
    <h1 class="c-pt icon-signin">ログイン</h1>
	<form action="${url('/user/login')}" method="POST">
		<@s.token />
		<dl class="c-input-item">
			<dt class="c-input-item-n">メールアドレス</dt>
			<dd>
				<@s.textfield name="loginForm.mailAddress" placeholder="メールアドレス" />
				<@s.fielderror><@s.param value="%{'loginForm.mailAddress'}" /></@s.fielderror>
			</dd>
		</dl>
		<dl class="c-input-item">
			<dt class="c-input-item-n">パスワード</dt>
			<dd>
				<@s.password name="loginForm.password" placeholder="パスワード" />
				<@s.fielderror><@s.param value="%{'loginForm.password'}" /></@s.fielderror>
				<div class="icon-circle-arrow-right" style="margin-top:12px"><a class="c-dl" href="${url('/user/send-password')}">パスワードをお忘れですか？</a></div>
			</dd>
		</dl>
		<div class="c-input-item">
			<@s.submit value="ログイン"/>
		</div>
	</form>
</section>

<section class="c-s">
    <h1 class="c-pt icon-facebook-sign">Facebookアカウントでログイン</h1>
    <div class="btn" style="padding:12px">
        <a class="icon-facebook-sign" href="${url('/fb-user/login')}">ログイン</a>
	</div>
</section>
