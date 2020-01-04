<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>赊购审核</title>
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
    <%--自定义angular module--%>
    <script src="/jsp/common/template/baseModule.js"></script>
    <%--日期处理--%>
    <link href="/jsp/common/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="/jsp/common/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/jsp/common/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="/jsp/common/js/moment.min.js"></script>
</head>
<body ng-app="m" ng-controller="c" ng-init='index = "审核管理"; subIndex = "赊购审核"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<script>
    var m = angular.module("m", ["nm"])
        .controller("c", function ($rootScope, $location) {
            $rootScope.navs = [
                {
                    name: "批次审核",
                    controller: "item"
                }, {
                    name: "价格审核",
                    controller: "price"
                }/*, {
                    name: "上下架审核",
                    controller: "onoff"
                }*/
            ];
            $rootScope.nav = $location.search().nav ? $location.search().nav : 0;
            $rootScope.navTo = function (index) {
                /*if(index === 1){
                    swal("提示!", "暂未实现!", "warning");
                    return;
                }*/
                $rootScope.nav = index;
                $location.search("nav", $rootScope.nav);
                $rootScope.$broadcast("onNav", $rootScope.navs[$rootScope.nav]);
            };
            $rootScope.$watch('$viewContentLoaded', function () {
                $rootScope.$broadcast("onNav", $rootScope.navs[$rootScope.nav]);
            });
        });
</script>
<div class="container" style="width: 100%;"><%--nav特殊情况width设为100%--%>
    <div class="container-fluid" style="margin-top: 5px;">
        <ul class="nav nav-pills nav-justified m-t" style="border-bottom: 1px solid #5594CB;">
            <li role="presentation" ng-repeat="n in navs" ng-class='{"active":nav == $index}'>
                <a href="javascript:void(0);" ng-bind="n.name" ng-click="navTo($index)"></a>
            </li>
        </ul>
        <div class="ng-hide" ng-show="nav == 0">
            <%@ include file="/jsp/product/verify/productCredit/item.jspf" %>
        </div>
        <div class="ng-hide" ng-show="nav == 1">
            <%@ include file="/jsp/product/verify/productCredit/price.jspf" %>
        </div>
        <%--<div class="ng-hide" ng-show="nav == 2">
            <%@ include file="/jsp/product/verify/productCredit/price.jspf" %>
        </div>--%>
    </div>
</div>
</body>
</html>
