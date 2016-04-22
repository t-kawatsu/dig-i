<#assign contents = "resource" />
<#include "../common/_contents-header.ftl" />

<form action="${url('/resource/create')}" method="POST">
  <dl class="c-input-con clearfix">
	<dt>url</dt>
	<dd>
		<@s.textfield name="resourceForm.url" readonly="isUpdate" />
		<@s.fielderror><@s.param value="%{'resourceForm.url'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>&nbsp;</dt>
	<dd>
		<@s.submit value="送信する" cssClass="btn btn-submit" />
	</dd>
  </dl>
</form>
