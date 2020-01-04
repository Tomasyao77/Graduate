<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>产品详情</title>
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
<body ng-app="m" ng-controller="c" ng-init='index = "产品管理"' style="min-width: 1200px;">
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container clearfix" style="width: 98%;margin-top: 20px;">
    <div class="row">
        <div class="col-xs-1">
            <p style="font-size: 150%;font-weight: bold;" class="text-muted">编辑产品</p>
            <p>
                <button ng-click="entity._openModal('edit', product)" class="btn btn-warning">产品信息</button>
            </p>
            <p>
                <button ng-click="entityListFile._openModal('edit', product)" class="btn btn-primary">列表图片</button>
            </p>
            <p>
                <button ng-click="entityFile._openModal('edit', product)" class="btn btn-primary">轮播图片</button>
            </p>
            <%--<p>
                <button ng-click="entityInfoFile._openModal('view', product)" class="btn btn-primary">详情图片</button>
            </p>--%>
            <%--<p>
                <button ng-click="entityNew._openModal('add', product)" class="btn btn-success">上架新品</button>
            </p>
            <p>
                <button ng-click="entityCredit._openModal('add', product)" class="btn btn-info">发起赊购</button>
            </p>--%>
        </div>
        <div class="col-xs-4">
            <p style="font-size: 150%;font-weight: bold;" class="text-muted">基本信息</p>
            <div style="max-height: 700px;overflow-y: auto;overflow-x: hidden;">
                <div id="myCarousel" ng-show="product.files != null && product.files.length > 0" class="carousel slide"
                     style="width: 375px;">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                        <li data-target="#myCarousel" data-slide-to="2"></li>
                    </ol>
                    <!-- 轮播（Carousel）项目 -->
                    <div class="carousel-inner">
                        <div ng-repeat="item in product.files" class="item" ng-class="{'active':$index===0}"
                             style="width: 375px;height: 383px;">
                            <img ng-src="{{item.picture.url}}" class="img-responsive" style="width: 375px;" alt="">
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
                    <div entity-view-img="product.listfiles[0].picture.url" title="列表图" type="img" icon="icon-picture"
                         rewidth="3-0-8"></div>
                    <div entity-view-text="product.name" title="产品名称" icon="icon-credit-card" rewidth="3-0-8"></div>
                    <div entity-view-textarea="product.description" title="产品描述" icon="icon-book"></div>
                    <div entity-view-text="product.type | productType : productTypePage" title="产品类型" icon="icon-tag"
                         rewidth="3-0-8"></div>
                    <div entity-view-text="product.institution.name" title="所属机构" icon="icon-hospital"
                         rewidth="3-0-8"></div>
                </div>
            </div>
        </div>
        <div class="col-xs-7">
            <p style="font-size: 150%;font-weight: bold;" class="text-muted">产品详情</p>
            <div style="max-height: 700px;overflow-y: auto;">
                <div ng-repeat="item in product.infofiles" class="item"
                     style="width: 500px;position: relative;border: 1px solid #ccfcff;">
                    <%--<img ng-src="{{item.picture.url}}" class="img-responsive" alt="">--%>
                    <img ng-src="{{item.picture.url}}" style="margin:0 auto; display:block;"
                         width=100%; height=100%;/>
                    <i class="icon-remove-sign icon-2x"
                       style="position:absolute;cursor:pointer;top:5px;right:-80px;color: #ff4438;"
                       ng-click="deleteInfoFile.delete(item)"></i>
                    <i class="icon-edit icon-2x"
                       style="position:absolute;cursor:pointer;top:5px;right:-40px;color: #ff4438;"
                       ng-click="editInfoFile.chooseImg(item)"></i>
                </div>
                <div class="fileupload pull-left clearfix" style="position: relative;width: 500px;height: 100px;">
                    <i class="icon-plus icon-2x fileupload-icon"></i>
                    <input type="file" id="addinfofile-input-id"
                           ng-click="addInfoFile.chooseImg()"
                           onchange="angular.element(this).scope().addInfoFile.onChange()"
                           style="display:inline-block; width: 100%; height: 100%; opacity:0;cursor: pointer;"
                           accept="image/png,image/gif,image/jpeg,image/jpg">
                </div>
            </div>
        </div>
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
        .controller("c", function ($scope, page, ajax, entity, $filter, alertService, Upload) {
            /**
             * 获取地址栏name
             */
            $scope.GetQueryString = function (name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) {
                    return decodeURI(r[2]);
                }
                return null;
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
            //获取一种产品
            $scope.getProduct = function (callback) {
                ajax.ajax("/user/redirect/trade/productsells/getProduct", "POST", {
                    userId: userId,
                    id: $scope.GetQueryString("id"),
                    type: 0
                }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.product = data.value;
                        callback && callback();
                    }
                }).error(function (data) {
                    console.log(data);
                });
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
            /**
             * 产品
             */
            $scope.entity = entity.getEntity(
                {
                    name: "", description: "", type: ""
                },
                {
                    name: {}, description: {}
                }, function (action, row) {//beforeOpen
                    $scope.entity.imgChange = false;//表示没有选中文件
                    if ($scope.entity.action === "edit") {
                        $scope.entity.entity = angular.copy(row);
                    }
                }, function () {//submit
                    if ($scope.typeCheck.f()) {
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
                                $scope.getProduct();
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
            /**
             * 编辑产品列表展示图片
             */
            $scope.entityListFile = entity.getEntity(
                {img: ""},
                {}, function (action, row) {//beforeOpen
                    $scope.entityListFile.imgChange = false;//表示没有选中文件
                    if ($scope.entityListFile.action === "edit") {
                        $scope.entityListFile.entity = angular.copy(row);
                        if ($scope.entityListFile.entity.listfiles.length > 0) {
                            $scope.entityListFile.entity.img = $scope.entityListFile.entity.listfiles[0].picture.url;
                        }
                    }
                }, function () {//submit
                    if (!$scope.ifChooseEditImg($scope.entityListFile.entity.img)) {
                        $scope.entityListFile._success();
                        return false;
                    }
                    var oldIds = [];
                    if ($scope.entityListFile.entity.listfiles.length > 0) {
                        oldIds.push($scope.entityListFile.entity.listfiles[0].id);
                    }
                    Upload.upload({
                        url: '/file/redirect/trade/product/updateProductFiles',
                        data: {
                            userId: 1,
                            productId: $scope.entityListFile.entity.id,
                            oldIds: oldIds,
                            file: $scope.entityListFile.imgChange === true ? $scope.entityListFile.entity.img : null,
                            type: 2//列表
                        }
                    }).then(function (resp) {
                        console.log(resp);
                        if (resp.status === 200 && resp.data.success) {
                            $scope.entityListFile._success();
                            alertService.show("操作成功!", "success", "80%");
                            $scope.getProduct();
                        } else {
                            swal("提示!", "编辑产品图片失败", "error");
                        }
                        //$scope.fileReturn = resp.data.value.url;
                        //console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
                    }, function (resp) {
                        console.log(resp);
                        $scope.entityListFile._error();
                        swal("提示!", "操作失败!", "error");
                    }, function (evt) {
                        var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                        console.log(evt);
                        //console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                    });
                }, "modal-listfile");
            /**
             * 编辑产品图片
             */
            $scope.entityFile = entity.getEntity(
                {img: "", img1: "", img2: ""},
                {}, function (action, row) {//beforeOpen
                    if ($scope.entityFile.action === "edit") {
                        $scope.entityFile.entity = angular.copy(row);
                        $scope.entityFile.entity.img = "";
                        $scope.entityFile.entity.img1 = "";
                        $scope.entityFile.entity.img2 = "";
                        var files = $scope.entityFile.entity.files;
                        if (files.length > 0) {
                            for (var i = 0; i < files.length; i++) {
                                $scope.entityFile.entity["img" + (i == 0 ? "" : i) + "Src"] =
                                    files[i] ? files[i].picture.url : "";
                                $scope.entityFile.entity["img" + (i == 0 ? "" : i)] = 1;
                            }
                        }
                        //清空多余的图片(上个残留)
                        for (var j = 0; j < 3; j++) {
                            if (!files[j]) {
                                $("#modal-imgEdit-id" + (j == 0 ? "" : j)).attr("src", "");
                            }
                        }
                    }
                }, function () {//submit
                    var flagAll = false;
                    (function () {
                        for (var i = 0; i < 3; i++) {
                            flagAll = flagAll ||
                                $scope.ifChooseEditImg($scope.entityFile.entity["img" + (i == 0 ? "" : i)]);
                        }
                    })();
                    if (!flagAll) {//什么都没改就提交
                        $scope.entityFile._success();
                        return false;
                    }
                    //被改变的id
                    var oldIds = [];
                    (function () {
                        for (var i = 0; i < 3; i++) {
                            var t = $scope.ifChooseEditImg($scope.entityFile.entity["img" + (i == 0 ? "" : i)]);
                            flagAll = flagAll || t;
                            if (t && $scope.entityFile.entity.files[i]) {
                                oldIds.push($scope.entityFile.entity.files[i].id);
                            }
                        }
                    })();
                    var data = {
                        userId: 1,
                        productId: $scope.entityFile.entity.id,
                        oldIds: oldIds,
                        type: 0//基本
                    };
                    (function () {//文件
                        for (var i = 0; i < 3; i++) {
                            data["file" + (i == 0 ? "" : i)] = $scope.entityFile.entity["img" + (i == 0 ? "" : i)];
                        }
                    })();
                    Upload.upload({
                        url: '/file/redirect/trade/product/updateProductFiles',
                        data: data
                    }).then(function (resp) {
                        console.log(resp);
                        if (resp.status === 200 && resp.data.success) {
                            $scope.entityFile._success();
                            alertService.show("操作成功!", "success", "80%");
                            $scope.getProduct();
                        } else {
                            swal("提示!", "编辑产品图片失败", "error");
                        }
                        //$scope.fileReturn = resp.data.value.url;
                        //console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
                    }, function (resp) {
                        console.log(resp);
                        $scope.entityFile._error();
                        swal("提示!", "操作失败!", "error");
                    }, function (evt) {
                        var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                        console.log(evt);
                        //console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                    });
                }, "modal-file");
            /**
             * 编辑产品详情图片
             */
            /*$scope.entityInfoFile = entity.getEntity(
             {img: "", img1: "", img2: ""},
             {}, function (action, row) {//beforeOpen
             if ($scope.entityInfoFile.action === "view") {
             $scope.entityInfoFile.entity = row;
             }
             }, function () {//submit
             }, "modal-infofile");*/
            //产品详情图片单张编辑
            $scope.editInfoFile = {
                oldItem: {},
                newFile: {},
                onChange: function () {
                    var file = $("#infofile-input-id")[0].files[0];
                    if (typeof file === "object") {
                        var url = window.URL || window.webkitURL || window.mozURL;
                        $scope.editInfoFile.newFile = url.createObjectURL(file);
                        $scope.editInfoFile.confirmEdit(file);
                    }
                },
                chooseImg: function (item) {
                    $("#infofile-input-id").val("");//清空input file域
                    $("#infofile-input-id").click();//触发input file点击事件
                    $scope.editInfoFile.oldItem = item;
                },
                confirmEdit: function (file) {
                    var dom = document.createElement("div");
                    var html = '<div class="row clearfix" style="height: 500px;">' +
                        '<div class="col-xs-6">' +
                        '<p style="font-size: 120%;font-weight: bold;" class="text-muted">替换前</p>' +
                        '<img src={oldsrc} class="img-rounded" width="300px" height="400px" style="margin-bottom: 20px;"/>' +
                        '</div>' +
                        '<div class="col-xs-6">' +
                        '<p style="font-size: 120%;font-weight: bold;" class="text-success">替换后</p>' +
                        '<img src={newsrc} class="img-rounded" width="300px" height="400px" style="margin-bottom: 20px;"/>' +
                        '</div>' +
                        '</div>';
                    var t = $(html.replace("{oldsrc}", $scope.editInfoFile.oldItem.picture.url)
                        .replace("{newsrc}", $scope.editInfoFile.newFile));
                    dom.innerHTML = t.html();
                    swal({
                        closeOnClickOutside: false,//禁止点击背景关闭alert
                        title: "提示!确认要替换吗?",
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
                                var oldIds = [];
                                oldIds.push($scope.editInfoFile.oldItem.id);
                                Upload.upload({
                                    url: '/file/redirect/trade/product/updateProductFiles',
                                    data: {
                                        userId: 1,
                                        productId: $scope.product.id,
                                        oldIds: oldIds,
                                        file: file,
                                        type: 1//详情
                                    }
                                }).then(function (resp) {
                                    console.log(resp);
                                    if (resp.status === 200 && resp.data.success) {
                                        alertService.show("操作成功!", "success", "80%");
                                        $scope.getProduct();
                                    } else {
                                        swal("提示!", "编辑产品图片失败", "error");
                                    }
                                    //$scope.fileReturn = resp.data.value.url;
                                    //console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
                                }, function (resp) {
                                    console.log(resp);
                                    swal("提示!", "操作失败!", "error");
                                }, function (evt) {
                                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                                    console.log(evt);
                                    //console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                                });
                                break;
                        }
                    });
                }
            };
            //产品详情图片单张删除
            $scope.deleteInfoFile = {
                delete: function (item) {
                    var dom = document.createElement("div");
                    var html = '<div class="row clearfix" style="height: 500px;">' +
                        '<div class="col-xs-12">' +
                        '<img src={oldsrc} class="img-rounded" width="300px" height="400px" style="margin-bottom: 20px;"/>' +
                        '</div>' +
                        '</div>';
                    var t = $(html.replace("{oldsrc}", item.picture.url));
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
                                Upload.upload({
                                    url: '/file/redirect/trade/product/deleteProductFile',
                                    data: {
                                        userId: 1,
                                        id: item.id
                                    }
                                }).then(function (resp) {
                                    console.log(resp);
                                    if (resp.status === 200 && resp.data.success) {
                                        alertService.show("操作成功!", "success", "80%");
                                        $scope.getProduct();
                                    } else {
                                        swal("提示!", "操作失败", "error");
                                    }
                                    //$scope.fileReturn = resp.data.value.url;
                                    //console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
                                }, function (resp) {
                                    console.log(resp);
                                    swal("提示!", "操作失败!", "error");
                                }, function (evt) {
                                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                                    console.log(evt);
                                    //console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                                });
                                break;
                        }
                    });
                }
            };
            //产品详情图片单张新增
            $scope.addInfoFile = {
                onChange: function () {
                    var file = $("#addinfofile-input-id")[0].files[0];
                    if (typeof file === "object") {
                        var url = window.URL || window.webkitURL || window.mozURL;
                        $scope.addInfoFile.addFile = url.createObjectURL(file);
                        $scope.addInfoFile.confirmAdd(file);
                    }
                },
                chooseImg: function (item) {
                    $("#addinfofile-input-id").val("");//清空input file域
                },
                confirmAdd: function (file) {
                    var dom = document.createElement("div");
                    var html = '<div class="row clearfix" style="height: 500px;">' +
                        '<div class="col-xs-12">' +
                        '<img src={addsrc} class="img-rounded" width="300px" height="400px" style="margin-bottom: 20px;"/>' +
                        '</div>' +
                        '</div>';
                    var t = $(html.replace("{addsrc}", $scope.addInfoFile.addFile));
                    dom.innerHTML = t.html();
                    swal({
                        closeOnClickOutside: false,//禁止点击背景关闭alert
                        title: "提示!确认要新增吗?",
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
                                Upload.upload({
                                    url: '/file/redirect/trade/product/updateProductFiles',
                                    data: {
                                        userId: 1,
                                        productId: $scope.product.id,
                                        oldIds: [],
                                        file: file,
                                        type: 1//详情
                                    }
                                }).then(function (resp) {
                                    console.log(resp);
                                    if (resp.status === 200 && resp.data.success) {
                                        alertService.show("操作成功!", "success", "80%");
                                        $scope.getProduct();
                                    } else {
                                        swal("提示!", "新增产品图片失败", "error");
                                    }
                                    //$scope.fileReturn = resp.data.value.url;
                                    //console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
                                }, function (resp) {
                                    console.log(resp);
                                    swal("提示!", "操作失败!", "error");
                                }, function (evt) {
                                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                                    console.log(evt);
                                    //console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                                });
                                break;
                        }
                    });
                }
            };
            $scope.ifChooseEditImg = function (e) {
                return (typeof e === "object");
            };
            $scope.ngfChange = function (index, e) {
                if (typeof e === "object") {
                    var url = window.URL || window.webkitURL || window.mozURL;
                    switch (index) {
                        case 0:
                            $scope.entityFile.entity.imgSrc = url && url.createObjectURL(e);
                            break;
                        case 1:
                            $scope.entityFile.entity.img1Src = url && url.createObjectURL(e);
                            break;
                        case 2:
                            $scope.entityFile.entity.img2Src = url && url.createObjectURL(e);
                            break;
                    }
                }
            };
            /**
             * 新品
             */
            $scope.entityNew = entity.getEntity(
                {num: "", originalPrice: "", currentPrice: ""},
                {num: {}, originalPrice: {}},
                function (action, row) {//beforeOpen
                    if ($scope.entityNew.action === "add") {
                        $scope.entityNew.entity = angular.copy(row);
                        if ($scope.ifNewedFlag) {
                            $scope.entityNew.entity.originalPrice = 0;
                        }
                    }
                }, function () {//submit
                    ajax.ajax("/user/redirect/trade/productsells/addProductNewItem", "POST", {
                        userId: 1,
                        productId: $scope.entityNew.entity.id,
                        num: $scope.entityNew.entity.num,
                        originalPrice: $scope.entityNew.entity.originalPrice,
                        currentPrice: $scope.entityNew.entity.currentPrice
                    }).success(function (data) {
                        console.log(data);
                        if (data.success) {
                            $scope.getProduct();
                            alertService.show("操作成功!", "success", "80%");
                        } else {
                            swal("提示!", "操作失败!", "error");
                        }
                        $scope.entityNew._success();//隐藏模态框
                    }).error(function (data) {
                        console.log(data);
                        $scope.entityNew._error();//隐藏模态框
                    });
                }, "modal-new");
            /**
             * 赊购
             */
            $scope.entityCredit = entity.getEntity(
                {num: "", originalPrice: "", currentPrice: ""},
                {num: {}, originalPrice: {}},
                function (action, row) {//beforeOpen
                    if ($scope.entityCredit.action === "add") {
                        $scope.entityCredit.entity = angular.copy(row);
                        if ($scope.ifCreditedFlag) {
                            $scope.entityCredit.entity.originalPrice = 0;
                        }
                    }
                }, function () {//submit
                    ajax.ajax("/user/redirect/trade/productsells/addProductCreditItem", "POST", {
                        userId: 1,
                        productId: $scope.entityCredit.entity.id,
                        num: $scope.entityCredit.entity.num,
                        originalPrice: $scope.entityCredit.entity.originalPrice,
                        currentPrice: $scope.entityCredit.entity.currentPrice
                    }).success(function (data) {
                        console.log(data);
                        if (data.success) {
                            $scope.getProduct();
                            alertService.show("操作成功!", "success", "80%");
                        } else {
                            swal("提示!", "操作失败!", "error");
                        }
                        $scope.entityCredit._success();//隐藏模态框
                    }).error(function (data) {
                        console.log(data);
                        $scope.entityCredit._error();//隐藏模态框
                    });
                }, "modal-credit");
            //此产品是否上架为新品过
            $scope.ifNewed = function () {
                ajax.ajax("/user/redirect/trade/productsells/getProductNew", "POST", {
                    userId: userId,
                    productId: $scope.product.id
                }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.ifNewedFlag = true;
                        $scope.productNew = data.value;
                    } else {
                        $scope.ifNewedFlag = false;
                    }
                }).error(function (data) {
                    console.log(data);
                });
            };
            //此产品是否上架为赊购过
            $scope.ifCredited = function () {
                ajax.ajax("/user/redirect/trade/productsells/getProductCredit", "POST", {
                    userId: userId,
                    productId: $scope.product.id
                }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.ifCreditedFlag = true;
                        $scope.productCredit = data.value;
                    } else {
                        $scope.ifCreditedFlag = false;
                    }
                }).error(function (data) {
                    console.log(data);
                });
            };
            $scope.$watch('$viewContentLoaded', function () {
                $scope.getProductTypePage();
                $scope.getProduct(function () {
                    $scope.ifNewed();
                    $scope.ifCredited();
                });
            });
        });
</script>
<%--新增/编辑产品--%>
<div entity-modal="modal" title="产品" e="entity">
    <entity-modal-body>
        <div entity-edit-text="name" type="text" title="名称" entity="entity.entity" e="entity"
             vld="entity.validate"></div>
        <div entity-edit-textarea="description" title="描述" entity="entity.entity" e="entity"
             vld="entity.validate"></div>
        <div class="form-group">
            <label class="col-sm-2 col-sm-offset-2 control-label">
                <span class="text-danger small icon-asterisk m-r"></span>类型</label>
            <div class="col-sm-4">
                <select class="form-control" ng-model="entity.entity.type"
                        ng-options="x.id as x.type for x in productTypePage"
                        ng-change="typeCheck.change()">
                </select>
            </div>
            <div class="col-sm-2">
                <p class="m-t m-b-0 text-danger" ng-show="typeCheck.f()" ng-bind="typeCheck.t"></p>
            </div>
        </div>
        <div ng-show="entity.action === 'add'" entity-edit-img="img" myid="modal-img-id"
             title="产品图片1" <%--rewidth="2-0-8"--%>
             entity="entity.entity" e="entity"></div>
        <div ng-show="entity.action === 'add' && entity.entity.img != null && entity.entity.img != ''"
             entity-edit-img="img1" myid="modal-img-id1" title="产品图片2"
             entity="entity.entity" e="entity"></div>
        <div ng-show="entity.action === 'add' && entity.entity.img1 != null && entity.entity.img1 != ''"
             entity-edit-img="img2" myid="modal-img-id2" title="产品图片3"
             entity="entity.entity" e="entity"></div>
        <div ng-show="entity.action === 'add'"
             entity-edit-img="infoimg" myid="modal-infoimg-id" title="产品详情图片"
             entity="entity.entity" e="entity"></div>
    </entity-modal-body>
</div>
<%--编辑产品列表展示图片--%>
<div entity-modal="modal-listfile" title="产品图片" e="entityListFile">
    <entity-modal-body>
        <div class="form-group">
            <div entity-edit-img="img" myid="modal-listimg-id" title="产品列表展示图片，建议尺寸452px*452px"
                 entity="entityListFile.entity" e="entityListFile"></div>
        </div>
    </entity-modal-body>
</div>
<%--编辑产品图片--%>
<div entity-modal="modal-file" title="产品图片" e="entityFile">
    <entity-modal-body>
        <div class="form-group">
            <label class="col-sm-2 col-sm-offset-2 control-label">
                <span>产品图片1，建议尺寸464px*464px</span>
            </label>
            <div class="col-sm-4">
                <div class="btn btn-primary block" ngf-select ng-model="entityFile.entity.img"
                     ngf-pattern="'.jpg,.png,.gif,.jpeg'" ngf-accept="'image/jpg,image/png,image/gif,image/jpeg'"
                     ngf-max-size="20MB" style="width: 100px;" ngf-change="ngfChange(0,entityFile.entity.img)">选择文件
                </div>
                <img id="modal-imgEdit-id" class="img-responsive img-rounded"
                     ng-src="{{entityFile.entity.imgSrc}}" style="max-height: 200px;margin-top: 10px;">
            </div>
        </div>
        <div class="form-group" ng-show="entityFile.entity.img != null && entityFile.entity.img != ''">
            <label class="col-sm-2 col-sm-offset-2 control-label">
                <span>产品图片2，建议尺寸464px*464px</span>
            </label>
            <div class="col-sm-4">
                <div class="btn btn-primary block" ngf-select ng-model="entityFile.entity.img1"
                     ngf-pattern="'.jpg,.png,.gif,.jpeg'" ngf-accept="'image/jpg,image/png,image/gif,image/jpeg'"
                     ngf-max-size="20MB" style="width: 100px;" ngf-change="ngfChange(1,entityFile.entity.img1)">选择文件
                </div>
                <img id="modal-imgEdit-id1" class="img-responsive img-rounded"
                     ng-src="{{entityFile.entity.img1Src}}" style="max-height: 200px;margin-top: 10px;">
            </div>
        </div>
        <div class="form-group" ng-show="entityFile.entity.img1 != null && entityFile.entity.img1 != ''">
            <label class="col-sm-2 col-sm-offset-2 control-label">
                <span>产品图片3，建议尺寸464px*464px</span>
            </label>
            <div class="col-sm-4">
                <div class="btn btn-primary block" ngf-select ng-model="entityFile.entity.img2"
                     ngf-pattern="'.jpg,.png,.gif,.jpeg'" ngf-accept="'image/jpg,image/png,image/gif,image/jpeg'"
                     ngf-max-size="20MB" style="width: 100px;" ngf-change="ngfChange(2,entityFile.entity.img2)">选择文件
                </div>
                <img id="modal-imgEdit-id2" class="img-responsive img-rounded"
                     ng-src="{{entityFile.entity.img2Src}}" style="max-height: 200px;margin-top: 10px;">
            </div>
        </div>
    </entity-modal-body>
</div>
<%--编辑产品详情图片--%>
<%--<div entity-modal-view="modal-infofile" title="编辑 产品详情图片" e="entityInfoFile">
    <entity-modal-view-body>
        <div class="form-group" style="max-height: 700px;overflow-y: auto;">
            <label class="col-sm-3 control-label">
                <span>产品详情图片</span>
            </label>
            <div class="col-sm-8">
                <div class="fileupload pull-left clearfix"
                     ng-repeat="item in entityInfoFile.entity.infofiles track by $index"
                     style="position: relative;width: 500px;height: 750px;">
                    <img ng-src="{{item.picture.url}}" style="margin:0 auto; display:block;"
                         width=100%; height=100%;/>
                    <i class="icon-remove-sign icon-2x"
                       style="position:absolute;cursor:pointer;top:5px;right:-80px;color: #ff4438;"
                       ng-click="uploadimg_del($index)"></i>
                    <i class="icon-edit icon-2x"
                       style="position:absolute;cursor:pointer;top:5px;right:-40px;color: #ff4438;"
                       ng-click="editInfoFile.chooseImg(item)"></i>
                </div>
            </div>
        </div>
    </entity-modal-view-body>
</div>--%>
<%--编辑产品详情图片临时input file注意不能写在模态框里面--%>
<input type="file" style="display:none;" name="file" id="infofile-input-id"
       onchange="angular.element(this).scope().editInfoFile.onChange()"
       accept="image/png,image/gif,image/jpeg,image/jpg">
<%--设为新品--%>
<div entity-modal="modal-new" title="新品" e="entityNew">
    <entity-modal-body>
        <div entity-view-tag="entityNew.entity.name" type="text" title="名称"></div>
        <div entity-view-tag="entityNew.entity.description" type="textarea" title="描述"></div>
        <div entity-edit-text="num" type="number" title="数量" entity="entityNew.entity" e="entityNew"
             vld="entityNew.validate" min="0" max="100000" step="1"></div>
        <div entity-edit-text="originalPrice" type="text" title="原价" entity="entityNew.entity" e="entityNew"
             vld="entityNew.validate" ng-show="!ifNewedFlag"></div>
        <div entity-edit-text="currentPrice" type="text" title="现价" entity="entityNew.entity" e="entityNew"
             vld="entityNew.validate" ng-show="!ifNewedFlag"></div>
    </entity-modal-body>
</div>
<%--设为赊购--%>
<div entity-modal="modal-credit" title="赊购" e="entityCredit">
    <entity-modal-body>
        <div entity-view-tag="entityCredit.entity.name" type="text" title="名称"></div>
        <div entity-view-tag="entityCredit.entity.description" type="textarea" title="描述"></div>
        <div entity-edit-text="num" type="number" title="数量" entity="entityCredit.entity" e="entityCredit"
             vld="entityCredit.validate" min="0" max="100000" step="1"></div>
        <div entity-edit-text="originalPrice" type="text" title="原价" entity="entityCredit.entity" e="entityCredit"
             vld="entityCredit.validate" ng-show="!ifCreditedFlag"></div>
        <div entity-edit-text="currentPrice" type="text" title="现价" entity="entityCredit.entity" e="entityCredit"
             vld="entityCredit.validate" ng-show="!ifCreditedFlag"></div>
    </entity-modal-body>
</div>
</body>
</html>
