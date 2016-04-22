<title>メールアドレス変更</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
	<form action="${url('/user/update-mail-address')}" method="POST" class="c-gw-c mb-8 c-wrap-c">
		<@s.token />
		<dl class="c-input-item c-gw-c">
			<dt class="c-input-item-n">メールアドレス</dt>
			<dd>
				<@s.textfield name="userMailAddressForm.mailAddress" placeholder="メールアドレス" />
				<@s.fielderror><@s.param value="%{'userMailAddressForm.mailAddress'}" /></@s.fielderror>
			</dd>
		</dl>
		<div class="c-input-item">
			<@s.submit value="変更する"/>
		</div>
	</form>
</section>
