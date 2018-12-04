<#include "/ftl/common/layout_dl.ftl">
<@header>
</@header>
<@body>
<form role="form" class="layui-form layui-form-pane pd10" method="post" action="/mps/route/doEdit">
  <div class="box-body">
  	<input type="hidden" value="${(entity.id)}" name="id" />
      <div class="form-group">
          <label for="path">路径</label>
          <input type="text" id="path" name="path" class="form-control" value="${(entity.path)!}" lay-verify="required"  placeholder="请输入路径">
      </div>
      <div class="form-group">
          <label for="serviceId">服务名称</label>
          <input type="text" id="serviceId" name="serviceId" class="form-control" value="${(entity.serviceId)!}" lay-verify="required"  placeholder="请输入服务名称">
      </div>
      <div class="form-group">
          <label for="url">代理URL</label>
          <input type="text" id="url" name="url" class="form-control" value="${(entity.url)!}" placeholder="请输入代理URL">
      </div>
      <div class="form-group">
          <label>敏感请求头</label>
          <textarea class="form-control" name="sensitiveheadersList" rows="3" placeholder="请输入敏感请求头，最多300个字符 ..." >${(entity.sensitiveheadersList)!}</textarea>
      </div>
      <div class="form-group">
          <label   class="col-sm-2 control-label">去掉前缀</label>
          <div class="col-sm-10">
              <label>
                  <input name="stripPrefix" type="radio" class="minimal" ${(entity.stripPrefix?string == '1')?string('checked','')}  value="1" lay-ignore> 是
              </label>
              <label>
                  <input name="stripPrefix" type="radio" class="minimal" ${(entity.stripPrefix?string == '0')?string('checked','')}  value="0" lay-ignore> 否
              </label>
          </div>
      </div>
      <div class="form-group">
          <label   class="col-sm-2 control-label">重试</label>
          <div class="col-sm-10">
              <label>
                  <input name="retryable" type="radio" class="minimal" ${(entity.retryable?string == '1')?string('checked','')}  value="1" lay-ignore> 是
              </label>
              <label>
                  <input name="retryable" type="radio" class="minimal" ${(entity.retryable?string == '0')?string('checked','')} value="0" lay-ignore> 否
              </label>
          </div>
      </div>
      <div class="form-group">
          <label   class="col-sm-2 control-label">状态</label>
          <div class="col-sm-10">
              <label>
                  <input name="enabled" type="radio" class="minimal" ${(entity.enabled?string == '1')?string('checked','')} value="1" lay-ignore> 启用
              </label>
              <label>
                  <input name="enabled" type="radio" class="minimal" ${(entity.enabled?string == '0')?string('checked','')} value="0" lay-ignore> 禁用
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