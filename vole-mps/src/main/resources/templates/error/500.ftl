<#include "/ftl/common/layout.ftl">
<@header>
</@header>
<@body>
<div class="content-wrapper">
  <!-- Main content -->
  <section class="content">
    <div class="error-page">
       <div>
        <h2 class="headline text-yellow">  <i class="fa fa-warning text-yellow"></i> 500, Server error.</h2>
        <p> 糟糕,服务器异常,您可以点击这里 <a href="/index.html">返回首页</a>或刷新页面重试！
        <p> Error:${error!}
        </p>
      </div><!-- /.error-content -->
    </div><!-- /.error-page -->
  </section><!-- /.content -->
</div><!-- /.content-wrapper -->
</@body>
<@footer>
</@footer>



