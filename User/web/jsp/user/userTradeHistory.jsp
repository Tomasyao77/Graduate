<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>消费列表</title>
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
<body ng-app="m" ng-controller="c">
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>消费列表</h4>
            <div class="clearfix">
                <label>
                    <button class="btn btn-success" ng-click="page.refreshTo(1)">
                        <span class="icon-refresh m-r"></span>刷新
                    </button>
                </label>
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <label>交易类型&nbsp;
                        <select class="form-control" ng-model="moneyType"
                                ng-options="m.value as m.name for m in moneyTypes"
                                ng-change="page.refreshTo(1)">
                            <option value="1">普通交易</option>
                        </select>
                    </label>
                </form>
            </div>
        </div>
        <%@ include file="/jsp/common/table.jspf" %>
    </div>

</div>
<script>
    var m = angular.module("m", ["nm"])
    m.controller("c", function ($scope, page, ajax, $location, entity, $filter, alertService) {
        $scope.GetQueryString = function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) {
                return decodeURI(r[2]);
            }
            return null;
        };
        //审核状态
        $scope.moneyType = 0 ;
        $scope.moneyTypes = [{value: 0, name: "普通交易"}, {value: 1, name: "优品体验"}];
        $scope.searchType = 1;
        $scope.selectChange = function () {
            $scope.page.refresh();
        };
        $scope.load = function (current, size, orderBy, asc) {
            if ($scope.moneyType == 0) {
                ajax.ajax("/user/redirect/trade/user/getShopTradeHistoryList", "POST",
                    {
                        userId: 1,
                        current: current,
                        size: size,
                        userId: $scope.GetQueryString("userId"),
                        orderBy: orderBy,
                        asc: asc
                    }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.page.refreshPage(data);
                    }
                });
            }else {
                ajax.ajax("/user/redirect/trade/user/getShopExperienceHistoryList", "POST",
                    {
                        userId: 1,
                        current: current,
                        size: size,
                        userId: $scope.GetQueryString("userId"),
                        orderBy: orderBy,
                        asc: asc
                    }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.page.refreshPage(data);
                    }
                });
            }
        };
        $scope.page = page.page($scope.load, "o.createTime", false);
        $scope.ths = [
            {
                name: "店铺",
                value: function (row) {
                    return row.store.name;
                },
                width: "10%"
            }, {
                name: "产品名称",
                value: function (row) {
                    return row.product.name;
                },
                width: "10%"
            }, {
                name: "数量/份",
                value: function (row) {
                    return row.quantity;
                },
                width: "5%"
            }, {
                name: "单价/元",
                value: function (row) {
                    return row.money;
                },
                width: "5%"
            }, {
                name: "总价/元",
                value: function (row) {
                    return row.totalMoney;
                },
                width: "5%"
            }, {
                name: "创建时间",
                value: function (row) {
                    return $filter("fmtDateYMdHMcn")(row.createTime);
                },
                orderBy: "o.createTime",
                width: "15%"
            }
        ];
//        $scope.operations = [
//            {
//
//            }
//        ];
        /**
         * entity
         */
        $scope.entityCreditGift = entity.getEntity(
            {}, {},
            function (action, row) {//beforeOpen
                if ($scope.entityCreditGift.action === "edit") {
                    $scope.entityCreditGift.entity = angular.copy(row);
                    $scope.entityCreditGift.entity.returnRate = angular.copy(row.returnRate);
                    $scope.entityCreditGift.entity.returnCollect = row.returnCollect;
                }
            },
            function () {//submit
                if ($scope.entityCreditGift.action === "edit") {
                    ajax.ajax( "/user/redirect/trade/creditGift/updateCreditGift","POST", {
                        productId: $scope.entityCreditGift.entity.product.id,
                        level: $scope.entityCreditGift.entity.level,
                        returnRate: $scope.entityCreditGift.entity.returnRate
                    }).success(function (data) {
                        if (data.success) {
                            alert("操作成功!");
                            alertService.show("操作成功!", "success", "80%");
                            $scope.page.refresh();
                        } else {
                            swal("提示!", "编辑失败!", "error");
                        }
                    }).error(function (data) {
                        console.log(data);
                    });
                }
                $scope.entityCreditGift._success();//隐藏模态框
            }, "modal-creditGift");


        $scope.$watch('$viewContentLoaded', function () {
            $scope.page.refreshTo(1);
        })

        $scope.$on("onNav", function (e, data) {
            if (data.controller === "c") {
                $scope.getProductTypePage();
                $scope.getInstitution(function () {
                    $scope.page.refreshTo(1);
                });
            }
        });
    })
</script>