<title>お問い合わせ</title>
<section class="c-s">
	<form action="${url('/inquiry/create')}" method="POST" class="c-gw-c mb-8 c-wrap-c">
		<@s.token />
		<dl class="c-input-item c-gw-c">
			<dt class="c-input-item-n">お問い合わせ項目</dt>
			<dd>
				<@s.select key="inquiryForm.typeId" list="inquiryForm.types"/>
				<@s.fielderror><@s.param value="%{'inquiryForm.typeId'}" /></@s.fielderror>
			</dd>
		</dl>
		<dl class="c-input-item c-gw-c">
			<dt class="c-input-item-n">メールアドレス</dt>
			<dd>
				<@s.textfield name="inquiryForm.mailAddress" />
				<@s.fielderror><@s.param value="%{'inquiryForm.mailAddress'}" /></@s.fielderror>
			</dd>
		</dl>
		<dl class="c-input-item c-gw-c">
			<dt class="c-input-item-n">お問い合わせ内容</dt>
			<dd>
				<@s.textarea name="inquiryForm.description" label="お問い合わせ内容" rows="6" />
				<@s.fielderror><@s.param value="%{'inquiryForm.description'}" /></@s.fielderror>
			</dd>
		</dl>
		<div class="c-input-item">
			<@s.submit value="送信する" />
		</div>
	</form>
</section>

