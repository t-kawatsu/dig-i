<div class="i-sort-menu clearfix">
  <div class="i-sort-menu-order c-color-g">新着順</div>
  <#assign types = {'ALL':'全て', 'RSS':'RSS', 'Amazon':'Amazon', 'Youtube':'Youtube', 'Facebook':'Facebook', 'Twitter':'Twitter'} />
  <#-- http://efreedom.com/Question/1-2790987/Freemarker-Hash-Struts2-Sselect-Tags-List-Property -->
  <@s.select list=types listKey="key" listValue="value" cssClass="i-sort-menu-sort" />

  <script type="text/javascript">
    $('.i-sort-menu-sort').on("change", function(){
        var type = $(this).val();
        var url = "${sortUrl}";
        var result_container_selector = ".js-result-con";
        $(result_container_selector).html('').prepend($('<div/>').addClass('js-loading'));
        $.ajax({
            url:url + "/resource-type/" + type, 
            type:"GET"
        }).done(function(data) { 
            $(result_container_selector).html(data);
        });
    });
  </script>
</div>
