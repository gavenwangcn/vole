<#include "/ftl/common/layout.ftl">
<@header>
</@header>
<@body>
<div class="content-wrapper">
  <!-- Content Header (Page header) -->
  <section class="content-header">
    <h1>
      <small>系统管理 > 角色管理</small>
    </h1>
  </section>
  <!-- Main content -->
  <section class="content">
    <!-- Your Page Content Here -->
    <div class="row">
      <div class="col-xs-12">
        <div class="box">
         <form action="/mps/role/list/1" method="post" class="form-inline">
          <div class="box-header">
	            <div class="input-group">
		            <a class="btn btn-primary dialog" href="javascript:;" data-url="/mps/role/add" data-title="创建新角色" data-width="850" data-height="400"><i class="fa fa-plus"></i> 创建新角色</a>
	            </div>
	          	 <div class="input-group">
		            <a class="btn btn-danger" delete-batch-url="/mps/role/deleteBatch" ><i class="fa fa-remove"></i> 批量删除选中</a>
	            </div>
             <div class="input-group">
	               <input type="text" name="search" value="${search!}" class="form-control" placeholder="Search">
	               <div class="input-group-btn">
	                 <button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>
	                 <a href="/mps/role/list/1" class="btn btn-default"><i class="fa fa-refresh"></i></a>
	               </div>
	          </div>
	         <div class="input-group pull-right">
                 <button type="button" class="btn btn-primary btn-flat" onclick="exportTo('角色数据');"><i class="fa fa-file-excel-o"></i> 导出</button>
             </div>
          </div><!-- /.box-header -->
          </form>
          <div class="box-body table-responsive no-padding">
            <table class="table table-hover">
              <tr>
                <th  width="100px">行号</th>
                <th>角色名称</th>
                  <th>角色编码</th>
                <th>描述</th>
                <th>创建时间</th>
                <th>状态</th>
                <th width="150px">操作</th>
              </tr>
              <#list pageData.getRecords() as role>
              	  <tr>
	                <td>
	                	<label>
	                	<input value="${role.roleId!}" name="id" type="checkbox" class="minimal checkbox-item">
	                	${((pageData.current-1)*pageData.size +role_index+1)!}
	                	</label>
	                </td>
	                <td>${(role.roleName)!}</td>
                      <td>${(role.roleCode)!}</td>
	                <td>${(role.roleDesc)!'--'}</td>
	                <td>${(role.createTime?string('yyyy/MM/dd HH:mm:ss'))!'--'}</td>
	                <td><#if role.delFlag?string=='0'>启用<#else><font color="red">禁用</font></#if></td>
	                <td>
	                		<a class="btn btn-success btn-xs dialog" href="javascript:;" data-url="/mps/role/auth/${(role.roleId)!}" data-title="授权" data-width="850" data-height="600" data-toggle="tooltip" title="授权" data-placement="bottom">授权</a>

	                		<a class="btn btn-primary btn-xs dialog" href="javascript:;" data-url="/mps/role/edit/${(role.roleId)!}" data-title="创建新角色" data-width="850" data-height="400" data-toggle="tooltip" title="编辑" data-placement="bottom">编辑</a>
	 	               	<a class="btn btn-danger btn-xs" data-toggle="tooltip" title="删除" data-placement="bottom"
	                	 data-tiggle="ajax"
	                	 data-submit-url="/mps/role/delete?id=${(role.roleId)!}"
	                	 data-confirm="您确定要删除该条记录吗?">删除</a>
	                </td>
	              </tr>
              </#list>
            </table>
          </div><!-- /.box-body -->
          <div class="box-footer row">
          	<div class="col-md-6">
         	  <#include "/ftl/common/paginateBar.ftl" />
	  		  <@paginate pageData=pageData actionUrl="/mps/role/list/" urlParas="?search=${search!}&daterange=${daterange!}"  />
            </div>
            <div class="col-md-6 pull-left">
            <#include "/ftl/common/paginate.ftl" />
 			<@paginate currentPage=pageData.getCurrent() totalPage=pageData.getPages() actionUrl="/mps/role/list/" urlParas="?search=${search!}&pageSize=${pageData.getPageSize()!}"  />
            </div>
          </div>
        </div><!-- /.box -->
      </div>
    </div>
  </section><!-- /.content -->
</div><!-- /.content-wrapper -->
</@body>
<@footer>
</@footer>