<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>登录</title>
    <%--基础css--%>
    <%--<link href="/jsp/common/css/bootstrap.min.css" rel="stylesheet">
    <link href="/jsp/common/css/font-awesome.min.css" rel="stylesheet">
    <link href="/jsp/common/css/bootstrap-extend.css" rel="stylesheet">
    <link href="/jsp/common/css/validate.css" rel="stylesheet">--%>
    <%--基础js--%>
    <script src="/jsp/common/js/jquery.min.js"></script>
    <script src="/jsp/common/js/bootstrap.min.js"></script>
    <script src="/jsp/common/js/angular.min.js"></script>
    <script src="/jsp/common/js/ng-file-upload-all.min.js"></script>
    <script src="/jsp/common/js/angular-md5.min.js"></script>
    <%--自定义angular module--%>
    <script src="/jsp/common/template/baseModule.js"></script>
    <%--日期处理--%>
    <%--<link href="/jsp/common/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="/jsp/common/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/jsp/common/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="/jsp/common/js/moment.min.js"></script>--%>
    <style>
        body{
            background: #ebebeb;
            font-family: "Helvetica Neue","Hiragino Sans GB","Microsoft YaHei","\9ED1\4F53",Arial,sans-serif;
            color: #222;
            font-size: 12px;
        }
        *{padding: 0;margin: 0;}
        .top_div{
            background: #FF7752;
            /*#FF7752 #008ead*/
            width: 100%;
            height: 400px;
        }
        .ipt{
            border: 1px solid #d3d3d3;
            padding: 10px 10px;
            width: 290px;
            border-radius: 4px;
            padding-left: 35px;
            -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
            box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
            -webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
            -o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
            transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s
        }
        .ipt:focus{
            border-color: #66afe9;
            outline: 0;
            -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);
            box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)
        }
        .u_logo{
            background: url("/jsp/common/asset/img/login_images/username.png") no-repeat;
            padding: 10px 10px;
            position: absolute;
            top: 43px;
            left: 40px;

        }
        .p_logo{
            background: url("/jsp/common/asset/img/login_images/password.png") no-repeat;
            padding: 10px 10px;
            position: absolute;
            top: 12px;
            left: 40px;
        }
        a{
            text-decoration: none;
        }
        .tou{
            background: url("/jsp/common/asset/img/login_images/tou1.png") no-repeat;
            width: 97px;
            height: 92px;
            position: absolute;
            top: -87px;
            left: 140px;
        }
        .left_hand{
            background: url("/jsp/common/asset/img/login_images/left_hand.png") no-repeat;
            width: 32px;
            height: 37px;
            position: absolute;
            top: -38px;
            left: 150px;
        }
        .right_hand{
            background: url("/jsp/common/asset/img/login_images/right_hand.png") no-repeat;
            width: 32px;
            height: 37px;
            position: absolute;
            top: -38px;
            right: -64px;
        }
        .initial_left_hand{
            background: url("/jsp/common/asset/img/login_images/hand.png") no-repeat;
            width: 30px;
            height: 20px;
            position: absolute;
            top: -12px;
            left: 100px;
        }
        .initial_right_hand{
            background: url("/jsp/common/asset/img/login_images/hand.png") no-repeat;
            width: 30px;
            height: 20px;
            position: absolute;
            top: -12px;
            right: -112px;
        }
        .left_handing{
            background: url("/jsp/common/asset/img/login_images/left-handing.png") no-repeat;
            width: 30px;
            height: 20px;
            position: absolute;
            top: -24px;
            left: 139px;
        }
        .right_handinging{
            background: url("/jsp/common/asset/img/login_images/right_handing.png") no-repeat;
            width: 30px;
            height: 20px;
            position: absolute;
            top: -21px;
            left: 210px;
        }
    </style>
</head>
<body ng-app="m" ng-controller="c">
    <div style="position: absolute;top: 150px;width:100%;text-align: center;">
        <p style="font-size: 200%;color: #f5f5f5">人脸图像智能应用云平台</p>
    </div>

    <div class="top_div"></div>

    <div style="width: 400px;height: 200px;margin: auto auto;background: #ffffff;text-align: center;margin-top: -100px;border: 1px solid #e7e7e7">
        <div style="width: 165px;height: 96px;position: absolute">
            <div class="tou"></div>
            <div id="left_hand" class="initial_left_hand"></div>
            <div id="right_hand" class="initial_right_hand"></div>
        </div>

        <p style="padding: 30px 0 10px 0;position: relative;">
            <span class="u_logo"></span>
            <input class="ipt" type="text" placeholder="请输入用户名" ng-model="username">
        </p>
        <p style="position: relative;">
            <span class="p_logo"></span>
            <input id="password" class="ipt" type="password" placeholder="请输入密码" ng-model="password">
        </p>

        <div style="height: 50px;line-height: 50px;margin-top: 30px;border-top: 1px solid #e7e7e7;">
            <p style="margin: 0 35px 20px 45px;">
                <%--<span style="float: left"><a href="#" style="color:#ccc;">忘记密码?</a></span>--%>
                <span ng-show="e" style="float: left"><a href="#" style="color:#ff4438;">用户名或密码错误</a></span>
                <span style="float: right">
                   <%--<a href="#" style="color:#ccc;margin-right:10px;">注册</a>--%>
                   <a href="#" style="
                   background: #ff7752;padding: 7px 10px;border-radius: 4px;
                   border: 1px solid #ff7d59;color: #FFF;font-weight: bold;"
                   ng-click="login()">登录</a>
               </span>
            </p>
        </div>

    </div>
    <div style="position: fixed;bottom: 0;text-align: center;width: 100%;">
        Copyright ©2020 <a style="margin-left: 10px;color: #000000;text-decoration: underline"
                           href="https://github.com/Tomasyao77/">
        https://github.com/Tomasyao77/</a>
    </div>
    <script type="text/javascript">
        $(function(){
            //得到焦点
            $("#password").focus(function(){
                $("#left_hand").animate({
                    left: "150",
                    top: " -38"
                },{step: function(){
                    if(parseInt($("#left_hand").css("left"))>140){
                        $("#left_hand").attr("class","left_hand");
                    }
                }}, 2000);
                $("#right_hand").animate({
                    right: "-64",
                    top: "-38px"
                },{step: function(){
                    if(parseInt($("#right_hand").css("right"))> -70){
                        $("#right_hand").attr("class","right_hand");
                    }
                }}, 2000);
            });
            //失去焦点
            $("#password").blur(function(){
                $("#left_hand").attr("class","initial_left_hand");
                $("#left_hand").attr("style","left:100px;top:-12px;");
                $("#right_hand").attr("class","initial_right_hand");
                $("#right_hand").attr("style","right:-112px;top:-12px");
            });
        });
    </script>
    <script>
        angular.module("m", ["baseModule", "angular-md5"])
            .controller("c", function ($scope, ajax, md5) {
                $scope.e = false;
                $scope.username = "";
                $scope.password = "";
                $scope.login = function () {
                    if ($scope.username.length <= 0 || $scope.username.password <= 0) {
                        return false;
                    }
                    ajax.ajax("/user/login/loginAdmin", "POST", {
                        "username": $scope.username,
                        "password": md5.createHash($scope.password)
                    }).success(function (data) {
                        console.log(data);
                        if (data.success) {
                            var moduleJson = eval("(" + data.value.moduleJson + ")");
                            if (moduleJson[0].nodes.length > 0) {
                                window.location.href = moduleJson[0].nodes[0].url;
                            } else {
                                window.location.href = moduleJson[0].url;
                            }
                        } else {
                            $scope.e = true;
                        }
                    });
                };
            });
    </script>
</body>
</html>
