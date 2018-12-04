<#include "/ftl/common/layout_dl.ftl">
<@header>
<style>
	td > label{
		padding: 3px;
	}
</style>
</@header>
<@body>
     <div class="row">
      <div class="col-md-12">
        <!-- general form elements -->
        <form action="/mps/role/doAuth" method="post" class="layui-form layui-form-pane">
        <input type="hidden" value="${(sysRole.roleId)!}" name="roleId" />
        <div class="box">
          <!-- form start -->
          <div class="box-body">
	        <table class="table table-hover">
	          <#list permissions as vo>
	          <tr>
	             <td width="150px">
	             	<label><input name="permissionId" type="checkbox" 'checked'
                                  <#list permissionIds as id>
                                  <#if vo.permissionId?string == id?string>
                                      'checked'
                                  </#if>
                                  </#list>
                                  value="${(vo.permissionId)!}" class="minimal checkbox-tr" lay-ignore> ${(vo.permission)!} ></label></td>
	           </tr>
	           </#list>
	        </table>
	      </div>
         <div class="box-footer">
              <button type="submit" class="btn btn-success" lay-submit lay-filter="submit"><i class="fa fa-save"></i>  确认授权</button>
              <a  class="btn btn-default" href="javascript:parent.layer.closeAll('iframe');"><i class="fa fa-angle-left"></i>  返回</a>
         </div>
        </div><!-- /.box -->
        </form>
      </div><!--/.col (left) -->
     </div>
</@body>
<@footer>
<script type="text/javascript">
	
	 $(".checkbox-tr").on('ifClicked',function(){
		$(this).parents('tr').find('.checkbox-td').iCheck('check');
	}).on('ifUnchecked',function(){
		$(this).parents('tr').find('.checkbox-td').iCheck('uncheck');
	});
	
</script>
</@footer>