<title>ワード登録</title>

<link href="${assets('user-tag.css', 'css')}" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
  <div class="c-gw-c">
    <div class="c-clear-d icon-ok-sign mb-8">ワードが抽出されました。</div>  
    <div class="c-alert-d icon-exclamation-sign mb-8">
      	登録出来るワードは<@s.text name="app.tag.regist.limit" />個までです。<br/>
      	登録したいワードを<@s.text name="app.tag.regist.limit" />個選択して下さい。
    </div>  
  </div>
  
  <form method="POST" action="${url('/user-tag/exceed')}">
    <ul class="c-t-items mb-8 c-gw-c">
		<#list words as row>
		<#assign check = checkes?seq_contains(row_index) />
        <li class="<#if check>checked</#if>">
            <div><input type="checkbox" name="checkes" value="${row_index}" <#if check>checked="checked"</#if> />${row.name?html}</div>
        </li>
		</#list>
    </ul>
      
    <div class="c-gw-c">
      <div class="c-alert-d mb-8 ta-c">残り<span class="remain">0</span>個</div>
    </div>
    
	<div class="c-input-item">
		<div class="btn mb-8"><input type="submit" value="登録する" /></div>
		<div class="btn"><a href="${url('/user-tag/index')}">戻る</a></div>
	</div>
  </form>
  
  <script type="text/javascript">
  	$(document).ready(function() {
  		var limit = <@s.text name="app.tag.regist.limit" />;
  		$('.c-t-items').on('click', 'li', function() {
  			if($(this).hasClass("checked")) {
  				$(this).removeClass("checked");
  				$(this).find("input").removeAttr("checked");
  			} else {
  				if(limit <= $('.c-t-items li.checked').length) {
  					alert("登録出来る制限数を超えています。");
  					return false;
  				}
  				$(this).addClass("checked");
  				$(this).find("input").attr("checked", "checked");
  			}
  			
  			$('.remain').text(limit - $('.c-t-items li.checked').length);
  		});
  		
  		$('.remain').text(limit - $('.c-t-items li.checked').length);
  	});
  </script>
</section>
