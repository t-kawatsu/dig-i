<#if siteInformations?? && 0 < siteInformations?size>
<section class="c-s">
    <h1 class="c-sh bold icon-info-sign c-sh-h-icon">お知らせ</h1>
    <ul class="c-sis">
		<#list siteInformations as row>
        <li>
			<span class="icon-time c-sis-date">${row.startAt?string("yyyy-MM-dd")}</span>
			<p class="c-sis-t">${row.title?html}</p>
        </li>
		</#list>
    </ul>
</section>
<#else>
</#if>
