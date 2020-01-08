<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>用户管理</title>
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
<body ng-app="m" ng-controller="c" ng-init='index = "用户管理"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>用户列表</h4>
            <div class="clearfix">
                <%--<label>
                    <button class="btn btn-success" ng-click="entity._openModal('add')">
                        <span class="icon-plus m-r"></span>新增&nbsp;用户
                    </button>
                </label>--%>
                <%--仅做测试<label>
                    <button class="btn btn-success" ng-click="download()">
                        <span class="icon-plus m-r"></span>download
                    </button>
                </label>--%>
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <%--<label>角色类型&nbsp;
                        <select class="form-control" ng-model="searchRole"
                                ng-options="x.id as x.name for x in roleList"
                                ng-change="page.refreshTo(1)">
                        </select>
                    </label>--%>
                    <input class="form-control m-l" type="text" placeholder="姓名" ng-model="search">
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
            $scope.download = function () {//应用普通form提交
                var protocolStr = document.location.protocol.replace(":", ""),
                    baseUrl = "";
                if(protocolStr === "http") {
                    baseUrl = "/trade"
                } else if(protocolStr === "https") {
                    baseUrl = "https://kdtrade.d9lab.net";
                }
                var postUrl = baseUrl + "/product/downloadProducts";//提交地址
                var data1 = 1;//第一个数据
                /*var data2 = $scope.deviceSn;//第二个数据
                var data3 = $scope.appId;//第三个数据*/
                var ExportForm = document.createElement("FORM");
                document.body.appendChild(ExportForm);
                ExportForm.method = "POST";
                //生成input临时元素
                var newElement = document.createElement("input");
                newElement.setAttribute("name", "userId");
                newElement.setAttribute("type", "hidden");
                /*var newElement2 = document.createElement("input");
                newElement2.setAttribute("name", "deviceSn");
                newElement2.setAttribute("type", "hidden");
                var newElement3 = document.createElement("input");
                newElement3.setAttribute("name", "appId");
                newElement3.setAttribute("type", "hidden");*/
                //添加到表单并设置value
                ExportForm.appendChild(newElement);
                /*ExportForm.appendChild(newElement2);
                ExportForm.appendChild(newElement3);*/
                newElement.value = data1;
                /*newElement2.value = data2;
                newElement3.value = data3;*/
                ExportForm.action = postUrl;
                ExportForm.submit();
            };
            //获取角色list
            /*$scope.getRoleList = function (callback) {
                ajax.ajax("/user/admin/getRoleList", "POST", {
                    userId: 1
                }).success(function (data) {
                    $scope.roleList = data.list;
                    $scope.adminRoleList = angular.copy(data.list);
                    $scope.roleList.unshift({id: 0, name: "全部"});//往array首部插入一个元素，其它顺移
                    $scope.searchRole = $scope.roleList[0].id;
                    callback();
                }).error(function (data) {
                    console.log(data);
                });
            };*/
            $scope.load = function (current, size, orderBy, asc) {
//                ajax.ajax("/user/redirect/trade/user/getUserTradeList", "POST",
//                    {
//                        userId: 1,
//                        current: current,
//                        size: size,
//                        search: $scope.search,
//                        orderBy: orderBy,
//                        asc: asc
//                    }).success(function (data) {
//                    console.log(data);
//                    if (data.success) {
//                        $scope.page.refreshPage(data);
//                    }
//                })
            };
            $scope.searchArea = {
                code: 100000,
                level: 0,
                change: function (area) {
                    this.code = area.code;
                    this.level = area.level;
                    $scope.page.refreshTo(1);
                }
            };
            $scope.page = page.page($scope.load, "createTime", true);
            $scope.ths = [
                {width: "1%"},
                {
                    name: "用户名",
                    value: function (row) {
                        var t =  row.user.username;
                        var username = t.substring(0,6)+"***"+t.substring(t.length-6);
                        return username;
                    },
                    width: "10%"
                }, {
                    name: "姓名",
                    value: function (row) {
                        return row.user.name;
                    },
                    width: "5%"
                }, {
                    name: "手机号",
                    value: function (row) {
                        return row.user.phone;
                    },
                    width: "10%"
                },{
                    name: "交易总金额",
                    value: function (row) {
                        return row.totalTrade;
                    },
                    width: "10%"
                },{
                    name: "体验总金额",
                    value: function (row) {
                        return row.totalExperience;
                    },
                    width: "10%"
                },{
                    name: "消费总金额",
                    value: function (row) {
                        return row.total;
                    },
                    width: "10%"
                }, {
                    name: "状态",
                    style: function (row) {
                        return {
                            "color": row.user.status === true ? "green" : "red"
                        }
                    },
                    value: function (row) {
                        return row.user.status === true ? "正常" : "被禁";
                    },
                    width: "5%"
                },{
                    name: "微信Id",
                    value: function (row) {
                        return row.user.openId && row.user.openId.substring(0, 10);
                    },
                    width: "8%"
                },{
                    name: "创建时间",
                    value: function (row) {
                        return $filter("fmtDateYMdHMcn")(row.user.createTime);
                    },
                    orderBy: "createTime",
                    width: "15%"
                }
            ];
            $scope.operations = [
                {
                    name: function () {
                        return "消费记录";
                    },
                    clas: function (row) {
                        return {
                            "btn btn-xs btn-default": true,
                            "hide": row.total ==0
                        };
                    },
                    click: function (row) {
                        window.open("/jsp/user/userTradeHistory.jsp?userId=" + row.user.id);
                    }
                }
                /*{
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
                        /!*ajax.ajax("/user/admin/deleteOneUser", "POST", {
                            userId: 1,
                            id: row.id
                        }).success(function (data) {
                            console.log(data);
                            $scope.page.refresh();
                        }).error(function (data) {
                            console.log(data);
                        });*!/
                    }
                }*/
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
                                swal("提示!", "新增失败!用户名已存在!", "error");
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
        <div entity-edit-text="username" type="text" title="用户名" entity="entity.entity" e="entity"
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
