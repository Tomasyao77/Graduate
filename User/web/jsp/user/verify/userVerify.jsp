<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>用户认证审核</title>
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
<body ng-app="m" ng-controller="c" ng-init='index = "审核管理"; subIndex = "开店认证"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>开店认证列表</h4>
            <div class="clearfix">
                <label>
                    <button class="btn btn-success" ng-click="page.refreshTo(1)">
                        <span class="icon-refresh m-r"></span>刷新
                    </button>
                </label>
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <label>审核状态&nbsp;
                        <select class="form-control" ng-model="searchType"
                                ng-options="x.id as x.name for x in verifyType"
                                ng-change="selectChange()"></select>
                    </label>
                    <input class="form-control m-l" type="text"
                           placeholder="姓名" ng-model="search">
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
        .controller("c", function ($scope, page, ajax, entity, $filter, alertService, $interval) {
            //审核状态
            $scope.verifyType = [{id: 0, name: "待审核"}, {id: 1, name: "审核通过"}, {id: 2, name: "审核未通过"}];
            $scope.searchType = 0;
            $scope.selectChange = function () {
                $scope.page.refresh();
            };

            $scope.load = function (current, size, orderBy, asc) {
                ajax.ajax("/user/user/getUserVerifyPageListAdminMybatis", "POST",
                    {
                        userId: 1,
                        current: current,
                        size: size,
                        search: $scope.search,
                        orderBy: orderBy,
                        asc: asc,
                        verify: $scope.searchType
                    }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.page.refreshPage(data);
                    }
                });
            };
            $scope.page = page.page($scope.load, "uv.create_time", false);
            $scope.ths = [
                {width: "1%"},
                {
                    name: "姓名",
                    value: function (row) {
                        return row.name;
                    },
                    width: "5%"
                }, {
                    name: "手机号",
                    value: function (row) {
                        return row.phone;
                    },
                    width: "10%"
                }, {
                    name: "所属区域",
                    value: function (row) {
                        return row.region;
                    },
                    width: "10%"
                }, {
                    name: "申请理由",
                    value: function (row) {
                        return row.reason;
                    },
                    width: "10%"
                }, {
                    name: "店铺名",
                    value: function (row) {
                        return row.storeName;
                    },
                    width: "10%"
                }, {
                    name: "邀请人",
                    value: function (row) {
                        if(row.store == null){
                            return;
                        }
                        return row.store.user.name;
                    },
                    width: "5%"
                }, {
                    name: "备注",
                    value: function (row) {
                        return row.remark;
                    },
                    width: "10%"
                }, {
                    name: "提交时间",
                    value: function (row) {
                        return $filter("fmtDateYMdHMcn")(row.createTime);
                    },
                    orderBy: "uv.create_time",
                    width: "10%"
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
                                    ajax.ajax("/user/user/updateUserVerifyStatus", "POST", {
                                        userId: 1,
                                        id: row.id,
                                        verify: 1
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
                                    ajax.ajax("/user/user/updateUserVerifyStatus", "POST", {
                                        userId: 1,
                                        id: row.id,
                                        verify: 2
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
                        return "备注";
                    },
                    clas: function (row) {
                        return {
                            "btn btn-xs btn-primary": true,
                            "hide": $scope.searchType === 2
                        };
                    },
                    click: function (row) {
                        $scope.entity._openModal("edit", row);
                    }
                }
            ];
            $scope.entity = entity.getEntity(
                {remark: ""},
                {
                    remark: {}
                }, function (action, row) {//beforeOpen
                    if ($scope.entity.action === "edit") {
                        $scope.entity.entity = angular.copy(row);
                    }
                }, function () {//submit
                    if ($scope.entity.action === "edit") {
                        ajax.ajax("/user/user/updateRemark", "POST", {
                            userId: userId,
                            id: $scope.entity.entity.id,
                            remark: $scope.entity.entity.remark
                        }).success(function (data) {
                            console.log(data);
                            if (data.success) {
                                $scope.page.refresh();
                                alertService.show("操作成功!", "success", "80%");
                            } else {
                                swal("提示!", "编辑失败!名称已存在", "error");
                            }
                        }).error(function (data) {
                            console.log(data);
                        });
                    }
                    $scope.entity._success();//隐藏模态框
                }, "modal");

            $scope.$watch('$viewContentLoaded', function () {
                $scope.page.refreshTo(1);
            });
        });
</script>
<%--模态框--%>
<div entity-modal="modal" title="备注" e="entity">
    <entity-modal-body>
        <div entity-view-tag="entity.entity.name" type="text" title="姓名"></div>
        <div entity-edit-textarea="remark" title="备注" entity="entity.entity" e="entity"
             vld="entity.validate"></div>
    </entity-modal-body>
</div>
</body>
</html>
