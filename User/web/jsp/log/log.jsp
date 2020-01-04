<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>日志记录</title>
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
<body ng-app="m" ng-controller="c" ng-init='index = "系统运维"; subIndex = "日志记录"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>日志列表</h4>
            <div class="clearfix">
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <label>平台&nbsp;
                        <select class="form-control" ng-model="searchObj.searchMavenModule"
                                ng-options="x.id as x.name for x in searchObj.mavenModule"
                                ng-change="page.refreshTo(1)">
                        </select>
                    </label>
                    <label>搜索类型&nbsp;
                        <select class="form-control" ng-model="searchObj.searchType"
                                ng-options="x.id as x.name for x in searchObj.strSearch"
                                ng-change="searchObj.searchReset()">
                        </select>
                    </label>
                    <input class="form-control m-l" type="text" placeholder="用户id/类/方法" ng-model="searchObj.search">
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
            /**
             * 搜索条件
             */
            $scope.searchObj = {
                mavenModule: [{id: -1, name: "全部"}, {id: 0, name: "User"}, {id: 1, name: "Trade"}],
                searchMavenModule: -1,//搜索maven_module
                strSearch: [{id: 0, name: "用户id"}, {id: 1, name: "类"}, {id: 2, name: "方法"}],
                searchType: -1,
                search: "",
                user_id: -1,//搜索user_id
                clazz: -1,//搜索clazz
                method: -1,//搜索method
                searchReset: function () {
                    var v =  $scope.searchObj.search;
                    $scope.searchObj.user_id = $scope.searchObj.searchType === 0 ? v : null;
                    $scope.searchObj.clazz = $scope.searchObj.searchType === 1 ? v : null;
                    $scope.searchObj.method = $scope.searchObj.searchType === 2 ? v : null;
                }
            };
            $scope.$watch('searchObj.search', function () {
                $scope.searchObj.searchReset();
            });
            //$scope.searchMavenModule = -1;
            $scope.load = function (current, size, orderBy, asc) {
                ajax.ajax("/user/log/getLogUserPage", "POST",
                    {
                        userId: userId,
                        current: current,
                        size: size,
                        orderBy: orderBy,
                        asc: asc,
                        user_id: $scope.searchObj.user_id,
                        clazz: $scope.searchObj.clazz,
                        method: $scope.searchObj.method,
                        maven_module: $scope.searchObj.searchMavenModule
                    }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.page.refreshPage(data);
                    }
                })
            };
            $scope.page = page.page($scope.load, "lu.create_time", false);
            $scope.ths = [
                {width: "1%"},
                {
                    name: "用户",
                    value: function (row) {
                        return row.user == null ? "" : row.user.username;
                    },
                    width: "5%"
                }, {
                    name: "IP",
                    value: function (row) {
                        return row.ip;
                    },
                    width: "5%"
                }, {
                    name: "操作",
                    value: function (row) {
                        return row.opType;
                    },
                    width: "5%"
                }, {
                    name: "描述",
                    value: function (row) {
                        return row.description;
                    },
                    width: "5%"
                }, {
                    name: "类",
                    value: function (row) {
                        return row.clazz;
                    },
                    width: "5%"
                }, {
                    name: "方法",
                    value: function (row) {
                        return row.method;
                    },
                    width: "5%"
                }, {
                    name: "平台",
                    value: function (row) {
                        return row.mavenModule;
                    },
                    width: "5%"
                }, {
                    name: "耗时",
                    value: function (row) {
                        return row.methodTimeTaking + "s";
                    },
                    width: "5%"
                }, {
                    name: "返回结果",
                    value: function (row) {
                        return row.result;
                    },
                    width: "10%"
                }, {
                    name: "创建时间",
                    value: function (row) {
                        return $filter("fmtDateYMdHMcn")(row.createTime);
                    },
                    orderBy: "lu.create_time",
                    width: "10%"
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
