<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/9/14
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>导出</title>
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
    <%--其他js--%>
    <script src="/jsp/common/js/tableExporter.min.js"></script>
</head>
<body ng-app="m" ng-controller="c">
<script>
    var m = angular.module("m",["baseModule"]);
    m.controller("c",function($scope, page, ajax, entity, $filter){
            $scope.getQueryString=function (name) {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null){return  decodeURI(r[2]);} return null;
            };

            $scope.htmlInit = function (callback) {
                $scope.operating = $scope.getQueryString("operating");
                $scope.institutionId = $scope.getQueryString("institutionId");
                callback && callback();
            };
            $scope.dateRange = {
                start: null,
                end: null,
                change: function (start, end) {
                    $scope.dateRange.start = start;
                    $scope.dateRange.end = end;
                }
            };
            $scope.load = function(current, size, orderBy, asc){
                ajax.ajax("/user/redirect/trade/statics/getCreditProductExpListByInstitution","POST",
                    {
                        institutionId:$scope.institutionId,
                        current: 1,
                        size: 1000,
                        startTime:$scope.dateRange.start,
                        endTime:$scope.dateRange.end,
                        search: $scope.search,
                        orderBy: orderBy,
                        asc: asc
                    }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.page.refreshPage(data);
                        setTimeout(function () {
                            if ($scope.operating != null && $scope.operating == 'export') {
                                $scope.leadingOut();
                            } else {
                                // $scope.print();
                            }
                        }, 100);
                    } else {
                        console.log("获取打印任务安排失败");
                    }
                })
            };
            $scope.page = page.page($scope.load,"create_time", false);
            $scope.ths = [
                {width: "1%"},
                {
                    name: "赊购店铺",
                    value: function (row) {
                        return row.storeDetail.name;
                    },
                    width: "20%"
                }, {
                    name: "产品名称",
                    value: function (row) {
                        return row.productDetail.name;
                    },
                    width: "20%"
                }, {
                    name: "数量",
                    value: function (row) {
                        return row.num;
                    },
                    width: "20%"
                }, {
                    name: "日期",
                    value: function (row) {
                        return $filter("fmtDateYMdHMcn")(row.create_time);
                    },
                    width: "30%"
                }
            ];
            //导出
            $scope.leadingOut = function () {
                $('.isPrint').tableExport({
                    filename: '赊购记录明细'+$scope.dateRange.start+'--'+$scope.dateRange.end,
                    format: 'xls',
                    cols: '2,3,4,5'
                });
            };

            $scope.$watch('$viewContentLoaded', function () {
                $scope.htmlInit($scope.dateRange.change($scope.getQueryString("startTime"),$scope.getQueryString("endTime")));
                $scope.page.refreshTo(1);
            })
        })
</script>
<div>
    <%@ include file="/jsp/common/table.jspf" %>
</div>
</body>
</html>
