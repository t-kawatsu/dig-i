<#macro resourceIcon resourceType>
	<#local iconFile = "icn_" + resourceType?lower_case >
	<#if resourceType == 'Twitter'>
		<#local iconFile = iconFile + ".ico">
	<#else>
		<#local iconFile = iconFile + ".png">
	</#if>
	<img src="${assets(iconFile, 'images', 'common')}" alt="${resourceType}" width="16" height="16" />
</#macro>

<#macro date date>
	<span class="c-i-date icon-time c-color-g">${date?string("yyyy年MM月dd日 H時")}</span>
</#macro>
