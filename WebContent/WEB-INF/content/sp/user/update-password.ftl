<title>パスワード変更</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
	<form action="${url('/user/update-password')}" method="POST" class="c-gw-c mb-8 c-wrap-c">
		<@s.token />
		<dl class="c-input-item c-gw-c">
			<dt class="c-input-item-n">パスワード</dt>
			<dd>
				<@s.password name="userPasswordForm.password" placeholder="パスワード" />
				<@s.fielderror><@s.param value="%{'userPasswordForm.password'}" /></@s.fielderror>
				<span class="c-input-item-d p-4">※6文字以上12文字以内で半角英数字のみ使用可能</span>
			</dd>
		</dl>
		<dl class="c-input-item c-gw-c">
			<dt class="c-input-item-n">パスワード(確認)</dt>
			<dd>
				<@s.password name="userPasswordForm.passwordCon" placeholder="パスワード" />
				<@s.fielderror><@s.param value="%{'userPasswordForm.passwordCon'}" /></@s.fielderror>
			</dd>
		</dl>
		<div class="c-input-item">
			<@s.submit value="変更する"/>
		</div>
	</form>
</section>
