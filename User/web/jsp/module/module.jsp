<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>模块管理</title>
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
    <%--z-tree模块树--%>
    <%--<link href="/jsp/plugin/ztree/css/zTreeStyle.css" rel="stylesheet">
    <script src="/jsp/plugin/ztree/js/jquery.ztree.all.min.js"></script>
    <script src="/jsp/module/js/ztreeModule.js"></script>--%>
    <%--angular-ui-tree--%>
    <link href="/jsp/common/css/angular-ui-tree.min.css" rel="stylesheet">
    <link href="/jsp/common/css/app.css" rel="stylesheet">
    <script src="/jsp/common/js/angular-ui-tree.min.js"></script>
</head>
<body ng-app="m" ng-controller="c" ng-init='index = "模块角色";subIndex = "模块管理"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>模块列表</h4>
            <div class="clearfix">
                <label class="pull-left">
                    <button class="btn btn-success" ng-click="entity._openModal('add')">
                        <span class="icon-plus m-r"></span>新增&nbsp;父模块
                    </button>
                    <button class="btn btn-default" ng-click="btnClick.showAll(btnClick.expandStatus)">
                        <i ng-class="btnClick.iconClass()"></i>&nbsp;
                        <span ng-show="btnClick.expandStatus">展开</span>
                        <span ng-show="!btnClick.expandStatus">折叠</span>
                    </button>
                </label>

                <form class="form-inline pull-left" style="margin-left: 20px;" ng-submit="page.refreshTo(1)">
                    <label>管理员类型&nbsp;
                        <select class="form-control" ng-model="searchType"
                                ng-options="x.id as x.name for x in twoRootRole"
                                ng-change="selectChange()"></select>
                    </label>
                    <%--<input class="form-control m-l" type="text"
                           placeholder="模块名称" ng-model="search">
                    <button class="btn btn-primary" type="submit">
                        <span class="icon-search m-r"></span> 搜索
                    </button>--%>
                </form>
            </div>
        </div>
        <!-- Nested node template -->
        <script type="text/ng-template" id="nodes_renderer.html">
            <div ui-tree-handle class="tree-node tree-node-content">
                <a class="btn btn-info btn-xs" ng-if="node.nodes && node.nodes.length > 0" data-nodrag
                   ng-click="toggle(this)">
                    <span class="glyphicon"
                          ng-class="{'glyphicon-chevron-right': collapsed,'glyphicon-chevron-down': !collapsed}"></span>
                </a>
                {{node.name}}
                <a class="pull-right btn btn-danger btn-xs" data-nodrag ng-click="btnClick.remove(this)">
                    <span class="glyphicon glyphicon-remove"></span></a>
                <a class="pull-right btn btn-primary btn-xs" data-nodrag ng-click="btnClick.edit(this, $event)">
                    <span class="glyphicon glyphicon-edit"></span></a>
                <a class="pull-right btn btn-success btn-xs" ng-if="node.level === 0"
                   data-nodrag ng-click="btnClick.newSubItem(this)"
                   style="margin-right: 8px;"><span class="glyphicon glyphicon-plus"></span></a>
                <a class="pull-right btn btn-default btn-xs" data-nodrag ng-click="btnClick.eyeToggle(this)"
                   style="margin-right: 8px;">
                    <i ng-show="node.status === true" class="icon-eye-open text-danger icon-large"></i>
                    <i ng-show="node.status === false" class="icon-eye-close text-muted icon-large"></i>
                </a>
            </div>
            <ol ui-tree-nodes="" ng-model="node.nodes" ng-class="{hidden: collapsed}">
                <li ng-repeat="node in node.nodes" ui-tree-node ng-include="'nodes_renderer.html'"></li>
            </ol>
        </script>

        <div class="row">
            <div class="col-sm-6" style="max-height: 650px;overflow-y: auto;">
                <div ui-tree="treeOptions" id="tree-root">
                    <ol ui-tree-nodes ng-model="moduleList">
                        <li ng-repeat="node in moduleList" ui-tree-node ng-include="'nodes_renderer.html'"></li>
                    </ol>
                </div>
            </div>
            <div class="col-sm-5">
                <div class="form-horizontal" ng-show="editNode!=null && rightShow" style="padding-top: 20px;">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">名称</label>
                        <div class="col-sm-10"><input type="text" ng-class="{'input-error':inputError(editNode.name)}"
                                                      class="form-control" ng-model="editNode.name"/></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">url</label>
                        <div class="col-sm-10"><input type="text" ng-class="{'input-error':inputError(editNode.url)}"
                                                      class="form-control" ng-model="editNode.url"/></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">icon</label>
                        <div class="col-sm-10"><input type="text" ng-class="{'input-error':inputError(editNode.icon)}"
                                                      class="form-control" ng-model="editNode.icon"/></div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10 col-sm-offset-2">
                            <button class="btn btn-success" ng-click="updateOneModule()">提交</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    angular.module("m", ["nm", "ui.tree"])
        .controller("c", function ($scope, page, ajax, entity, alertService) {
            /**管理员类型*/
            $scope.twoRootRole = [
                {id: 0, name: "内容管理员"},
                {id: 1, name: "商户管理员"}];
            $scope.searchType = 0;
            $scope.selectChange = function () {
                $scope.rightShow = false;
                $scope.btnClick.expandStatus = true;
                $scope.getModuleList();
            };

            $scope.rightShow = false;
            /**
             * 获取模块列表树
             */
            $scope.getModuleList = function () {
                //ajax.ajax("/user/admin/getModuleList", "POST", {
                ajax.ajax("/user/admin/getModuleListMybatis", "POST", {
                    userId: userId,
                    type: $scope.searchType
                }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.moduleOriginList = angular.copy(data.list);
                        $scope.moduleList = data.list;
                    }
                });
            };
            $scope.entity = entity.getEntity(
                {name: "", parentId: "", url: "", icon: ""},
                {
                    name: {}, url: {}, icon: {}
                }, function (action, row) {//beforeOpen
                    if ($scope.entity.action === "add") {
                        $scope.entity.pEntity = row;
                    } else if ($scope.entity.action === "addSub") {
                        $scope.entity.pEntity = row;
                    }
                }, function () {//submit
                    if ($scope.entity.action === "add") {
                        ajax.ajax("/user/admin/addOneModule", "POST", {
                            userId: 1,
                            name: $scope.entity.entity.name,
                            parentId: 0,
                            url: $scope.entity.entity.url,
                            icon: $scope.entity.entity.icon,
                            type: $scope.searchType
                        }).success(function (data) {
                            if (data.success) {
                                $scope.getModuleList();
                                alertService.show("操作成功!", "success", "80%");
                            } else {
                                swal("提示!", "新增失败!名称已存在", "error");
                            }

                        }).error(function (data) {
                            console.log(data);
                        });
                    } else if ($scope.entity.action === "addSub") {
                        ajax.ajax("/user/admin/addOneModule", "POST", {
                            userId: 1,
                            name: $scope.entity.entity.name,
                            parentId: $scope.entity.pEntity.id,
                            url: $scope.entity.entity.url,
                            icon: $scope.entity.entity.icon,
                            type: $scope.searchType
                        }).success(function (data) {
                            if (data.success) {
                                $scope.getModuleList();
                                alertService.show("操作成功!", "success", "80%");
                            } else {
                                swal("提示!", "新增失败!名称已存在", "error");
                            }
                        }).error(function (data) {
                            console.log(data);
                        });
                    }
                    $scope.entity._success();//隐藏模态框
                }, "modal");

            /**
             * 各种点击事件
             */
            $scope.btnClick = {
                showAll: function (es) {
                    var s = es === true ? "collapse" : "expand";
                    $scope.$broadcast('angular-ui-tree:' + s + '-all');
                    this.expandStatus = !this.expandStatus;
                },
                expandStatus: true,
                iconClass: function () {
                    return {"icon-chevron-down": this.expandStatus, "icon-chevron-up": !this.expandStatus};
                },
                newSubItem: function (scope) {
                    var nodeData = scope.$modelValue;
                    $scope.entity._openModal('addSub', nodeData);
                },
                edit: function (scope, $event) {
                    var nodeData = scope.$modelValue;
                    $scope.editNode = angular.copy(nodeData);
                    $scope.rightShow = true;
                    //var $parent = $($event.target).parent().parent();
                },
                eyeToggle: function (scope) {
                    var nodeData = scope.$modelValue;
                    ajax.ajax("/user/admin/updateModuleStatus", "POST", {
                        userId: userId,
                        id: nodeData.id,
                        status: !nodeData.status
                    }).success(function (data) {
                        if (data.success) {
                            $scope.getModuleList();
                            alertService.show("操作成功!", "success", "80%");
                        } else {
                            swal("提示!", "操作失败!", "error");
                        }
                    }).error(function (data) {
                        console.log(data);
                    });
                },
                remove: function (scope) {
                    //scope.remove();
                    var nodeData = scope.$modelValue;
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
                                ajax.ajax("/user/admin/deleteOneModule", "POST", {
                                    userId: 1,
                                    id: nodeData.id
                                }).success(function (data) {
                                    if (data.success) {
                                        $scope.getModuleList();
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
            };
            /**
             * 编辑信息
             */
            $scope.inputError = function (v) {
                return v === null || v === '' || v === undefined;
            };
            $scope.updateOneModule = function () {
                ajax.ajax("/user/admin/updateOneModule", "POST", {
                    userId: 1,
                    id: $scope.editNode.id,
                    name: $scope.editNode.name,
                    url: $scope.editNode.url,
                    icon: $scope.editNode.icon,
                    type: $scope.searchType
                }).success(function (data) {
                    if (data.success) {
                        $scope.getModuleList();
                        alertService.show("操作成功!", "success", "80%");
                    } else {
                        swal("提示!", "编辑失败", "error");
                    }
                }).error(function (data) {
                    console.log(data);
                });
            };
            /**
             * 改变索引
             */
            $scope.ifOrigin = function (s) {
                var originList = [], destList = [];
                for (var i in $scope.moduleOriginList) {
                    originList.push($scope.moduleOriginList[i].id);
                }
                for (var j in s) {
                    destList.push(s[j].id);
                }
                for (var z in originList) {
                    if (originList[z] !== destList[z]) {
                        return false;
                    }
                }
                return true;
            };
            $scope.ifOriginSub = function (t) {
                var originList = [], destList = [];
                for (var i in $scope.moduleOriginList) {
                    if ($scope.moduleOriginList[i].id === t[0].parentId) {
                        for (var j in $scope.moduleOriginList[i].nodes) {
                            originList.push($scope.moduleOriginList[i].nodes[j].id);
                        }
                        break;
                    }
                }
                for (var k in t) {
                    destList.push(t[k].id);
                }
                for (var z in originList) {
                    if (originList[z] !== destList[z]) {
                        return false;
                    }
                }
                return true;
            };
            /**
             * 模块树回调方法
             */
            $scope.treeOptions = {
                beforeDrop: function (e) {
                    var sourceValue = e.source.nodeScope.$modelValue.level,
                        destValue = e.dest.nodesScope.node ? e.dest.nodesScope.node.level : undefined;
                    if (sourceValue <= destValue) {
                        return false;
                    }
                    if (sourceValue == 1 && destValue == undefined) {
                        return false;
                    }
                },
                dropped: function (e) {
                    console.log(e);
                    var soruce = e.source,
                        dest = e.dest,
                        sourceValue = soruce.nodeScope.$modelValue,
                        destValue = dest.nodesScope.node ? dest.nodesScope.node : undefined;
                    /*console.log(dest.nodesScope);
                     console.log(dest.nodesScope.node);*/
                    if (sourceValue.level === 1 && destValue.level === 0 && sourceValue.parentId === destValue.id) {//同父移位
                        if ($scope.ifOriginSub(e.source.nodesScope.$modelValue)) {
                            return;
                        }//是否原地踏步
                        (function () {
                            var pId = destValue.nodes[0].parentId,
                                destNodesArr = [];
                            for (var i in destValue.nodes) {
                                destNodesArr.push(destValue.nodes[i].id);
                            }
                            ajax.ajax("/user/admin/updateModuleIndex", "POST", {
                                userId: 1,
                                dId: pId,
                                dIdSubIds: destNodesArr,
                                type: 0
                            }).success(function (data) {
                                console.log(data);
                                $scope.getModuleList();
                            }).error(function (data) {
                                console.log(data);
                            });
                        })();
                    } else if (sourceValue.level === 1 && destValue.level === 0 && sourceValue.parentId !== destValue.id) {//异父移位
                        (function () {
                            var destPId = dest.nodesScope.$nodeScope.$modelValue.id,
                                destNodesArr = [],
                                sourcePId = sourceValue.parentId,
                                sourceNodesArr = [];
                            for (var i in destValue.nodes) {
                                destNodesArr.push(destValue.nodes[i].id);
                            }
                            for (var j in soruce.nodesScope.$modelValue) {
                                sourceNodesArr.push(soruce.nodesScope.$modelValue[j].id);
                            }
                            console.log(sourcePId);
                            console.log(soruce.nodesScope.$modelValue);
                            ajax.ajax("/user/admin/updateModuleIndex", "POST", {
                                userId: 1,
                                sId: sourcePId,
                                sIdSubIds: sourceNodesArr,
                                dId: destPId,
                                dIdSubIds: destNodesArr,
                                type: 1
                            }).success(function (data) {
                                console.log(data);
                                $scope.getModuleList();
                            }).error(function (data) {
                                console.log(data);
                            });
                        })();
                    } else if (sourceValue.level === 0 && destValue === undefined) {//父父移位
                        if ($scope.ifOrigin(e.source.nodesScope.$modelValue)) {
                            return;
                        }//是否原地踏步
                        (function () {
                            var pIdArr = [];
                            for (var i in dest.nodesScope.$modelValue) {
                                pIdArr.push(dest.nodesScope.$modelValue[i].id);
                            }
                            ajax.ajax("/user/admin/updateModuleIndex", "POST", {
                                userId: 1,
                                dIdSubIds: pIdArr,//这里跟上面2种含义不同
                                type: 2
                            }).success(function (data) {
                                console.log(data);
                                $scope.getModuleList();
                            }).error(function (data) {
                                console.log(data);
                            });
                        })();
                    }
                }
            };
            $scope.$watch('$viewContentLoaded', function () {
                $scope.getModuleList();
            });
        });
</script>
<%--模态框--%>
<div entity-modal="modal" title="模块" e="entity">
    <entity-modal-body>
        <div entity-edit-text="name" type="text" title="名称" entity="entity.entity" e="entity"
             vld="entity.validate"></div>
        <div entity-edit-text="url" type="text" title="url" entity="entity.entity" e="entity"
             vld="entity.validate"></div>
        <div entity-edit-text="icon" type="text" title="icon" entity="entity.entity" e="entity"
             vld="entity.validate"></div>
        <div class="form-group">
            <label class="col-sm-2 col-sm-offset-2 control-label">类别</label>
            <div class="col-sm-4">
                <select class="form-control" ng-model="searchType"
                        ng-options="x.id as x.name for x in twoRootRole"
                        ng-change="selectChange()" disabled>
                </select>
            </div>
        </div>
    </entity-modal-body>
</div>
</body>
</html>
