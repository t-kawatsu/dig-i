<title>RSS一覧</title>

<link href="${assets('resource.css', 'css')}?_=20130415" media="screen, print" rel="stylesheet" type="text/css" >

<section class="c-s">
  <div class=" c-gw-c mb-8">
	<#include "../common/_paginator.ftl" />
	<#assign resources = pager.items />
	<#include "../common/_resource-items.ftl" />
    <div class="mt-8">
	  <#include "../common/_paginator.ftl" />
    </div>
  </div>
</section>
