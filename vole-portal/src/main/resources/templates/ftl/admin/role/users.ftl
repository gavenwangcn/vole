<#include "/ftl/common/layout_dl.ftl">
<@header>
</@header>
<@body>
 <table class="table table-hover">
  <tr>
    <th>登陆名</th>
    <th>用户名</th>
    <th>创建时间</th>
    <th>状态</th>
  </tr>
  <#list users as user>
  	  <tr>
          <td>${(user.loginname)!}</td>
     <td>${(user.username)!}</td>
     <td>${(user.createTime?string('yyyy/MM/dd HH:mm:ss'))!}</td>
     <td><#if user.delFlag==0>启用<#else><font color="red">禁用</font></#if></td>
   </tr>
  </#list>
</table>
</@body>
<@footer>
</@footer>
