<%--
  Created by IntelliJ IDEA.
  User: YTY
  Date: 2016/5/24
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>顾客解码统计</title>
    <!-- Bootstrap -->
    <link href="/jsp/common/css/bootstrap.min.css" rel="stylesheet">
    <link href="/jsp/common/css/font-awesome.min.css" rel="stylesheet">
    <link href="/jsp/common/css/bootstrap-extend.css" rel="stylesheet">
    <link href="/jsp/common/css/angular-chart.min.css" rel="stylesheet">
    <script src="/jsp/common/js/jquery.min.js"></script>
    <script src="/jsp/common/js/bootstrap.min.js"></script>
    <script src="/jsp/common/js/angular.min.js"></script>
    <script src="/jsp/common/js/module/pageModule.js"></script>
    <script src="/jsp/common/js/Chart.min.js"></script>
    <script src="/jsp/common/js/angular-chart.min.js"></script>
    <script src="/jsp/common/js/moment.min.js"></script>
    <link href="/jsp/common/css/daterangepicker.min.css" rel="stylesheet">
    <script src="/jsp/common/js/daterangepicker.min.js"></script>
    <script src="/jsp/common/js/module/dateRangeModule.js"></script>
    <script>
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        });
        var m = angular.module("m", ["nm", "pageModule", "chart.js", 'dateRangeModule']);
        var c = m.controller("c", function ($scope, $location, ajax, $rootScope, $filter, page) {
            $scope.parlorId = "";
            $scope.ageLabels = ["20岁以下", "20岁~30岁", "30岁~40岁", "40岁~50岁", "50岁以上"];
            $scope.ageLegend = ["实际年龄", "检测年龄"];
            $scope.ageData = [[], []];
            $scope.genderLabels = ["男", "女"];
            $scope.scoreLabels = ["60分以下", "60分~70分", "70分到80分", "80分以上"];
            $scope.scoreLegend = ["60分以下", "60分~70分", "70分到80分", "80分以上"];
            $scope.genderLegend = ["男", "女"];
            $scope.dateRange = {
                start: null,
                end: null,
                change: function (start, end) {
                    $scope.dateRange.start = start;
                    $scope.dateRange.end = end;
                    $scope.refresh();
                }
            };
            ajax.ajax("/server/adminParlor/getAdminParlorVos", "POST",
                {
                    userId: userId,
                    current: 1,
                    size: 1,
                    areaCode: 100000,
                    level: 0
                }).success(function (data) {
                if (data.success && data.page.list.length > 0) {
                    $scope.parlorId = data.page.list[0].id;
                    $scope.refresh();
                } else {//如果登录的不是商家那就是顾问
                    ajax.ajax("/server/adminParlor/getParlorOfAdvisor", "POST",
                        {
                            userId: userId
                        }).success(function (data) {
                        if (data.success) {
                            $scope.parlorId = data.page.list[0].id;
                            $scope.refresh();
                        }
                    });
                }
            });
            $scope.loadGender = function () {
                ajax.ajax("/server/redirect/detection/detection/getGenderCount", "POST", {
                    userId: userId,
                    parlorId: $scope.parlorId,
                    startTime: $scope.dateRange.start,
                    endTime: $scope.dateRange.end
                }).success(function (data) {
                    if (data.success) {
                        $scope.genderData = data.value;
                    }
                });
            };
            $scope.loadScore = function () {
                ajax.ajax("/server/redirect/detection/detection/getScoreCount", 'POST', {
                    userId: userId,
                    parlorId: $scope.parlorId,
                    startTime: $scope.dateRange.start,
                    endTime: $scope.dateRange.end
                }).success(function (data) {
                    if (data.success) {
                        $scope.scoreData = data.value;
                    }
                });
            };
            $scope.loadAge = function () {
                ajax.ajax("/server/redirect/detection/detection/getAgeCount", 'POST', {
                    userId: userId,
                    parlorId: $scope.parlorId,
                    startTime: $scope.dateRange.start,
                    endTime: $scope.dateRange.end
                }).success(function (data) {
                    if (data.success) {
                        $scope.ageData[0] = data.value;
                    }
                });
            };
            $scope.loadDetectAge = function () {
                ajax.ajax("/server/redirect/detection/detection/getDetectAgeCount", 'POST', {
                    userId: userId,
                    parlorId: $scope.parlorId,
                    startTime: $scope.dateRange.start,
                    endTime: $scope.dateRange.end
                }).success(function (data) {
                    if (data.success) {
                        $scope.ageData[1] = data.value;
                    }
                });
            };
            $scope.refresh = function () {
                if ($scope.parlorId != "") {
                    $scope.loadGender();
                    $scope.loadScore();
                    $scope.loadAge();
                    $scope.loadDetectAge();
                }
            }
            $scope.$watch('$viewContentLoaded', function () {
                $scope.refresh()
            });
        });
    </script>
</head>
<body ng-app="m" ng-init='index = "营销报表";subIndex="统计管理" '>
<jsp:include page="/jsp/newAgent/cesj-nav/nav.jsp"/>
<div class="container-fluid" ng-controller="c">
    <h4>顾客解码统计</h4>
    <div class="row">
        <form class="form-inline" ng-submit="refresh()" style="text-align: center">
            <label>起止时间
                <div date-range change="dateRange.change($startDate, $endDate)"
                     start="dateRange.start"
                     end="dateRange.end">
                </div>
            </label>
        </form>
        <div class="p-y-lg clearfix col-xs-6" style="text-align: center">
            <h4 class="text-muted">
            <span class="m-l">解码年龄分析
                <span class="btn-link icon-question-sign"
                      data-toggle="tooltip" data-placement="bottom"
                      title="参与解码顾客的真实年龄与解码年龄的分布柱状图，用于分析整体检测的年龄趋势，及参与解码顾客的年龄分布"></span>
            </span></h4>
            <canvas id="Bar" class="chart chart-Bar" chart-data="ageData"
                    chart-labels="ageLabels" chart-legend="true" chart-series="ageLegend"
                    height="230">
            </canvas>
        </div>
        <div class="p-y-lg clearfix col-xs-6" style="text-align: center">
            <h4 class="text-muted">
                <span class="m-l">解码结果分析
                    <span class="btn-link icon-question-sign"
                          data-toggle="tooltip" data-placement="bottom"
                          title="参与解码顾客的得分分布，用来统计顾客解码的分数段，可供给专家或顾问作参考"></span>
            </span></h4>
            <canvas id="Radar" class="chart chart-Doughnut" chart-data="scoreData"
                    chart-labels="scoreLabels" chart-legend="true" chart-series="scoreLegend"
                    height="100">
            </canvas>
            <h4 class="text-muted">
                <span class="m-l">用户性别分布
                    <span class="btn-link icon-question-sign"
                          data-toggle="tooltip" data-placement="bottom"
                          title="参与解码顾客的性别比例"></span>
            </span></h4>
            <canvas id="Pie" class="chart chart-Pie" chart-data="genderData"
                    chart-labels="genderLabels" chart-legend="true" chart-series="genderLegend"
                    height="100">
            </canvas>
        </div>
    </div>
</div>
</body>
</html>