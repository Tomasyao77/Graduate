(function ($) {
    "use strict";
    angular.module("ztreeModule", [])
        .constant("myConstant", {
            "iconOpenPath": "/jsp/plugin/ztree/css/img/diy/p_open.png",
            "iconClosePath": "/jsp/plugin/ztree/css/img/diy/p_close.png",
            "iconModulePath": "/jsp/plugin/ztree/css/img/diy/module.png"})
        .service("ztree", function (myConstant) {
                this.getZTree = function (ztId, $scope, option) {
                    var zTreeObj;
                    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
                    var setting = {
                        //点击
                        callback: {
                            onClick: function (event, treeId, treeNode) {
                                console.log(treeNode);
                                $scope.$apply(function () {//即时更新视图
                                    $scope.clickItem = treeNode;
                                });
                            },
                            beforeDrag: function (treeId, treeNodes) {
                                return !treeNodes[0].isParent;
                            },
                            beforeRemove: function (treeId, treeNode) {
                                //return confirm("确认要删除吗?");
                            }
                        },
                        //选择
                        check: {
                            enable: option.check,
                            chkStyle: "checkbox",
                            chkboxType: {"Y": "ps", "N": "ps"}
                        },
                        //拖拽
                        edit: {
                            enable: false,
                            drag: {
                                isCopy: false,
                                isMove: true,
                                prev: true,
                                next: true,
                                inner: false
                            },
                            showRemoveBtn: function (treeId, treeNode) {
                                if (treeNode.level !== 0) {
                                    return true;
                                }
                                if (treeNode.level === 0 && !treeNode.children) {
                                    return true;
                                }
                                if (treeNode.level === 0 && treeNode.children && treeNode.children.length === 0) {
                                    return true;
                                }
                                return false;
                            }
                        },
                        view: {
                            //addHoverDom: addHoverDom,
                            //removeHoverDom: removeHoverDom,
                            selectedMulti: false//禁用多选
                        }
                    };

                    function addHoverDom(treeId, treeNode) {
                        /*if (treeNode.level === 0) {
                            return false;
                        }*/
                        var sObj = $("#" + treeNode.tId + "_span");
                        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
                        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                            + "' title='add node' onfocus='this.blur();'></span>";
                        sObj.after(addStr);
                        var btn = $("#addBtn_" + treeNode.tId);
                        if (btn) btn.bind("click", function () {
                            if (!treeNode.isLastNode && treeNode.level === 0) {
                                return false;
                            }
                            var zTree = $.fn.zTree.getZTreeObj(ztId);
                            //添加节点
                            //如果是level0就添加level0的节点
                            if(treeNode.level === 0){

                            }

                            var node = {parentTId: treeNode.parentTId, name: "未命名模块"};
                            if (treeNode.level === 0) {
                                node.iconOpen = myConstant.iconOpenPath;
                                node.iconClose = myConstant.iconClosePath;
                                node.children = [];
                            } else {
                                node.icon = myConstant.iconModulePath;
                            }
                            zTree.addNodes(zTree.getNodeByTId("" + treeNode.parentTId), node);
                            return false;
                        });
                    }

                    function removeHoverDom(treeId, treeNode) {
                        $("#addBtn_" + treeNode.tId).unbind().remove();
                    }

                    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
                    /**
                     * node example:
                     * var zNodes = [{
                         name: "模块管理",
                         open: false,
                         iconOpen: myConstant.iconOpenPath,
                         iconClose: myConstant.iconClosePath,
                         children: [
                         {name: "test1_1", icon: myConstant.iconModulePath},
                         {name: "test1_2", icon: myConstant.iconModulePath}]
                     },{
                         name: "角色管理",
                         open: true,
                         iconOpen: myConstant.iconOpenPath,
                         iconClose: myConstant.iconClosePath,
                         children: [
                         {name: "test2_1", icon: myConstant.iconModulePath},
                         {name: "test2_2", icon: myConstant.iconModulePath}]
                     }];
                     */

                    /*$scope.ztreeNodes = function () {
                        var zNodes = [{
                            name: "模块管理",
                            open: true,
                            iconOpen: myConstant.iconOpenPath,
                            iconClose: myConstant.iconClosePath,
                            children: [
                                {name: "test1_1", icon: myConstant.iconModulePath},
                                {name: "test1_2", icon: myConstant.iconModulePath}]
                        },{
                            name: "角色管理",
                            open: true,
                            iconOpen: myConstant.iconOpenPath,
                            iconClose: myConstant.iconClosePath,
                            children: [
                                {name: "test2_1", icon: myConstant.iconModulePath},
                                {name: "test2_2", icon: myConstant.iconModulePath}]
                        }];
                        return zNodes;
                    };*/

                    zTreeObj = $.fn.zTree.init($("#" + ztId), setting, $scope.ztreeNodes());
                    return zTreeObj;
                };
            }
        );
})(jQuery);
