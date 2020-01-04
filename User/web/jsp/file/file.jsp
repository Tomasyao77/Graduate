<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>文件管理</title>
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
<body ng-app="m" ng-controller="c" ng-init='index = "系统运维"; subIndex = "文件管理"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>文件列表</h4>
            <div class="clearfix">
                <%--<label>
                    <button class="btn btn-success" ng-click="entity._openModal('add')">
                        <span class="icon-plus m-r"></span>新增&nbsp;文件
                    </button>
                </label>--%>
                    <%--仅做测试<label>
                        <button class="btn btn-success" ng-click="download()">
                            <span class="icon-plus m-r"></span>download
                        </button>
                    </label>--%>
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <label>状态&nbsp;
                        <select class="form-control" ng-model="searchStatus"
                                ng-options="x.id as x.name for x in statusList"
                                ng-change="page.refreshTo(1)">
                        </select>
                    </label>
                    <input class="form-control m-l" type="text" placeholder="id" ng-model="searchId">
                    <input class="form-control m-l" type="text" placeholder="url" ng-model="searchUrl">
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
            $scope.statusList = [{id:-1,name:"全部"},{id:0,name:"正常"},{id:1,name:"已删除"}];
            $scope.searchStatus = -1;

            $scope.load = function (current, size, orderBy, asc) {
                ajax.ajax("/fileManage/getFilePage", "POST",
                    {
                        userId: 1,
                        current: current,
                        size: size,
                        id: $scope.searchId,
                        url: $scope.searchUrl,
                        isDeleted: $scope.searchStatus,
                        orderBy: orderBy,
                        asc: asc
                    }).success(function (data) {
                        console.log(data);
                        if (data.success) {
                            $scope.page.refreshPage(data);
                        }
                })
            };
            $scope.page = page.page($scope.load, "f.create_time", false);
            $scope.ths = [
                {
                    img: function (row) {
                        return row.url;
                    },
                    clas: function () {
                        return {
                            "img-rounded": true
                        };
                    },
                    width: "1%"
                }, {
                    name: "id",
                    value: function (row) {
                        return row.id;
                    },
                    width: "3%"
                }, {
                    name: "url",
                    value: function (row) {
                        return row.url;
                    },
                    link: function (row) {
                        return row.url;
                    },
                    width: "20%"
                },{
                    name: "状态",
                    style: function (row) {
                        return {
                            "color": row.is_deleted === false ? "green" : "red"
                        }
                    },
                    value: function (row) {
                        return row.is_deleted === false ? "正常" : "已删除";
                    },
                    width: "2%"
                },{
                    name: "类型",
                    value: function (row) {
                        return row.type;
                    },
                    width: "2%"
                },{
                    name: "创建时间",
                    value: function (row) {
                        return $filter("fmtDateYMdHMcn")(row.create_time);
                    },
                    orderBy: "f.create_time",
                    width: "10%"
                }
            ];
            $scope.operations = [
                {
                    name: function () {
                        return "删除";
                    },
                    clas: function (row) {
                        return {
                            "btn btn-xs btn-danger": true
                        };
                    },
                    click: function (row) {
                        var dom = document.createElement("div");
                        var html = '<div class="row clearfix" style="height: 500px;">' +
                            '<div class="col-xs-12">' +
                            '<img src={oldsrc} class="img-rounded" width="300px" height="400px" style="margin-bottom: 20px;"/>' +
                            '</div>' +
                            '</div>';
                        var t = $(html.replace("{oldsrc}", row.url));
                        dom.innerHTML = t.html();
                        swal({
                            closeOnClickOutside: false,//禁止点击背景关闭alert
                            title: "提示!确认要删除吗?",
                            content: dom,//自定义dom节点,不能是html字符串
                            className: "sweetalert_custom",
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
                                    ajax.ajax("/fileManage/deleteFile", "POST", {
                                        userId: userId,
                                        id: row.id
                                    }).success(function (data) {
                                        console.log(data);
                                        $scope.page.refresh();
                                    }).error(function (data) {
                                        console.log(data);
                                    });
                                    break;
                            }
                        });
                    }
                }
            ];
            /**
             * 角色entity
             */
            $scope.entity = entity.getEntity(
                {username: "", password: "", confirmPassword: "", name: "", phone: ""},
                {username: {}, password: {}, confirmPassword: {}
                }, function (action, row) {//beforeOpen
                    if ($scope.entity.action === "add") {

                    } else if ($scope.entity.action === "edit") {
                        $scope.entity.entity = angular.copy(row);

                    }
                }, function () {//submit
                    if ($scope.entity.action === "add") {
                        ajax.ajax("/user/user/addOneUser", "POST", {
                            userId: 1,
                            username: $scope.entity.entity.username,
                            password: md5.createHash($scope.entity.entity.password),
                            name: $scope.entity.entity.name,
                            phone: $scope.entity.entity.phone
                        }).success(function (data) {
                            console.log(data);
                            if (data.success){
                                alertService.show("操作成功!", "success", "80%");
                                $scope.page.refresh();
                            } else {
                                swal("提示!", "新增失败!文件名已存在!", "error");
                            }
                        }).error(function (data) {
                            console.log(data);
                        });
                    } else if ($scope.entity.action === "edit") {

                    }
                    $scope.entity._success();//隐藏模态框
                }, "modal");

            $scope.$watch('$viewContentLoaded', function () {
                $scope.page.refreshTo(1);
            });
            $scope.selectChange = function () {
                console.log($scope.entity.entity.selectRole);
            };
        });
</script>
<%--模态框--%>
<div entity-modal="modal" title="账号" e="entity">
    <entity-modal-body>
        <div entity-edit-text="username" type="text" title="文件名" entity="entity.entity" e="entity"
             vld="entity.validate">
        </div>
        <div entity-edit-text="password" type="password" title="密码" entity="entity.entity" e="entity"
             vld="entity.validate"></div>
        <div entity-edit-text="confirmPassword" type="password" title="确认密码" entity="entity.entity" e="entity"
             vld="entity.validate" confirm="yes"></div>
        <div entity-edit-text="name" type="text" title="姓名" entity="entity.entity" e="entity"></div>
        <div entity-edit-text="phone" type="tel" title="手机号" entity="entity.entity" e="entity"></div>
    </entity-modal-body>
</div>
</body>
</html>
