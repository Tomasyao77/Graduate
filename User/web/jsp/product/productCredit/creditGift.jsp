<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>赊购返点</title>
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
            <h4>返点列表</h4>
            <div class="clearfix">
                <label>
                    <button class="btn btn-success" ng-click="page.refreshTo(1)">
                        <span class="icon-refresh m-r"></span>刷新
                    </button>
                </label>
            </div>
        </div>
        <%@ include file="/jsp/common/table.jspf" %>
    </div>

    <%--编辑返点--%>
    <div entity-modal="modal-creditGift" title="价格" e="entityCreditGift">
        <entity-modal-body>
            <div entity-view-tag="entityCreditGift.entity.level" type="text" title="等级"></div>
            <div entity-view-tag="entityCreditGift.entity.maxCollect" type="text" title="最大赊购量"></div>
            <div entity-edit-text="returnRate" type="text" title="返点比例" entity="entityCreditGift.entity" e="entityCreditGift"
                 vld="entityCreditGift.validate"></div>
            <div entity-view-tag="entityCreditGift.entity.returnCollect" type="text" title="返点数量"></div>
        </entity-modal-body>
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
        $scope.verifyType = [{id: 1, name: "审核通过"}, {id: 0, name: "待审核"}, {id: 2, name: "审核未通过"}];
        $scope.searchType = 1;
        $scope.selectChange = function () {
            $scope.page.refresh();
        };
        $scope.load = function (current, size, orderBy, asc) {
            ajax.ajax("/user/redirect/trade/creditGift/getCreditGiftList", "POST",
                {
                    userId: 1,
                    current: current,
                    size: size,
                    productId: $scope.GetQueryString("productId"),
                    orderBy: orderBy,
                    asc: asc
                }).success(function (data) {
                console.log(data);
                if (data.success) {
                    $scope.page.refreshPage(data);
                }
            });
        };
        $scope.page = page.page($scope.load, "c.create_time", false);
        $scope.ths = [
            {
                name: "产品名称",
                value: function (row) {
                    return row.product.name;
                },
                width: "10%"
            }, {
                name: "等级",
                value: function (row) {
                    return row.level;
                },
                width: "10%"
            }, {
                name: "最大赊购量",
                value: function (row) {
                    return row.maxCollect;
                },
                width: "10%"
            }, {
                name: "返点比例",
                value: function (row) {
                    return row.returnRate;
                },
                width: "10%"
            }, {
                name: "返还数量",
                value: function (row) {
                    return row.returnCollect;
                },
                width: "10%"
            },{
                name: "创建时间",
                value: function (row) {
                    return $filter("fmtDateYMdHMcn")(row.createTime);
                },
                orderBy: "c.create_time",
                width: "15%"
            }
        ];
        $scope.operations = [
            {
                name: function () {
                    return "查看与编辑返点";
                },
                clas: function (row) {
                    return {
                        "btn btn-xs btn-success": true,
                    };
                },
                click: function (row) {
                   // window.open("/jsp/product/creditGift.jsp?id="+row.id);
                    $scope.entityCreditGift._openModal("edit", row);
                }
            }
        ];
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