<title>メール送信設定変更</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
    <h1 class="c-pt icon-wrench">メール送信設定変更</h1>
    <p> 
新しいニュースがでたらメールにてお知らせします。<br />
そのお知らせメールの頻度を設定することができます。
    </p>
	<form action="${url()}" method="POST">
		<@s.token />
		<dl class="c-input-item">
			<dt class="c-input-item-n">お知らせメール送信頻度</dt>
			<dd>
				<@s.radio key="userMailNoticeFrequencyCodeForm.mailNoticeFrequencyCode" 
					list="userMailNoticeFrequencyCodeForm.mailNoticeFrequencyCodes" />
				<@s.fielderror><@s.param value="%{'userMailNoticeFrequencyCodeForm.mailNoticeFrequencyCode'}" /></@s.fielderror>
			</dd>
		</dl>
		<div class="c-input-item">
			<@s.submit value="変更する"/>
		</div>
	</form>
</section>
