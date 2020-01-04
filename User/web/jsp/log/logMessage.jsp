<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>短信记录</title>
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
<body ng-app="m" ng-controller="c" ng-init='index = "系统运维"; subIndex = "短信记录"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>短信列表</h4>
            <div class="clearfix">
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <%--<label>搜索类型&nbsp;
                        <select class="form-control" ng-model="searchObj.searchType"
                                ng-options="x.id as x.name for x in searchObj.strSearch"
                                ng-change="searchObj.searchReset()">
                        </select>
                    </label>--%>
                    <input class="form-control m-l" type="text" placeholder="手机号" ng-model="search">
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
    angular.module("m", ["nm"])
        .controller("c", function ($scope, page, ajax, entity, $filter, alertService) {
            //$scope.type = ["验证码", "普通短信"];

            $scope.load = function (current, size, orderBy, asc) {
                ajax.ajax("/message/getLogMessagePage", "POST",
                    {
                        userId: userId,
                        current: current,
                        size: size,
                        orderBy: orderBy,
                        asc: asc,
                        search: $scope.search
                    }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.page.refreshPage(data);
                    }
                })
            };
            $scope.page = page.page($scope.load, "lm.create_time", false);
            $scope.ths = [
                {width: "1%"},
                {
                    name: "手机号",
                    value: function (row) {
                        return row.phone;
                    },
                    width: "5%"
                }, {
                    name: "类型",
                    value: function (row) {
                        return row.type;
                    },
                    width: "2%"
                }, {
                    name: "平台",
                    value: function (row) {
                        return row.platform;
                    },
                    width: "2%"
                }, {
                    name: "详情",
                    value: function (row) {
                        return row.msg_content;
                    },
                    width: "20%"
                }, {
                    name: "状态",
                    style: function (row) {
                        return {
                            "color": row.success === true ? "green" : "red"
                        };
                    },
                    value: function (row) {
                        return row.success === true ? "成功" : "失败";
                    },
                    width: "2%"
                }, {
                    name: "发送时间",
                    value: function (row) {
                        return $filter("fmtDateYMdHMcn")(row.create_time);
                    },
                    orderBy: "lm.create_time",
                    width: "5%"
                }, {
                    name: "送达时间",
                    value: function (row) {
                        return $filter("fmtDateYMdHMcn")(row.update_time);
                    },
                    width: "5%"
                }
            ];
            $scope.operations = [
                /*{
                 name: function (row) {
                 return row.status === true ? "禁用" : "启用";
                 },
                 clas: function (row) {
                 return {
                 "btn btn-xs btn-warning": row.status === true,
                 "btn btn-xs btn-success": row.status !== true
                 };
                 },
                 click: function (row) {
                 /!*ajax.ajax("/user/admin/deleteOneUser", "POST", {
                 userId: 1,
                 id: row.id
                 }).success(function (data) {
                 console.log(data);
                 $scope.page.refresh();
                 }).error(function (data) {
                 console.log(data);
                 });*!/
                 }
                 }*/
            ];

            $scope.$watch('$viewContentLoaded', function () {
                $scope.page.refreshTo(1);
            });
        });
</script>
</body>
</html>
