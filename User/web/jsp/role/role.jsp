<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>角色管理</title>
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
<body ng-app="m" ng-controller="c" ng-init='index = "模块角色";subIndex = "角色管理"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>角色列表</h4>
            <div class="clearfix">
                <label>
                    <button class="btn btn-success" ng-click="entity._openModal('add')">
                        <span class="icon-plus m-r"></span>新增&nbsp;角色
                    </button>
                </label>
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <label>管理员类型&nbsp;
                        <select class="form-control" ng-model="searchType"
                                ng-options="x.id as x.name for x in twoRootRole"
                                ng-change="selectChange()"></select>
                    </label>
                    <input class="form-control m-l" type="text"
                           placeholder="模块名称" ng-model="search">
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
            /**管理员类型*/
            $scope.twoRootRole = [
                {id: 0, name: "内容管理员"},
                {id: 1, name: "商户管理员"}];
            $scope.searchType = 0;
            $scope.selectChange = function () {
                $scope.page.refreshTo(1);
                $scope.getModuleList();
            };
            /**
             * 获取模块列表树
             */
            $scope.getModuleList = function () {
                ajax.ajax("/user/admin/getModuleList", "POST", {
                    userId: userId,
                    type: $scope.searchType
                }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.moduleOriginList = data.list;
                    }
                });
            };
            $scope.load = function (current, size, orderBy, asc) {
                ajax.ajax("/user/admin/getRolePageList", "POST",
                    {
                        userId: userId,
                        current: current,
                        size: size,
                        name: $scope.search,
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
                    name: "角色名称",
                    value: function (row) {
                        return row.name;
                    },
                    width: "15%"
                }, {
                    name: "描述",
                    value: function (row) {
                        return row.description;
                    },
                    width: "20%"
                }, {
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
                    clas: function (row) {
                        return {
                            "btn btn-xs btn-primary": true
                        };
                    },
                    click: function (row) {
                        $scope.entity._openModal("edit", row);
                    }
                }, {
                    name: function () {
                        return "删除";
                    },
                    clas: function () {
                        return {
                            "btn btn-xs btn-danger": true,
                            "hide": true
                        };
                    },
                    click: function (row) {
                        swal("确认要删除吗?请谨慎操作", {
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
                                    /*ajax.ajax("/user/admin/deleteOneRole", "POST", {
                                        userId: 1,
                                        id: row.id
                                    }).success(function (data) {
                                        if (data.success) {
                                            $scope.page.refresh();
                                            alertService.show("操作成功!", "success", "80%");
                                        } else {
                                            swal("提示!", "操作失败!", "error");
                                        }
                                    }).error(function (data) {
                                        console.log(data);
                                    });*/
                                    break;
                                default:
                                //swal("提示!", "操作失败!", "error");
                            }
                        });
                    }
                }
            ];
            /**
             * 角色entity
             */
            $scope.entity = entity.getEntity(
                {name: "", description: ""},
                {
                    name: {}, description: {}
                }, function (action, row) {//beforeOpen
                    if ($scope.entity.action === "add") {
                        $scope.moduleList = angular.copy($scope.moduleOriginList);
                    } else if ($scope.entity.action === "edit") {
                        $scope.moduleList = angular.copy($scope.moduleOriginList);
                        $scope.entity.entity = angular.copy(row);
                        //获取该角色的模块树(list即可)
                        ajax.ajax("/user/permission/getRoleModuleList", "POST", {
                            userId: 1,
                            roleId: row.id
                        }).success(function (data) {
                            console.log(data);
                            if (data.success) {//勾选相应复选框
                                var adminModules = [];
                                var adminModulesBtnAuth = [];
                                for (var t in data.list) {
                                    adminModules.push(data.list[t].moduleId);
                                    adminModulesBtnAuth.push(data.list[t].btnAuth);
                                }
                                //遍历模块树//检查是否勾选模块复选框以及增删改禁用复选框
                                for (var i in $scope.moduleList) {
                                    var module = $scope.moduleList[i];//item
                                    if (module.nodes.length > 0) {//有子模块
                                        for (var j in module.nodes) {
                                            module.nodes[j].btnAuthArr = [];
                                            var index1 = adminModules.indexOf(module.nodes[j].id);
                                            if (index1 >= 0) {
                                                module.nodes[j].checked = true;
                                                for(var k=0; k<4; k++){
                                                    module.nodes[j].btnAuthArr[k] = adminModulesBtnAuth[index1].indexOf(""+k) >= 0;
                                                }
                                            }//如果没有这个模块，后面的0123都为false
                                        }
                                    } else {//无子模块
                                        module.btnAuthArr = [];
                                        var index2 = adminModules.indexOf(module.id);
                                        if (index2 >= 0) {
                                            module.checked = true;
                                            for(var l=0; l<4; l++){
                                                module.btnAuthArr[l] = adminModulesBtnAuth[index2].indexOf(""+l) >= 0;
                                            }
                                        }//如果没有这个模块，后面的0123都为false
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
                    if (modules.length <= 0) {
                        swal("提示!", "请授予权限!", "warning");
                        return;
                    }
                    //console.log(modules);return;
                    if ($scope.entity.action === "add") {
                        ajax.ajax("/user/admin/addOneRole", "POST", {
                            userId: 1,
                            name: $scope.entity.entity.name,
                            description: $scope.entity.entity.description,
                            moduleIds: modules,
                            type: $scope.searchType
                        }).success(function (data) {
                            console.log(data);
                            if (data.success) {
                                $scope.page.refresh();
                                alertService.show("操作成功!", "success", "80%");
                            } else {
                                swal("提示!", "新增失败!名称已存在", "error");
                            }

                        }).error(function (data) {
                            console.log(data);
                        });
                    } else if ($scope.entity.action === "edit") {
                        ajax.ajax("/user/permission/updateRoleModule", "POST", {
                            userId: 1,
                            roleId: $scope.entity.entity.id,
                            name: $scope.entity.entity.name,
                            description: $scope.entity.entity.description,
                            roleModuleIds: modules
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
                $scope.getModuleList();
            });
        });
</script>
<%--模态框--%>
<div entity-modal="modal" title="角色" e="entity">
    <entity-modal-body>
        <div entity-edit-text="name" type="text" title="名称" entity="entity.entity" e="entity"
             vld="entity.validate"></div>
        <div entity-edit-textarea="description" title="描述" entity="entity.entity" e="entity"
             vld="entity.validate"></div>
        <div class="form-group">
            <label class="col-sm-2 col-sm-offset-2 control-label">授予权限</label>
            <div class="col-sm-6">
                <div ng-repeat="item in moduleList">
                    <div ng-if="item.nodes.length === 0">
                        <%--无子模块前缀样式--%>
                        <label style="cursor: pointer;user-select: none;" class="text-primary">
                            <span ng-if="item.nodes.length === 0">
                                <input type="checkbox" ng-model="item.checked">
                                <span ng-bind="item.name"></span>&nbsp;|&nbsp;
                            </span>
                        </label>
                        <label class="text-muted">
                            <label style="cursor: pointer;user-select: none;"><span><input type="checkbox" ng-model="item.btnAuthArr[0]"> 增</span></label>
                            <label style="cursor: pointer;user-select: none;"><span><input type="checkbox" ng-model="item.btnAuthArr[1]"> 删</span></label>
                            <label style="cursor: pointer;user-select: none;"><span><input type="checkbox" ng-model="item.btnAuthArr[2]"> 改</span></label>
                            <label style="cursor: pointer;user-select: none;"><span><input type="checkbox" ng-model="item.btnAuthArr[3]"> 禁用</span></label>
                        </label>
                    </div>
                    <div ng-if="item.nodes.length > 0">
                        <%--有子模块前缀样式--%>
                        <label style="cursor: pointer;user-select: none;" class="text-primary">
                            <span  class="icon-chevron-down"
                                   style="/*margin-left: -20px*/"></span>
                            <span ng-bind="item.name"></span>
                        </label>
                    </div>
                    <%--如果有子模块--%>
                    <div class="m-l-lg" ng-if="item.nodes.length" ng-repeat="itemNode in item.nodes">
                        <label style="cursor: pointer;user-select: none;" class="text-primary">
                            <input type="checkbox" ng-model="itemNode.checked">
                            <span ng-bind="itemNode.name"></span>
                        </label>
                        <label class="text-muted">
                            <label style="cursor: pointer;user-select: none;"><span><input type="checkbox" ng-model="itemNode.btnAuthArr[0]"> 增</span></label>
                            <label style="cursor: pointer;user-select: none;"><span><input type="checkbox" ng-model="itemNode.btnAuthArr[1]"> 删</span></label>
                            <label style="cursor: pointer;user-select: none;"><span><input type="checkbox" ng-model="itemNode.btnAuthArr[2]"> 改</span></label>
                            <label style="cursor: pointer;user-select: none;"><span><input type="checkbox" ng-model="itemNode.btnAuthArr[3]"> 禁用</span></label>
                        </label>
                    </div>
                </div>
            </div>
        </div>
    </entity-modal-body>
</div>
</body>
</html>
