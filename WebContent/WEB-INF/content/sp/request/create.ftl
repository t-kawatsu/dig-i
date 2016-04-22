<title>ご意見・ご要望</title>
<section class="c-s">
    <div class="c-gw-c mb-8"> 
      <p class="c-wrap-c">
<@s.text name='app.site.name'/>をより良くするために、ご意見・ご要望をお聞かせください。<br/>
※お問い合わせにおきましては<a href="${url('/inquiry/create')}">お問い合わせフォーム</a>からおよせください。
      </p>
    </div>
	<@s.fielderror />
	<form action="${url('/request/create')}" method="POST">
		<@s.token />
		<dl class="c-input-item">
			<dt></dt>
			<dd class="c-gw-c">
				<@s.textarea name="requestForm.description" placeholder='〇〇のサイトのRSSを追加してほしい！' rows="6" />
			</dd>
		</dl>
		<div class="c-input-item">
			<@s.submit value="送信" />
		</div>
	</form>
</section>
