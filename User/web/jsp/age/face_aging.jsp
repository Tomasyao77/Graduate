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
    <script src="/jsp/common/template/uploadimg.js"></script>
    <%--日期处理--%>
    <link href="/jsp/common/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="/jsp/common/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/jsp/common/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="/jsp/common/js/moment.min.js"></script>
</head>
<body ng-app="m" ng-controller="c" ng-init='index = "年龄面貌合成"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>年龄面貌合成</h4>
            <div class="clearfix">
                <label>
                    <button class="btn btn-default" ng-click="">
                        <%--<span class="icon-plus m-r"></span>上传&nbsp;图片--%>
                        <uploadimg shows="imgshows_file" imgs="uploadimgs_file" cp="cp-file-id" pv="preview-id" v="1"></uploadimg>
                    </button>
                    <input type="text" class="form-control form-inline" ng-model="age">
                </label>
                <label>性别&nbsp;
                        <select class="form-control" ng-model="gender"
                                ng-options="x.id as x.name for x in genderList">
                        </select>
                    </label>
                <label>
                    <button class="btn btn-success" ng-click="haveImg = true;face_aging()">
                        <span class="icon-camera-retro m-r"></span>开始合成
                    </button>
                </label>
                <label>
                    <button class="btn btn-warning" ng-click="aging_done = false;init()">
                        <span class="icon-camera-retro m-r"></span>重置
                    </button>
                </label>
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
                    <%--<input class="form-control m-l" type="text" placeholder="姓名" ng-model="search">--%>
                    <%--<button class="btn btn-primary" type="submit">--%>
                        <%--<span class="icon-search m-r"></span> 搜索--%>
                    <%--</button>--%>
                </form>
            </div>
        </div>
        <%--<%@ include file="/jsp/common/table.jspf" %>--%>
        <div style="margin: 10px 10px;" ng-hide="aging_done">
            <div class="m-a-md">（示例）根据原始图片合成指定年龄段的图片：</div>
            <div class="m-a-md">共10个年龄段0-70岁：0-5 6-10 11-15 16-20 21-25 26-30 31-35 36-45 46-55 56-70</div>
            <div class="m-a-md">
                <img src="/jsp/common/asset/age/6_1_3.jpg" style="width: 130px;height: 130px;">
                <span class="icon-arrow-right icon-2x m-l m-r"></span>
                <div class="m-t-md">
                    <img src="/jsp/common/asset/age/epoch100_6_1_3.jpg.png" style="width: 100%;height: 130px;">
                </div>
            </div>
            <hr/>
            <div class="m-a-md">
                <img src="/jsp/common/asset/age/7_0_1.jpg" style="width: 130px;height: 130px;">
                <span class="icon-arrow-right icon-2x m-l m-r"></span>
                <div class="m-t-md">
                    <img src="/jsp/common/asset/age/epoch100_7_0_1.jpg.png" style="width: 100%;height: 130px;">
                </div>
            </div>
        </div>
        <div ng-show="aging_done">
            <div class="m-a-md">共10个年龄段0-70岁：0-5 6-10 11-15 16-20 21-25 26-30 31-35 36-45 46-55 56-70</div>
            <div class="m-a-md">
                <img ng-src="{{fileToShow}}" style="width: 130px;height: 130px;">
                <span class="icon-arrow-right icon-2x m-l m-r"></span>
                <div class="m-t-md">
                    <img ng-src="{{result_url}}" style="width: 100%;height: 130px;">
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    angular.module("m", ["nm", "uploadModule"])
        .controller("c", function ($scope, page, ajax, entity, $filter, md5, alertService, Upload, fileReader) {
            $scope.genderList = [{id:0,name:"男"},{id:1,name:"女"}];
            $scope.gender = 0;
            $scope.aging_done = false;

            $scope.face_aging = function () {
                $scope.aging_done = false;
                var data = {
                    userId: userId,
                    age: $scope.age,
                    gender: $scope.gender
                };
                console.log(data);
                //return;
                //处理要上传的图片
                data.file = $scope.uploadimgs_file[0];
                fileReader.readAsDataUrl(data.file, $scope)
                    .then(function (result) {
                        $scope.fileToShow = result;
                    });
                Upload.upload({
                    url: '/file/redirect/user/age/face_aging',
                    data: data
                }).then(function (resp) {
                    console.log(resp);
                    if (resp.status === 200 && resp.data.success) {
                        alertService.show("操作成功!", "success", "80%");
                        //$scope.init();
                        $scope.aging_done = true;
                        $scope.result_url = resp.data.value;
                    } else {
                        swal("Sorry!", "检测失败!", "error");
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
            };

            $scope.init = function () {
                /**
                 * 多图上传
                 */
                $scope.imgshows_file = [];//显示url集合
                $scope.uploadimgs_file = [];//上传file集合

                //清空文件组件的内容
                $("#cp-file-id").val("");

                $scope.haveImg = false;
            };

            $scope.$watch('$viewContentLoaded', function () {
                $scope.init();
            });
        });
</script>
</body>
</html>
