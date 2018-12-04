<#assign ctx = request.contextPath>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>统一登录</title>

    <link rel="shortcut icon" href="/static/favicon.ico">
    <link href="/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/static/css/animate.css" rel="stylesheet">
    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
    <script src="/static/js/jquery.min.js?v=2.1.4"></script>
    <script src="/static/js/bootstrap.min.js?v=3.3.6"></script>
</head>

<body class="gray-bg">
<div class="middle-box text-center loginscreen  animated fadeInDown">
    <div style="padding: 100px 0px;">
        <div>
            <h1 class="logo-name">Passport</h1>
        </div>
        <h3>欢迎使用 统一登陆</h3>
        <br/>
        <h4 style="color: red;">
<#if Session.SPRING_SECURITY_LAST_EXCEPTION?exists>
    ${Session.SPRING_SECURITY_LAST_EXCEPTION.message!"登陆失败"}
</#if>
        </h4>
        <form class="m-t" role="form" action="/passport/form" method="post">

            <div class="form-group">
                <input type="text" name="username" class="form-control" placeholder="用户名" required="" autofocus>
            </div>
            <div class="form-group">
                <input type="password" name="password" class="form-control" placeholder="密码" required="">
            </div>
            <input type="hidden" name="test" class="form-control" placeholder="service" value="test">
            <input type="hidden" name="service" class="form-control" placeholder="service" value="${service!}">
            <input type="hidden" name="target" class="form-control" placeholder="target" value="${target!}">
            <#--<div class="form-group" style="float: left;">-->
                <#--<div class="checkbox" style="text-align: left">-->
                    <#--<label>-->
                        <#--<input type="checkbox" name="remember" style="margin-top: 2px;">记住我-->
                    <#--</label>-->
                <#--</div>-->
            <#--</div>-->
            <button type="submit" class="btn btn-primary block full-width m-b">登 录</button>
            </p>
        </form>
    </div>
</div>
</body>

</html>
