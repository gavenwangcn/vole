<#macro header>
<!DOCTYPE html>
<html>
<head>
  <#include "/ftl/common/head.ftl">
   <link rel="stylesheet" href="/static/plugins/layui/css/layui.css">
  <#nested> 
</head>
</#macro>
<!-- /header -->
<#macro body>
	<#nested>
</#macro>
<!-- /body -->
<#macro footer>
	<#include "/ftl/common/js.ftl">
	<#nested>
</body>
</html>
</#macro>