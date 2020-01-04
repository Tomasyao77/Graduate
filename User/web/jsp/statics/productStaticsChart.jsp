<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/10/10
  Time: 17:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>赊购统计</title>
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
    <script src="/jsp/common/js/module/dateRangeModule.js"></script>
    <%--日期处理--%>
    <link href="/jsp/common/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="/jsp/common/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/jsp/common/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="/jsp/common/js/moment.min.js"></script>
    <link href="/jsp/common/css/daterangepicker.min.css" rel="stylesheet">
    <script src="/jsp/common/js/daterangepicker.min.js"></script>
    <%--生成图表--%>
    <script src="/jsp/common/js/Chart.min.js"></script>
    <%--动画效果--%>
    <script src="/jsp/common/js/countUp.min.js"></script>
</head>
<body ng-app="m" ng-controller="c" ng-init='index = "统计图表"'>

<style>
    .column {
        float: left;
        text-align:center;
        width: 50%;
        padding-left: 20px;
        box-sizing: border-box;
        font-size: xx-large;
    }
</style>
<jsp:include page="/jsp/common/nav.jsp"/>
<div class="container" style="width: 98%">
    <div class="panel panel-default m-t-lg">
        <div class="panel-heading">
            <h4>赊购统计</h4>
            <div class="clearfix">
                <form class="form-inline pull-right">
                    <select class="form-control" ng-model="chartType" ng-options="x.id as x.name for x in staticType" ng-change="uploadChange()"></select>
                    <label>时间
                        <div date-range change="dateRange.change($startDate, $endDate)"
                             start="dateRange.start"
                             end="dateRange.end">
                        </div>
                    </label>
                </form>
            </div>
        </div>
        <div>
            <div class="column">
                <span>今日赊购数量：</span><span id = "todayCredit">{{todayCredit}}</span>
            </div>
            <div class="column">
                <span>总赊购数量：</span><span id = "totalCredit">{{totalCredit}}</span>
            </div>
        </div>
        <div class = "panel-body">
            <canvas id="myChart" width="400" height="200"></canvas>
        </div>
    </div>
</div>
<script>
    angular.module("m",["nm","dateRangeModule"])
        .controller("c",function($scope, page, ajax, entity, $filter){
            $scope.todayCredit = 0;
            $scope.totalCredit = 0;
            $scope.staticType = [
                {
                    id: 0,
                    name: "赊购数量统计"
                },
                {
                    id: 1,
                    name: "收入统计"
                },
                {
                    id: 2,
                    name: "商品售出数量统计"
                }
            ];
            $scope.chartType = 0;
            $scope.uploadChange = function(){
                switch ($scope.chartType){
                    case 0: $scope.getInstitution(function () {
                        $scope.loadProductNum();
                    });break;
                    case 1: $scope.getInstitution(function () {
                        $scope.loadIncome();
                    });break;
                    default:  $scope.getInstitution(function () {
                        $scope.loadProductNum();
                    });
                }
                $scope.updateTitle(myChart); //更新标题
            };
            // 数字滚动动画
            var options = {
                useEasing: true,
                useGrouping: true,
                separator: ',',
                decimal: '.'
            };
            var demo = new CountUp('todayCredit', 0, 4068, 0, 2.5, options);
            var demo2 = new CountUp('totalCredit', 0, 4068, 0, 2.5, options);
            if (!demo.error) {
                demo.start();
                demo2.start();
            } else {
                console.error(demo.error);
            }

            $scope.institution={
                id: null
            };
            var ctx = document.getElementById("myChart");
            var myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    datasets: [{
                        data: [],
                        borderColor: "#3e95cd",
                        borderWidth: 1,
                        fill: false
                    }]
                },
                options: {
                    title: {
                        display: true,
                        text: '赊购总数统计',
                        fontFamily: 'Helvetica',
                        padding: 20,
                        fontSize: 24,
                        lineHeight: 1.2
                    },
                    legend: {
                        display: false
                    },
                    tooltips: {
                        enabled: false
                    },
                    //坐标轴
                    scales: {
                        fontSize:24,
                        yAxes: [{
                            beginAtZero: true,//是否从零开始
                            //轴文字控制
                            ticks: {
                                // stepSize: 20//数字之间的间隔,设置之后例如: [2,3,4]
                            }
                        }]
                    }
                }
            });

            $scope.dateLabel = [];
            $scope.dateRange = {
                start: null,
                end: null,
                change: function (start, end) {
                    $scope.dateRange.start = start;
                    $scope.dateRange.end = end;
                    $scope.labels=[];
                    $scope.dateLabel = $scope.getDate(start, end);
                    $scope.updateLabel(myChart, $scope.dateLabel);      //重载标签
                    switch ($scope.chartType){
                        case 0: $scope.getInstitution(function () {
                            $scope.loadProductNum();
                        });break;
                        case 1: $scope.getInstitution(function () {
                            $scope.loadIncome();
                        });break;
                        default:  $scope.getInstitution(function () {
                            $scope.loadProductNum();
                        });
                    }      //重载数据
                    // console.log($scope.dateLabel);
                }
            };

            //更新标题
            $scope.updateTitle = function (chart) {
                chart.options.title.text = $scope.staticType[$scope.chartType].name;
                chart.update();
            };

            //更新标签
            $scope.updateLabel = function(chart, label) {
                chart.data.labels = [];
                for (var i = 0; i < label.length; i++) {
                    chart.data.labels.push(label[i]);
                }
                chart.update();
            };
            //更新数据
            $scope.updateData = function(chart, data){
                chart.data.datasets[0].data = [];
                var flag = 0;
                for (var i = 0; i < $scope.dateLabel.length; i++) {
                    // console.log(parseInt(data.list[flag].date));
                    // console.log(Date.parse($scope.dateLabel[i]) - 28800000);
                    var NSDate = Date.parse($scope.dateLabel[i]);
                    var timestamp = NSDate - 28800000;  //这里通过parseInt转换成的Long类型的时间戳是东八区的，因此需要减去8小时
                    // console.log(flag);
                    if(flag<data.list.length){
                        if(data.list[flag].date == timestamp)
                        {
                            // console.log(flag);
                            // console.log(parseInt(data.list[flag].date) == Date.parse($scope.dateLabel[i]));
                            chart.data.datasets[0].data.push(data.list[flag].num);
                            flag++;
                        }
                        else{
                            chart.data.datasets[0].data.push(0);
                        }
                    }
                    else {
                        chart.data.datasets[0].data.push(0);
                    }
                }
                chart.update();
            };

            $scope.getDate = function(start, end){
                start = new Date(start).getTime();
                end = new Date(end).getTime();
                var date  = [];
                for(;start < end;start += 86400000){
                    var tmp = new Date(start);
                    var Y = tmp.getFullYear() + '-';
                    var M = (tmp.getMonth()+1 < 10 ? '0'+(tmp.getMonth()+1) : tmp.getMonth()+1) + '-';
                    var D = (tmp.getDate() < 10 ? '0'+tmp.getDate() : tmp.getDate()) ;
                    date.push(Y+M+D);
                    // date.push(tmp.getFullYear()+'-'+(tmp.getMonth()+1)+'-'+tmp.getDate());
                }
                console.log(date);
                return date;
            };


            $scope.loadIncome = function(){
                ajax.ajax("/user/redirect/trade/statics/getCreditIncomeByInstitution","POST",
                    {
                        institutionId:$scope.institution.id,
                        startTime:$scope.dateRange.start,
                        endTime:$scope.dateRange.end
                    }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.updateData(myChart,data);
                    }
                })
            };

            $scope.loadProductNum = function(){
                ajax.ajax("/user/redirect/trade/statics/getCreditProductNumListByInstitution","POST",
                    {
                        institutionId:$scope.institution.id,
                        startTime:$scope.dateRange.start,
                        endTime:$scope.dateRange.end
                    }).success(function (data) {
                    console.log(data);
                    if (data.success) {
                        $scope.updateData(myChart,data);
                    }
                })
            };

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
                        $scope.institution.id = data.value.id;
                        callback && callback();
                    }
                });
            };

            $scope.$watch('$viewContentLoaded', function () {
                $scope.getInstitution(function () {
                    $scope.loadProductNum();
                });
            });
        })
</script>
</body>
</html>
