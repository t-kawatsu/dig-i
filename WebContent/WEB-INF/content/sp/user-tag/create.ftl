<title>ワード登録</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
  <div class="c-gw-c c-wrap-c">
    <form action="${url()}" method="POST">
		<@s.token />
		<dl class="c-input-item">
			<dt class="c-input-item-n">ワードを入力して下さい。</dt>
			<dd>
				<@s.textfield name="tagForm.name" placeholder="ワード" />
				<@s.fielderror><@s.param value="%{'tagForm.name'}" /></@s.fielderror>
				<div class="p-4">※登録可能なワードは<@s.text name="app.tag.regist.limit" />個までです。</div>
			</dd>
		</dl>
		<div class="c-input-item">
			<@s.submit value="登録する"/>
		</div>
	</form>
  </div>
</section>
