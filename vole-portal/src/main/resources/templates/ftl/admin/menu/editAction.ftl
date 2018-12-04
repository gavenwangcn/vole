<#include "/ftl/common/layout_dl.ftl">
<@header>
</@header>
<@body>
 <form role="form" class="layui-form layui-form-pane" method="post" action="/admin/menu/doEdit">
  <input type="hidden" name="id" value="${(sysMenu.id)!}" />
  <div class="box-body">
      <div class="form-group">
         <label>选择目录/菜单</label>
         <select id="dir" class="form-control select2" style="width: 100%;" lay-verify="required" lay-ignore>
           <option value="" selected="selected">--请选择--</option>
           <#list list as m>
           	 <option value="${(m.id)!}" ${(m.id==pSysMenu.parentId)?string('selected="selected"','')}>${(m.code)!}-${(m.menuName)!}</option>
           </#list>
         </select>
         <select id="pid" name="pid" class="form-control select2" style="width: 100%;" lay-verify="required" lay-ignore>
         		<option value="${(pSysMenu.id)!}" selected="selected">${(pSysMenu.code)!}-${(pSysMenu.menuName)!}</option>
       		</select>
	</div>
	<div class="form-group">
       <label for="uname">编码</label>
      	<input type="text" name="code" class="form-control" value="${(sysMenu.code)!}"
      	placeholder="请输入编码,如050101" lay-verify="required">
     </div>
     <div class="form-group">
       <label for="uname">功能名称</label>
      	<input type="text" name="menuName" value="${(sysMenu.menuName)!}" class="form-control" placeholder="请输入目录名称"  lay-verify="required">
     </div>
     <div class="form-group">
       <label for="resource">权限资源</label>
      	<input type="text" id="resource" name="resource" value="${(sysMenu.resource)!}" class="form-control" placeholder="请输入权限资源名称" lay-verify="required">
     </div> 
     <div class="form-group">
       <label for="uname">排序</label>
      	<input type="text" name="sort" value="${(sysMenu.sort)!}" class="form-control" placeholder="请输入排序"  lay-verify="required|number">
     </div>
      <div class="form-group">
	    <button type="submit" class="btn btn-success" lay-submit lay-filter="submit"><i class="fa fa-save"></i>  提 交</button>
	    <a  class="btn btn-default" href="javascript:parent.layer.closeAll('iframe');"><i class="fa fa-close"></i>  取消</a>
	  </div>
   </div><!-- /.box-body -->
</form>
</@body>
<@footer>
<script>
$("#dir").on('change',function(){
	var pid = $(this).val();
	 $.post('/admin/menu/json?_dc='+new Date().getTime(),{parentId:pid},function(response){
		if(response.code==200){
			$("#pid").empty();
			$("#pid").select2({
				data:response.data
			});
		}
	}); 
});
</script>
</@footer>