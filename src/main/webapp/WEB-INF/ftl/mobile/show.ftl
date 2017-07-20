<!DOCTYPE html>
<html>
<head>
    <title>日记</title>
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
    <h2 class="demos-second-title">日记</h2>
    <p class="demos-sub-title">${diary.createOn !?string("yyyy-MM-dd HH:mm:ss")}</p>
</header>
<article class="weui-article">
    <h1>${(diary.title) ! "" }</h1>
    <section>
        <h2 class="title">${(diary.weather) ! "" }</h2>
        <section>
            <h4>日记内容:</h4>
            <p>
                ${(diary.content) ! "" }
            </p>
        </section>
    </section>
</article>
<div class="weui-btn-area">
<a href="javascript:;" onclick="window.history.back();" class="weui-btn weui-btn_primary close-popup">关闭</a>
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

</script>
</body>
</html>
