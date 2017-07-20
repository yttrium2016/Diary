<#-- 取得 应用的绝对根路径 -->
<#assign basePath=request.contextPath>
<!DOCTYPE html>
<html>
<head>
    <title>日记界面</title>
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
    <h1 class="demos-title">日记界面</h1>
</header>
<div class="weui-btn-area">
<div class="weui-cells weui-cells_form">

    <form id="dairyForm" method="post">
        <div class="weui-cells__title">标题：</div>
        <div class="weui-cells">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" name="title" id="title" value="${(diary.title) ! "" }"
                           placeholder="请输入标题" required="">
                </div>
            </div>
        </div>

        <div class="weui-cells__title">内容：</div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__bd">
            <textarea class="weui-textarea" placeholder="请输入内容" rows="4" name="content"
                      id="content" required="">${(diary.content) ! "" }</textarea>
                    <div class="weui-textarea-counter">
                    </div>
                </div>
            </div>
        </div>


        <div class="weui-cells__title">天气：</div>
        <div class="weui-cells">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" id="weather" value="${(diary.weather) ! "" }" name="weather"
                           placeholder="请输入天气" required="">

                </div>
                <div class="weui-cell__ft">
                    <a onclick="getWeather()"><i class="weui-icon-search"></i></a>
                </div>
            </div>
        </div>
    <#if (diary.id) ??>
        <input name="id" hidden="hidden" type="text" value="${(diary.id)!""}">
    </#if>
    </form>
    </div>
</div>

<div class="weui-flex">
    <div class="weui-flex__item">
        <div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" onclick="doSubmit()" id="submit">提交</a>
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

    function getWeather() {
        var city = '';
        city = $("#weather").val();
        $.showLoading("获取天气中...");
        $.ajax({
            type: "post",
            async: true,
            url: "${basePath }/open/getWeatherByCity.do",
            dataType: "json",
            data: {
                "city": city
            },
            success: function (data, textStatus) {
                $.hideLoading();
                if (textStatus) {

                    if(data.status == 200) {
                        var w = city + ':' + data.data.forecast[0].type.toString() + ',' + data.data.forecast[0].low.toString() + ',' + data.data.forecast[0].high.toString();
                        $("#weather").val(w);
                    }
                }
            }
            , error: function () {
                $.hideLoading();
            }
        });
    }

    //获取url中的参数
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    }

    var id = getUrlParam("id");
    if (id != null && id != '') {

    } else {
        $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function () {
//        alert(remote_ip_info.country);
//        alert(remote_ip_info.province);
//        alert(remote_ip_info.city);

            var city = '';
            city = remote_ip_info.city;

            $.showLoading("自动获取天气中...");
            $.ajax({
                type: "post",
                async: true,
                url: "${basePath }/open/getWeatherByCity.do",
                dataType: "json",
                data: {
                    "city": city
                },
                success: function (data, textStatus) {
                    $.hideLoading();
                    if (textStatus) {

                        if(data.status == 200) {
                            var w = city + ':' + data.data.forecast[0].type.toString() + ',' + data.data.forecast[0].low.toString() + ',' + data.data.forecast[0].high.toString();
                            $("#weather").val(w);
                        }
                    }
                }
                , error: function () {
                    $.hideLoading();
                }
            });

        });
    }

    function doSubmit() {
        var title = $('#title').val();
        var content = $('#content').val();
        if (!title) $.toptip('标题不能为空...', 'warning');
        else if (!content) $.toptip('内容不能为空...', 'warning');
        else submit();
    }


    function submit() {
        var formParam = $("form").serialize();

        $.showLoading("提交中...");
        $.ajax({
            type: "post",
            async: true,
            url: "${basePath }/diary/addOrEditDiary.do",
            dataType: "json",
            data: formParam,
            success: function (data, textStatus) {
                $.hideLoading();
                if (textStatus) {

                    if (data.code == 1)
                        $.toast(data.message, function () {
                            self.location=document.referrer;
//                            location.href = data.redirect;
                        });
                    else
                        $.toast(data.message, "cancel", function (toast) {
                        });
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
