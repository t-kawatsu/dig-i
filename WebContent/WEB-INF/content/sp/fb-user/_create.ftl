<section class="c-s">
    <h1 class="c-pt icon-facebook-sign">Facebookアカウントで登録</h1>
    <p style="padding-bottom:12px;">
Facebookアカウントでユーザー登録をすると、次回からFacebookアカウントでログインすることができます。
    </p>
    <form action="${url('/fb-user/create')}" method="POST">
		<@s.token />
		<div class="c-input-item">
			<@s.checkbox name="fbUserForm.agree" />
			<span class="c-input-item-n">利用規約に同意する</span>
			<@s.fielderror><@s.param value="%{'fbUserForm.agree'}" /></@s.fielderror>
		</div>
		<div class="icon-circle-arrow-right u-link-items" ><a class="c-dl" href="${url('/terms/index')}"><@s.text name="app.site.name"/>利用規約はこちら</a></div>
		<div class="c-input-item">
			<@s.submit value="Facebookアカウントで登録"/>
		</div>
    </form>
</section>
