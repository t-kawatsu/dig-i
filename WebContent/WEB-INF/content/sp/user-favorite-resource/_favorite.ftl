<#if !isFavorited!false>
  <li id="js-btn-f-con" class="r-ctrl-menu-border-r"><a class=" icon-star-empty ajax-get" href="${url('/user-favorite-resource/toggle-ajax/resource-id/${resource.id?c}')}#js-btn-f-con">${cntUserFavorites?c}</a></li>
<#else>
  <li id="js-btn-f-con" class="r-ctrl-menu-border-r"><a class=" icon-star-empty ajax-get" href="${url('/user-favorite-resource/toggle-ajax/resource-id/${resource.id?c}')}#js-btn-f-con">${cntUserFavorites?c}</a></li>
</#if>