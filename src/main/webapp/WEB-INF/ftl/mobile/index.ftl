<#-- 取得 应用的绝对根路径 -->
<#assign basePath=request.contextPath>
<!DOCTYPE html>
<html>
<head>
    <title>日记本</title>
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

<!-- 天气信息 -->
<div style="width: 100%; height:35px; border:none;background: #99ccff;">
    <div class="weather" style="padding: 2px;text-align: center;" pc>
        <div id="tp-weather-widget"></div>
        <script>(function (T, h, i, n, k, P, a, g, e) {
            g = function () {
                P = h.createElement(i);
                a = h.getElementsByTagName(i)[0];
                P.src = k;
                P.charset = "utf-8";
                P.async = 1;
                a.parentNode.insertBefore(P, a)
            };
            T["ThinkPageWeatherWidgetObject"] = n;
            T[n] || (T[n] = function () {
                (T[n].q = T[n].q || []).push(arguments)
            });
            T[n].l = +new Date();
            if (T.attachEvent) {
                T.attachEvent("onload", g)
            } else {
                T.addEventListener("load", g, false)
            }
        }(window, document, "script", "tpwidget", "//widget.seniverse.com/widget/chameleon.js"))</script>
        <script>tpwidget("init", {
            "flavor": "slim",
            "location": "WX4FBXXFKE4F",
            "geolocation": "enabled",
            "language": "zh-chs",
            "unit": "c",
            "theme": "chameleon",
            "container": "tp-weather-widget",
            "bubble": "disabled",
            "alarmType": "badge",
            "color": "#FFFFFF",
            "uid": "U9EC08A15F",
            "hash": "039da28f5581f4bcb5c799fb4cdfb673"
        });
        tpwidget("show");</script>
    </div>
</div>

<header class='demos-header'>
    <h1 class="demos-title">日记本</h1>
    <p class='demos-sub-title'>欢迎你,<#if Session.loginUser?exists>${Session.loginUser.userName}</#if>&ensp;这是个轻量级的日记本</p>
</header>

<div class="weui-grids">
    <a href="/diary/addOrEdit.shtml" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_button.png" alt="">
        </div>
        <p class="weui-grid__label">
            写日记
        </p>
    </a>
    <a href="/diary/listDiary.shtml?type=my"
       class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_article.png" alt="">
        </div>
        <p class="weui-grid__label">
            我的日记
        </p>
    </a>
    <a href="/user/accredit.shtml" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_toast.png" alt="">
        </div>
        <p class="weui-grid__label">
            日记授权
        </p>
    </a>
    <a href="/diary/listDiary.shtml?type=friend" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_cell.png" alt="">
        </div>
        <p class="weui-grid__label">
            朋友日记
        </p>
    </a>
    <a href="/user/editUser.shtml" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_panel.png" alt="">
        </div>
        <p class="weui-grid__label">
            我的设置
        </p>
    </a>
    <a onclick="showLogout()" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_dialog.png" alt="">
        </div>
        <p class="weui-grid__label">
            退出
        </p>
    </a>
    <#--<a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_progress.png" alt="">
        </div>
        <p class="weui-grid__label">
            Progress
        </p>
    </a>
    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_msg.png" alt="">
        </div>
        <p class="weui-grid__label">
            Msg
        </p>
    </a>
    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_article.png" alt="">
        </div>
        <p class="weui-grid__label">
            Article
        </p>
    </a>
    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_actionSheet.png" alt="">
        </div>
        <p class="weui-grid__label">
            ActionSheet
        </p>
    </a>
    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_icons.png" alt="">
        </div>
        <p class="weui-grid__label">
            Icons
        </p>
    </a>
    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_tab.png" alt="">
        </div>
        <p class="weui-grid__label">
            Navbar
        </p>
    </a>

    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_search_bar.png" alt="">
        </div>
        <p class="weui-grid__label">
            SearchBar
        </p>
    </a>
    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_new.png" alt="">
        </div>
        <p class="weui-grid__label">
            Loadmore
        </p>
    </a>
    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_ptr.png" alt="">
        </div>
        <p class="weui-grid__label">
            下拉刷新
        </p>
    </a>
    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_up.png" alt="">
        </div>
        <p class="weui-grid__label">
            滚动加载
        </p>
    </a>
    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_picker.png" alt="">
        </div>
        <p class="weui-grid__label">
            Picker
        </p>
    </a>
    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_calendar.png" alt="">
        </div>
        <p class="weui-grid__label">
            Calendar
        </p>
    </a>
    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_city.png" alt="">
        </div>
        <p class="weui-grid__label">
            City Picker
        </p>
    </a>
    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_datetime.png" alt="">
        </div>
        <p class="weui-grid__label">
            Datetime
        </p>
    </a>
    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_swiper.png" alt="">
        </div>
        <p class="weui-grid__label">
            Swiper
        </p>
    </a>

    <a onclick="showToast(this)" class="weui-grid js_grid">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_select.png" alt="">
        </div>
        <p class="weui-grid__label">
            Select
        </p>
    </a>

    <a onclick="showToast(this)" class="weui-grid js_grid open-popup">
        <div class="weui-grid__icon">
            <img src="/img/icon_nav_photo.png" alt="">
        </div>
        <p class="weui-grid__label">
            Photos
        </p>
    </a>-->
</div>

<div class="weui-footer">
    <p class="weui-footer__links">
        <a href="http://www.yttrium2016.cn" class="weui-footer__link">杨振宇博客</a>
    </p>
    <p class="weui-footer__text">Copyright © 2017 yttrium2016.cn</p>
</div>

<style>
    .weui-footer {
        margin: 25px 0 10px 0;
    }
</style>


<script src="/lib/jquery-2.1.4.js"></script>
<script src="/lib/fastclick.js"></script>
<script>
    $(function () {
        FastClick.attach(document.body);
    });
</script>
<script src="/js/jquery-weui.js"></script>
<script>
    function showToast(a) {
        var name = a.getElementsByTagName('p').item(0).textContent;
        $.toast(name, "text");
        console.log(a);
    }

    function showLogout() {
        $.confirm(name, "确认退出?", function () {
            location.href = '/user/logout.shtml';
        }, function () {
            //取消操作
        });
    }


</script>
</body>

</html>
