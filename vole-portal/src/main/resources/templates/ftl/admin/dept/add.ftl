<#include "/ftl/common/layout_dl.ftl">
<@header>
</@header>
<@body>
 <form role="form" class="layui-form layui-form-pane pd10" method="post" action="/admin/dept/doAdd">
   <div class="box-body">
     <div class="form-group">
       <label for="deptName">部门名称</label>
      	<input type="text" id="deptName" name="deptName" class="form-control" lay-verify="required"  placeholder="请输入部门名称">
     </div>
     <div class="form-group">
            <label>部门描述</label>
            <textarea class="form-control" name="deptDesc" rows="3" placeholder="请输入描述，最多300个字符 ..." ></textarea>
     </div>
     <div class="form-group">
   		<button type="submit" class="btn btn-success" lay-submit lay-filter="submit"><i class="fa fa-save"></i>  提 交</button>
   	 </div>
   </div>
 </form>
</@body>
<@footer>
</@footer>