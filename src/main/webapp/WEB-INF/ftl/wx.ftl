<#-- 取得 应用的绝对根路径 -->
<#assign basePath=request.contextPath>
<!DOCTYPE html>
<html>
<head>
    <title>扫描二维码订阅页面</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

    <meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.
">

    <link rel="stylesheet" href="/lib/weui.min.css">
    <link rel="stylesheet" href="/css/jquery-weui.css">
    <link rel="stylesheet" href="/css/demos.css">

</head>

<body ontouchstart>


<header class='demos-header'>
    <h1 class="demos-title">扫描二维码订阅页面</h1>
</header>

<div class="weui-cells weui-cells_form" style="text-align:center">
    <img style="max-width: 100%" src="/img/wx.jpg">
</div>

<div class="weui-flex">
    <div class="weui-flex__item">
        <div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" href="/login.shml" id="btnRegister">返回</a>
        </div>
    </div>
</div>

<div class="weui-footer">
    <p class="weui-footer__links">
        <a href="http://www.yttrium2016.cn" class="weui-footer__link">杨振宇博客</a>
    </p>
    <p class="weui-footer__text">Copyright © 2017 yttrium2016.cn</p>
</div>


<script src="/lib/jquery-2.1.4.js"></script>

<#-- 解除ios慢的特性 -->
<script src="/lib/fastclick.js"></script>
<script>
    $(function () {
        FastClick.attach(document.body);
    });
</script>
<script src="/js/jquery-weui.js"></script>

<script>

    //获取url中的参数
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    }

</script>
</body>
</html>
