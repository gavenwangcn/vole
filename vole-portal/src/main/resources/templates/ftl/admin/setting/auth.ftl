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
        <form action="/admin/setting/doAuth" method="post" class="layui-form layui-form-pane">
        <input type="hidden" value="${(sysSetting.id)!}" name="sysId" />
        <div class="box">
          <!-- form start -->
          <div class="box-body">
	        <table class="table table-hover">
	          <#list treeMenuAllowAccesses as vo>
	          <tr>
	             <td width="150px">
	             	<label><input name="mid" type="checkbox"
							<#if (vo.allowAccess) ==1>
							    checked="checked"
							</#if>
								  value="${(vo.sysMenu.id)!}" class="minimal checkbox-tr" lay-ignore> <i class="fa ${(vo.sysMenu.icon)!}"></i> ${(vo.sysMenu.menuName)!}</label></td>
	             </td>
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