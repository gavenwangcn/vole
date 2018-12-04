<#include "/ftl/common/layout_dl.ftl">
<@header>
</@header>
<@body>
<form class="form-horizontal layui-form layui-form-pane" method="post" action="/admin/role/doAdd">
  <div class="box-body">
    <div class="form-group">
      <label for="uname"  class="col-sm-2 control-label">角色名</label>
       <div class="col-sm-10">
     		<input type="text" id="uname" name="roleName" class="form-control" placeholder="请输入角色名"  lay-verify="required">
    	 </div>
    </div>
      <div class="form-group">
          <label for="ucode"  class="col-sm-2 control-label">角色编码</label>
          <div class="col-sm-10">
              <input type="text" id="ucode" name="roleCode" class="form-control" placeholder="请输入角色编码"  lay-verify="required|check" check-url="/admin/role/checkRole">
          </div>
      </div>
    <div class="form-group">
           <label  class="col-sm-2 control-label">描述</label>
           <div class="col-sm-10">
           	<textarea class="form-control" name="roleDesc" rows="3" placeholder="请输入描述，最多300个字符 ..." ></textarea>
           </div>
         </div>
    <div class="form-group">
    <label   class="col-sm-2 control-label">状态</label>
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
       	 <label   class="col-sm-2 control-label"></label>
        <div class="col-sm-10">
      	<button type="submit" class="btn btn-success" lay-submit lay-filter="submit"><i class="fa fa-save"></i>  提 交</button>
      	<a  class="btn btn-default" href="javascript:parent.layer.closeAll('iframe');"><i class="fa fa-close"></i>  取消</a>
   	  </div>
 </div>
  </div>
</form>
</@body>
<@footer>
</@footer>