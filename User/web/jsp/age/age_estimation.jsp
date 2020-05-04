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
<body ng-app="m" ng-controller="c" ng-init='index = "年龄估计"'>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%;">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>年龄估计</h4>
            <div class="clearfix">
                <label>
                    <button class="btn btn-default" ng-click="">
                        <%--<span class="icon-plus m-r"></span>上传&nbsp;图片--%>
                        <uploadimg shows="imgshows_file" imgs="uploadimgs_file" cp="cp-file-id" pv="preview-id" v="1"></uploadimg>
                    </button>
                </label>
                <label>
                    <button class="btn btn-success" ng-click="haveImg = true;age_estimation()">
                        <span class="icon-camera-retro m-r"></span>开始检测
                    </button>
                </label>
                <label>
                    <button class="btn btn-warning" ng-click="haveImg = false;init()">
                        <span class="icon-camera-retro m-r"></span>重置
                    </button>
                </label>
                    <%--仅做测试<label>
                        <button class="btn btn-success" ng-click="download()">
                            <span class="icon-plus m-r"></span>download
                        </button>
                    </label>--%>
            </div>
        </div>
        <%--<%@ include file="/jsp/common/table.jspf" %>--%>
        <div style="margin: 10px 10px;" ng-hide="haveImg">
            <div class="m-a-md">（示例）估计一张图片中的人脸年龄：</div>
            <img src="/jsp/common/asset/age/3.jpg" style="width: 400px;height: 460px;">
            <span class="icon-arrow-right icon-2x m-l m-r"></span>
            <img src="/jsp/common/asset/age/3_age.jpg" style="width: 400px;height: 460px;">
        </div>
        <div ng-show="haveImg">
            <h3>年龄估计结果（岁）：&nbsp;<span ng-bind="age_result" class="text-danger"></span></h3>
        </div>
    </div>
</div>
<script>
    angular.module("m", ["nm", "uploadModule"])
        .controller("c", function ($scope, page, ajax, entity, $filter, md5, alertService, Upload) {
            $scope.age_estimation = function () {
                var data = {
                    userId: userId
                };
                //处理要上传的图片
                data.file = $scope.uploadimgs_file[0];
                Upload.upload({
                    url: '/file/redirect/user/age/estimation',
                    data: data
                }).then(function (resp) {
                    console.log(resp);
                    if (resp.status === 200 && resp.data.success) {
                        alertService.show("操作成功!", "success", "80%");
                        //$scope.init();
                        $scope.age_result = resp.data.value;
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
