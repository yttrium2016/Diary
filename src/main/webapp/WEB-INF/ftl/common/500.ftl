<#-- 取得 应用的绝对根路径 -->
<#assign basePath=request.contextPath>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>505錯誤</title>
</head>
<body>
<h2>这个类-> ${clazz ! ""} 错误</h2><br/>
<h2>这个方法错误-> ${methodName ! ""} 错误</h2><br/>
<h2>这行错误-> ${line ! ""} 错误</h2><br/>
<h2>错误信息-> ${message ! ""}</h2><br/>
</body>
</html>