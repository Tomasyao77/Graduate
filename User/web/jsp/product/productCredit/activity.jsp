<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>产品活动</title>
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
            <h4>活动列表</h4>
            <div class="clearfix">
                <label>
                    <button class="btn btn-success" ng-click="entity._openModal('add')">
                        <span class="icon-plus m-r"></span>新增&nbsp;活动
                    </button>
                </label>
                <%--<form class="form-inline pull-right" ng-submit="page.refreshTo(1)">--%>
                    <%--<input class="form-control m-l" type="text"--%>
                           <%--placeholder="产品名称" ng-model="search">--%>
                    <%--<button class="btn btn-primary" type="submit">--%>
                        <%--<span class="icon-search m-r"></span> 搜索--%>
                    <%--</button>--%>
                <%--</form>--%>
            </div>
        </div>
        <%@ include file="/jsp/common/table.jspf" %>
    </div>

    <div entity-modal="modal-add" title="活动" e="entity">
        <entity-modal-body>
            <div entity-edit-text="title" type="text" title="标题" entity="entity.entity" e="entity"
                 vld="entity.validate" rewidth="2-0-8"></div>
            <div entity-edit-textarea="content" type="text" title="详情" entity="entity.entity" e="entity"
                 vld="entity.validate" rewidth="2-0-8"></div>
            <div entity-edit-text="limitNum" type="text" title="店铺限量" entity="entity.entity" e="entity"
                 vld="entity.validate" rewidth="2-0-8"></div>
            <div entity-edit-text="limitUserNum" type="text" title="个人限量" entity="entity.entity" e="entity"
                 vld="entity.validate" rewidth="2-0-8"></div>

            <label for="dtp_input1" class="col-md-2 control-label">日期时间选择器：</label>
            <div id="dtp_input1" class="input-group date col-md-4">
                <input class="form-control" type="text" value="">
                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>

            <div entity-edit-text="startTime" type="datetime-local" title="起始时间" entity="entity.entity" e="entity"
                 vld="entity.validate" rewidth="2-0-8"></div>
            <div entity-edit-text="endTime" type="datetime-local" title="结束时间" entity="entity.entity" e="entity"
                 vld="entity.validate" rewidth="2-0-8"></div>
        </entity-modal-body>
    </div>

    <%--编辑活动--%>
    <div entity-modal="modal-edit" title="活动" e="entityEdit">
        <entity-modal-body>
            <div entity-edit-text="title" type="text" title="标题" entity="entityEdit.entity" e="entity"
                 vld="entity.validate" rewidth="2-0-8"></div>
            <div entity-edit-textarea="content" type="text" title="详情" entity="entityEdit.entity" e="entity"
                 vld="entity.validate" rewidth="2-0-8"></div>
            <div entity-edit-text="limitNum" type="text" title="活动产品限量" entity="entityEdit.entity" e="entity"
                 vld="entity.validate" rewidth="2-0-8"></div>
            <div entity-edit-text="limitUserNum" type="text" title="个人限量" entity="entityEdit.entity" e="entity"
                 vld="entity.validate" rewidth="2-0-8"></div>
            <div entity-edit-text="startTime" type="datetime-local" title="起始时间" entity="entityEdit.entity" e="entity"
                 vld="entity.validate" rewidth="2-0-8"></div>
            <div entity-edit-text="endTime" type="datetime-local" title="结束时间" entity="entityEdit.entity" e="entity"
                 vld="entity.validate" rewidth="2-0-8"></div>
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

        $scope.a = $('#dtp_input1').datetimepicker({
            language : 'zh-CN',
            format : 'yyyy-mm-dd hh:ii:ssZ p',
            todayBtn : true,
            autoclose : true
        });

            $scope.load = function (current, size, orderBy, asc) {
            ajax.ajax("/user/redirect/trade/activity/getProductActivityPage", "POST",
                {
                    userId: 1,
                    current: current,
                    size: size,
                    productId: $scope.GetQueryString("productId"),
//                    productId: 56,
                    search: $scope.search,
                    orderBy: orderBy,
                    asc: asc
                }).success(function (data) {
                console.log(data);
                if (data.success) {
                    $scope.page.refreshPage(data);
                }
            });
        };
        $scope.page = page.page($scope.load, "a.create_time", false);
        $scope.ths = [
            {
                name: "产品名称",
                value: function (row) {
                    return row.product.name;
                },
                width: "10%"
            }, {
                name: "活动标题",
                value: function (row) {
                    return row.title;
                },
                width: "10%"
            }, {
                name: "活动介绍",
                value: function (row) {
                    return row.content;
                },
                width: "10%"
            }, {
                name: "活动产品限量",
                value: function (row) {
                    return row.limit_num;
                },
                width: "5%"
            }, {
                name: "单人限量",
                value: function (row) {
                    return row.limit_user_num;
                },
                width: "5%"
            }, {
                name: "活动状态",
                value: function (row) {
                    if (row.status == false) {
                        return "已上线";
                    }else return"未上线";
                },
                width: "5%"
            }, {
                name: "起始时间",
                value: function (row) {
                    return $filter("fmtDateYMdHMcn")(row.start_time);
                },
                width: "15%"
            },{
                name: "结束时间",
                value: function (row) {
                    return $filter("fmtDateYMdHMcn")(row.end_time);
                },
                width: "15%"
            },{
                name: "创建时间",
                value: function (row) {
                    return $filter("fmtDateYMdHMcn")(row.create_time);
                },
                orderBy: "a.create_time",
                width: "15%"
            }
        ];
        $scope.operations = [
            {
                name: function () {
                    return "上线";
                },
                clas: function (row) {
                    return {
                        "btn btn-xs btn-success": true,
                        "hide": row.status === false
                    };
                },
                click: function (row) {
                    ajax.ajax("/user/redirect/trade/activity/updateActivityStatus", "POST", {
                        userId:1,
                        activityId: row.id,
                        productId: $scope.GetQueryString("productId"),
                        status: 0
                    }).success(function (data) {
                        if (data.success) {
                            ajax.ajax("/user/redirect/trade/activity/resetSecondKill", "POST", {
                                userId:1,
                                productId: $scope.GetQueryString("productId"),
                                limitTotal: row.limit_num,
                                status: 0
                            }).success(function (data){
                                if (data.success){
                                    $scope.page.refresh();
                                    alertService.show("操作成功!", "success", "80%");
                                }
                            })
//                            $scope.page.refresh();
//                            alertService.show("操作成功!", "success", "80%");
                        } else {
                            swal("提示!", "操作失败!已有活动开启!", "error");
                        }
                    }).error(function (data) {
                        console.log(data);
                    });
                }
            },{
                name: function () {
                    return "下线";
                },
                clas: function (row) {
                    return {
                        "btn btn-xs btn-dangerous": true,
                        "hide": row.status === true
                    };
                },
                click: function (row) {
                    ajax.ajax("/user/redirect/trade/activity/updateActivityStatus", "POST", {
                        userId:1,
                        activityId: row.id,
                        productId: $scope.GetQueryString("productId"),
                        status: 1
                    }).success(function (data) {
                        if (data.success) {
                            ajax.ajax("/user/redirect/trade/activity/resetSecondKill", "POST", {
                                userId:1,
                                productId: $scope.GetQueryString("productId"),
                                limitTotal: row.limit_num,
                                status: 1
                            }).success(function (data){
                                if (data.success){
                                    $scope.page.refresh();
                                    alertService.show("操作成功!", "success", "80%");
                                }
                            })
//                            $scope.page.refresh();
//                            alertService.show("操作成功!", "success", "80%");
                        } else {
                            swal("提示!", "操作失败!", "error");
                        }
                    }).error(function (data) {
                        console.log(data);
                    });
                }
            },{
                name: function () {
                    return "编辑";
                },
                clas: function (row) {
                    return {
                        "btn btn-xs btn-default": true,
                    };
                },
                click: function (row) {
                    // window.open("/jsp/product/creditGift.jsp?id="+row.id);
                    $scope.entityEdit._openModal("edit", row);
                }
            }
        ];

        /**
         * entity
         */
        $scope.entity = entity.getEntity(
            {}, {},
            function (action, row) {//beforeOpen
                if ($scope.entity.action === "add") {
                    $scope.entity.entity = angular.copy(row);
//                    $scope.entityEdit.entity.returnRate = angular.copy(row.returnRate);
//                    $scope.entityEdit.entity.returnCollect = row.returnCollect;
                }
            },
            function () {//submit
                if ($scope.entity.action === "add") {
                    $('#dtp_input1').datetimepicker({
                        language : 'zh-CN',
                        format : 'yyyy-mm-dd hh:ii:ss',
                        todayBtn : true,
                        autoclose : true
                    });
                    ajax.ajax( "/user/redirect/trade/activity/addProductActivity","POST", {
                        userId:1,
                        productId: $scope.GetQueryString("productId"),
//                        productId:56,
                        title:$scope.entity.entity.title,
                        content:$scope.entity.entity.content,
                        limitNum:$scope.entity.entity.limitNum,
                        limitUserNum:$scope.entity.entity.limitUserNum,
                        startTime:$filter('date')($scope.entity.entity.startTime, "yyyy-MM-dd HH:mm:ss"),
                        endTime:$filter('date')($scope.entity.entity.endTime, "yyyy-MM-dd HH:mm:ss")
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
                $scope.entity._success();//隐藏模态框
            }, "modal-add");

        $scope.entityEdit = entity.getEntity(
            {}, {},
            function (action, row) {//beforeOpen
                if ($scope.entityEdit.action === "edit") {
                    $scope.entityEdit.entity = angular.copy(row);
                    $scope.entityEdit.entity.limitNum = row.limit_num;
                    $scope.entityEdit.entity.limitUserNum = row.limit_user_num;
//                    $scope.entityEdit.entity.startTime = $filter('datetime-local')(row.start_time, "yyyy-MM-dd HH:mm:ss");
//                    $scope.entityEdit.entity.endNum = row.end_time;
                }
            },
            function () {//submit
                if ($scope.entityEdit.action === "edit") {
                    ajax.ajax( "/user/redirect/trade/activity/updateProductActivity","POST", {
                        userId:1,
                        activityId: $scope.entityEdit.entity.id,
                        title:$scope.entityEdit.entity.title,
                        content:$scope.entityEdit.entity.content,
                        limitNum:$scope.entityEdit.entity.limitNum,
                        limitUserNum:$scope.entityEdit.entity.limitUserNum,
                        startTime:$filter('date')($scope.entityEdit.entity.startTime, "yyyy-MM-dd HH:mm:ss"),
                        endTime:$filter('date')($scope.entityEdit.entity.endTime, "yyyy-MM-dd HH:mm:ss")
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
                $scope.entityEdit._success();//隐藏模态框
            }, "modal-edit");


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