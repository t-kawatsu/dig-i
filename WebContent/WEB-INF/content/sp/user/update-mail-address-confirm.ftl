<title>メールアドレス変更</title>

<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
    <div class="c-gw-c mb-8"> 
    	<p class="c-wrap-c mb-8">
			<span class="c-clear-d icon-ok">入力されたメールアドレスの所有者か確認のために&nbsp;
			<span class="bold">${userMailAddressForm.mailAddress?html}</span>&nbsp;宛にメールをお送りいたしました。</span><br />
		  <span class="red">
		    まだ変更は完了していません。
		  </span><br/>
		  メールに記載された内容に沿い、メールアドレスの変更を完了させて下さい。<br />
		  変更処理はログイン状態が保持されている間有効です。<br/>
		  もしアクセスしても有効期限が切れているなどの理由でメールアドレスが変更できなかった場合は
		  再度メールアドレス変更画面からやり直してください。
    	</p>
	</div>
    <div class="c-gw-c mb-8">
		<#include "../common/_notice-mail-block.ftl" />
    </div>
</section>
