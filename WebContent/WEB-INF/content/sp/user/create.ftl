<title>ユーザー登録</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
    <h1 class="c-pt icon-plus">ユーザー登録(無料)</h1>
    <p class="u-tmp-r-d">
ユーザー登録をするには、まずは以下の事項を入力して頂き「仮登録をする」ボタンを押して下さい。<br/>
入力されたメールアドレス宛に、本登録に必要なURLを記載したメールをお送りします。<br />
メールの本文に記載された本登録へのURLをクリックすると会員登録が完了します。
    </p>

    <div class="btn-op icon-question-sign u-btn-p-m-h"><a href="${url('/help/read-ajax/1')}">迷惑メール防止設定</a></div>

	<form action="${url('/user/create')}" method="POST">
		<@s.token />
		<dl class="c-input-item">
			<dt class="c-input-item-n">メールアドレス</dt>
			<dd>
				<@s.textfield name="userForm.mailAddress" placeholder="メールアドレス" />
				<@s.fielderror><@s.param value="%{'userForm.mailAddress'}" /></@s.fielderror>
			</dd>
		</dl>
		<dl class="c-input-item">
			<dt class="c-input-item-n">パスワード</dt>
			<dd>
				<@s.password name="userForm.password" placeholder="パスワード" />
				<@s.fielderror><@s.param value="%{'userForm.password'}" /></@s.fielderror>
				<span class="c-input-item-d">※6文字以上12文字以内で半角英数字のみ使用可能</span>
			</dd>
		</dl>
		<dl class="c-input-item">
			<dt class="c-input-item-n">パスワード(確認)</dt>
			<dd>
				<@s.password name="userForm.passwordCon" placeholder="パスワード" />
				<@s.fielderror><@s.param value="%{'userForm.passwordCon'}" /></@s.fielderror>
			</dd>
		</dl>
		<div class="c-input-item">
			<@s.checkbox name="userForm.agree" />
			<span class="c-input-item-n">利用規約に同意する</span>
			<@s.fielderror><@s.param value="%{'userForm.agree'}" /></@s.fielderror>
		</div>
		<div class="icon-circle-arrow-right u-link-items" ><a class="c-dl" href="${url('/terms/index')}"><@s.text name="app.site.name"/>利用規約はこちら</a></div>

		<div class="c-input-item">
			<@s.submit value="仮登録する"/>
		</div>
	</form>
</section>

<section class="c-s">
    <h1 class="c-pt icon-facebook-sign">Facebookアカウントでユーザー登録</h1>
    <p>Facebookアカウントを使ってユーザー登録をすることもできます</p>
    <div class="c-l-fbu-create icon-arrow-right">
        <a class="c-dl" href="${url('/fb-user/create')}">Facebookアカウント>で簡単ユーザー登録はこちら</a>
    </div>
</section>
