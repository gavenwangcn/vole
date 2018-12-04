//layui模块的定义
layui.define(['layer','form','upload'], function(exports){
	
	var $ = layui.jquery, upload = layui.upload, form = layui.form;
	// 验证
	form.verify({
		username : function(value, item) { // value：表单的值、item：表单的DOM对象
			if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
				return '用户名不能有特殊字符';
			}
			if (/(^\_)|(\__)|(\_+$)/.test(value)) {
				return '用户名首尾不能出现下划线\'_\'';
			}
			if (/^\d+\d+\d$/.test(value)) {
				return '用户名不能全为数字';
			}
		},
		pass  : function(value){
			if(value!=''){
				if(!/^[\S]{6,12}$/.test(value)){
					return '密码必须6到12位，且不能出现空格';
				}
			}
		},
		//重复密码相等验证
		eqPwd: function(value) {
			//获取密码
			var pwd = $("#password").val();
			if(pwd!=value) {
				return '两次输入的密码不一致';
			}
		},
		
		file : function(value, item) {
			if (value == '') {
				return "上传文件不能为空";
			}
		},
		//异步检测
		check:function(value,item){
			var checkUrl = $(item).attr('check-url');
			var name = $(item).attr('name');
			var _msg = "";
			if(checkUrl!=''){
				$.ajax({  
			         type : "post",  
			          url : checkUrl,  
			          data : name +"=" + value,  
			          async : false,  
			          success : function(data){  
			        	  if(data.code!=200){
							if(data.msg){
								_msg =  data.msg;
							}else{
								_msg = "该字段已存在";
							}
						}
			          }  
			     }); 
			}
			if(_msg!=''){
				return _msg;
			}
		}
	});

	// 文件上传

	upload.render({
		elem : '#file-btn',
		url : '/file/upload/',
		size : 5 * 1024, // 限制文件大小，单位 KB
		done : function(res) {
			if (res.status == 'success') {
				layer.msg("文件上传成功", {
					icon : 1
				});
				var urls = res.urls;
				$("#file-txt").html(urls[0]);
				$("#file-val").val(urls[0]);
			} else {
				layer.msg(res.msg, {
					icon : 2
				});
			}
		}
	});

	// 监听提交
	form.on('submit(submit)', function(data) {
		var values = data.field, fm = data.form;
		
		//获取checkbox选中的值
		var $ch = $("input:checkbox:checked");
		var name = {};
		var chvs = [];
		if($ch && $ch[0]){
			name = $ch[0].name;
			$ch.each(function() {
				chvs.push($(this).val());
			});
			values[name] = chvs;
		}
		var index = layer.load(3); // 换了种风格
		$.post($(fm).attr('action'), values, function(data) {
			layer.close(index);
			if (data.code == 200) {
				if(data.msg){
					parent.layer.msg(data.msg, {icon : 1});
				}else{
					parent.layer.msg('提交成功', {icon : 1});
				}
				// 关闭当前frame
				parent.layer.closeAll('iframe');
				parent.location.reload();
			} else {
				layer.msg(data.msg, {icon : 2 });
			}

		});
		return false;
	});
	//监听开关
	form.on('switch(switch)', function(data){
		layer.tips(data.elem.checked?'是':'否',data.othis);
	});
	
  exports('xform', {});
});  
 