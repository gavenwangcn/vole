<#include "/ftl/common/layout.ftl">
<@header>
</@header>
<@body>
<div class="content-wrapper">
  <!-- Content Header (Page header) -->
  <section class="content-header">
    <h1>
      <small>会员管理 > 权限管理</small>
    </h1>
  </section>
  <!-- Main content -->
  <section class="content">
    <!-- Your Page Content Here -->
    <div class="row">
      <div class="col-xs-12">
        <div class="box">
         <form action="/mps/permission/list/1" method="post" class="form-inline">
          <div class="box-header">
	            <div class="input-group">
	            	<a class="btn btn-primary dialog" href="javascript:;" data-title="创建权限" data-url="/mps/permission/add" data-width="800" data-height="600"><i class="fa fa-plus"></i> 创建权限</a>
	            </div>
              <div class="input-group">
                  <a class="btn btn-danger" delete-batch-url="/mps/permission/deleteBatch" ><i class="fa fa-remove"></i> 批量删除选中</a>
              </div>
             <div class="input-group">
               <input type="text" name="search" value="${search!}" class="form-control" placeholder="Search">
               <div class="input-group-btn">
                 <button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>
                 <a href="/admin/setting/list/1" class="btn btn-default"><i class="fa fa-refresh"></i></a>
               </div>
            </div>
            <div class="input-group pull-right">
                 <button type="button" class="btn btn-primary btn-flat" onclick="exportTo('权限数据');"><i class="fa fa-file-excel-o"></i> 导出</button>
            </div>
          </div><!-- /.box-header -->
          </form>
          <div class="box-body table-responsive no-padding">
            <table class="table table-hover">
              <tr>
                <th  width="100px">行号</th>
                <th>权限标识</th>
                <th>权限URL</th>
                  <th>权限方法</th>
                  <th>描述</th>
                <th width="120px">操作</th>
              </tr>
              <#list pageData.getRecords() as entity>
              	  <tr>
	                <td>
                        <label>
                        <input value="${entity.permissionId!}" name="id" type="checkbox" class="minimal checkbox-item">
                        ${((pageData.current-1)*pageData.size +entity_index+1)!}
                        </label>
                    </td>
	                <td>${(entity.permission)!}</td>
	                <td>${(entity.url)!'--'}</td>
                      <td>${(entity.method)!'--'}</td>
                      <td>${(entity.description)!'--'}</td>
	                <td>
                        <a class="btn btn-primary btn-xs dialog" href="javascript:;" data-url="/mps/permission/edit/${(entity.permissionId)!}" data-title="编辑权限" data-width="800" data-height="600" data-toggle="tooltip" title="编辑" data-placement="bottom">编辑</a>
                        <a class="btn btn-danger btn-xs" data-toggle="tooltip" title="删除" data-placement="bottom"
                           data-tiggle="ajax"
                           data-submit-url="/mps/permission/delete?id=${(entity.permissionId)!}"
                           data-confirm="您确定要删除该条记录吗?">删除</a>
	                </td>
	              </tr>
              </#list>
            </table>
          </div><!-- /.box-body -->
          <div class="box-footer row">
            <div class="col-md-6">
         	  <#include "/ftl/common/paginateBar.ftl" />
	  		  <@paginate pageData=pageData actionUrl="/mps/permission/list/" urlParas="?search=${search!}"  />
            </div>
            <div class="col-md-6 pull-left">
	             <#include "/ftl/common/paginate.ftl" />
	  			 <@paginate currentPage=pageData.getCurrent() totalPage=pageData.getPages() actionUrl="/mps/permission/list/" urlParas="?search=${search!}&pageSize=${pageData.getPageSize()!}"  />
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