<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>子账号详情</title>
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
    <script src="/jsp/common/js/angular-md5.min.js"></script>
    <%--自定义angular module--%>
    <script src="/jsp/common/template/baseModule.js"></script>
    <%--日期处理--%>
    <link href="/jsp/common/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="/jsp/common/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/jsp/common/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="/jsp/common/js/moment.min.js"></script>
    <%--alert--%>
    <script src="/jsp/common/js/sweetalert.min.js"></script>
</head>
<body ng-app="m" ng-controller="c" ng-init='index = "子账号管理"'>
<%--<jsp:include page="/jsp/common/nav.jsp"/>--%>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>子账号列表</h4>
            <div class="clearfix">
                <label>
                    <button class="btn btn-success" ng-click="entity._openModal('add')">
                        <span class="icon-plus m-r"></span>新增&nbsp;子账号
                    </button>
                </label>
                <label style="margin-left: 20px;">当前总账号:&nbsp;</label>
                <span ng-bind="aParent.username" class="text-danger" style="font-size: 120%;"></span>
                <label style="margin-left: 20px;">角色类型:&nbsp;</label>
                <span ng-bind="aParent.role.name" class="text-danger" style="font-size: 120%;"></span>
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
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
    angular.module("m", ["baseModule", "angular-md5"])
        .controller("c", function ($scope, page, ajax, entity, $filter, md5, alertService) {
            $scope.aId = <%=request.getParameter("aId")%>
            /**管理员类型*/
            $scope.twoRootRole = ["内容管理员", "商户管理员"];
            //获取父账号
            $scope.getParent = function (callback) {
                ajax.ajax("/user/admin/getOneAdmin", "POST", {
                    userId: 1,
                    id: $scope.aId
                }).success(function (data) {
                    $scope.aParent = data.value;
                    callback();
                }).error(function (data) {
                    console.log(data);
                });
            };
            $scope.load = function (current, size) {
                ajax.ajax("/user/admin/getAdminPageList", "POST",
                    {
                        userId: 1,
                        current: current,
                        size: size,
                        username: $scope.search,
                        roleId: $scope.aParent.role.id,
                        parentId: $scope.aParent.id,
                        type: $scope.twoRootRole.indexOf($scope.aParent.role.type)
                    }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.page.refreshPage(data);
                    }
                });
            };
            $scope.page = page.page($scope.load);
            $scope.ths = [
                {width: "1%"},
                {
                    name: "用户名",
                    value: function (row) {
                        return row.username;
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
                }, {
                    name: "状态",
                    style: function (row) {
                        return {
                            "color": row.status === true ? "green" : "red"
                        }
                    },
                    value: function (row) {
                        return row.status === true ? "正常" : "被禁";
                    },
                    width: "10%"
                }, {
                    name: "创建时间",
                    value: function (row) {
                        return $filter("fmtDateYMdHMcn")(row.createTime);
                    },
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
                }
            ];
            /**
             * 新增entity
             */
            $scope.entity = entity.getEntity(
                {username: "", password: "", confirmPassword: ""},
                {
                    username: {}, password: {}, confirmPassword: {}
                }, function (action, row) {//beforeOpen
                    if ($scope.entity.action === "add") {
                        $scope.moduleList = angular.copy($scope.moduleOriginList);
                    }
                }, function () {//submit
                    if ($scope.entity.action === "add") {
                        var modules = [];
                        for (var i in $scope.moduleList) {
                            var module = $scope.moduleList[i];//item
                            if (module.nodes.length > 0) {//有子模块
                                for (var j in module.nodes) {
                                    if (module.nodes[j].checked) {
                                        modules.push(module.nodes[j].id);
                                    }
                                }
                            } else {//无子模块
                                if (module.checked) {
                                    modules.push(module.id);
                                }
                            }
                        }
                        if (modules.length <= 0) {
                            swal("提示!", "请授予权限!", "warning");
                            return;
                        }
                        //console.log(modules);return;
                        ajax.ajax("/user/admin/addOneAdminSub", "POST", {
                            userId: 1,
                            username: $scope.entity.entity.username,
                            password: md5.createHash($scope.entity.entity.password),
                            parentId: $scope.aParent.id,
                            roleId: $scope.aParent.role.id,
                            name: $scope.entity.entity.name,
                            contact: $scope.entity.entity.contact,
                            moduleIds: modules
                        }).success(function (data) {
                            console.log(data);
                            if (data.success) {
                                alertService.show("操作成功!", "success", "80%");
                                $scope.page.refresh();
                            } else {
                                swal("提示!", "新增失败!用户名已存在!", "error");
                            }
                        }).error(function (data) {
                            console.log(data);
                            swal("提示!", "交互失败!", "error");
                        });
                    } else if ($scope.entity.action === "edit") {

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
                        $scope.moduleList = angular.copy($scope.moduleOriginList);
                        $scope.entityEdit.entity = angular.copy(row);
                        //获取该角色的模块树(list即可)
                        //这里和role页面不一样不是调用getRoleModuleList
                        //而是调用getAdminModuleList
                        ajax.ajax("/user/permission/getAdminModuleList", "POST", {
                            userId: 1,
                            adminId: row.id
                        }).success(function (data) {
                            console.log(data);
                            if(data.success){//勾选相应复选框
                                var adminModules = [];
                                for (var t in data.list) {
                                    adminModules.push(data.list[t].moduleId);
                                }
                                //遍历模块树
                                for (var i in $scope.moduleList) {
                                    var module = $scope.moduleList[i];//item
                                    if (module.nodes.length > 0) {//有子模块
                                        for (var j in module.nodes) {
                                            if(adminModules.indexOf(module.nodes[j].id) >= 0){
                                                module.nodes[j].checked = true;
                                            }
                                        }
                                    } else {//无子模块
                                        if(adminModules.indexOf(module.id) >= 0){
                                            module.checked = true;
                                        }
                                    }
                                }
                            }
                        }).error(function (data) {
                            console.log(data);
                        });
                    }
                }, function () {//submit
                    var modules = [];
                    for (var i in $scope.moduleList) {
                        var module = $scope.moduleList[i];//item
                        if (module.nodes.length > 0) {//有子模块
                            for (var j in module.nodes) {
                                if (module.nodes[j].checked) {
                                    modules.push(module.nodes[j].id);
                                }
                            }
                        } else {//无子模块
                            if (module.checked) {
                                modules.push(module.id);
                            }
                        }
                    }
                    if(modules.length <= 0){
                        swal("提示!", "请授予权限!", "warning");
                        return;
                    }
                    //console.log(modules);return;
                    if( $scope.entityEdit.action === "edit"){
                        ajax.ajax("/user/permission/updateAdminModule", "POST", {
                            userId: 1,
                            adminId:  $scope.entityEdit.entity.id,
                            name:  $scope.entityEdit.entity.name,
                            contact:  $scope.entityEdit.entity.contact,
                            adminModuleIds: modules
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
                    $scope.entityEdit._success();//隐藏模态框
                }, "modal-edit");

            $scope.$watch('$viewContentLoaded', function () {
                $scope.getParent(function () {
                    //获取父账号所有的权限
                    ajax.ajax("/user/permission/getRoleModuleFormatList", "POST", {
                        userId: 1,
                        roleId: $scope.aParent.role.id
                    }).success(function (data) {
                        console.log(data);
                        $scope.moduleOriginList = data.list;
                    }).error(function (data) {
                        console.log(data);
                    });
                    $scope.page.refreshTo(1);
                });
            });
        });
</script>
<%--模态框--%>
<div entity-modal="modal" title="子账号" e="entity">
    <entity-modal-body>
        <div entity-edit-text="username" type="text" title="用户名" entity="entity.entity" e="entity"
             vld="entity.validate"></div>
        <div entity-edit-text="name" type="text" title="姓名" entity="entity.entity" e="entity">
        </div>
        <div entity-edit-text="contact" type="text" title="联系方式" entity="entity.entity" e="entity">
        </div>
        <div entity-edit-text="password" type="password" title="密码" entity="entity.entity" e="entity"
             vld="entity.validate"></div>
        <div entity-edit-text="confirmPassword" type="password" title="确认密码" entity="entity.entity" e="entity"
             vld="entity.validate" confirm="yes"></div>
        <div class="form-group">
            <label class="col-sm-2 col-sm-offset-2 control-label">授予权限</label>
            <div class="col-sm-4">
                <div ng-repeat="item in moduleList">
                    <div class="checkbox">
                        <label>
                            <%--无子模块前缀样式--%>
                            <input ng-if="!item.nodes.length" type="checkbox" ng-model="item.checked">
                            <%--有子模块前缀样式--%>
                            <span ng-if="item.nodes.length" class="icon-chevron-down" style="margin-left: -20px"></span>
                            <span ng-bind="item.name"></span>
                        </label>
                    </div>
                    <%--如果有子模块--%>
                    <div class="m-l-lg checkbox" ng-if="item.nodes.length" ng-repeat="itemNode in item.nodes">
                        <label>
                            <input type="checkbox" ng-model="itemNode.checked">
                            <span ng-bind="itemNode.name"></span>
                        </label>
                    </div>
                </div>
            </div>
        </div>
    </entity-modal-body>
</div>
<div entity-modal="modal-edit" title="子账号" e="entityEdit">
    <entity-modal-body>
        <div entity-view-tag="entityEdit.entity.username" type="text" title="用户名">
        </div>
        <div entity-edit-text="name" type="text" title="姓名" entity="entityEdit.entity" e="entityEdit">
        </div>
        <div entity-edit-text="contact" type="text" title="联系方式" entity="entityEdit.entity" e="entityEdit">
        </div>
        <div class="form-group">
            <label class="col-sm-2 col-sm-offset-2 control-label">授予权限</label>
            <div class="col-sm-4">
                <div ng-repeat="item in moduleList">
                    <div class="checkbox">
                        <label>
                            <%--无子模块前缀样式--%>
                            <input ng-if="item.nodes.length === 0" type="checkbox" ng-model="item.checked">
                            <%--有子模块前缀样式--%>
                            <span ng-if="item.nodes.length > 0" class="icon-chevron-down" style="margin-left: -20px"></span>
                            <span ng-bind="item.name"></span>
                        </label>
                    </div>
                    <%--如果有子模块--%>
                    <div class="m-l-lg checkbox" ng-if="item.nodes.length" ng-repeat="itemNode in item.nodes">
                        <label>
                            <input type="checkbox" ng-model="itemNode.checked">
                            <span ng-bind="itemNode.name"></span>
                        </label>
                    </div>
                </div>
            </div>
        </div>
    </entity-modal-body>
</div>
</body>
</html>
