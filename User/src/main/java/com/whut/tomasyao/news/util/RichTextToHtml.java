package com.whut.tomasyao.news.util;

import edu.whut.pocket.base.util.EncryptUtil;
import edu.whut.pocket.base.util.StringUtil;
import edu.whut.pocket.file.util.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-10-12 19:53
 */

public class RichTextToHtml {
    public String transfer(String content) {
        String head = "<!DOCTYPE html><html lang=\"zh-CN\"><head><meta charset=\"utf-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"renderer\" content=\"webkit\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><title>医美专栏</title><link href=\"https://kdusers.d9lab.net/jsp/common/css/bootstrap.min.css\" rel=\"stylesheet\"><link href=\"https://kdusers.d9lab.net/jsp/common/css/font-awesome.min.css\" rel=\"stylesheet\"><link href=\"https://kdusers.d9lab.net/jsp/common/css/bootstrap-extend.css\" rel=\"stylesheet\"><link href=\"https://kdusers.d9lab.net/jsp/common/css/validate.css\" rel=\"stylesheet\"><script src=\"https://kdusers.d9lab.net/jsp/common/js/jquery.min.js\"></script><script src=\"https://kdusers.d9lab.net/jsp/common/js/bootstrap.min.js\"></script><script src=\"https://kdusers.d9lab.net/jsp/common/js/angular.min.js\"></script><script src=\"https://kdusers.d9lab.net/jsp/common/js/ng-file-upload-all.min.js\"></script><script src=\"https://kdusers.d9lab.net/jsp/common/template/baseModule.js\"></script><style>        body{            padding: 20px 10px;        }</style></head><script>    angular.module(\"nm\", [\"baseModule\"])        .controller(\"nc\", function ($scope) {        });    angular.module(\"m\", [\"nm\"])        .controller(\"c\", function ($scope, ajax) {            $scope.GetQueryString = function(name){                var reg = new RegExp(\"(^|&)\"+ name +\"=([^&]*)(&|$)\");                var r = window.location.search.substr(1).match(reg);                if(r!=null){return  decodeURI(r[2]);} return null;            };            var userId=$scope.GetQueryString(\"userId\");            var newsId=$scope.GetQueryString(\"newsId\");            $scope.collectImgArr = [\"/jsp/common/asset/un_collect.png\",            \"/jsp/common/asset/collected.png\"];            $scope.collectImg = $scope.collectImgArr[0];            //获取新闻            $scope.getOneNews = function(callback){                ajax.ajax(\"/news/getOneNews\", \"POST\", {                    userId: userId,                    newsId: newsId                }).success(function (data) {                    console.log(data);                    if (data.success){                        $scope.news = data.value;                        if($scope.news.newsCollect !== null){                            $scope.collectImg = $scope.collectImgArr[1];                        }                        callback && callback();                    }                }).error(function (data) {                    console.log(data);                });            };            //收藏            $scope.collectNews = function(){                ajax.ajax(\"/news/collectNews\", \"POST\", {                    userId: userId,                    newsId: newsId                }).success(function (data) {                    console.log(data);                    if (data.success){                        $scope.getOneNews();                        $scope.collectImg = $scope.collectImgArr[1];                    }                }).error(function (data) {                    console.log(data);                });            };            //取消收藏            $scope.cancelNews = function(){                ajax.ajax(\"/news/cancelNews\", \"POST\", {                    userId: userId,                    newsId: newsId                }).success(function (data) {                    console.log(data);                    if (data.success){                        $scope.getOneNews();                        $scope.collectImg = $scope.collectImgArr[0];                    }                }).error(function (data) {                    console.log(data);                });            };            $scope.getOneNews();            //点击图标            $scope.clickImg = function(){                if($scope.news.newsCollect !== null){                    $scope.cancelNews();                } else{                    $scope.collectNews();                }            };        });</script><body ng-app=\"m\" ng-controller=\"c\"><div class=\"clearfix\" style=\"width: 100%;\"><div style=\"float: left;\" class=\"clearfix\"></div><img ng-click=\"clickImg()\" ng-src=\"{{collectImg}}\" style=\"width: 16px;height: 16px;margin-top: -3px;\">&nbsp;收藏<span style=\"margin-left: 5px;\" ng-bind=\"news.view\"></span>&nbsp;次浏览</div>\t\t\t",
                tail = "</body></html>",
                whole = head + content + tail;

        //本地
        String outFile = "/home/zouy/WorkSpace/svn_ws/kdgg/Pocket/Trade/src/main/java/edu/whut/pocket/file/temp/";
        //服务器
        //String outFile = "/alfa/whut/webapps/tomcat-debug/kdtrade.d9lab.net/WEB-INF/classes/edu/whut/pocket/file/temp/";*/
        //临时文件
        String randomTenStr = StringUtil.getRandomString(10) + (new Date().getTime());
        try {
            outFile += EncryptUtil.md5(randomTenStr) + ".html";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //在磁盘中临时保存
        try {
            File fileText = new File(outFile);
            FileWriter fileWriter = new FileWriter(fileText);
            fileWriter.write(whole);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //上传文件到fdfs并删除临时文件
        File file = new File(outFile);
        String resultUrl = "";
        try {
            resultUrl = FileUtil.upload("1", file);
            clearFiles(outFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultUrl;
    }

    //删除文件和目录
    private void clearFiles(String workspaceRootPath) {//使用时最好精确到文件
        File file = new File(workspaceRootPath);
        if (file.exists()) {
            deleteFile(file);
        }
    }
    private void deleteFile(File file) {
        if (file.isDirectory()) {//递归调用
            File[] files = file.listFiles();
            if(files != null){
                for (File f : files) {
                    deleteFile(f);
                }
            }
        }
        file.delete();
    }

}
