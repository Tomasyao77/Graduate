package com.whut.tomasyao.storeDetail.controller;
/*
 *@ClassName: UserAndStoreInfo
 *@Desctiption: 用于获取用户和店铺信息的接口
 *@Author: XWY
 *@Date: 2019/3/30 16:56
 *@Version: 1.0
 */

import edu.whut.pocket.base.model.File;
import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.base.vo.ResponseMap;
import edu.whut.pocket.dubbo.file.service.IDubboFileService;
import edu.whut.pocket.dubbo.storeBanner.service.IStoreBannerService;
import edu.whut.pocket.storeDetail.service.StoreDetailService;
import edu.whut.pocket.storeDetail.vo.StoreDetailVo;
import edu.whut.pocket.user.vo.UserVerifyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = {"店铺详情"}, description = "用于获取店铺详情", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(value = "/storeDetail")
public class StoreDetailController {

    private static final Logger logger = Logger.getLogger(StoreDetailController.class);

    @Autowired
    private StoreDetailService storeDetailService;

    @Autowired
    private IDubboFileService dubboFileService;

    @Autowired
    private IStoreBannerService storeBannerService;

    @ApiOperation(value = "根据userId和storeId获取对应的店铺信息和用户信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE, notes = "获取店铺详情")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int")
    )
    @RequestMapping(value = "/getStoreDetailByUserId", method = RequestMethod.POST)
    public Map getStoredDetailByUserId(HttpServletRequest request, int userId, int storeId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        UserVerifyVo storeDetailByUserId = storeDetailService.getStoreDetailByUserId(userId);

        if (null == storeDetailByUserId) {
            map.putFailure("查询失败", -1);
        }
        //查询该店邀请的店铺
        int nextStoresCount = storeDetailService.getNextStoresCount(storeId);
        logger.info(nextStoresCount + "个下一级店铺");
        //根据storeId查店铺封面
        String storeBannerUrl = storeBannerService.getStoreBannerByStoreId(storeId);

        StoreDetailVo storeDetailVo = new StoreDetailVo();
        //设置该用户的详情
        storeDetailVo.setUserVerifyVo(storeDetailByUserId);
        //设置该用户邀请的数量
        storeDetailVo.setNextStoresCount(nextStoresCount);
        //设置该用户店铺封面
        storeDetailVo.setStoreBannerUrl(storeBannerUrl);
        List<StoreDetailVo> storeDetail = new ArrayList<>();
        storeDetail.add(storeDetailVo);
        return map.putList(storeDetail, "查询成功");
    }


    @ApiOperation(value = "根据userId获取店铺的下一级邀请店铺", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE, notes = "获取下一级店铺详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "storeId", value = "店铺id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "当前页", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页大小", paramType = "form", dataType = "int", required = true)
    })
    @RequestMapping(value = "/getNextStores", method = RequestMethod.POST)
    public Map getNextStoresByStoreId(HttpServletRequest request, int userId, int storeId, int current, int size) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        List<UserVerifyVo> nextStores = storeDetailService.getNextStores(userId);
//        int count = storeDetailService.getNextStoresCount(storeId);
        if (null != nextStores) {
            Page<UserVerifyVo> page = new Page<>(nextStores, nextStores.size(), current, size);
            return map.putPage(page, "查询成功");
        }
        return map.putFailure("查询失败", -1);
    }

    @ApiOperation(value = "根据storeId获取店铺的封面", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE, notes = "店铺封面")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "storeId", value = "店铺id", paramType = "form", dataType = "int")
    )
    @RequestMapping(value = "/getStoreBannerByStoreId", method = RequestMethod.POST)
    public Map getStoreBannerByStoreId(HttpServletRequest request, int storeId) throws Exception {
        System.out.println("进入getStoreBannerByStoreId");
        ResponseMap map = ResponseMap.getInstance();
        String storeBannerUrl = storeBannerService.getStoreBannerByStoreId(storeId);
        if (null != storeBannerUrl) {
            return map.putValue(storeBannerUrl, "查询店铺封面URL成功！");
        }
        return map.putFailure("查询失败", -1);
    }

    @ApiIgnore
    @RequestMapping(value = "/modifyStoreBanner", method = RequestMethod.POST)
    public Map modifyStoreBanner(HttpServletRequest request, int storeId, String file, String fileThumb) throws Exception {
        logger.info("更新图片");
        ResponseMap map = ResponseMap.getInstance();
        File picture = null, pictureThumb = null;
        if (file != null && file != "") {
            picture = dubboFileService.addOneFile(file);
            pictureThumb = dubboFileService.addOneFile(fileThumb);
        }
        Integer updateStoreBanner = storeBannerService.updateStoreBanner(storeId, picture.getId(), pictureThumb.getId());
        if (updateStoreBanner == null) {
            return map.putFailure("更新失败", -1);
        }
        return map.putSuccess("更新成功！");
    }
}
