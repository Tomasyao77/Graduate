<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/12/12
  Time: 14:44
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
    <title>月奖励管理</title>
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
<body ng-app="m" ng-controller="c" ng-init='index = "奖励管理";subIndex = "月奖励管理"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>奖励列表</h4>
            <div class="clearfix">
                <button class="btn btn-primary pull-left" ng-click="export()"><span class="icon-print m-r"></span>导出</button>
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
    var m = angular.module("m", ["nm","dateRangeModule"])
        .controller("c", function($scope, page, ajax, entity, $filter,alertService){
            $scope.dateRange = {
                start: null,
                end: null,
                change: function (start, end) {
                    console.log(end);
                    $scope.dateRange.start = start;
                    $scope.dateRange.end = end;
                    $scope.page.refreshTo(1);
                }
            };
            $scope.load = function(current, size, orderBy, asc){
                ajax.ajax("/user/redirect/trade/statics/getRewardList","POST",
                    {
                        userId:1,
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
            $scope.page = page.page($scope.load,"pay_time", false);
            $scope.ths = [
                {width: "1%"},
                {
                    name: "店铺名称",
                    value: function (row) {
                        return row.storeDetail.name;
                    },
                    width: "10%"
                },
                {
                    name: "用户名",
                    value: function (row) {
                        return row.storeDetail.user.name;
                    },
                    width: "10%"
                },
                {
                    name: "电话",
                    value: function (row) {
                        return row.storeDetail.user.phone;
                    },
                    width: "10%"
                },
                {
                    name: "销售额",
                    value: function (row) {
                        return row.totalSellMoney;
                    },
                    width: "10%"
                }, {
                    name: "上次发放奖励时间",
                    value: function (row) {
                        return row.topRewardHistory == null? '未发放过奖励':$filter("fmtDateYMdHMcn")(row.topRewardHistory.createTime);
                    },
                    width: "10%"
                }, {
                    name: "月奖励金额",
                    value: function (row) {
                        return row.rewardMoney;
                    },
                    width: "10%"
                }
            ];
            $scope.operations = [
                {
                    name: function () {
                        return "审核通过";
                    },
                    clas: function () {
                        return {
                            "btn btn-xs btn-danger": true
                        };
                    },
                    click: function (row) {
                        $scope.entity._openModal('edit', row);
                    }
                },
                {
                    name: function () {
                        return "查看历史奖励";
                    },
                    clas: function () {
                        return {
                            "btn btn-xs btn-default": true
                        };
                    },
                    click: function (row,current, size, orderBy, asc) {
                        ajax.ajax("/user/redirect/trade/reward/getRewardHistoryList", "POST", {
                            userId:1,
                            storeId: row.storeDetail.id,
                            current: 1,
                            size: 1000,
                            orderBy: "createTime",
                            asc: false
                        }).success(function (data) {
                            $scope.historyList = data.page.list;
                            console.log($scope.historyList);
                            $("#modal-history").modal("show");
                        })
                    }
                }];
            $scope.entity = entity.getEntity(
                {
                },
                {
                },function (action, row) {
                    if ($scope.entity.action === "edit") {
                        $scope.entity.entity = angular.copy(row);
                        $scope.entity.entity.store = row.storeDetail.id;
                        $scope.entity.entity.rewardMoney = row.rewardMoney.toString();
                    }
                },function (){
                    if($scope.entity.action === "edit"){
                        swal({
                            closeOnClickOutside: false,//禁止点击背景关闭alert
                            title: "提示!",
                            text: "确认发放奖励吗?",
                            icon: "warning",
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
                                    ajax.ajax("/user/redirect/trade/reward/addOneReward", "POST", {
                                        userId:1,
                                        storeId: $scope.entity.entity.store,
                                        rewardMoney:parseFloat($scope.entity.entity.rewardMoney),
                                        startTime:$scope.dateRange.start,
                                        endTime:$scope.dateRange.end
                                    }).success(function (data) {
                                        if (data.success) {
                                            $scope.page.refresh();
                                            alertService.show("新增发放奖励记录成功!", "success", "80%");
                                            $scope.page.refreshTo(1);
                                        } else {
                                            swal("提示!", "新增失败!", "error");
                                        }
                                    }).error(function (data) {
                                        console.log(data);
                                    });
                                    break;
                            }
                        });
                    }
                    $scope.entity._success();//隐藏模态框
                }, "modal-reward");
            $scope.$watch('$viewContentLoaded', function () {
                $scope.page.refreshTo(1);
            });
            $scope.export = function(){
                window.open("/user/redirect/trade/reward/exportRewardExcel?" + $.param({
                    userId:1,
                    current: 1,
                    size: 1000,
                    startTime:$scope.dateRange.start,
                    endTime:$scope.dateRange.end,
                    orderBy: "totalSellMoney",
                    asc: false
                }))
            }
        })
</script>
<div entity-modal="modal-reward" title="奖励金额" e="entity">
    <entity-modal-body>
        <div entity-edit-text="rewardMoney" type="text" title="奖励金额" entity="entity.entity" e="entity"></div>
    </entity-modal-body>
</div>

<div class="modal fade" id="modal-history" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">奖励历史记录</h4>
            </div>
            <table class="history" align="center">
                <tr>
                    <th>奖励金额</th><th>发放时间</th><th>开始时间</th><th>截止时间</th>
                </tr>
                <tr ng-repeat="x in historyList" onmouseover="this.style.backgroundColor='#ffff66';" onmouseout="this.style.backgroundColor='#d4e3e5';">
                    <td>{{ x.reward }}</td>
                    <td>{{ x.createTime | date:"yyyy-MM-dd HH:mm:ss" }}</td>
                    <td>{{ x.startTime | date:"yyyy-MM-dd HH:mm:ss" }}</td>
                    <td>{{ x.endTime | date:"yyyy-MM-dd HH:mm:ss"  }}</td>
                </tr>
            </table>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>

<!-- CSS goes in the document HEAD or added to your external stylesheet -->
<style type="text/css">
    table.history {
        font-family: verdana,arial,sans-serif;
        font-size:11px;
        color:#333333;
        border-width: 1px;
        border-color: #ffffff;
        border-collapse: collapse;
    }
    table.history th {
        background-color: #36cae0;
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #a9c6c9;
    }
    table.history tr {
        background-color: #e5e1e5;
    }
    table.history td {
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #44c9b9;
    }
</style>



