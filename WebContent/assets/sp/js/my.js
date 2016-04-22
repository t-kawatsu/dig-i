// jquery mobile自動初期化無効
/*
$(document).bind('mobileinit', function(){
$.extend(  $.mobile , {
  autoInitializePage : false,
  ajaxEnabled: false,
  defaultPageTransition: 'none',
  ajaxLinksEnabled: false,
  pushStateEnabled: false
});
});
*/

var MySlider = function(items_con_selector, items_selector, pager_con_selector){
  this.items_con = $(items_con_selector);
  this.items = $(items_selector);
  this.pager_con = $(pager_con_selector);
  this.current_item = 1;
  this.panel_width = null;
  this.panel_orientation = 0;
  this.touch_start_page_x = 0;
  this.flick_xmove_border = 5; // $.os.android ? 5 : 30;
  this.diffX = 0;
};

MySlider.prototype = {

  flicked_border: 70,

  init: function(){
    $(window).on("orientationchange resize", {obj: this}, function(event){
      var _this = event.data.obj;
      if(_this.getPanelOrientation() == _this.panel_orientation){
        return;
      }
      _this.resize();
      _this.panel_orientation = _this.getPanelOrientation();
    });
    this.resize();
    this.bindSwipeSlide();
    this.items.show();
    this.reflectPager();
  },

  getPanelOrientation: function(){
    return (window.orientation == undefined) ? 0 : window.orientation;
  },

  resize: function(){
    this.panel_width = $('body').width();
    this.items.width(this.panel_width);
    this.items_con.width(this.panel_width * this.items.length);
    this.setToItem(this.current_item);
  },

  next: function(){
    if(this.items.length <= this.current_item){
      return false;
    }
    this.current_item++;
    this.moveToItem(this.current_item); 
  },

  prev: function(){
    if(this.current_item <= 1){
      return false;
    }
    this.current_item--;
    this.moveToItem(this.current_item); 
  },

  moveToItem: function(i){
    var x = -1 * this.panel_width * (i -1);
    this.moveX(x);
    this.reflectPager();
  },

  moveX: function(x){
    this.items_con.css("-webkit-transition-duration", "0.3s");
    this.items_con.css("-webkit-transition-timing-function", "ease-in-out");
    this.items_con.css("-webkit-transform", "translate3d(" +x + "px, 0, 0)");
  },

  setToItem: function(i){
    var x = this.getItemBaseX(i);
    this.setX(x);
  },

  setX: function(x){
    this.items_con.css("-webkit-transition-duration", "0s");
    this.items_con.css("-webkit-transition-timing-function", "default");
    this.items_con.css("-webkit-transform", "translate3d(" +x + "px, 0, 0)");
  },

  reflectPager: function(){
    if(this.current_item <= 1){
		this.pager_con.find('.prev').css('visibility','hidden');
	}else{
		this.pager_con.find('.prev').css('visibility','visible');
	}
    if(this.items.length <= this.current_item){
		this.pager_con.find('.next').css('visibility','hidden');
    }else{
		this.pager_con.find('.next').css('visibility','visible');
	}

	this.pager_con.find('.page').addClass('off').filter("[class*='page-"+this.current_item+"']").removeClass('off');
  },

  getItemBaseX: function(i){
    return -1 * this.panel_width * (i -1);
  },

  bindSwipeSlide: function(){
    this.bindSwipeHandler(this.items_con, null); 
  },

  bindSwipeHandler: function(obj, func){
    obj.on("touchmove touchstart touchend", {callback:func, _this:this}, function(event){
      var _this = event.data._this;
      // inspired from cookpad
	  if(event.type == 'touchstart' || event.type == 'touchmove'){
        if(event.originalEvent.touches[0] == undefined){ return false; }
        var touch = event.originalEvent.touches[0];
        if(event.type == 'touchstart'){
           _this.touch_start_page_x = touch.pageX;
           _this.diffX = 0;
        }else if(event.type == 'touchmove'){
           _this.diffX = touch.pageX - _this.touch_start_page_x;
           if(Math.abs(_this.diffX) > _this.flick_xmove_border){
             _this.setX(_this.getItemBaseX(_this.current_item) + _this.diffX);
             event.preventDefault();
           }
        }
      }else if(event.type == 'touchend'){
        if(_this.diffX > _this.flicked_border && 1 < _this.current_item){
           _this.prev();
        }else if(_this.diffX < -1*_this.flicked_border && _this.current_item < _this.items.length){
           _this.next();
        }else{
           _this.moveToItem(_this.current_item);
        }
      }
    });
  }
};

var My = function(){

  return {
  
    delete_container_selector: '.c-dc',
    btn_delete_selector: '.btn-d',

    delete_confirm_str: '削除しますか？',

    favorite_complete_str: '登録しました',

    init: function(){
      this.setContainerLink();
      this.setDeleteLink();
      this.setAjaxGetLink();
      this.Overlay.bindContents();
      this.setCollapsibleContents();
	  this.setMoreLink();
    },

    /**
     * 領域に対してのリンク
     */
    setContainerLink: function(){
      // iphoneでdivにdelegateがなぜかきかない。。。
      // 解決策 http://javascript-memo2.seesaa.net/article/280879569.html
      $('body').on('click', '.js-cl', function(event){
        if(event.isPropagationStopped()){
          return false;
        }
        // @TODO アンカークリックの無効
        location.href = $(this).find('a').attr('href');
      });

    },

	Overlay: {
		container_selector:           'body',
		btn_overlay_selector:        '.js-modal',
		overlay_background_selector: '.my-o-back',
		overlay_con_selector:        '.my-o-con',
		btn_remove_overlay_selector: '.btn-ro',
		
		/**
		 * オーバーレイ表示リンクの設定
		 */
		bindContents: function(){
			$(this.container_selector).on('click', this.btn_overlay_selector, {clazz:this}, function(event){
				event.preventDefault();
				event.data.clazz.generate();
				$.ajax({
					url:$(this).attr('href'), 
					type:"GET"
				}).done(function(data) {
					event.data.clazz.ajaxCompleteHandler(data, this);
				});
				return false;
			});
		},

	    /**
		 * create a overlay and show
	     */
	    generate: function(){
			if($(this.overlay_background_selector).length < 1){
				$(this.container_selector).prepend(
					$('<div />').addClass(this.overlay_background_selector.replace('.', ''))
				);
			}

			var overlay_background = $(this.overlay_background_selector);
			overlay_background.css('opacity', '0').css('display', 'block').css('opacity', '1');

			var overlay_con = $('<div />').addClass(this.overlay_con_selector.replace('.', ''));
			overlay_con.on('click', function(event){
				event.stopPropagation();
			});

			// loading image
			overlay_con.prepend($('<div/>').addClass('js-loading'));

			overlay_background.prepend(overlay_con);

			overlay_background.on('click', {clazz:this}, function(event){
				event.data.clazz.remove();
			});
		},

	    ajaxCompleteHandler: function(ret, elem){
	      if($(this.overlay_con_selector).length < 1){
	        return false;
	      }
	      $(this.overlay_con_selector).html(ret)
			.prepend($('<i/>').addClass('icon-remove-sign').addClass(this.btn_remove_overlay_selector.replace('.', '')));

		  $(this.btn_remove_overlay_selector).on('click', {clazz:this}, function(event){
			event.data.clazz.remove();
		  });
	    },

		remove: function(){
			$(this.overlay_background_selector).remove();
		}
	},

    /**
     * 開閉可能なコンテンツの設定
     */
    setCollapsibleContents: function(){
      $("div[data-role='collapsible']").each(function(){
      $(this).find('h3').on('click', function(){
        if($(this).hasClass('icon-caret-right')){
          $(this).addClass("icon-caret-down").removeClass('icon-caret-right')
            .nextAll().show();
        }else{
          $(this).addClass("icon-caret-right").removeClass('icon-caret-down')
            .nextAll().hide();
        }
      }).addClass("icon-caret-right").nextAll().hide();
      });
    },

    /**
     * お気に入り登録ajax
     */
    setAjaxGetLink: function(){
    	$('body').on('click','.ajax-get', function(e) {
    		e.preventDefault();
    		var perse = $(this).attr('href').split('#');
    		var render_elem_id = perse[1];
    		return my.ajaxRewrite(
    				$(this).attr('href'), null, '#'+render_elem_id, "GET");
    	});
    },
    
    ajaxRewrite: function(url, data, destSelector, method) {
    	var dest = $(destSelector);
		if(dest.hasClass('btn-ajax-loading')){
			return true;
		}
		dest.addClass('btn-ajax-loading')
			.children().css('visibility','hidden');
		$.ajax({
			url:  url, 
			type: method || "GET",
			data: data || {}
		}).done(function(data) {
			dest.removeClass('btn-ajax-loading');
			if (destSelector.match("^#") 
					&& ("#"+$(data).attr('id') == destSelector)) {
				dest.replaceWith(data);
				return;
			} else {
				dest.html(data);
			}
		});
		return true;
	},

    /**
     * 削除機能の設定
     */
    setDeleteLink: function(){
      $('body').on('click', this.btn_delete_selector, function(event){
        var ret = confirm(my.delete_confirm_str);
        $(this).fadeOut("fast");
        if(ret){
          $(this).parents(my.delete_container_selector).fadeOut("fast");
          $.ajax({
            url:$(this).attr('href'), 
            type:"GET"
          }).done(function(data) { 
          });
        }
        event.preventDefault();
        return false;
      });

      this.bindSwipeEventHandler(this.delete_container_selector);
      $('body').on('swipe swipeleft swiperight', this.delete_container_selector, function(event){
        if($.data($(this).find(my.btn_delete_selector), 'animating')){
          return;
        }
        if (event.type === 'swipeleft'){
          $.data($(this).find(my.btn_delete_selector), 'animating', true);
          $(this).find(my.btn_delete_selector).fadeIn("normal", function(){
            $.data($(this).find(my.btn_delete_selector), 'animating', false);
          });
        }else if(event.type === 'swiperight'){
          $.data($(this).find(my.btn_delete_selector), 'animating', true);
          $(this).find(my.btn_delete_selector).fadeOut("fast", function(){
            $.data($(this).find(my.btn_delete_selector), 'animating', false);
          });
        }
      });
    },

	setMoreLink: function(){
	  $('body').on('click', '.btn-more a', function(){
        event.preventDefault();
		var wrap_elem = $(this).parents('.btn-more');
        if(wrap_elem.hasClass('btn-ajax-loading')){
          return false;
        }
		var result_container_class = 
			wrap_elem.attr('data-result-container-class');
        wrap_elem.addClass('btn-ajax-loading');
        $.ajax({
          url:$(this).attr('href'), 
          type:"GET"
        }).done(function(data) {
			wrap_elem.removeClass('btn-ajax-loading');
			$('.'+result_container_class).append(data);
        });
        return false;
	  });
	},

    /**
     * スワイプイベントの設定
     */
    bindSwipeEventHandler: function(selector){
	  var flicked_border = 70;
      $('body').on("touchmove touchstart touchend", selector, function(event){
        // inspired from cookpad
        if(event.type == 'touchstart' || event.type == 'touchmove'){
          if(event.originalEvent.touches[0] == undefined){ return false; }
            var touch = event.originalEvent.touches[0];
            if(event.type == 'touchstart'){
              $.data(this, 'touch_start_page_x', touch.pageX);
              $.data(this, 'swipe_diff', 0);
            }else if(event.type == 'touchmove'){
              var swipe_diffX = touch.pageX - $.data(this, 'touch_start_page_x');
              $.data(this, 'swipe_diff', swipe_diffX);
              if(swipe_diffX > flicked_border){
                $(this).trigger("swiperight");
                $(this).trigger("swipe");
              }else if(swipe_diffX < -flicked_border){
                $(this).trigger("swipeleft");
                $(this).trigger("swipe");
              }
              // event.preventDefault();
            }
          }else if(event.type == 'touchend'){
            var swipe_diffX = $.data(this, 'swipe_diff');
            if(swipe_diffX > flicked_border){
              $(this).trigger("swipeendright");
              $(this).trigger("swipeend");
            }else if(swipe_diffX < -flicked_border){
              $(this).trigger("swipeendleft");
              $(this).trigger("swipeend");
            }
          }
      });
    }
  }
};

var my = new My();
my.init();
