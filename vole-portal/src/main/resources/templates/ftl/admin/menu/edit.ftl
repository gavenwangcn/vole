<#include "/ftl/common/layout_dl.ftl">
<@header>
</@header>
<@body>
 <form role="form" class="layui-form layui-form-pane" method="post" action="/admin/menu/doEdit">
   <input type="hidden" name="id" value="${(sysMenu.id)!}" />
  <div class="box-body">
    <div class="form-group">
      <label for="uname">编码</label>
     	<input type="text" name="code" class="form-control" placeholder="请输入编码,如05" 
     	value="${(sysMenu.code)!}" lay-verify="required">
    </div>
    <div class="form-group">
      <label for="uname">目录名称</label>
     	<input type="text" name="menuName" class="form-control" value="${(sysMenu.menuName)!}" placeholder="请输入目录名称"  lay-verify="required">
    </div>
    <div class="form-group">
      <label for="uname">排序</label>
     	<input type="text" name="sort" class="form-control" value="${(sysMenu.sort)!}"  placeholder="请输入排序"  lay-verify="required|number">
    </div>
    <div class="form-group">
      <label for="uname">图标 <i class="fa ${(sysMenu.icon)!}"></i></label>
     	<input type="text" name="icon" class="form-control" value="${(sysMenu.icon)!}" placeholder="请输入图标,如:fa-user" >
    </div>
    <div class="form-group">
	    <button type="submit" class="btn btn-success" lay-submit lay-filter="submit"><i class="fa fa-save"></i>  提 交</button>
	    <a  class="btn btn-default" href="javascript:parent.layer.closeAll('iframe');"><i class="fa fa-close"></i>  取消</a>
	  </div>
  </div><!-- /.box-body -->
</form>
</@body>
<@footer>
</@footer>