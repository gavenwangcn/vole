<#include "/ftl/common/layout_dl.ftl">
<@header>
</@header>
<@body>
 <form class="form-horizontal layui-form layui-form-pane"  method="post" action="/admin/user/doEdit">
  <div class="box-body">
    <div class="form-group">
      <label for="uname" class="col-sm-2 control-label">用户名</label>
      <input type="hidden" value="${sysUser.id}" name="id">
      <div class="col-sm-10">
     	<input type="text" id="uname" name="userName" value="${(sysUser.username)!}" class="form-control" placeholder="请输入用户名" lay-verify="required|username">
    	</div>
    </div>
    <div class="form-group">
          <label  class="col-sm-2 control-label">部门</label>
          <div class="col-sm-10">
          <select name="deptId" class="form-control select2" style="width: 100%;" lay-verify="required" lay-ignore>
           <#list deptList as dept>
             <option value="${(dept.id)!}"  ${(sysUser.deptId?? && dept.id==sysUser.deptId)?string('selected="selected"','')}  >${(dept.deptName)!}</option>
           </#list>
         </select>
         </div>
       </div>
    <div class="form-group">
      <label for="password"  class="col-sm-2 control-label">密码</label>
      <div class="col-sm-10">
      <input type="password" class="form-control"  id="password"  name="password" placeholder="密码为空则不修改密码" lay-verify="pass">
    	</div>
    </div>
    <div class="form-group">
      <label for="password"  class="col-sm-2 control-label">确认密码</label>
      <div class="col-sm-10">
      <input type="password" class="form-control"  id="confpassword"  name="confpwd" placeholder="请输入密码" lay-verify="pass|eqPwd">
    	</div>
    </div>
    <div class="form-group">
           <label  class="col-sm-2 control-label">电话</label>
           <div class="col-sm-10">
               <input type="text" id="uphone" name="phone" class="form-control" value="${(sysUser.phone)!}" placeholder="请输入电话" lay-verify="required|phone" >
           </div>
         </div>
    <div class="form-group">
    	   <label  class="col-sm-2 control-label">状态</label>
    	   <div class="col-sm-10">
         <label>
           <input name="delFlag" type="radio" class="minimal" ${(sysUser.delFlag == 0)?string('checked','')} value="0" lay-ignore> 启用
         </label>
         <label>
           <input name="delFlag" type="radio" class="minimal" ${(sysUser.delFlag == 1)?string('checked','')}  value="1" lay-ignore> 禁用
         </label>
         </div>
       </div>
       <div class="form-group">
       	<label  class="col-sm-2 control-label">角色</label>
       	<div class="col-sm-10">
       	<#list sysRoles as role>
          <label title="${(role.roleDesc)!}">
            <input type="checkbox" name="roleId" class="minimal" value="${(role.id)!}" ${(myRolds?seq_contains('${role.id}')?string('checked',''))} lay-ignore>
            ${(role.roleName)!}
          </label>
          &nbsp;
         </#list>
         </div>
       </div>
       <div class="form-group">
       	<label  class="col-sm-2 control-label"></label>
       	<div class="col-sm-10">
       		 <button type="submit" class="btn btn-success" lay-submit lay-filter="submit"><i class="fa fa-save"></i>  提 交</button>
       	</div>
       </div>
  </div><!-- /.box-body -->
</form>
</@body>
<@footer>
</@footer>