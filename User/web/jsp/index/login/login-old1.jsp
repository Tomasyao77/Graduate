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
    <link href="/jsp/common/css/bootstrap.min.css" rel="stylesheet">
    <link href="/jsp/common/css/font-awesome.min.css" rel="stylesheet">
    <link href="/jsp/common/css/bootstrap-extend.css" rel="stylesheet">
    <link href="/jsp/common/css/validate.css" rel="stylesheet">
    <%--基础js--%>
    <script src="/jsp/common/js/jquery.min.js"></script>
    <script src="/jsp/common/js/bootstrap.min.js"></script>
    <script src="/jsp/common/js/angular.min.js"></script>
    <script src="/jsp/common/js/ng-file-upload-all.min.js"></script>
    <script src="/jsp/common/js/angular-md5.min.js"></script>
    <%--自定义angular module--%>
    <script src="/jsp/common/template/baseModule.js"></script>
    <%--日期处理--%>
    <link href="/jsp/common/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="/jsp/common/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/jsp/common/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="/jsp/common/js/moment.min.js"></script>
</head>
<body ng-app="m" ng-controller="c">
<div style="padding:35px 120px;">
    <div class="clearfix" style="margin-top: 50px">
        <%--登录区域--%>
        <div style="width: 650px;" class="center-block">
            <div style="background-color: rgba(255,0,0,0.1);border: solid 1px #fff;border-radius: 15px;padding: 60px">
                <h2 class="m-y-0 text-white text-center">
                    <span style="color: #EDB840">口袋光谷管理平台</span>
                </h2>
                <div class="clearfix" style="margin-top: 10px;margin-bottom: -20px;">
                    <h3 class="text-center text-muted">
                        <span>管理员登录</span>
                    </h3>
                </div>
                <%--管理员登录--%>
                <div>
                    <div class="clearfix"
                         style="border-radius: 15px;background-color: rgba(187,187,187,0.5);padding:25px;margin-top:50px ;position: relative">
                        <div class="pull-left">
                            <img src="/jsp/common/asset/img/login_user.png" style="height: 40px">
                        </div>
                        <div class="pull-left" style="height: 40px;width: 1px;background-color: #fff;margin: 0 25px"></div>
                        <div style="position: absolute;top:25px;left: 120px;right: 25px">
                            <input style="display:none"><%--假的input让浏览器去填充--%>
                            <input type="tel" ng-model="username" placeholder="用户名" autocomplete="off"
                                   style="outline: none;background-color: transparent;border: none;height: 40px;line-height: 40px;color: #fff;font-size: 30px;width: 100%;display: block">
                        </div>
                    </div>
                    <div class="clearfix"
                         style="border-radius: 15px;background-color: rgba(187,187,187,0.5);padding:25px;margin-top:20px ;position: relative">
                        <div class="pull-left">
                            <img src="/jsp/common/asset/img/login_password.png" style="height: 40px">
                        </div>
                        <div class="pull-left" style="height: 40px;width: 1px;background-color: #fff;margin: 0 25px"></div>
                        <div style="position: absolute;top:25px;left: 120px;right: 25px">
                            <input type="password" ng-model="password" placeholder="密码" autocomplete="off"
                                   style="outline: none;background-color: transparent;border: none;height: 40px;line-height: 40px;color: #fff;font-size: 30px;width: 100%;display: block">
                        </div>
                    </div>
                    <div style="height: 1em;" class="m-y-md">
                        <p class="text-danger m-y-0 text-center" ng-show="e">用户名或密码错误</p>
                    </div>
                    <button class="btn btn-success center-block" ng-click="login()"
                            style="width: 60%;border-radius: 15px;height: 70px;font-size: 200%;">登录</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    angular.module("m", ["baseModule", "angular-md5"])
        .controller("c", function ($scope, ajax, md5) {
            $scope.e = false;
            $scope.username = "";
            $scope.password = "";
            $scope.login = function () {
                if($scope.username.length <=0 || $scope.username.password <=0){
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
