<#include "/ftl/common/layout_dl.ftl">
<@header>
</@header>
<@body>
<form class="form-horizontal layui-form layui-form-pane" method="post" action="/mps/member/doAddAction">
   <div class="box-body">
       <div class="form-group">
           <label for="uname" class="col-sm-2 control-label">用户名</label>
           <div class="col-sm-10">
               <input type="text" id="mname" name="membername" class="form-control" placeholder="请输入会员名" lay-verify="required|username|check" check-url="/mps/member/checkName">
           </div>
       </div>
     <div class="form-group">
       <label for="password"  class="col-sm-2 control-label">密码</label>
        <div class="col-sm-10">
       <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" lay-verify="required|pass">
     	</div>
     </div>
     <div class="form-group">
       <label for="password2" class="col-sm-2 control-label">确认密码</label>
        <div class="col-sm-10">
       <input type="password" class="form-control" id="password2" name="password2" placeholder="请再次输入密码" lay-verify="required|pass|eqPwd">
     	</div>
     </div>
     <div class="form-group">
            <label class="col-sm-2 control-label">电话</label>
             <div class="col-sm-10">
                 <input type="text" id="uphone" name="phone" class="form-control" placeholder="请输入电话" lay-verify="required|phone|check" check-url="/mps/member/checkPhone">
          	</div>
          </div>
     <div class="form-group">
     	  <label class="col-sm-2 control-label">状态</label>
          <div class="col-sm-10">
         <label>
             <input name="delFlag" type="radio" class="minimal" checked value="0" lay-ignore> 启用
            </label>
         <label>
             <input name="delFlag" type="radio" class="minimal"  value="1" lay-ignore> 禁用
            </label>
          </div>
        </div>
        <div class="form-group">
        	<label class="col-sm-2 control-label">角色</label>
        	 <div class="col-sm-10">
        	<#list roleList as role>
          <label title="${(role.roleDesc)!}">
            <input type="checkbox" name="roleId" class="minimal" value="${(role.roleId)!}" lay-ignore>
            	${(role.roleName)!}
          </label>
          &nbsp;
          </#list>
          </div>
        </div>
         <div class="form-group">
         		<label class="col-sm-2 control-label">&nbsp;</label>
         		 <div class="col-sm-10">
         		 	<button type="submit" class="btn btn-success" lay-submit lay-filter="submit"><i class="fa fa-save"></i>  提 交</button>
   		  </div>
         </div>
     </div><!-- /.box-body -->
 </form>
</@body>
<@footer>
</@footer>