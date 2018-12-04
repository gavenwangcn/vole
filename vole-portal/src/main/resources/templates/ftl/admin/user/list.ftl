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
           <form action="/admin/user/list/1" method="post" class="form-inline">
	          <div class="box-header">
	            <div class="input-group">
		            <a class="btn btn-primary dialog" href="javascript:;" data-url="/admin/user/add" data-title="创建新用户" data-width="850" data-height="550"><i class="fa fa-plus"></i> 创建新用户</a>
	            </div>
	            <div class="input-group">
	               <input type="text" name="search" value="${search!}" class="form-control" placeholder="Search">
	               <div class="input-group-btn">
	                 <button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>
	                 <a href="/admin/user/list/1" class="btn btn-default"><i class="fa fa-refresh"></i></a>
	               </div>
	            </div>
	            <div class="input-group pull-right">
                 <button type="button" class="btn btn-primary btn-flat" onclick="exportTo('用户数据');"><i class="fa fa-file-excel-o"></i> 导出</button>
            </div>
	          </div><!-- /.box-header -->
           </form>
          <div class="box-body table-responsive no-padding">
            <table class="table table-hover">
              <tr>
                <th  width="100px"><input name="userState" type="checkbox" class="minimal checkbox-toolbar"> 行号</th>
                  <th>登陆名</th>
                  <th>用户名</th>
                <th>电话</th>
                <th>创建时间</th>
                <th>部门</th>
                <th>状态</th>
                <th width="120px">操作</th>
              </tr>
              <#list pageData.getRecords() as user>
              	  <tr>
	                <td>
	                   <label>
	                	<input type="checkbox" class="minimal checkbox-item">
	                	${((pageData.current-1)*pageData.size +user_index+1)!}
	                	</label>
	                </td>
                      <td>${(user.loginname)!}</td>
	                <td>${(user.username)!}</td>
	                <td>${(user.phone)!'--'}</td>
	                <td>${(user.create_time?string('yyyy/MM/dd HH:mm:ss'))!}</td>
	                <td>${(user.dept_name)!'--'}</td>
	                <td><#if user.del_flag==0>启用<#else><font color="red">禁用</font></#if></td>
	                <td>
	                	  <a class="btn btn-primary btn-xs dialog" href="javascript:;" data-url="/admin/user/edit/${(user.user_id)!}" data-title="编辑用户" data-width="850" data-height="550">编辑</a>
	                	<a class="btn btn-danger btn-xs"
	                	 data-tiggle="ajax"
	                	 data-submit-url="/admin/user/delete?id=${(user.user_id)!}"
	                	 data-confirm="您确定要删除该条记录吗?">删除</a>
	                </td>
	              </tr>
              </#list>
            </table>
          </div><!-- /.box-body -->
          <div class="box-footer row">
            <div class="col-md-6">
         	  <#include "/ftl/common/paginateBar.ftl" />
	  		  <@paginate pageData=pageData actionUrl="/admin/user/list/" urlParas="?search=${search!}&daterange=${daterange!}"  />
            </div>
            <div class="col-md-6 pull-left">
	             <#include "/ftl/common/paginate.ftl" />
	  			 <@paginate currentPage=pageData.getCurrent() totalPage=pageData.getPages() actionUrl="/admin/user/list/" urlParas="?search=${search!}&pageSize=${pageData.getPageSize()!}"  />
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