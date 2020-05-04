<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>检测统计</title>
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
    <!-- chart -->
    <link href="/jsp/common/css/angular-chart.min.css" rel="stylesheet">
    <script src="/jsp/common/js/Chart.min.js"></script>
    <script src="/jsp/common/js/angular-chart.min.js"></script>
    <script>
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        });
        var m = angular.module("m", ["nm", "chart.js"]);
        var c = m.controller("c", function ($rootScope, $location) {
            /*//tab导航
            $rootScope.tabNavs = [
                {name: "拓客统计", type: 0}, {name: "报告统计", type: 1}
            ];
            $rootScope.tabNav = $location.search().type ? $location.search().type : 0;
            $rootScope.navTo = function (index) {
                $rootScope.tabNav = index;
                $location.search("type", $rootScope.tabNav);
                $rootScope.$broadcast("onNav", $rootScope.tabNavs[$rootScope.tabNav]);
            };
            $rootScope.$watch('$viewContentLoaded', function () {
                $rootScope.$broadcast("onNav", $rootScope.tabNavs[$rootScope.tabNav]);
            });*/
        });
    </script>
</head>
<body ng-app="m" ng-init='index = "检测统计"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container-fluid" ng-controller="c">
    <%--<ul class="nav nav-tabs nav-justified m-t">--%>
        <%--<li role="presentation" ng-repeat="n in tabNavs" ng-class='{"active":tabNav == $index}'>--%>
            <%--<a href="javascript:void(0)" ng-bind="n.name" ng-click="navTo($index)"></a>--%>
        <%--</li>--%>
    <%--</ul>--%>
    <%@ include file="/jsp/sheet/customer_report/detection_report.jspf" %>
    <%--<div class="ng-hide" ng-show="tabNav == 0">
        <%@ include file="/jsp/sheet/customer_report/detection_report.jspf" %>
    </div>--%>
    <%--<div class="ng-hide" ng-show="tabNav == 1">
        <%@ include file="/jsp/sheet/customer_report/baogao_report.jspf" %>
    </div>--%>
</div>
</body>
</html>