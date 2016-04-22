<title>ユーザー登録</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
    <h1 class="c-pt icon-plus">仮登録完了</h1>
    <p class="icon-ok-sign">仮登録が完了しました。</p>
    <p class="ucc-tmp-d">
<span class="bold">${tmpUser.mailAddress?html}</span>宛に本登録に必要なURLを記載したメールを送信しま
した。<br />
メールの本文に記載されている本登録のためのURLをクリックするとユーザー登録が完了します。<br />
    </p>
    <p class="c-err-d">
仮登録後24時間を過ぎると本登録が出来なくなるのでご注意ください。<br/>
本登録をせずに24時間を過ぎた場合は、再度仮登録からやり直してください。
    </p>
    <div class="btn-op icon-question-sign u-btn-p-m-h"><a href="${url('/help/read-ajax/1')}">メールが届かない?</a></div>
</section>

