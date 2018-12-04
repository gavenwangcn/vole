<#include "/ftl/common/layout_dl.ftl">
<@header>
</@header>
<@body>
 <form role="form" class="layui-form layui-form-pane pd10" method="post" action="/mps/client/doAddAction">
   <div class="box-body">
       <div class="form-group">
           <label for="uname" class="col-sm-2 control-label">客户端标识</label>
           <div class="col-sm-10">
               <input type="text" id="clientId" name="clientId" class="form-control" placeholder="请输入客户端标识" lay-verify="required|check" check-url="/mps/client/checkClient">
           </div>
       </div>
       <div class="form-group">
           <label for="password"  class="col-sm-2 control-label">密码</label>
           <div class="col-sm-10">
               <input type="password" class="form-control" id="password" name="clientSecret" placeholder="请输入密码" lay-verify="required|pass">
           </div>
       </div>
       <div class="form-group">
           <label for="password2" class="col-sm-2 control-label">确认密码</label>
           <div class="col-sm-10">
               <input type="password" class="form-control" id="password2" name="clientSecret2" placeholder="请再次输入密码" lay-verify="required|pass|eqPwd">
           </div>
       </div>
       <div class="form-group">
           <label class="col-sm-2 control-label">作用域</label>
           <div class="col-sm-10">
               <input type="text" id="scope" name="scope" class="form-control" placeholder="请输入作用域" lay-verify="required" >
           </div>
       </div>
       <div class="form-group">
           <label class="col-sm-2 control-label">授权类型</label>
           <div class="col-sm-10">
        	<#list grantTypes as grantType>
                <label >
                    <input type="checkbox" name="grantTypes" class="minimal" value="${(grantType.key)!}" lay-ignore>
                    ${(grantType.name)!}
                </label>
                &nbsp;
            </#list>
           </div>
       </div>
       <div class="form-group">
           <label   class="col-sm-2 control-label">自动授权</label>
           <div class="col-sm-10">
               <label>
                   <input name="autoapprove" type="radio" class="minimal" checked value="true" lay-ignore> 启用
               </label>
               <label>
                   <input name="autoapprove" type="radio" class="minimal"  value="false" lay-ignore> 禁用
               </label>
           </div>
       </div>
     <div class="form-group">
   		<button type="submit" class="btn btn-success" lay-submit lay-filter="submit"><i class="fa fa-save"></i>  提 交</button>
   	 </div>
   </div>
 </form>
</@body>
<@footer>
</@footer>