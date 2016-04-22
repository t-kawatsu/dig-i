<section class="c-s">
    <h3 class="c-sh bold icon-search c-sh-h-icon">情報を検索する</h3>
    <form action="${url('/search/read')}" method="GET" class="c-s-form clearfix">
	  <div class="fl c-input-sw">
        <input type="search" name="word" placeholder="検索ワードを入力" />
		<a class="c-input-swr icon-remove-sign" href="#"></a>
	  </div>
	  <div class="fl c-input-sw-submit">
		<@s.submit value="検索"/>
	  </div>
		<@s.token />
	<script type="text/javascript">
		$('.c-input-swr').on('click', function(e){
			e.preventDefault();
			$('.c-input-sw').find('input').val('');
		});
	</script>
    </form>
</section>
