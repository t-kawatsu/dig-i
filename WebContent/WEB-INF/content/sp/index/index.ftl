<title>おすすめニュース配信サービス</title>

<link href="${assets('index.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >
<#if !isLogined>
<section class="t-sitems-w">
<ul class="t-sitems clearfix">
    <li class="t-sitem t-sitem-01 clearfix">
        <div class="t-sitem-01-d">
<img src="${assets('lbl-top-01-logo.png', 'images')}" alt="dig-i" width="38" height="26" />(ディギー)はあなたが<br/> 
興味のあるニュースや情報を<br />
さまざまな情報サイトから探<br />
して配信します。
        </div>
        <div class="t-sitem-01-i"><img style="padding-left:3px;" width="114" height="76" src="${assets('lbl-top-01-logo.png', 'images')}" alt="dig-i" /><img src="${assets('lbl-top-01-logo-reflec.png', 'images')}" alt="" style="position:relative; top:-7px;" width="111" height="42" /></div>
    </li>
    <li class="t-sitem t-sitem-02 clearfix">
        <div class="t-sitem-02-d">
まずはログインしてあなたが<br />
興味あるキーワードを登録<br />
しましょう。
        </div>
        <div class="t-sitem-02-i">
<img width="115" height="125" src="${assets('lbl-top-02-tags.png', 'images')}" />
        </div>
    </li>
    <li class="t-sitem t-sitem-03 clearfix">
        <div class="t-sitem-03-d">
登録されたワードをもとに<br/>
さまざまな情報サイトから<br />
興味のありそうなニュースや<br />
情報を見つけ配信します。
        </div>
        <div class="t-sitem-03-i"><img width="99" height="127" src="${assets('lbl-top-03.png', 'images')}" alt="dig-i" /></div>
    </li>
</ul>
<ul class="c-cl t-items-p-w">
  <li class="icon-chevron-left prev off"></li>
  <li class="page-1 page">●</li>
  <li class="page-2 page">●</li>
  <li class="page-3 page">●</li>
  <li class="icon-chevron-right next off"></li>
</ul>
</section>
<section class="c-s">
    <div class="mb-8 bold ta-c"><a href="#">本サービスは広告収益のため<br/>無料でご利用頂けます。</a></div>
    <h2 class="c-sh bold icon-signin c-sh-h-icon">ログイン</h2>
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
<#else>

<section class="c-s">
    <#if relateTags?? && 0 < relateTags?size>
    <h3 class="c-sh bold icon-tags">おすすめワード</h3>
    <ul class="c-ots clearfix">
		<#list relateTags as row>
        <li><a class="icon-tag" href="${url('/tag-information/index/tag-id/${row.id}')}">${row.name?html}</a></li>
		</#list>
    </ul>
	</#if>
</section>

<#include "../common/_advertise-1.ftl" />
<div class="t-c-menu-wrap">
<ul class="t-c-menu c-cl bold">
    <li class="t-ptag on"><a href="${url('/index/popular-ajax')}">人気</a></li><li class="t-ntag"><a href="${url('/index/new-ajax')}" >新規</a></li>
</ul>
</div>
<section class="c-s t-c">
	<#include "_popular-ajax.ftl" />
</section>

<div class="btn btn-rl">
    <a class="icon-rss" href="${url('/resource/index')}">RSS一覧</a>
</div>

<#include "../common/_site-information.ftl" />
<#include "../common/_search.ftl" />

</#if>


<script>
  $(document).ready(function(){
    var mySlider = new MySlider('.t-sitems', '.t-sitem', '.t-items-p-w');
    mySlider.init();
  });

  $('body').on('click', '.t-c-menu a', function(){
    if($(this).parents('li').hasClass('on')){
        return false;
    }
    $('.t-c-menu').find('li').removeClass('on');
    $(this).parents('li').addClass('on');
    $('.t-c').html('').prepend($('<div/>').addClass('js-loading'));
    $.ajax({
        url:$(this).attr('href'), 
        type:"GET"
    }).done(function(data) { 
        $('.t-c').html(data);
    });
    return false;
  });
</script>