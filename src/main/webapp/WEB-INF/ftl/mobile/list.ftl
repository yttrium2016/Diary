<#-- 取得 应用的绝对根路径 -->
<#assign basePath=request.contextPath>
<!DOCTYPE html>
<html>
<head>
    <title>日记列表界面</title>
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
<div class="weui-search-bar" id="searchBar">
    <form onsubmit="return getDiaryList();" class="weui-search-bar__form">
        <div class="weui-search-bar__box">
            <i class="weui-icon-search"></i>
            <input type="search" class="weui-search-bar__input" id="searchInput" placeholder="搜索">
            <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
        </div>
        <label class="weui-search-bar__label" id="searchText"
               style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
            <i class="weui-icon-search"></i>
            <span>搜索</span>
        </label>
    </form>
    <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
</div>

<div class="page__bd">
    <div class="weui-panel" id="add">
        <div class="weui-panel__hd">日记列表
            <div style="float: right">欢迎你
            <#if Session.loginUser?exists>
            ${Session.loginUser.userName}
                <a href="/user/logout.shtml">退出</a>
            </#if>
            </div>
        </div>
        <div id="diaryData">
        <#if diaryList?? && (diaryList?size > 0) >
            <#list diaryList!"" as diary>
                <div class="weui-media-box weui-media-box_text">
                    <h4 class="weui-media-box__title">${diary.title !""}</h4>
                    <p class="weui-media-box__desc">${diary.content !""}</p>
                    <ul class="weui-media-box__info">
                        <li class="weui-media-box__info__meta">作者:${diary.userName !""}</li>
                        <li class="weui-media-box__info__meta">时间:${diary.createOn !?string("yyyy-MM-dd HH:mm:ss")}</li>
                        <li class="weui-media-box__info__meta" style="float: right">
                            <a href="/diary/showDiary.shtml?id=${diary.id !""}"
                               class="weui-btn weui-btn_mini weui-btn_default">查看</a>&nbsp;
                            <a href="/diary/addOrEdit.shtml?id=${diary.id !""}"
                               class="weui-btn weui-btn_mini weui-btn_primary">编辑</a>&nbsp;
                            <a onclick="_delete(${diary.id !""},'${diary.title !""}')"
                               class="weui-btn weui-btn_mini weui-btn_warn">删除</a>
                        </li>
                    </ul>
                </div>
            </#list>
        </#if>
        </div>
    </div>
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

    function add(diary) {
        $("#diaryData").append("<div class=\"weui-media-box weui-media-box_text\"> " +
                "<h4 class=\"weui-media-box__title\">" + diary.title + "</h4> " +
                "<p class=\"weui-media-box__desc\">" + diary.content + "</p> " +
                "<ul class=\"weui-media-box__info\"> " +
                "<li class=\"weui-media-box__info__meta\">作者:" + diary.userName + "</li> " +
                "<li class=\"weui-media-box__info__meta\">时间:" + diary.createOn + "</li>" +
                "<li class=\"weui-media-box__info__meta\" style=\"float: right\">"+
                    "<a href=\"/diary/showDiary.shtml?id="+diary.id+"\" class=\"weui-btn weui-btn_mini weui-btn_default\">查看</a>&nbsp;"+
                    "<a href=\"/diary/addOrEdit.shtml?id="+diary.id+"\" class=\"weui-btn weui-btn_mini weui-btn_primary\">编辑</a>&nbsp;"+
                    "<a onclick=\"_delete("+diary.id+",'"+diary.title+"')\" class=\"weui-btn weui-btn_mini weui-btn_warn\">删除</a> "+
                "</li>" +
                "</ul> " +
                "</div>")
        $("#diaryData ul:last-child").append("")
    }

    function getDiaryList() {
        var title = $("#searchInput").val();
        $.showLoading("查找中...");
        getDiarys(title,true);
        return false;
    }

    function getDiarys(title, isShow) {
        $.ajax({
            type: "post",
            async: true,
            url: "/diary/getDiaryList.do",
            dataType: "json",
            data: {
                "type": "${type ! ""}",
                "title": title
            },
            success: function (data, textStatus) {
                $.hideLoading();
                if (textStatus) {
                    if (data.code == 1) {
                        if (isShow) {
                            $.toast(data.message, function () {
                            });
                        }
                        $("#diaryData").empty();
                        for (var a = 0; a < data.data.length; a++) {
                            add(data.data[a]);
                        }
                    }
                    else {
                        if (isShow) {
                            $.toast(data.message, "cancel", function (toast) {
                            });
                        }
                    }
                }
            }
            , error: function () {
                $.hideLoading();
            }
        });
    }

    function _delete(id, name) {
        $.confirm(name, "确认删除?", function () {
            $.showLoading("删除中...");
            $.ajax({
                type: "post",
                async: true,
                url: "/diary/delete.do",
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
                            getDiarys("",false);
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
