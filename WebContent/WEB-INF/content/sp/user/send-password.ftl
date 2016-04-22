<title>パスワード送信</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
    <h1 class="c-pt icon-key">パスワード送信</h1>
    <p style="padding-bottom:6px">
ご登録いただいたメールアドレス宛にパスワードを記載し送信いたします。<br />
なお、パスワードの変更はログイン後、パスワード変更画面から出来ます。
    </p>
	<form action="${url()}" method="POST">
		<@s.token />
		<dl class="c-input-item">
			<dt class="c-input-item-n">メールアドレス</dt>
			<dd>
				<@s.textfield name="sendPasswordForm.mailAddress" placeholder="メールアドレス" />
				<@s.fielderror><@s.param value="%{'sendPasswordForm.mailAddress'}" /></@s.fielderror>
				<div>※会員登録の際に登録されたメールアドレスを入力して下さい。</div>
			</dd>
		</dl>
		<div class="c-input-item">
			<@s.submit value="パスワード送信"/>
		</div>
	</form>
</section>
