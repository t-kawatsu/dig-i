<#if !isFavorited!false>
  <li id="js-btn-f-con" class="i-ctrl-menu-border-r"><a class=" icon-bookmark-empty ajax-get" href="${url('/user-favorite/toggle-ajax/information-id/${information.id?c}')}#js-btn-f-con">${cntUserFavorites?c}</a></li>
<#else>
  <li id="js-btn-f-con" class="i-ctrl-menu-border-r"><a class=" icon-bookmark-empty ajax-get" href="${url('/user-favorite/toggle-ajax/information-id/${information.id?c}')}#js-btn-f-con">${cntUserFavorites?c}</a></li>
</#if>