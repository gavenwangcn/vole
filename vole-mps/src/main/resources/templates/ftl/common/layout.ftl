<#macro header>
<!DOCTYPE html>
<html>
<head>
  <#include "/ftl/common/head.ftl">
  <#nested> 
</head>
</#macro>
<!-- /header -->
<#macro body>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<#include "/ftl/common/header.ftl">
	<#include "/ftl/common/menu.ftl">
	<#nested>
	<#include "/ftl/common/footer.ftl">
	<div class="control-sidebar-bg"></div>
</div>
</#macro>
<!-- /body -->
<#macro footer>
	<#include "/ftl/common/js.ftl">
	<#nested>
</body>
</html>
</#macro>