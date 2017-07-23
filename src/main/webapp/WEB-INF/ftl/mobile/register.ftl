<!DOCTYPE html>
<html>
  <head>
    <title>注册页面</title>
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
      <h1 class="demos-title">用户注册</h1>
    </header>

    <div class="weui-cells weui-cells_form">
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">登录名</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" name="loginName" id="loginName" type="text" autocapitalize="on"
                       placeholder="请输入登录名" onkeyup="value=value.replace(/[\W]/g,'') "
                       onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
            </div>
        </div>

        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">用户名</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" name="userName" id="userName" type="text" autocapitalize="on"
                       placeholder="请输入用户名" >
            </div>
        </div>

        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">密码</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" name="loginPassword" id="loginPassword" type="password" placeholder="请输入密码">
            </div>
        </div>

        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">确认密码</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" name="rePassword" id="rePassword" type="password" placeholder="请输入确认密码">
            </div>
        </div>
    </div>

    <label for="weuiAgree" class="weui-agree">
      <input id="weuiAgree" type="checkbox" class="weui-agree__checkbox">
      <span class="weui-agree__text">
        阅读并同意<a href="javascript:void(0);">《相关条款》</a>
      </span>
    </label>

    <div class="weui-flex">
        <div class="weui-flex__item">
            <div class="weui-btn-area">
                <a class="weui-btn weui-btn_primary" href="/login.shml" id="btnBack">返回</a>
            </div>
        </div>
        <div class="weui-flex__item">
            <div class="weui-btn-area">
                <a class="weui-btn weui-btn_primary" href="javascript:" id="btnRegister">注册</a>
            </div>
        </div>
    </div>


    <script src="/lib/jquery-2.1.4.js"></script>
<script src="/lib/fastclick.js"></script>
<script>
  $(function() {
    FastClick.attach(document.body);
  });
</script>
<script src="/js/jquery-weui.js"></script>


    <script>
      $("#btnRegister").click(function() {
          var loginName = $('#loginName').val();
          var loginPassword = $('#loginPassword').val();
          var rePassword = $('#rePassword').val();
          var userName = $('#userName').val();
          if (!loginName) $.toptip('登录名不能为空...', 'warning');
          else if (!userName) $.toptip('用户名不能为空...', 'warning');
          else if (!loginPassword) $.toptip('密码不能为空...', 'warning');
          else if (!rePassword) $.toptip('确认密码不能为空...', 'warning');
          else doRegister(loginName, loginPassword,userName,rePassword);
      });

      function doRegister(loginName, loginPassword,userName,rePassword) {
          $.showLoading("注册中...");

          $.ajax({
              type: "post",
              async: true,
              url: "/user/register.do",
              dataType: "json",
              data: {
                  "loginName": loginName,
                  "loginPassword": loginPassword,
                  "rePassword":rePassword,
                  "userName":userName
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
