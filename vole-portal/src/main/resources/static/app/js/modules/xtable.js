//layui xtable
layui.define(['layer','table','element','form'], function(exports){
	
	var $ = layui.jquery,
		element = layui.element,
		form = layui.form,
		table = layui.table;
	
	//弹窗
	$(".dialog").on('click',function(){
		var me = this;
		 var url = $(this).attr('data-url');
		 width=$(me).attr('data-width') || 800,
		 height=$(me).attr('data-height') || 600,
		 title = $(me).attr('data-title') || '';
		 x_admin_show(title,url,width,height);
	});
	
	//监听table的工具条
	 table.on('tool(table)', function(obj){
		var me = this;
		var url = $(me).attr('data-url'),
			width=$(me).attr('data-width'),
			height=$(me).attr('data-height'),
			id=$(me).attr('data-id') || 'id',
			title = $(me).attr('data-title') || '';
	    var data = obj.data;
	    
	    //删除
	    if(obj.event === 'del'){
	      layer.confirm('确定删除?',{icon: 3, title:'警告'}, function(index){
	        $.post(url,{ids:[data[id]]},function(json){
	        	if(json.code==200){
	        		layer.msg('删除成功');
	        		layer.close(index);
	        		table.reload('table');
	        	}else{
	        		layer.msg(json.msg, {icon:2});
	        	}
	        });
	      });
	      
	      //编辑
	     } else if(obj.event === 'edit'){
	    	x_admin_show(title,url+'?id='+data[id],width,height);
	    } else {
	    	x_admin_show(title,url+'?id='+data[id],width,height);
	    }
	  });
	 
	  //批量删除
	  $(".del-all").on('click',function(){
		  var url = $(this).attr('data-url');
		  var id = $(this).attr('data-id') || "id";
		  var checkStatus = table.checkStatus('table');
	      data = checkStatus.data;
	      if(data.length==0){
	    	  layer.msg("请选择要删除的记录");
	    	  return;
	      }
	      var _ids = [];
	      $.each(data,function(i,n){
	    	  _ids.push(n[id]);
	      });
	      layer.confirm('确定删除?',{icon: 3, title:'警告'}, function(index){
	        $.post(url,{ids:_ids},function(json){
	        	if(json.code==200){
	        		layer.msg('删除成功');
	        		layer.close(index);
	        		table.reload('table');
	        	}else{
	        		layer.msg(json.msg, {icon:2});
	        	}
	        });
	      });
		});
	  
	  //条件搜索
	  form.on('submit(search)', function(data){
   	   var values = data.field,
   	   	fm = data.form;
	   	 table.reload('table', {
	        where: values
	      });
         return false;
       });
  
  exports('xtable', {});
});  
 