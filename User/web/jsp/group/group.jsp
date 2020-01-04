<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>分组管理</title>
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
<body ng-app="m" ng-controller="c" ng-init='index = "分组管理"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>商户</h4>
            <div class="clearfix">
                <%--<label>
                    <button class="btn btn-success" ng-click="entity._openModal('add')">
                        <span class="icon-plus m-r"></span>新增&nbsp;账号
                    </button>
                </label>--%>
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <%--<label>管理员类型&nbsp;
                        <select class="form-control" ng-model="searchType"
                                ng-options="x.id as x.name for x in twoRootRole"
                                ng-change="selectChange()"></select>
                    </label>--%>
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
        .controller("c", function ($scope, page, ajax, entity, $filter) {
            /**管理员类型*/
            /*$scope.twoRootRole = [
                {id: 0, name: "内容管理员"},
                {id: 1, name: "商户管理员"}];
            $scope.searchType = 0;
            $scope.selectChange = function () {
                $scope.page.refreshTo(1);
                $scope.getRoleList();
            };*/
            //获取角色list
            $scope.getRoleList = function (callback) {
                ajax.ajax("/user/admin/getRoleList", "POST", {
                    userId: 1,
                    type: 1
                }).success(function (data) {
                    $scope.roleList = data.list;
                    $scope.adminRoleList = angular.copy(data.list);
                    $scope.roleList.unshift({id: 0, name: "全部"});//往array首部插入一个元素，其它顺移
                    $scope.searchRole = $scope.roleList[0].id;
                    for(var t in $scope.roleList){
                        if($scope.roleList[t].name === "商家"){
                            $scope.searchRole =  $scope.roleList[t].id;
                            break;
                        }
                    }
                    callback();
                }).error(function (data) {
                    console.log(data);
                });
            };
            $scope.load = function (current, size, orderBy, asc) {
                ajax.ajax("/user/admin/getAdminPageList", "POST",
                    {
                        userId: 1,
                        current: current,
                        size: size,
                        username: $scope.search,
                        roleId: $scope.searchRole,
                        parentId: 0,
                        orderBy: orderBy,
                        asc: asc,
                        type: 1
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
                    name: "角色",
                    value: function (row) {
                        return row.role.name;
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
                    width: "10%"
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
                        return "机构划入";
                    },
                    clas: function () {
                        return {
                            "btn btn-xs btn-default": true
                        };
                    },
                    click: function (row) {
                        $scope.entity._openModal("institution", row);
                    }
                }
            ];
            /**
             * 角色entity
             */
            $scope.entity = entity.getEntity(
                {}, {},
                function (action, row) {//beforeOpen
                    if ($scope.entity.action === "institution") {
                        $scope.entity.entity.parlors = {};//防止上一次打开的视觉残留
                        $scope.admin = row;
                        ajax.ajax("/user/institution/getAdminInstitutionList", "POST",
                            {
                                userId: userId,
                                adminId: row.id
                            }).success(function (data) {
                                console.log(data);
                                if (data.success) {
                                    $scope.entity.entity.parlors = data.list;
                                }
                        });
                    }
                }, function () {//submit
                }, "modal");

            $scope.parlor = {
                page: {},
                service: function (page) {
                    this.page = page;
                },
                load: function (current, size, name, callback) {
                    ajax.ajax("/user/institution/getInstitutionPageList", "POST",
                        {
                            userId: userId,
                            current: current,
                            size: size,
                            level: 0,
                            areaCode: 100000,
                            name: name
                        }).success(function (data) {
                        if (data.success) {
                            callback(data);
                        }
                    });
                },
                minus: function (index) {
                    var entity = $scope.entity.entity;
                    ajax.ajax("/user/institution/deleteAdminInstitution", "POST",
                        {
                            userId: userId,
                            id: entity.parlors[index].id
                        }).success(function (data) {
                        if (data.success) {
                            entity.parlors.splice(index, 1);
                        }
                    });
                },
                plus: function (parlor) {
                    var entity = $scope.entity.entity;
                    for (var i in entity.parlors) {
                        if (parlor.id == entity.parlors[i].parlorId) {
                            return;
                        }
                    }
                    ajax.ajax("/user/institution/addAdminInstitution", "POST",
                        {
                            userId: userId,
                            adminId: $scope.admin.id,
                            institutionId: parlor.id
                        }).success(function (data) {
                        if (data.success) {
                            if (data.value == null) {
                                swal("提示!", "只能添加一家机构!", "warning");
                            } else {
                                entity.parlors.push(data.value);
                            }
                        }
                    });
                }
            };

            $scope.$watch('$viewContentLoaded', function () {
                $scope.getRoleList(function () {
                    $scope.page.refreshTo(1);
                });
            });
        });
</script>
<%--模态框--%>
<div entity-modal="modal" title="机构划入" e="entity">
    <entity-modal-body>
        <div class="clearfix">
            <script type="text/ng-template" id="parlor_template.html">
                <div class="clearfix">
                    <img class="pull-left img-responsive img-circle" style="width: 5em;height: 5em"
                         ng-src="{{row.pictureThumb}}">

                    <div class="m-y" style="margin-left: 7em">
                        <p ng-bind="row.name"></p>

                        <p ng-bind="row.areaMergerName | mergerArea"></p>
                    </div>
                </div>
            </script>
            <div class="col-xs-6">
                <div class="row" list="entity.entity.parlors" template-id="parlor_template.html"
                     title="已选择机构列表"
                     close="parlor.minus($index)" height="500px">
                </div>
            </div>
            <div class="col-xs-6">
                <div class="row" list-page="entity.entity.parlors" template-id="parlor_template.html"
                     select="parlor.plus($row)"
                     height="500px"
                     placeholder="名称" load="parlor.load($current, $size, $name, $callback)">
                </div>
            </div>
        </div>
    </entity-modal-body>
</div>
</body>
</html>
