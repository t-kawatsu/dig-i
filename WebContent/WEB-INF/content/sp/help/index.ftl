<title>ヘルプ</title>

<section class="c-s" style="line-height:22px">
<div class="c-gw-c mb-8"> 
  <div class="c-wrap-items c-collapsible-items">
    <#list helps as row>
    <div data-role="collapsible">
      <h3 >${row.title}</h3>
      <p class="collapsible-d">${row.description}</p>
    </div>
    </#list>
  </div>
</div>
</section>
