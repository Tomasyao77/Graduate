<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>产品管理</title>
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
<body ng-app="m" ng-controller="c" ng-init='index = "产品管理"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>产品列表</h4>
            <div class="clearfix">
                <label>
                    <button class="btn btn-success" ng-click="entity._openModal('add')">
                        <span class="icon-plus m-r"></span>新增&nbsp;产品
                    </button>
                </label>
                <form class="form-inline pull-right" ng-submit="page.refreshTo(1)">
                    <%--<label>管理员类型&nbsp;
                        <select class="form-control" ng-model="searchType"
                                ng-options="x.id as x.name for x in twoRootRole"
                                ng-change="selectChange()"></select>
                    </label>--%>
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
    angular.module("m", ["nm", "uploadModule"])
        .controller("c", function ($scope, page, ajax, entity, $filter, alertService, Upload) {
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
                ajax.ajax("/user/redirect/trade/product/getProductPage", "POST",
                    {
                        userId: userId,
                        current: current,
                        size: size,
                        search: $scope.search,
                        orderBy: orderBy,
                        asc: asc,
                        institutionId: $scope.institution.id,
                        type: 0
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
                        if (row.files.length <= 0) {//如果没有图片显示默认图片
                            /*row.files.push({
                             picture: {url: $scope.default_picture},
                             pictureThumb: {url: $scope.default_picture_thumb}
                             });*/
                            //上述会改变row的结构
                            return $scope.default_picture_thumb;
                        }
                        return row.files[0].pictureThumb.url;
                    },
                    width: "5%"
                },
                {
                    name: "产品名称",
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
                }, /*{
                    name: "审核",
                    value: function (row) {
                        return row.description;
                    },
                    width: "20%"
                }, */{
                    name: "创建时间",
                    value: function (row) {
                        return $filter("fmtDateYMdHMcn")(row.createTime);
                    },
                    orderBy: "p.create_time",
                    width: "15%"
                }
            ];
            $scope.operations = [
                {
                    name: function () {
                        return "查看详情";
                    },
                    clas: function (row) {
                        return {
                            "btn btn-xs btn-default": true
                        };
                    },
                    click: function (row) {
                        window.open("/jsp/product/productInfo.jsp?id="+row.id);
                    }
                },
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
                        return "上架优品";
                    },
                    clas: function () {
                        return {
                            "btn btn-xs btn-success": true
                        };
                    },
                    click: function (row) {
                        $scope.entityNew._openModal("add", row);
                    }
                }, {
                    name: function () {
                        return "发起赊购";
                    },
                    clas: function () {
                        return {
                            "btn btn-xs btn-info": true
                        };
                    },
                    click: function (row) {
                        $scope.entityCredit._openModal("add", row);
                    }
                }/*, {
                    name: function () {
                        return "删除";
                    },
                    clas: function () {
                        return {
                            "btn btn-xs btn-danger": true
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
                                    ajax.ajax("/user/redirect/trade/product/deleteOneProduct", "POST", {
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
                                    });
                                    break;
                                default:
                                //swal("提示!", "操作失败!", "error");
                            }
                        });
                    }
                }*/
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
                    if ($scope.entity.action === "add") {
                        var data = {
                            userId: userId,
                            name: $scope.entity.entity.name,
                            description: $scope.entity.entity.description,
                            institutionId: $scope.institution.id,
                            type: $scope.entity.entity.type
                        };
                        //处理要上传的图片
                        (function () {
                            data.listfile = $scope.uploadimgs_listfile[0];
                            for(var i=0; i<$scope.uploadimgs_basefile.length; i++){
                                data["file"+(i==0?"":i)] = $scope.uploadimgs_basefile[i];
                            }
                            for(var j=0; j<$scope.uploadimgs_infofile.length; j++){
                                data["infofile"+(j==0?"":j)] = $scope.uploadimgs_infofile[j];
                            }
                        })();
                        Upload.upload({
                            url: '/file/redirect/trade/product/addProduct',
                            data: data
                        }).then(function (resp) {
                            console.log(resp);
                            if (resp.status === 200 && resp.data.success) {
                                $scope.entity._success();
                                alertService.show("操作成功!", "success", "80%");
                                $scope.page.refresh();
                            } else {
                                swal("提示!", "名称已存在", "error");
                            }
                            //$scope.fileReturn = resp.data.value.url;
                            //console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
                        }, function (resp) {
                            console.log(resp);
                            $scope.entity._error();
                            swal("提示!", "操作失败!", "error");
                        }, function (evt) {
                            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                            console.log(evt);
                            //console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                        });
                    } else if ($scope.entity.action === "edit") {
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

            /**
             * 优品
             */
            $scope.entityNew = entity.getEntity(
                {num: "", originalPrice: "", currentPrice: ""},
                {num: {}, originalPrice: {}},
                function (action, row) {//beforeOpen
                    $scope.product = angular.copy(row);
                    $scope.ifNewed(function () {
                        if ($scope.entityNew.action === "add") {
                            $scope.entityNew.entity = angular.copy(row);
                            if ($scope.ifNewedFlag) {
                                $scope.entityNew.entity.originalPrice = 0;
                            }
                        }
                    });
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
                            $scope.page.refresh();
                            alertService.show("操作成功!", "success", "80%");
                            swal("操作成功！", "请耐心等待管理员审核，可在“优品管理”->“历史记录”中查看审核状态，审核通过后" +
                                "可在“优品管理”->“优品列表”中进行优品管理", "success");
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
                function (action, row , callback) {//beforeOpen
                    $scope.product = angular.copy(row);
                    $scope.ifCredited(function () {
                        if ($scope.entityCredit.action === "add") {
                            $scope.entityCredit.entity = angular.copy(row);
                            if ($scope.ifCreditedFlag) {
                                $scope.entityCredit.entity.originalPrice = 0;
                            }
                        }
                    });
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
                            $scope.page.refresh();
                            alertService.show("操作成功!", "success", "80%");
                            swal("操作成功！", "请耐心等待管理员审核，可在“赊购管理”->“历史记录”中查看审核状态，审核通过后" +
                                "可在“赊购管理”->“赊购列表”中进行赊购管理", "success");
                        } else {
                            swal("提示!", "操作失败!", "error");
                        }
                        $scope.entityCredit._success();//隐藏模态框
                    }).error(function (data) {
                        console.log(data);
                        $scope.entityCredit._error();//隐藏模态框
                    });
                }, "modal-credit");
            //此产品是否上架为优品过
            $scope.ifNewed = function (callback) {
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
                    callback && callback();
                }).error(function (data) {
                    console.log(data);
                });
            };
            //此产品是否上架为赊购过
            $scope.ifCredited = function (callback) {
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
                    callback && callback();
                }).error(function (data) {
                    console.log(data);
                });
            };

            $scope.$watch('$viewContentLoaded', function () {
                $scope.getProductTypePage();
                $scope.getInstitution(function () {
                    $scope.page.refreshTo(1);
                });
            });
        });
</script>
<%--新增/编辑产品--%>
<div entity-modal="modal" title="产品" e="entity">
    <entity-modal-body>
        <div entity-edit-text="name" type="text" title="名称" entity="entity.entity" e="entity"
             vld="entity.validate" rewidth="2-0-8"></div>
        <div entity-edit-textarea="description" title="描述" entity="entity.entity" e="entity"
             vld="entity.validate" rewidth="2-0-8"></div>
        <div class="form-group">
            <label class="col-sm-2 control-label">
                <span class="text-danger small icon-asterisk m-r"></span>类型</label>
            <div class="col-sm-8">
                <select class="form-control" ng-model="entity.entity.type"
                        ng-options="x.id as x.type for x in productTypePage"
                        ng-change="typeCheck.change()">
                </select>
            </div>
            <div class="col-sm-2">
                <p class="m-t m-b-0 text-danger" ng-show="typeCheck.f()" ng-bind="typeCheck.t"></p>
            </div>
        </div>
        <div class="form-group" ng-show="entity.action === 'add'">
            <label class="col-sm-2 control-label">
                <span class="text-danger small icon-asterisk m-r"></span>列表展示图(最多1张，建议尺寸452px*452px)</label>
            <div class="col-sm-10">
                <uploadimg shows="imgshows_listfile" imgs="uploadimgs_listfile" cp="cp-listfile-id" v="1"></uploadimg>
            </div>
        </div>
        <div class="form-group" ng-show="entity.action === 'add'">
            <label class="col-sm-2 control-label">
                <span class="text-danger small icon-asterisk m-r"></span>产品轮播图(最多3张，建议尺寸464px*464px)</label>
            <div class="col-sm-10">
                <uploadimg shows="imgshows_basefile" imgs="uploadimgs_basefile" cp="cp-basefile-id" v="3"></uploadimg>
            </div>
        </div>
        <div class="form-group" ng-show="entity.action === 'add'">
            <label class="col-sm-2 control-label">
                <span class="text-danger small icon-asterisk m-r"></span>产品详情图(最多10张)</label>
            <div class="col-sm-10">
                <uploadimg shows="imgshows_infofile" imgs="uploadimgs_infofile" cp="cp-infofile-id" v="10"></uploadimg>
            </div>
        </div>
    </entity-modal-body>
</div>
<%--设为优品--%>
<div entity-modal="modal-new" title="优品" e="entityNew">
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
