<title>ログイン</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
	<div class="mb-8 bold ta-c"><a href="#">本サービスは広告収益のため<br/>無料でご利用頂けます。</a></div>
    <div class="c-gw-c">
		<p class="c-alert-d icon-info-sign">
			外部サービスのアカウントを使って<@s.text name="app.site.name" />にログインできます。<br/>
			また、ログインし本サービスお使いいただく際は、<a href="${url('/terms/index')}">本サービスの利用規約</a>に同意されたものとみなします。
		</p>
	</div>
	<ul class="c-sns-login-menu">
		<li class="btn-fb"><a class="icon-facebook-sign" href="${url('/fb-user/login', 'true', 'true')}">Facebookでログイン</a></li>
		<li class="btn-tw"><a class="icon-twitter-sign" href="${url('/tw-user/login', 'true', 'true')}">Twitterでログイン</a></li>
	</ul>
</section>