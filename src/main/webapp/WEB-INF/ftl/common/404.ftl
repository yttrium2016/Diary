<#-- 取得 应用的绝对根路径 -->
<#assign basePath=request.contextPath>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title ! "错误页面"}</title>
    <link rel="stylesheet" href="${basePath}/css/ShapeShifter.css">
</head>

<body>

<canvas class="canvas"></canvas>

<script src="${basePath}/js/ShapeShifter.js"></script>
<script>
    var action = window.location.href,
            i = action.indexOf('?a=');
    if (i !== -1) {
        viewShow(decodeURI(action).substring(i + 3));
    } else {
        viewShow('${message !"错误页面|404   "}');
    }
</script>
</body>
</html>
