<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>价格管理</title>
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
    <script src="/jsp/common/template/uploadimg.js"></script>
    <%--日期处理--%>
    <link href="/jsp/common/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="/jsp/common/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/jsp/common/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="/jsp/common/js/moment.min.js"></script>
</head>
<body ng-app="m" ng-controller="c" ng-init='index = "价格管理"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>产品列表</h4>
            <div class="clearfix">
                <%--<label>审核状态&nbsp;
                    <select class="form-control" ng-model="searchType"
                            ng-options="x.id as x.name for x in verifyType"
                            ng-change="selectChange()"></select>
                </label>--%>
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <label>交易类型&nbsp;
                        <select class="form-control" ng-model="searchType"
                                ng-options="x.id as x.name for x in tradeType"
                                ng-change="selectChange()"></select>
                    </label>&nbsp;
                    <label>审核状态&nbsp;
                        <select class="form-control" ng-model="searchVerifyType"
                                ng-options="x.id as x.name for x in verifyType"
                                ng-change="selectChange()"></select>
                    </label>
                    <input class="form-control m-l" type="text"
                           placeholder="名称" ng-model="search">
                    <button class="btn btn-primary" type="submit">
                        <span class="icon-search m-r"></span> 搜索
                    </button>
                </form>
            </div>
        </div>
        <%@ include file="/jsp/common/table.jspf" %>
    </div>
    <%--查看详情--%>
    <div entity-modal-view="modal-view" title="查看 产品详情" e="entityView">
        <entity-modal-view-body>
            <div class="row">
                <div class="col-xs-6">
                    <p style="font-size: 150%;font-weight: bold;" class="text-muted">基本信息</p>
                    <div style="max-height: 700px;overflow-y: auto;overflow-x: hidden;">
                        <div id="myCarousel"
                             ng-show="entityView.entity.files != null && entityView.entity.files.length > 0"
                             class="carousel slide"
                             style="width: 375px;">
                            <!-- 轮播（Carousel）指标 -->
                            <ol class="carousel-indicators">
                                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                                <li data-target="#myCarousel" data-slide-to="1"></li>
                                <li data-target="#myCarousel" data-slide-to="2"></li>
                            </ol>
                            <!-- 轮播（Carousel）项目 -->
                            <div class="carousel-inner">
                                <div ng-repeat="item in entityView.entity.files" class="item"
                                     ng-class="{'active':$index===0}"
                                     style="width: 375px;height: 383px;">
                                    <img ng-src="{{item.picture.url}}" class="img-responsive" style="width: 375px;"
                                         alt="">
                                </div>
                            </div>
                            <!-- 轮播（Carousel）导航 -->
                            <a class="carousel-control left" href="#myCarousel" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            </a>
                            <a class="carousel-control right" href="#myCarousel" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                            </a>
                        </div>
                        <div class="row" style="padding: 10px 20px;">
                            <div entity-view-img="entityView.entity.listfiles[0].picture.url" title="列表图" type="img"
                                 icon="icon-picture"
                                 rewidth="3-0-8"></div>
                            <div entity-view-text="entityView.entity.name" title="产品名称" icon="icon-credit-card"
                                 rewidth="3-0-8"></div>
                            <div entity-view-textarea="entityView.entity.description" title="产品描述"
                                 icon="icon-book"></div>
                            <div entity-view-text="entityView.entity.type | productType : productTypePage" title="产品类型"
                                 icon="icon-tag"
                                 rewidth="3-0-8"></div>
                            <div entity-view-text="entityView.entity.institution.name" title="所属机构" icon="icon-hospital"
                                 rewidth="3-0-8"></div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-6">
                    <p style="font-size: 150%;font-weight: bold;" class="text-muted">产品详情</p>
                    <div style="max-height: 700px;overflow-y: auto;">
                        <div ng-repeat="item in entityView.entity.infofiles" class="item"
                             style="border: 1px solid #ccfcff;">
                            <%--<img ng-src="{{item.picture.url}}" class="img-responsive" alt="">--%>
                            <img ng-src="{{item.picture.url}}" style="margin:0 auto; display:block;"
                                 width=100%; height=100%;/>
                        </div>
                    </div>
                </div>
            </div>
        </entity-modal-view-body>
    </div>
</div>
<script>
    angular.module("m", ["nm"])
        .filter('productType', function () {
            return function (number, obj) {
                var text = "";
                for (var item in obj) {
                    if (number === obj[item]["id"]) {
                        text = obj[item]["type"];
                    }
                }
                return text;
            }
        })
        .controller("c", function ($scope, page, ajax, entity, $filter, alertService) {
            //交易类型
            $scope.tradeType = [{id: 1, name: "赊购"}, {id: 0, name: "优品"}];
            $scope.searchType = 1;
            //审核状态
            $scope.verifyType = [{id: 1, name: "审核通过"}, {id: 0, name: "待审核"}, {id: 2, name: "审核未通过"}];
            $scope.searchVerifyType = 1;
            $scope.selectChange = function () {
                $scope.page.refresh();
            };
            //获取产品类型
            $scope.getProductTypePage = function () {
                ajax.ajax("/user/redirect/trade/product/getProductTypePage", "POST",
                    {
                        userId: 1,
                        current: 1,
                        size: 100,
                        orderBy: "create_time",
                        asc: false
                    }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.productTypePage = data.page.list;
                    }
                });
            };
            /**
             * 产品类型
             */
            $scope.typeCheck = {
                f: function () {
                    return ($scope.entity.entity.type == null ||
                    $scope.entity.entity.type == undefined ||
                    $scope.entity.entity.type == "");
                },
                t: "请选择产品类型",
                change: function () {
                    console.log($scope.entity.entity.type);
                }
            };
            //默认图片
            $scope.config = {
                getData: function (callback) {
                    /**读config.json*/
                    $.getJSON("/jsp/common/template/config.json", function (data) {
                        callback(data);
                    });
                },
                defaultImg: function (row) {
                    console.log(row);
                    if (row.file == null) {
                        row.file = {url: $scope.default_picture};
                        row.fileThumb = {url: $scope.default_picture_thumb};
                    }
                    return row.fileThumb.url;
                }
            };
            $scope.config.getData(function (data) {
                $scope.default_picture = data.default_picture;
                $scope.default_picture_thumb = data.default_picture_thumb;
            });

            /**
             * 获取所属机构信息
             */
            $scope.getInstitution = function (callback) {
                ajax.ajax("/user/institution/getInstitutionByAdmin", "POST", {
                    userId: 1,
                    adminId: userId
                }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.institution = data.value;
                        callback && callback();
                    }
                });
            };
            $scope.load = function (current, size, orderBy, asc) {
                ajax.ajax("/user/redirect/trade/productverify/getProductPriceVerifyPage", "POST",
                    {
                        userId: userId,
                        current: current,
                        size: size,
                        search: $scope.search,
                        orderBy: orderBy,
                        asc: asc,
                        institutionId: $scope.institution.id,
                        verify: $scope.searchVerifyType,
                        type: $scope.searchType
                    }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.page.refreshPage(data);
                    }
                });
            };
            $scope.page = page.page($scope.load, "p.create_time", false);
            $scope.ths = [
                {
                    name: "封面",
                    img: function (row) {
                        row = row.product;
                        if(row.files.length <= 0){//如果没有图片显示默认图片
                            return $scope.default_picture_thumb;
                        }
                        return row.files[0].pictureThumb.url;
                    },
                    width: "5%"
                }, {
                    name: "产品名称",
                    value: function (row) {
                        return row.product.name;
                    },
                    width: "5%"
                }, {
                    name: "描述",
                    value: function (row) {
                        return row.product.description;
                    },
                    width: "10%"
                }, {
                    name: "期望改价",
                    style: function () {
                        return {
                            "color": "red"
                        };
                    },
                    value: function (row) {
                        return row.price;
                    },
                    width: "10%"
                }, {
                    name: "创建时间",
                    value: function (row) {
                        return $filter("fmtDateYMdHMcn")(row.createTime);
                    },
                    orderBy: "p.create_time",
                    width: "10%"
                }
            ];
            $scope.operations = [
                /*{
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
                },*/ {
                    name: function () {
                        return "查看";
                    },
                    clas: function () {
                        return {
                            "btn btn-xs btn-default": true
                        };
                    },
                    click: function (row) {
                        $scope.entityView._openModal("view", row);
                    }
                }
            ];
            /**
             * 产品
             */
            $scope.entity = entity.getEntity(
                {name: "", description: "", type: ""},
                {name: {}, description: {}},
                function (action, row) {//beforeOpen
                    $scope.entity.imgChange = false;//表示没有选中文件
                    if ($scope.entity.action === "add") {
                        //新版多图上传
                        $scope.imgshows_listfile = [];//显示url集合
                        $scope.uploadimgs_listfile = [];//上传file集合
                        $scope.imgshows_basefile = [];
                        $scope.uploadimgs_basefile = [];
                        $scope.imgshows_infofile = [];
                        $scope.uploadimgs_infofile = [];
                    } else if ($scope.entity.action === "edit") {
                        $scope.entity.entity = angular.copy(row);
                    }
                }, function () {//submit
                    if ($scope.typeCheck.f()) {//没选类型
                        return false;
                    }
                    if ($scope.entity.action === "edit") {
                        ajax.ajax("/user/redirect/trade/product/updateProduct", "POST", {
                            userId: 1,
                            id: $scope.entity.entity.id,
                            name: $scope.entity.entity.name,
                            description: $scope.entity.entity.description,
                            type: $scope.entity.entity.type
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

            //查看详情
            $scope.entityView = entity.getEntity(
                {},
                {},
                function (action, row) {//beforeOpen
                    if ($scope.entityView.action === "view") {
                        $scope.entityView.entity = row.product;
                    }
                }, function () {//submit
                }, "modal-view");

            $scope.$watch('$viewContentLoaded', function () {
                $scope.getProductTypePage();
                $scope.getInstitution(function () {
                    $scope.page.refreshTo(1);
                });
            });
        });
</script>
</body>
</html>
