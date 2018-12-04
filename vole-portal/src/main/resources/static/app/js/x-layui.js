/*弹出层*/
/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
function x_admin_show(title,url,w,h){
	if (title == null || title == '') {
		title=false;
	};
	if (url == null || url == '') {
		url="404.ftl";
	};
	if (w == null || w == '') {
		w=800;
	};
	if (h == null || h == '') {
		h=($(window).height()-50);
	};
	layer.open({
		type: 2,
		area: [w+'px', h +'px'],
		fix: false, //不固定
		maxmin: true,
		shadeClose: true,
		shade:0.4,
		title: title,
		content: url
	});
}

/*关闭弹出框口*/
function x_admin_close(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}  

//弹窗
$(".dialog").on('click',function(){
	var me = this;
	 var url = $(this).attr('data-url');
	 width=$(me).attr('data-width') || 800,
	 height=$(me).attr('data-height') || 400,
	 title = $(me).attr('data-title') || '';
	 x_admin_show(title,url,width,height);
});

