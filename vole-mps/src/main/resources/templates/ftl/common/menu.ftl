<aside class="main-sidebar">
 <!-- sidebar: style can be found in sidebar.less -->
 <section class="sidebar">
   <!-- Sidebar member panel (optional) -->
   <div class="user-panel">
     <div class="pull-left image">
       <img src="${(me.avatar)!}" class="img-circle">
     </div>
     <div class="pull-left info">
       <p>${(me.username)!'游客'}</p>
       <a href="#"><i class="fa fa-circle text-success"></i> 在线</a>
     </div>
   </div>
    <!-- search form -->
      <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="Search...">
          <span class="input-group-btn">
            <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i></button>
          </span>
        </div>
      </form>
      <!-- /.search form -->

   <!-- Sidebar Menu -->
   <ul class="sidebar-menu">
     <!-- Optionally, you can add icons to the links -->
     <li class="header">系统菜单</li>
     <#if treeMenus??>
	     <#list treeMenus as vo>
		     <li class="treeview <#if res??&&vo.sysMenu.id==res?number> active </#if> ">
		       <a href="#"><i class="fa ${(vo.sysMenu.icon)!}"></i> 
		       <span>${(vo.sysMenu.menuName)!}</span>  
		       <i class="fa fa-angle-left pull-right"></i>
		       </a>
		       <ul class="treeview-menu">
		         <#list vo.children as ch>
		         	<li><a href="${(ch.sysMenu.url)!}?p=${(vo.sysMenu.id)!}&t=${(ch.sysMenu.id)!}" style="<#if cur?? && ch.sysMenu.id==cur?number> color: white </#if>"><i class="fa ${(ch.sysMenu.icon)!}"></i> ${(ch.sysMenu.menuName)!}</a></li>
		         </#list>
		       </ul>
		     </li>
	     </#list>
     </#if>
   </ul><!-- /.sidebar-menu -->
 </section>
 <!-- /.sidebar -->
</aside>