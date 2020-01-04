<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/12/13
  Time: 0:14
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
    <title>产品提现</title>
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
<body ng-app="m" ng-controller="c" ng-init='index = "奖励管理";subIndex = "产品提现"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>产品提现列表</h4>
            <div class="clearfix">
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <%--<input class="form-control m-l" type="text" placeholder="标题" ng-model="search">--%>
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
    var m = angular.module("m", ["nm","dateRangeModule"])
        .controller("c", function($scope, page, ajax, entity, $filter,alertService){
            $scope.load = function(current, size, orderBy, asc){
                ajax.ajax("/user/redirect/trade/statics/getAllStoreWithdrawList","POST",
                    {
                        userId:1,
                        current: current,
                        size: size,
                        orderBy: orderBy,
                        asc: asc
                    }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.page.refreshPage(data);
                    }
                })
            };
            $scope.page = page.page($scope.load,"can_withdraw", false);
            $scope.ths = [
                {width: "1%"},
                {
                    name: "店铺名称",
                    value: function (row) {
                        return row.storeDetail.name;
                    },
                    width: "20%"
                }, {
                    name: "可提现",
                    value: function (row) {
                        return row.canWithdraw;
                    },
                    width: "20%"
                }, {
                    name: "已提现",
                    value: function (row) {
                        return row.withdraw;
                    },
                    width: "20%"
                },
                {
                    name: "正在审核",
                    value: function (row) {
                        return row.verifyWithdraw;
                    },
                    width: "20%"
                }
            ];
            $scope.$watch('$viewContentLoaded', function () {
                $scope.page.refreshTo(1);
            });
        })
</script>
</body>
</html>
