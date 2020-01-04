<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/9/8
  Time: 9:51
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
    <title>新品体验管理</title>
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
    <script src="/jsp/common/js/module/dateRangeModule.js"></script>
    <%--日期处理--%>
    <link href="/jsp/common/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="/jsp/common/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/jsp/common/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="/jsp/common/js/moment.min.js"></script>
    <link href="/jsp/common/css/daterangepicker.min.css" rel="stylesheet">
    <script src="/jsp/common/js/daterangepicker.min.js"></script>
</head>
<body ng-app="m" ng-controller="c" ng-init='index = "统计信息";subIndex = "优品体验记录"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>优品体验列表</h4>
            <div class="clearfix">
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <label>时间
                        <div date-range change="dateRange.change($startDate, $endDate)"
                             start="dateRange.start"
                             end="dateRange.end">
                        </div>
                    </label>
                    <input class="form-control m-l" type="text" placeholder="标题" ng-model="search">
                    <button class="btn btn-primary" type="submit">
                        <span class="icon-search m-r"></span> 搜索
                    </button>
                </form>
            </div>
        </div>
        <%@ include file="/jsp/common/table.jspf" %>
    </div>
</div>
<script>
    angular.module("m",["nm","dateRangeModule"])
        .controller("c",function($scope, page, ajax, entity, $filter){
            $scope.institution={
                id: null
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
                ajax.ajax("/user/redirect/trade/statics/getNewProductExpListByInstitution","POST",
                    {
                        institutionId:$scope.institution.id,
                        current: current,
                        size: size,
                        search: $scope.search,
                        startTime:$scope.dateRange.start,
                        endTime:$scope.dateRange.end,
                        orderBy: orderBy,
                        asc: asc
                    }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                    $scope.page.refreshPage(data);
                    }
                })
            };
            /**
             * 获取所属机构信息
             */
            $scope.getInstitution = function (callback) {
                ajax.ajax("/user/institution/getInstitutionByAdmin", "POST", {
                    userId: 1,
                    adminId: userId
                }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.institution.id = data.value.id;
                        callback && callback();
                    }
                });
            };
            $scope.page = page.page($scope.load,"create_time", false);
            $scope.ths = [
                {width: "1%"},
                {
                    name: "申请体验用户",
                    value: function (row) {
                        return row.userName;
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
                        return row.quantity;
                    },
                    width: "20%"
                }, {
                    name: "日期",
                    value: function (row) {
                        return $filter("fmtDateYMdHMcn")(row.createTime);
                    },
                    width: "30%"
                }
            ];
            $scope.$watch('$viewContentLoaded', function () {
                $scope.getInstitution(function () {
                    $scope.page.refreshTo(1);
                });
            });
        })
</script>
</body>
</html>
