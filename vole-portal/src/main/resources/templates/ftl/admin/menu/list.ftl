<#include "/ftl/common/layout.ftl">
<@header>
</@header>
<@body>
<div class="content-wrapper">
  <!-- Content Header (Page header) -->
  <section class="content-header">
    <h1>
      <small>系统管理 > 菜单管理</small>
    </h1>
  </section>
  <!-- Main content -->
  <section class="content">
    <!-- Your Page Content Here -->
    <div class="row">
      <div class="col-xs-12">
        <div class="box">
         <form action="/admin/menu/list/1" method="post" class="form-inline">
          <div class="box-header">
            <div class="input-group">
           	 <a class="btn btn-primary dialog" href="javascript:;" data-title="创建菜单" data-url="/admin/menu/add" data-width="800" data-height="650"><i class="fa fa-plus"></i> 创建菜单</a>
            </div>
            <div class="input-group">
               <input type="text" name="search" value="${search!}" class="form-control" placeholder="Search">
               <div class="input-group-btn">
                 <button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>
                 <a href="/admin/menu/list/1" class="btn btn-default"><i class="fa fa-refresh"></i></a>
               </div>
            </div>
            <div class="input-group pull-right">
                 <button type="button" class="btn btn-primary btn-flat" onclick="exportTo('菜单数据');"><i class="fa fa-file-excel-o"></i> 导出</button>
            </div>
          </div><!-- /.box-header -->
          </form>
          <div class="box-body table-responsive no-padding">
            <table class="table table-hover">
              <tr>
                <th  width="100px"><input value="root" type="checkbox" class="minimal checkbox-toolbar"> 行号</th>
                <th>菜单名称</th>
                <th>编码</th>
                <th>访问地址</th>
                <th>显示图标</th>
                <th>深度</th>
                <th>资源</th>
                <th>排序</th>
                <th>类型</th>
                <th width="150px">操作</th>
              </tr>
              <#list pageData.getRecords() as menu>
              	  <tr>
	                <td>
	                	<label>
	               	 		<input value="${menu.id}" name="roleState" type="checkbox" class="minimal checkbox-item">
	               		 	${((pageData.current-1)*pageData.size +menu_index+1)!}
	                 	</label>
	               	</td>
	                <td>${(menu.menuName)!}</td>
	                <td>${(menu.code)!}</td>
	                <td><#if menu.deep?? && (menu.deep==1 || menu.deep==3) >--<#else><a href="${(menu.url)!'#'}" target="_blank">${(menu.url)!}</a></#if></td>
	                <td><i class="fa ${(menu.icon)!}"></i></td>
	                <td>${(menu.deep)!}</td>
	                <td>${(menu.resource)!"--"}</td>
	                <td>${(menu.sort)!}</td>
	                <td><#if menu.deep?? && menu.deep==1 >目录<#elseif menu.deep?? && menu.deep==2>菜单<#else>功能</#if> </td>
	                <td>
	                	   <a class="btn btn-primary btn-xs dialog" href="javascript:;" data-title="编辑菜单" data-url="/admin/menu/edit/${(menu.id)!}" data-width="800" data-height="650"  data-toggle="tooltip" title="编辑" data-placement="bottom">编辑</a>
	                	<a class="btn btn-danger btn-xs" data-toggle="tooltip" title="删除" data-placement="bottom"
	                	 data-tiggle="ajax"
	                	 data-submit-url="/admin/menu/delete?id=${(menu.id)!}"
	                	 data-confirm="您确定要删除该条记录吗?">删除</a>
	                </td>
	              </tr>
              </#list>
            </table>
          </div><!-- /.box-body -->
          <div class="box-footer row">
          <div class="col-md-6">
         	  <#include "/ftl/common/paginateBar.ftl" />
	  		  <@paginate pageData=pageData actionUrl="/admin/menu/list/" urlParas="?search=${search!}"  />
            </div>
            <div class="col-md-6 pull-left">
            <#include "/ftl/common/paginate.ftl" />
 			<@paginate currentPage=pageData.getCurrent() totalPage=pageData.getPages() actionUrl="/admin/menu/list/" urlParas="?search=${search!}&pageSize=${pageData.getPageSize()!}"  />
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