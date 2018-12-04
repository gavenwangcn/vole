<#include "/ftl/common/layout_dl.ftl">
<@header>
</@header>
<@body>
<form class="form-horizontal layui-form layui-form-pane" method="post" action="/mps/member/doEditAction">
   <div class="box-body">
       <input type="hidden" value="${entity.memberId}" name="id">
     <div class="form-group">
     	  <label class="col-sm-2 control-label">状态</label>
          <div class="col-sm-10">
         <label>
             <input name="delFlag" type="radio" class="minimal" ${(entity.delFlag?string == '0')?string('checked','')} value="0" lay-ignore> 启用
            </label>
         <label>
             <input name="delFlag" type="radio" class="minimal" ${(entity.delFlag?string == '1')?string('checked','')} value="1" lay-ignore> 禁用
            </label>
          </div>
        </div>
        <div class="form-group">
        	<label class="col-sm-2 control-label">角色</label>
        	 <div class="col-sm-10">
        	<#list roleList as role>
          <label title="${(role.roleDesc)!}">
            <input type="checkbox" name="roleId" class="minimal" value="${(role.roleId)!}"  ${(myRolds?seq_contains('${role.roleId}')?string('checked',''))} lay-ignore>
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