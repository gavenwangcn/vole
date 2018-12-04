<#include "/ftl/common/layout_dl.ftl">
<@header>
</@header>
<@body>
<form role="form" class="layui-form layui-form-pane pd10" method="post" action="/admin/setting/doEdit">
  <div class="box-body">
  	<input type="hidden" value="${(entity.id)}" name="id" />
    <div class="form-group">
      <label for="sysName">系统名称</label>
     	<input type="text" id="sysName" name="sysName" class="form-control" value="${(entity.sysName)!}" lay-verify="required"  placeholder="请输入系统名称">
    </div>
      <div class="form-group">
          <label for="sysSubName">系统简称</label>
          <input type="text" id="sysSubName" name="sysSubName" class="form-control" value="${(entity.sysSubName)!}" lay-verify="required"  placeholder="请输入系统简称">
      </div>
      <div class="form-group">
          <label for="sysName">系统标识</label>
          <input type="text" id="sysGlobalKey" name="sysGlobalKey" class="form-control" value="${(entity.sysGlobalKey)!}" lay-verify="required"  placeholder="请输入系统表标识">
      </div>
    <div class="form-group">
    <button type="submit" class="btn btn-success" lay-submit lay-filter="submit"><i class="fa fa-save"></i>  提 交</button>
    </div>
  </div>
</form>
</@body>
<@footer>
</@footer>