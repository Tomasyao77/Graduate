<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>资讯管理</title>
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
    <link href="/jsp/common/css/wangEditor.min.css" rel="stylesheet">
    <script src="/jsp/common/js/wangEditor.min.js"></script>
    <script src="/jsp/common/js/module/editorModule.js"></script>
    <%--自定义angular module--%>
    <script src="/jsp/common/template/baseModule.js"></script>
    <%--日期处理--%>
    <link href="/jsp/common/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="/jsp/common/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/jsp/common/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="/jsp/common/js/moment.min.js"></script>

    <style>
        /*滚动条*/
        /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
        ::-webkit-scrollbar
        {
            width: 8px;
            height: 8px;
            background-color: #F5F5F5;
        }
        /*定义滚动条轨道 内阴影+圆角*/
        ::-webkit-scrollbar-track
        {
            -webkit-box-shadow: inset 0 0 6px rgb(201, 201, 201);
            border-radius: 10px;
            background-color: #F5F5F5;
        }
        /*定义滑块 内阴影+圆角*/
        ::-webkit-scrollbar-thumb
        {
            border-radius: 10px;
            -webkit-box-shadow: inset 0 0 6px rgb(64, 75, 86);
            background-color: #c9c9c9;
        }
    </style>
</head>
<body ng-app="m" ng-controller="c" ng-init='index = "资讯管理"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>资讯列表</h4>
            <div class="clearfix">
                <label>
                    <button class="btn btn-success" ng-click="entity._openModal('add')">
                        <span class="icon-plus m-r"></span>新增&nbsp;资讯
                    </button>
                </label>
                <%--<label>--%>
                    <%--<button class="btn btn-success" ng-click="entityAdd._openModal('add')">--%>
                        <%--<span class="icon-plus m-r"></span>新增&nbsp;顶部广告--%>
                    <%--</button>--%>
                <%--</label>--%>
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <label>模块&nbsp;
                        <select class="form-control" ng-model="newsItem"
                                ng-options="n.value as n.name for n in newsItems"
                                ng-change="page.refreshTo(1)">
                            <option value="">全部</option>
                        </select>
                    </label>
                    <label>状态&nbsp;
                        <select class="form-control" ng-model="statusType"
                                ng-options="s.value as s.name for s in statusTypes"
                                ng-change="page.refreshTo(1)">
                            <option value="">全部</option>
                        </select>
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
    angular.module("m", ["nm",'editorModule'])
        .controller("c", function ($scope, page, ajax, entity, $filter, Upload, alertService) {
            //$scope.newsItem = null;
            $scope.newsItems = [
                {
                    name: "名家谈",
                    value: "0"
                }, {
                    name: "店家秀",
                    value: "1"
                }, {
                    name: "用户说",
                    value: "2"
                },{
                    name: "开店教程",
                    value: "3"
                }
            ];
            $scope.statusType = null;
            $scope.statusTypes = [
                {
                    name: "发布",
                    value: "0"
                }, {
                    name: "下线",
                    value: "1"
                }
            ];
            $scope.load = function (current, size, orderBy, asc) {
                //ajax.ajax("/user/redirect/trade/server/news/getNewsPageList", "POST",
                ajax.ajax("/news/getLocalNewsPageList", "POST",
                    {
                        current: current,
                        size: size,
                        search: $scope.search,
                        newsItem: $scope.newsItem,
                        status: $scope.statusType,
                        orderBy: orderBy,
                        asc: asc
                    }).success(function (data) {
                    console.log(data);
                    console.log($scope.newsItems[0].name);
                    if (data.success) {
                        $scope.page.refreshPage(data);
                    }
                })
            };
            $scope.page = page.page($scope.load,"createTime",false);
            $scope.ths = [
                {width: "1%"},
                {
                    name: "模块",
                    value: function (row) {
                        return row.newsItem;
                    },
                    width: "20%"
                }, {
                    name: "标题",
                    value: function (row) {
                        return row.title;
                    },
                    width: "30%"
                }, {
                    name: "摘要",
                    value: function (row) {
                        return row.abstracts;
                    },
                    width: "50%"
                }, {
                    name: "状态",
                    value: function (row) {
                        if (row.status == "启用") {
                            return "已发布";
                        }else return"未发布";
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
                    clas: function () {
                        return {
                            "btn btn-xs btn-primary": true
                        };
                    },
                    click: function (row) {
                        $scope.entityEdit._openModal("edit", row);
                    }
                }, {
                    name: function () {
                        return "发布";
                    },
                    clas: function (row) {
                        return {
                            "btn btn-xs btn-success": true,
                             "hide": row.status === "启用"
                        };
                    },
                    click: function (row) {
                        ajax.ajax("/news/updateNewsStatus", "POST", {
                            userId: 1,
                            newsId: row.id,
                            status: 1
                        }).success(function (data) {
                            if (data.success) {
                                $scope.page.refresh();
                                alertService.show("操作成功!", "success", "80%");
                            } else {
                                swal("提示!", "操作失败!权限不够!", "error");
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
                            "btn btn-xs btn-warning": true,
                            "hide": row.status === "禁用"
                        };
                    },
                    click: function (row) {
                        ajax.ajax("/news/updateNewsStatus", "POST", {
                            userId: 1,
                            newsId: row.id,
                            status: 0
                        }).success(function (data) {
                            if (data.success) {
                                $scope.page.refresh();
                                alertService.show("操作成功!", "success", "80%");
                            } else {
                                swal("提示!", "操作失败!权限不够!", "error");
                            }
                        }).error(function (data) {
                            console.log(data);
                        });
                    }
                },{
                    name: function () {
                        return "删除";
                    },
                    clas: function () {
                        return {
                            "btn btn-xs btn-danger": true
                        };
                    },
                    click: function (row) {
                        swal("确认要删除 [" + (row.title) + "] 吗?", {
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
                                    ajax.ajax("/news/deleteOneNews", "POST", {
                                        userId: 1,
                                        id: row.id
                                    }).success(function (data) {
                                        if (data.success) {
                                            $scope.page.refresh();
                                            alertService.show("操作成功!", "success", "80%");
                                        } else {
                                            swal("提示!", "操作失败!权限不够!", "error");
                                        }
                                     }).error(function (data) {
                                            console.log(data);
                                     });
                                    break;
                                default:
                                }
                        });
                    }
                }
            ];

                //新增
                $scope.entity = entity.getEntity(
                    {
                        title: "",
                        picture: "",
                        abstracts: "",
                        content: "",
                        newsItem: "0"
                    },
                    {
                    },function (action, row) {
                        if ($scope.entity.action === "add") {
                            $scope.entity.entity.newsItem = "0";
                        }
                    },function (){

                        var content = [];
                        content.push($scope.entity.entity.content);

                        if ($scope.entity.action === "add"){
                            Upload.upload({
                            //url:"/file/redirect/trade/server/news/addOneNews",
                            url:"/file/redirect/user/news/addOneNews",
                                data: {
                                    userId: 1,
                                    newsItem: $scope.entity.entity.newsItem,
                                    title: $scope.entity.entity.title,
                                    file: $scope.entity.entity.picture,
                                    abstracts: $scope.entity.entity.abstracts,
                                    //content: $scope.entity.entity.content
                                    content: content
                                }
                            }).success(function (data) {
                                console.log(data);
                                if (data.success) {
                                    $scope.page.refreshTo(1);
                                } else {
                                    //alert("新增失败!名称已存在");
                                }
                            }).error(function (data) {
                                console.log(data);
                            });
                        }else if($scope.entity.action === "edit"){
                        }
                        $scope.entity._success();//隐藏模态框
                    }, "modal");

                //新增顶部广告
            $scope.entityAdd = entity.getEntity(
                {},
                {
                },function (action, row) {
                    $scope.entityAdd.imgChange = false;//表示没有选中文件
                    if ($scope.entityAdd.action === "add") {
                        $scope.moduleList = angular.copy($scope.moduleOriginList);
                    }
                    else if ($scope.entityAdd.action === "edit") {
                        $scope.entityAdd.entity = angular.copy(row);
                        console.log(row);
                        $scope.entityAdd.entity.picture = row.picture;
                    }
                },function (){
                    if ($scope.entityAdd.action === "add"){
                        Upload.upload({
                            url:"/file/redirect/user/news/addTopPic",
                            data: {
                                userId: 1,
                                file: $scope.entityAdd.entity.picture
                            }
                        }).success(function (data) {
                            console.log(data);
                            if (data.success) {
                                $scope.page.refresh();
                            } else {
                                //alert("新增失败!名称已存在");
                            }
                        }).error(function (data) {
                            console.log(data);
                        });
                    }else if($scope.entity.action === "edit"){
                    }
                    $scope.entityAdd._success();//隐藏模态框
                }, "modal-add");


            //编辑
            $scope.entityEdit = entity.getEntity(
                {}, {},
                function (action, row) {//beforeOpen
                    $scope.entityEdit.imgChange = false;//表示没有选中文件
                    if ($scope.entityEdit.action === "edit") {
                        $scope.entityEdit.entity = angular.copy(row);
                        $scope.entityEdit.entity.newsItem = $scope.newsItem;
//                        $scope.entityEdit.entity.picture = row[2].url;
//                        $scope.content = row[3];
//                        console.log(row);
                        if (row.picture != null) {
                            $scope.entityEdit.entity.picture = row.picture.url;
                            //$scope.content = row.content;
                            console.log(row);
                        }
                        //picture为非wangeditor的图片
//                        $scope.entityEdit.entity.picture = row.picture;
                        //$scope.entityEdit.entity.selectRole = $scope.entityEdit.entity.role.id;
                    }
                }, function () {//submit

                    var content = [];
                    content.push($scope.entityEdit.entity.content);

                    if ($scope.entityEdit.action === "edit") {
                        Upload.upload({
                        url:"/file/redirect/user/news/updateOneNews",
                        data: {
                            userId: 1,
                            id: $scope.entityEdit.entity.id,
                            newsItem: $scope.entityEdit.entity.newsItem,
                            title: $scope.entityEdit.entity.title,
                            file: $scope.entityEdit.entity.picture,
                            abstracts: $scope.entityEdit.entity.abstracts,
                            //content: $scope.entityEdit.entity.content
                            content: content
                        }
                        }).success(function (data) {
                            if (data.success){
                                alert("操作成功!");

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
        }
    )
</script>
<%--模态框--%>
<div entity-modal="modal" title="资讯" e="entity">
    <entity-modal-body>
        <div class="form-group">
            <label class="col-sm-2 text-right control-label">
                <span class="text-danger small icon-asterisk m-r"></span>模块
            </label>

            <div class="col-sm-6 form-inline">
                <label>
                    <select class="form-control" ng-model="entity.entity.newsItem"
                        ng-options="n.value as n.name for n in newsItems"
                        ng-change="page.refreshTo(1)">
                        <%--<option value="">全部</option>--%>
                    </select>
                </label>

            </div>
        </div>
        <div entity-edit-img="picture" type="text" title="图片" entity="entity.entity" e="entity" rewidth="2-0-8"
        ></div>
        <div entity-edit-text="title" type="text" title="标题" entity="entity.entity" e="entity" rewidth="2-0-8"
             ></div>

        <div entity-edit-textarea="abstracts" type="text" title="摘要" entity="entity.entity" e="entity" rewidth="2-0-8"
        ></div>

        <label class="">内容
        </label>

        <div class="">
            <div ng-model="entity.entity.content" editor
                 style="height: 1000px; width: 100%">
            </div>
        </div>

    </entity-modal-body>
</div>


<%--<div entity-modal="modal-add" title="顶部广告" e="entityAdd">--%>
    <%--<entity-modal-body>--%>

        <%--<div entity-edit-img="picture" type="text" title="顶部广告" entity="entityAdd.entity" e="entity"--%>
        <%--></div>--%>

    <%--</entity-modal-body>--%>
<%--</div>--%>



<div entity-modal="modal-edit" title="资讯" e="entityEdit">
    <entity-modal-body>
        <div class="form-group">
            <label class="col-sm-2 text-right control-label">模块
            </label>

            <div class="col-sm-6 form-inline">
                <label>
                    <select class="form-control" ng-model="entityEdit.entity.newsItems"
                            ng-options="n.value as n.name for n in newsItems"
                            ng-change="page.refreshTo(1)">
                        <%--<option value="">全部</option>--%>
                    </select>
                </label>

            </div>
        </div>
            <div entity-edit-text="title" title="标题" entity="entityEdit.entity" e="entity" rewidth="2-0-8"
                 vld="entity.validate"></div>

            <div entity-edit-img="picture"  type="text" title="图片" entity="entityEdit.entity" e="entity" rewidth="2-0-8"
            ></div>

            <div entity-edit-textarea="abstracts" type="text" title="摘要" entity="entityEdit.entity" e="entity" rewidth="2-0-8"
            ></div>

            <label class="">内容
            </label>
            <div class="col-xs-12">
                <div ng-model="entityEdit.entity.content" editor
                     style="height: 1000px; width: 100%">
                </div>
            </div>
    </entity-modal-body>
</div>
</div>
</body>
</html>