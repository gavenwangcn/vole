<#include "/ftl/common/layout_dl.ftl">
<@header>
</@header>
<@body>
 <form role="form" class="layui-form layui-form-pane pd10" method="post" action="/admin/setting/doAdd">
   <div class="box-body">
     <div class="form-group">
       <label for="deptName">系统名称</label>
      	<input type="text" id="sysName" name="sysName" class="form-control" lay-verify="required"  placeholder="请输入系统名称">
     </div>
       <div class="form-group">
           <label for="deptName">系统简称</label>
           <input type="text" id="sysSubName" name="sysSubName" class="form-control" lay-verify="required"  placeholder="请输入系统简称">
       </div>
       <div class="form-group">
           <label for="deptName">系统标识</label>
           <input type="text" id="sysGlobalKey" name="sysGlobalKey" class="form-control" lay-verify="required"  placeholder="请输入系统标识">
       </div>
     <div class="form-group">
   		<button type="submit" class="btn btn-success" lay-submit lay-filter="submit"><i class="fa fa-save"></i>  提 交</button>
   	 </div>
   </div>
 </form>
</@body>
<@footer>
</@footer>