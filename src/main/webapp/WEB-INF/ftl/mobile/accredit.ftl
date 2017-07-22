<!DOCTYPE html>
<html>
<head>
    <title>授权界面</title>
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
    <h1 class="demos-title">授权界面</h1>
</header>


<div class="weui-cells weui-cells_form">


    <div class="weui-cell">
        <div class="weui-cell__hd"><label for="name" class="weui-label">点击授权</label></div>
        <div class="weui-cell__bd">
            <input class="weui-input" id="user" type="text" value="">
        </div>
        <div class="weui-cell__ft">
            <a onclick="submit()" class="weui-btn weui-btn_mini weui-btn_primary">提交</a>
        </div>
    </div>


</div>
<div class="weui-cells__title">已授权用户</div>
<div class="weui-cells">

<#if privilegeUserList?? && (privilegeUserList?size > 0) >
    <#list privilegeUserList!"" as user>

        <div class="weui-cell">
            <div class="weui-cell__bd">
                <p>${user.userName !""}</p>
            </div>
            <div class="weui-cell__ft"><a onclick="_delete(${user.id !""})" class="weui-btn weui-btn_mini weui-btn_warn">删除</a></div>
        </div>
    </#list>
</#if>
</div>
<script src="/lib/jquery-2.1.4.js"></script>
<script src="/lib/fastclick.js"></script>
<script>
    $(function () {
        FastClick.attach(document.body);
    });
</script>
<script src="/js/jquery-weui.js"></script>


<script>
    var item = [
    <#if userList?? && (userList?size > 0) >
        <#list userList!"" as user>
            {
                title: '${user.userName !""}',
                value: ${user.id !""}
            },
        </#list>
    </#if>];


    $("#user").select({
        title: "让谁看你的日记",
        items: item
    });

    function submit() {
        var id = $("#user").attr("data-values");
        if (id){
            $.showLoading("添加中...");
            $.ajax({
                type: "post",
                async: true,
                url: "/privilege/add.do",
                dataType: "json",
                data: {
                    "id": id,
                },
                success: function (data, textStatus) {
                    $.hideLoading();
                    if (textStatus) {
                        if (data.code == 1) {

                            $.toast(data.message, function () {
                            });
                            location.reload();
                            setTimeout(function () {
                                $.hideLoading();
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
        }else {
            $.toptip('请选择授权用户...', 'warning');
        }
    }

    function _delete(id) {
        $.confirm(name, "确认删除?", function () {
            $.showLoading("删除中...");
            $.ajax({
                type: "post",
                async: true,
                url: "/privilege/delete.do",
                dataType: "json",
                data: {
                    "id": id,
                },
                success: function (data, textStatus) {
                    $.hideLoading();
                    if (textStatus) {
                        if (data.code == 1) {

                            $.toast(data.message, function () {
                            });
                            location.reload();
                            setTimeout(function () {
                                $.hideLoading();

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
        }, function () {
            //取消操作
        });
    }
</script>
</body>
</html>
