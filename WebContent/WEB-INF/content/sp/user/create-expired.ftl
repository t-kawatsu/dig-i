<title>ユーザー登録</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
    <h1 class="c-pt icon-plus">会員登録(無料)</h1>
    <p class="icon-ng-sign bold">登録出来ませんでした。</p>
    <p class="uc-expired-d c-err-d">
有効期限切れ、または既に登録されている可能性があります。<br/>
    <span class="icon-circle-arrow-right"><a class="c-dl" href="${url('/user/send-password')}">パスワードを忘れた方はこちら</a></span>
    </p>
    <div class="btn btn-bt">
        <a href="${url('/')}" >TOPへ戻る</a>
    </div>
</section>
