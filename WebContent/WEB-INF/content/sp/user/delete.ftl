<title>退会</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
	<form action="${url('/user/delete')}" method="POST" class="c-gw-c mb-8 c-wrap-c">
		<@s.token />
		<dl class="c-input-item c-gw-c">
			<dt class="c-input-item-n">退会理由</dt>
			<dd>
				<@s.select key="userSecessionForm.reasonCode" list="userSecessionForm.reasonCodes"/>
				<@s.fielderror><@s.param value="%{'userSecessionForm.reasonCode'}" /></@s.fielderror>
			</dd>
		</dl>
		<dl class="c-input-item c-gw-c">
			<dt class="c-input-item-n">自由入力</dt>
			<dd>
				<@s.textarea name="userSecessionForm.description" label="自由入力" rows="6" />
				<@s.fielderror><@s.param value="%{'userSecessionForm.description'}" /></@s.fielderror>
			</dd>
		</dl>
		<div class="c-input-item">
			<@s.submit value="退会する" />
		</div>
	</form>
</section>
