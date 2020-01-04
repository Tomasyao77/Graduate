<%--
  Created by IntelliJ IDEA.
  User: 14799
  Date: 2018/9/16
  Time: 19:29
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
<body ng-app="m" ng-controller="gift" ng-init='index = "审核管理"; subIndex = "返点审核"'>
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
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <label>审核状态&nbsp;
                        <select class="form-control" ng-model="verifyType"
                                ng-options="v.id as v.name for v in verifyTypes"
                                ng-change="selectChange()">
                            <option value="">全部</option>
                        </select>
                    </label>
                    <input class="form-control m-l" type="text"
                           placeholder="名称/描述" ng-model="search">
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
        .controller("gift", function ($scope, page, ajax, $location, entity, $filter, alertService) {
        //审核状态
        $scope.verifyType = null;
        $scope.verifyTypes = [{id: 0, name: "待审核"}, {id: 1, name: "审核通过"}, {id: 2, name: "审核未通过"}];
        $scope.searchType = 0;
        $scope.selectChange = function () {
            $scope.page.refresh();
        };

        $scope.load = function (current, size, orderBy, asc) {
            ajax.ajax("/user/redirect/trade/creditGift/getCreditGiftVerify", "POST",
                {
                    current: current,
                    size: size,
                    search: $scope.search,
                    verifyType: $scope.verifyType,
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
                width: "15%"
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
                    return "通过";
                },
                clas: function (row) {
                    return {
                        "btn btn-xs btn-success": true,
                        "hide": $scope.searchType !== 0
                    };
                },
                click: function (row) {
                    swal("确认要[通过]吗?", {
                        buttons: {
                            cancel: "关闭",
                            catch: {
                                text: "确定",
                                value: "catch"
                            }
                        }
                    }).then(function (value) {
                        switch (value) {
                            case "catch":
                                ajax.ajax("/user/redirect/trade/creditGift/verifyCreditGift", "POST", {
                                    userId: 1,
                                    creditGiftVerifyId: row.id,
                                    verifyType: 1
                                }).success(function (data) {
                                    if (data.success) {
                                        $scope.page.refresh();
                                        alertService.show("操作成功!", "success", "80%");
                                    } else {
                                        swal("提示!", "操作失败!", "error");
                                    }
                                }).error(function (data) {
                                    console.log(data);
                                });
                                break;
                            default:
                            //swal("提示!", "操作失败!", "error");
                        }
                    });
                }
            }, {
                name: function () {
                    return "拒绝";
                },
                clas: function (row) {
                    return {
                        "btn btn-xs btn-warning": true,
                        "hide": $scope.searchType !== 0
                    };
                },
                click: function (row) {
                    swal("确认要[拒绝]吗?", {
                        buttons: {
                            cancel: "关闭",
                            catch: {
                                text: "确定",
                                value: "catch"
                            }
                        }
                    }).then(function (value) {
                        switch (value) {
                            case "catch":
                                ajax.ajax("/user/redirect/trade/creditGift/verifyCreditGift", "POST", {
                                    userId: 1,
                                    creditGiftVerifyId: row.id,
                                    verifyType: 2
                                }).success(function (data) {
                                    if (data.success) {
                                        $scope.page.refresh();
                                        alertService.show("操作成功!", "success", "80%");
                                    } else {
                                        swal("提示!", "操作失败!", "error");
                                    }
                                }).error(function (data) {
                                    console.log(data);
                                });
                                break;
                            default:
                            //swal("提示!", "操作失败!", "error");
                        }
                    });
                }
            }
        ];

        $scope.$watch('$viewContentLoaded', function () {
            $scope.page.refreshTo(1);
        })
    })
</script>