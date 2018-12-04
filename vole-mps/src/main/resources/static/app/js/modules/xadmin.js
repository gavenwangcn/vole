/**
 * xadmin
 */
layui.define(['element'], function(exports){
	
	$ = layui.jquery;
  	element = layui.element; 
  	 
    //导航的hover效果、二级菜单等功能，需要依赖element模块
    // 侧边栏点击隐藏兄弟元素
  	$('.layui-nav-item').click(function(event) {
  		$(this).siblings().removeClass('layui-nav-itemed');
  	});

  	$('.layui-tab-title li').eq(0).find('i').remove();

  	height = $('.layui-layout-mps .site-demo').height();
  	$('.layui-layout-mps .site-demo').height(height-100);

  	if($(window).width()<750){
  		trun = 0;
  		$('.x-slide_left').css('background-position','0px -61px');
  	}else{
  		trun = 1;
  	}
  	$('.x-slide_left').click(function(event) {
  		if(trun){
  			$('.x-side').animate({left: '-200px'},200).siblings('.x-main').animate({left: '0px'},200);
  			$(this).css('background-position','0px -61px');
  			trun=0;
  		}else{
  			$('.x-side').animate({left: '0px'},200).siblings('.x-main').animate({left: '200px'},200);
  			$(this).css('background-position','0px 0px');
  			trun=1;
  		}
  		
  	});



    	//监听导航点击
    	element.on('nav(side)', function(elem){
      	title = elem.find('cite').text();
      	url = elem.find('a').attr('_href');

      	for (var i = 0; i <$('.x-iframe').length; i++) {
      		if($('.x-iframe').eq(i).attr('src')==url){
      			element.tabChange('x-tab', i);
      			return;
      		}
      	};
      	res = element.tabAdd('x-tab', {
      		 id:$('.x-iframe').length || 0,
  	         title: title//用于演示
  	        ,content: '<iframe frameborder="0" src="'+url+'" class="x-iframe"></iframe>'
  		    });

  		element.tabChange('x-tab', $('.layui-tab-title li').length-1);

      	$('.layui-tab-title li').eq(0).find('i').remove();
    });
  	
    exports('xadmin', {}); //注意，这里是模块输出的核心，模块名必须和use时的模块名一致
});    