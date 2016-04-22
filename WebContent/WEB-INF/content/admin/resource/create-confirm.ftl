<#assign contents = "resource" />
<#include "../common/_contents-header.ftl" />

<#if isUpdate!false>
<#assign formAction=url('/resource/update/' + id) />
<#else>
<#assign formAction=url('/resource/create-complete') />
</#if>
<form action="${formAction}" method="POST">
  <dl class="c-input-con clearfix">
	<dt>title</dt>
	<dd>
		<@s.textfield name="resourceForm.title" />
		<@s.fielderror><@s.param value="%{'resourceForm.title'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>link</dt>
	<dd>
		<@s.textfield name="resourceForm.link" readonly="isUpdate" />
		<@s.fielderror><@s.param value="%{'resourceForm.link'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>description</dt>
	<dd>
		<@s.textarea name="resourceForm.description" />
		<@s.fielderror><@s.param value="%{'resourceForm.description'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>imageTitle</dt>
	<dd>
		<@s.textfield name="resourceForm.imageTitle" readonly="isUpdate" />
		<@s.fielderror><@s.param value="%{'resourceForm.imageTitle'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>imageLink</dt>
	<dd>
		<@s.textfield name="resourceForm.imageLink" />
		<@s.fielderror><@s.param value="%{'resourceForm.imageLink'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>imageUrl</dt>
	<dd>
		<@s.textfield name="resourceForm.imageUrl" />
		<@s.fielderror><@s.param value="%{'resourceForm.imageUrl'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>url</dt>
	<dd>
		<@s.textfield name="resourceForm.url" readonly="isUpdate" />
		<@s.fielderror><@s.param value="%{'resourceForm.url'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>type</dt>
	<dd>
		<@s.select key="resourceForm.type" list="resourceForm.types" />
		<@s.fielderror><@s.param value="%{'resourceForm.type'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>categories</dt>
	<dd>
		<@s.checkboxlist key="resourceForm.categories" list="resourceForm.categoryList" />
		<@s.fielderror><@s.param value="%{'resourceForm.categories'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>status</dt>
	<dd>
		<@s.select key="resourceForm.status" list="resourceForm.statuses" />
		<@s.fielderror><@s.param value="%{'resourceForm.status'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>language_code</dt>
	<dd>
		<#if isUpdate!false >
		<@s.hidden name="resourceForm.languageCode" value="${resourceForm.languageCode}" />
		${resourceForm.languageCode}
		<#else>
		<@s.select key="resourceForm.languageCode" list="resourceForm.languageSelectList" readonly="readonly" />
		</#if>
		<@s.fielderror><@s.param value="%{'resourceForm.languageCode'}" /></@s.fielderror>
	</dd>
  </dl>
  <dl class="c-input-con clearfix">
	<dt>&nbsp;</dt>
	<dd>
		<@s.submit value="送信する" cssClass="btn btn-submit" />
	</dd>
  </dl>
</form>
