
$(document).ready(function(){
	
	$(document).on('click', '.js-cancel-modal', function(e) {
		e.preventDefault();
		$.fancybox.close();
	});
	
	$('.modal').fancybox({
		width:'auto', height:'auto',
		autoSize:true, type:'ajax', title:null
	});

	$('body').on('submit','.ajax-form', function(e) {

		var perse = $(this).attr('action').split('#');
		var render_elem_id = perse[1];
		var render_elem_selector = '#'+render_elem_id;
		if(My.Util.isLoadingState(render_elem_selector)) {
			return false;
		}

		var postData = {};
		$(this).find(':input').each(function() {
			postData[$(this).attr('name')] = $(this).val();
		});

		My.Util.setLoadingState(render_elem_selector);
		$(render_elem_selector).html(null);
		
		$.ajax({
			url:$(this).attr('action'), 
			type:$(this).attr('method'),
			data:postData
		}).done(function(data) {
			My.Util.removeLoadingState(render_elem_selector);
			if($(data).attr('id') == render_elem_id) {
				$(render_elem_selector).replaceWith(data);
			}else{
				$(render_elem_selector).html(data);
			}
		});
		return false;
	});
	
	$('body').on('click','.ajax-get', function(e) {
		e.preventDefault();
		var perse = $(this).attr('href').split('#');
		var render_elem_id = perse[1];
		return My.Util.ajaxRewrite(
				$(this).attr('href'), null, '#'+render_elem_id, "GET");
	});
	
});

My = function(){};

My.Util = {
		
	setLoadingState: function(selector) {
		$(selector).addClass('loading').height($(selector).height());
		$(selector).data('loading', true);
	},
	removeLoadingState: function(selector) {
		$(selector).removeClass('loading').height('auto');
		$(selector).data('loading', false);
	},
	isLoadingState: function(selector) {
		return $(selector).data('loading');
	},

	ajaxRewrite: function(url, data, destSelector, method) {
		if(My.Util.isLoadingState(destSelector)){
			return true;
		}
		My.Util.setLoadingState(destSelector);
		$(destSelector).html(null);
		$.ajax({
			url:  url, 
			type: method || "GET",
			data: data || {}
		}).done(function(data) {
			My.Util.removeLoadingState(destSelector);
			if (destSelector.match("^#") 
					&& ("#"+$(data).attr('id') == destSelector)) {
				$(destSelector).replaceWith(data);
				return;
			} else {
				$(destSelector).html(data);
			}
		});
		return true;
	}
};
