(function (angular, $) {
    "use strict";

    angular.module("uploadModule", ["alertModule"])
        /**
         * 图片读取
         */
        .factory('fileReader', ["$q", "$log", function ($q, $log) {
            var onLoad = function (reader, deferred, scope) {
                return function () {
                    scope.$apply(function () {
                        deferred.resolve(reader.result);
                    });
                };
            };
            var onError = function (reader, deferred, scope) {
                return function () {
                    scope.$apply(function () {
                        deferred.reject(reader.result);
                    });
                };
            };
            var getReader = function (deferred, scope) {
                var reader = new FileReader(); //fileReader
                reader.onload = onLoad(reader, deferred, scope);
                reader.onerror = onError(reader, deferred, scope);
                return reader;
            };
            var readAsDataURL = function (file, scope) {
                var deferred = $q.defer();
                var reader = getReader(deferred, scope);
                reader.readAsDataURL(file);
                return deferred.promise;

            };
            return {
                readAsDataUrl: readAsDataURL
            };
        }])
        /**
         * 图片上传预览
         */
        .directive('file', ['$parse', 'fileReader', 'alertService', function ($parse, fileReader, alertService) {
            return {
                restrict: 'A',
                link: function (scope, element, attrs) {
                    //console.log(element);
                    var model = $parse(attrs.file);
                    var modelSetter = model.assign;
                    element.bind('change', function (event) {
                        if(scope.volume <= scope.imgshows.length){//张数限制
                            //先清空+组件的内容
                            $("#" + scope.component).val("");
                            return;
                        }
                        scope.$apply(function () {
                            modelSetter(scope, element[0].files[0]);
                        });
                        //附件预览
                        scope.imgupload = (event.srcElement || event.target).files[0];
                        getFile(scope.imgupload, scope);
                        //获得预览图地址并且把file对象放入上传合集内
                        function getFile(imgupload, scope) {
                            if (!imgupload) {
                                return;
                            }
                            fileReader.readAsDataUrl(imgupload, scope)
                                .then(function (result) {
                                    var file = document.getElementById("" + scope.component).files[0];
                                    if(file.size / 1024 / 1024 > 1){//不能超过1MB
                                        alertService.show("图片大小限制<1MB!", "danger", "80%", "remove");
                                        $("#" + scope.component).val("");
                                        return;
                                    }
                                    $("#" + scope.preview).attr("src", result);
                                    scope.imgshows.push(result);
                                    scope.uploadimgs.push(file);//这里是放着传给后台的数据file，下面controller的时候会有
                                    /**
                                     * 这里要清空上传file组件的内容（也就是中间有个+的）
                                     * 否则该组件不能连续选择相同的文件,无法触发change事件(2020-2-11)
                                     */
                                    $("#" + scope.component).val("");
                                });
                        }
                    });
                }
            };
        }])
        /**
         * 多图上传组件
         */
        .directive('uploadimg', function () {
            return {
                restrict: 'E',
                scope: {
                    imgshows: "=shows", uploadimgs: "=imgs", volume: "=v",
                    component: "@cp", preview: "@pv"
                },
                template: '<div class="clearfix">' +
                <!--图片显示-->
                '<div class="fileupload pull-left clearfix" ng-repeat="itemSrc in imgshows track by $index" style="position: relative;">' +
                '<img ng-src="{{itemSrc}}" style="max-width:200px;max-height:300px;margin:0 auto; display:block;"' +
                ' width=100%; height=100%;/>' +
                '<i class="icon-remove-sign icon-large" style="position:absolute;cursor:pointer;top:5px;right:5px;"' +
                ' ng-click="uploadimg_del($index)"></i>' +

                '</div>' +
                <!--图片选择,选择后input中存放的就是最新的那一张图片-->
                '<div class="fileupload pull-left clearfix" style="position: relative;">' +
                '<i class="icon-plus icon-2x fileupload-icon"></i>' +
                '<input type="file" style="display:inline-block; width: 100%; height: 100%; opacity:0;cursor: pointer;" name="upload_img"' +
                /*file为自定义指令*/
                ' file="upload_img" id="{{component}}" placeholder="选择图片" accept="image/png,image/gif,image/jpeg,image/jpg">' +
                '</div>' +
                '</div>',
                link: function (scope, element, attrs) {
                    scope.uploadimg_del = function (index) {
                        $("#" + scope.preview).attr("src", "/jsp/common/asset/white.png");
                        scope.imgshows.splice(index, 1);
                        scope.uploadimgs.splice(index, 1);
                    };
                }
            }
        });
})(angular, jQuery);
