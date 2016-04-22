<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >
<#assign title = "Facebookアカウントで登録" >

<section class="c-s">
    <h1 class="c-pt icon-facebook-sign">Facebookアカウントで登録</h1>
    <p class="icon-ng-sign bold">登録出来ませんでした。</p>
    <p class="uc-expired-d c-err-d">既に登録されています。<br/>
    <span class="icon-circle-arrow-right"><a class="c-dl" href="${url('/fb-user/login')}">Facebookアカウントでログイン</a></span>
    </p>
    <div class="btn-bt btn">
        <a href="${url('/')}" >TOPへ戻る</a>
    </div>
</section>
