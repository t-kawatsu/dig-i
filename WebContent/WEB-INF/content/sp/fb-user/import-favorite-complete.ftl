<link href="${assets('user.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >
<#assign title = "Facebook情報登録" >

<section class="c-s">
    <h2 class="c-pt">Facebook取得情報一覧</h2>
    <ul>
	<#list tags as row>
        <li>${row?html}</li>
	</#list>
    </ul>
</section>
