<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>账号管理</title>
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
<body ng-app="m" ng-controller="c" ng-init='index = "模块角色";subIndex = "账号管理"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>账号列表</h4>
            <div class="clearfix">
                <label>
                    <button class="btn btn-success" ng-click="entity._openModal('add')">
                        <span class="icon-plus m-r"></span>新增&nbsp;账号
                    </button>
                </label>
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <label>管理员类型&nbsp;
                        <select class="form-control" ng-model="searchType" ng-options="x.id as x.name for x in twoRootRole"
                                ng-change="selectChange()"></select>
                    </label>&nbsp;
                    <label>角色&nbsp;
                        <select class="form-control" ng-model="searchRole"
                                ng-options="x.id as x.name for x in roleList"
                                ng-change="page.refreshTo(1)">
                        </select>
                    </label>
                    <input class="form-control m-l" type="text" placeholder="用户名" ng-model="search">
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
        .controller("c", function ($scope, page, ajax, entity, $filter, md5, alertService) {
            /**管理员类型*/
            $scope.twoRootRole = [
                {id: 0, name: "内容管理员"},
                {id: 1, name: "商户管理员"}];
            $scope.searchType = 0;
            $scope.selectChange = function () {
                $scope.getRoleList(function () {
                    $scope.page.refreshTo(1);
                });
            };
            //获取角色list
            $scope.getRoleList = function (callback) {
                ajax.ajax("/user/admin/getRoleList", "POST", {
                    userId: userId,
                    type: $scope.searchType
                }).success(function (data) {
                    $scope.roleList = data.list;
                    $scope.adminRoleList = angular.copy(data.list);
                    $scope.roleList.unshift({id: 0, name: "全部"});//往array首部插入一个元素，其它顺移
                    $scope.searchRole = $scope.roleList[0].id;
                    callback();
                }).error(function (data) {
                    console.log(data);
                });
            };
            $scope.load = function (current, size, orderBy, asc) {
                ajax.ajax("/user/admin/getAdminPageList", "POST",
                    {
                        userId: userId,
                        current: current,
                        size: size,
                        username: $scope.search,
                        roleId: $scope.searchRole,
                        parentId: 0,
                        orderBy: orderBy,
                        asc: asc,
                        type: $scope.searchType
                    }).success(function (data) {
                        console.log(data);
                        if (data.success) {
                            $scope.page.refreshPage(data);
                        }
                })
            };
            $scope.page = page.page($scope.load, "createTime", false);
            $scope.ths = [
                {width: "1%"},
                {
                    name: "用户名",
                    value: function (row) {
                        return row.username;
                    },
                    width: "10%"
                }, {
                    name: "管理员类型",
                    value: function (row) {
                        return row.role.type;
                    },
                    width: "10%"
                }, {
                    name: "角色",
                    value: function (row) {
                        return row.role.name;
                    },
                    width: "10%"
                }, {
                    name: "姓名",
                    value: function (row) {
                        return row.name;
                    },
                    width: "10%"
                }, {
                    name: "联系方式",
                    value: function (row) {
                        return row.contact;
                    },
                    width: "10%"
                },{
                    name: "状态",
                    style: function (row) {
                        return {
                            "color": row.status === true ? "green" : "red"
                        }
                    },
                    value: function (row) {
                        return row.status === true ? "正常" : "被禁";
                    },
                    width: "5%"
                },{
                    name: "创建时间",
                    value: function (row) {
                        return $filter("fmtDateYMdHMcn")(row.createTime);
                    },
                    orderBy: "createTime",
                    width: "15%"
                }
            ];
            $scope.operations = [
                {
                    name: function () {
                        return "编辑";
                    },
                    clas: function () {
                        return {
                            "btn btn-xs btn-primary": true
                        };
                    },
                    click: function (row) {
                        $scope.entityEdit._openModal("edit", row);
                    }
                },{
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
                        swal("确认要"+(row.status ? "禁用" : "启用")+"["+row.username+"]吗?", {
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
                                    ajax.ajax("/user/admin/deleteOneAdmin", "POST", {
                                        userId: 1,
                                        id: row.id
                                    }).success(function (data) {
                                        console.log(data);
                                        if(data.success){
                                            alertService.show("操作成功!", "success", "80%");
                                        }else {
                                            swal("提示!", "操作失败!权限不够!", "error");
                                        }
                                        $scope.page.refresh();
                                    }).error(function (data) {
                                        swal("提示!", "操作失败!", "error");
                                    });
                                    break;
                                default:
                                //swal("提示!", "操作失败!", "error");
                            }
                        });
                    }
                },/*{
                    name: function () {
                        return "子账号";
                    },
                    clas: function (row) {
                        return {
                            "btn btn-xs btn-default": true
                        };
                    },
                    click: function (row) {
                        window.open("/jsp/account/accountSubsDetail.jsp?aId="+row.id);
                    }
                },*/{
                    name: function () {
                        return "删除";
                    },
                    clas: function () {
                        return {
                            "btn btn-xs btn-danger": true,
                            "hide": true//先隐藏掉
                        };
                    },
                    click: function (row) {
                        swal("确认要删除 ["+(row.username)+"] 吗?", {
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
                                    ajax.ajax("/user/admin/deleteOneAdminForce", "POST", {
                                        userId: userId,
                                        id: row.id
                                    }).success(function (data) {
                                        if (data.success) {
                                            $scope.page.refresh();
                                            alertService.show("操作成功!", "success", "80%");
                                        } else {
                                            swal("提示!", "操作失败!权限不够!", "error");
                                        }
                                    });
                                    break;
                                default:
                                //swal("提示!", "操作失败!", "error");
                            }
                        });
                    }
                }
            ];
            /**
             * 新增entity
             */
            $scope.entity = entity.getEntity(
                {username: "", password: "", confirmPassword: "", contact: "", name: ""},
                {username: {}, password: {}, confirmPassword: {}
                }, function (action, row) {//beforeOpen
                    $scope.entity.entity.selectRole = $scope.adminRoleList[0].id;
                    if ($scope.entity.action === "add") {

                    }
                }, function () {//submit
                    if ($scope.entity.action === "add") {
                        ajax.ajax("/user/admin/addOneAdmin", "POST", {
                            userId: 1,
                            username: $scope.entity.entity.username,
                            password: md5.createHash($scope.entity.entity.password),
                            parentId: 0,
                            roleId: $scope.entity.entity.selectRole,
                            name: $scope.entity.entity.name,
                            contact: $scope.entity.entity.contact
                        }).success(function (data) {
                            console.log(data);
                            if (data.success){
                                alertService.show("操作成功!", "success", "80%");
                                $scope.page.refresh();
                            } else {
                                swal("提示!", "新增失败!用户名已存在!", "error");
                            }
                        }).error(function (data) {
                            console.log(data);
                            swal("提示!", "交互失败!", "error");
                        });
                    }
                    $scope.entity._success();//隐藏模态框
                }, "modal");
            /**
             * 编辑entity
             */
            $scope.entityEdit = entity.getEntity(
                {}, {},
                function (action, row) {//beforeOpen
                    if ($scope.entityEdit.action === "edit") {
                        $scope.entityEdit.entity = angular.copy(row);
                        $scope.entityEdit.entity.selectRole = $scope.entityEdit.entity.role.id;
                    }
                }, function () {//submit
                    if ($scope.entityEdit.action === "edit") {
                        ajax.ajax("/user/admin/updateOneAdmin", "POST", {
                            userId: 1,
                            id: $scope.entityEdit.entity.id,
                            roleId: $scope.entityEdit.entity.selectRole,
                            name: $scope.entityEdit.entity.name,
                            contact: $scope.entityEdit.entity.contact
                        }).success(function (data) {
                            if (data.success){
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
                $scope.getRoleList(function () {
                    $scope.page.refreshTo(1);
                });
            });
        });
</script>
<%--模态框--%>
<div entity-modal="modal" title="账号" e="entity">
    <entity-modal-body>
        <div entity-edit-text="username" type="text" title="用户名" entity="entity.entity" e="entity"
             vld="entity.validate">
        </div>
        <div entity-edit-text="name" type="text" title="姓名" entity="entity.entity" e="entity">
        </div>
        <div entity-edit-text="contact" type="text" title="联系方式" entity="entity.entity" e="entity">
        </div>
        <div entity-edit-text="password" type="password" title="密码" entity="entity.entity" e="entity"
             vld="entity.validate"></div>
        <div entity-edit-text="confirmPassword" type="password" title="确认密码" entity="entity.entity" e="entity"
             vld="entity.validate" confirm="yes"></div>
        <div class="form-group">
            <label class="col-sm-2 col-sm-offset-2 control-label">角色</label>
            <div class="col-sm-4">
                <select class="form-control" ng-model="entity.entity.selectRole"
                        ng-options="x.id as x.name for x in adminRoleList"
                        ng-change="selectChange()">
                </select>
            </div>
        </div>
    </entity-modal-body>
</div>
<div entity-modal="modal-edit" title="账号" e="entityEdit">
    <entity-modal-body>
        <div entity-view-tag="entityEdit.entity.username" type="text" title="用户名">
        </div>
        <div entity-edit-text="name" type="text" title="姓名" entity="entityEdit.entity" e="entityEdit">
        </div>
        <div entity-edit-text="contact" type="text" title="联系方式" entity="entityEdit.entity" e="entityEdit">
        </div>
        <div class="form-group">
            <label class="col-sm-2 col-sm-offset-2 control-label">角色</label>
            <div class="col-sm-4">
                <select class="form-control" ng-model="entityEdit.entity.selectRole"
                        ng-options="x.id as x.name for x in adminRoleList"
                        ng-change="selectChange()">
                </select>
            </div>
        </div>
    </entity-modal-body>
</div>
</body>
</html>
