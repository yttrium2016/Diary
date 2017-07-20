<#-- 取得 应用的绝对根路径 -->
<#assign basePath=request.contextPath>
<!DOCTYPE html>
<html>
<head>
    <title>日记本登录界面</title>
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
    <h1 class="demos-title">登录界面</h1>
</header>

<div class="weui-cells weui-cells_form">
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">用户名</label></div>
        <div class="weui-cell__bd">
            <input class="weui-input" name="loginName" id="loginName" type="text" autocapitalize="on"
                   placeholder="请输入用户名" onkeyup="value=value.replace(/[\W]/g,'') "
                   onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
        </div>
    </div>

    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">密码</label></div>
        <div class="weui-cell__bd">
            <input class="weui-input" name="loginPassword" id="loginPassword" type="password" placeholder="请输入密码">
        </div>
    </div>

</div>


<label for="weuiAgree" class="weui-agree">
    <input id="isRemember" type="checkbox" class="weui-agree__checkbox">
    <span class="weui-agree__text">
        记住密码
      </span>
</label>
<div class="weui-flex">
    <div class="weui-flex__item">
        <div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" href="javascript:" id="btnRegister">注册</a>
        </div>
    </div>
    <div class="weui-flex__item">
        <div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" href="javascript:" id="btnLogin">登录</a>
        </div>
    </div>
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

    var a = getUrlParam("error");
    if (a != null && a != '') {
        if (a == '1') {
            $.toptip('请先登录...');
        }
    }


    $("#btnLogin").click(function () {
        var loginName = $('#loginName').val();
        var loginPassword = $('#loginPassword').val();
        if (!loginName) $.toptip('用户名不能为空...', 'warning');
        else if (!loginPassword) $.toptip('密码不能为空...', 'warning');
//        else $.toptip('提交成功', 'success');
        else doLogin(loginName, loginPassword);
    });

    function doLogin(loginName, loginPassword) {
        $.showLoading("登录中...");

        $.ajax({
            type: "post",
            async: true,
            url: "${basePath }/user/login.do",
            dataType: "json",
            data: {
                "loginName": loginName,
                "loginPassword": loginPassword
            },
            success: function (data, textStatus) {

                $.hideLoading();

                if (textStatus) {

                    if (data.code == 1) {
                        $.toast(data.message, function () {

                        });
                        setTimeout(function () {
                            $.hideLoading();
                            location.href = data.redirect;
                        }, 900);

                    }
                    else {
                        $.toast(data.message, "cancel", function (toast) {
                        });
                        setTimeout(function () {
                            $.hideLoading();

                        }, 900);
                    }

                }

            }
            , error: function () {
                $.hideLoading();
            }
        });

    }
</script>
</body>
</html>
