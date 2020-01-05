package com.whut.tomasyao.user.controller;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-02-12 13:59
 */

import com.whut.tomasyao.base.common.MavenModule;
import com.whut.tomasyao.base.model.File;
import com.whut.tomasyao.log.aspect.LogAnnotation;
import com.whut.tomasyao.message.service.ILogMessageService;
import com.whut.tomasyao.user.model.UserAddress;
import com.whut.tomasyao.base.vo.Page;
import com.whut.tomasyao.file.service.IFileService;
import com.whut.tomasyao.user.model.UserVerify;
import com.whut.tomasyao.user.service.IUserAddressService;
import com.whut.tomasyao.user.service.IUserVerifyService;
import com.whut.tomasyao.user.vo.UserVerifyVo;
import com.whut.tomasyao.user.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.whut.tomasyao.base.vo.ResponseMap;
import com.whut.tomasyao.base.model.User;
import com.whut.tomasyao.user.service.IUserService;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"普通用户"}, description = "注册|认证|新品|认筹|开店|收货地址", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/user/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserAddressService userAddressService;
    @Autowired
    private IUserVerifyService userVerifyService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private ILogMessageService logMessageService;

    private static final Logger logger = Logger.getLogger(UserController.class);

    /**
     * 用户基本信息
     */
    @ApiIgnore
    //@LogAnnotation(opType = "获取一个用户", description = "获取一个用户啦啦啦", mavenModule = MavenModule.User)
    @RequestMapping(value = "/getOneUser", method = RequestMethod.POST)
    public Map getOneUser(HttpServletRequest request, int userId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        UserVo user = userService.getOneUser(userId);

        return map.putValue(user == null ? "user不存在" : user);
    }

    //************************************test************************************//
    @ApiIgnore
    @RequestMapping(value = "/getOneUserTwo", method = RequestMethod.POST)
    public Map getOneUserTwo(HttpServletRequest request, int userId, String phone) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        UserVo user = userService.getOneUser(userId, phone);

        return map.putValue(user == null ? "user不存在" : user);
    }

    @ApiIgnore
    @RequestMapping(value = "/findCount", method = RequestMethod.POST)
    public Map findCount(HttpServletRequest request, int userId, String phone) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        int count = userService.findCount(phone);

        return map.putValue(count);
    }

    @ApiIgnore
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Map findList(HttpServletRequest request, int userId, String phone) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        List<User> users = userService.findList(phone);

        return map.putList(users);
    }

    @ApiIgnore
    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    public Map findPage(HttpServletRequest request, int userId, String phone, int current, int size) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Page<User> users = userService.findPage(phone, current, size);

        return map.putPage(users);
    }

    @ApiIgnore
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Map updateUser(HttpServletRequest request, int userId, String phone, String name) {
        ResponseMap map = ResponseMap.getInstance();

        Integer count = null;
        try {
            count = userService.updateUser(userId, phone, name);
            if (count != 1) {
                return map.putFailure("更新失败", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map.putSuccess("更新成功");
    }

    @ApiIgnore
    @RequestMapping(value = "/getUserPageList", method = RequestMethod.POST)
    public Map getUserPageList(HttpServletRequest request, int userId,
                               int current, int size, int areaCode, int level,
                               String name, String orderBy, Boolean asc) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Page<User> userPage = userService.getUserPageList(
                current, size, areaCode, level, name, orderBy, asc);
        return map.putPage(userPage, "获取成功");
    }

    @ApiOperation(value = "用户注册", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "小程序获取到用户openId后调用此接口注册用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "固定传1", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "password", value = "密码(md5 32位)", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "openId", value = "微信openId", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "platform", value = "用户平台类型(0小程序 1安卓 2IOS)", paramType = "form", dataType = "int", required = true)
    })
    @LogAnnotation(opType = "用户注册", description = "小程序获取到用户openId后调用此接口注册用户", mavenModule = MavenModule.User)
    @RequestMapping(value = "/addOneUser", method = RequestMethod.POST)
    public Map addOneUser(HttpServletRequest request, int userId, String password,
                          String openId, Integer platform, String wxName) throws Exception {
        ResponseMap map = ResponseMap.getInstance();

        if (platform == null || !Arrays.asList(new Integer[]{0, 1, 2}).contains(platform)) {
            return map.putFailure("新增失败,用户来源不明", -1);
        }
        User user = userService.addOneUser(password, openId == null ? "" : openId, platform, wxName);
        if (user == null) {
            return map.putFailure("新增失败,用户已存在", -1);
        }

        return map.putValue(user, "新增成功");
    }

    @ApiOperation(value = "修改用户基本信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "修改普通用户的电话,姓名等基本信息"
    )
    @LogAnnotation(opType = "修改用户基本信息", description = "修改普通用户的电话,姓名等基本信息", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateOneUser", method = RequestMethod.POST)
    public Map updateOneUser(HttpServletRequest request, int userId, int id,
                             String phone, String name, String code) throws Exception {
        ResponseMap map = ResponseMap.getInstance();

        User user = userService.updateOneUser(id, phone, name);
        if (user == null) {
            return map.putFailure("编辑失败", -1);
        }

        return map.putValue(user, "编辑成功");
    }

    @ApiOperation(value = "修改用户头像", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "修改普通用户的头像"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "用户id", paramType = "form", dataType = "int")
    })
    @LogAnnotation(opType = "修改用户头像", description = "修改普通用户的头像", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateAvatar", method = RequestMethod.POST)
    public Map updateAvatar(MultipartHttpServletRequest request, int userId, int id) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        File picture = null, pictureThumb = null;
        List<String[]> resultFiles = fileService.uploadWithThumbnails(request);
        if (resultFiles.size() > 0) {
        }
        logger.info("resultFiles.size(): " + resultFiles.size());
        User user = userService.updateAvatar(id, picture, pictureThumb);
        if (user == null) {
            return map.putFailure("编辑失败", -1);
        }

        return map.putValue(user, "编辑成功");
    }

    @ApiOperation(value = "保存微信相关信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "保存微信相关信息"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "wxName", value = "微信昵称", paramType = "form", dataType = "strin")
    })
    @LogAnnotation(opType = "保存微信相关信息", description = "保存微信相关信息", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateUserWx", method = RequestMethod.POST)
    public Map updateUserWx(HttpServletRequest request, int userId, String wxName) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Integer user = userService.updateUserWx(userId, wxName);
        if (user == null) {
            return map.putFailure("编辑失败", -1);
        }

        return map.putValue(user, "编辑成功");
    }

    /**
     * 用户收货地址
     */
    @ApiOperation(value = "获取一条收货地址", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "某个用户获取一条收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "固定传1", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "记录id", paramType = "form", dataType = "int")
    })
    @RequestMapping(value = "/getOneUserAddress", method = RequestMethod.POST)
    public Map getOneUserAddress(HttpServletRequest request, int userId, int id) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        UserAddress userAddress = userAddressService.getOneUserAddress(id);

        return map.putValue(userAddress == null ? "获取失败" : userAddress);
    }

    @ApiOperation(value = "新增一条收货地址", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "某个用户新增一条收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "areaCode", value = "地区码", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "address", value = "详细街道,门牌号", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "realName", value = "真实姓名", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "form", dataType = "string"),
            //@ApiImplicitParam(name = "code", value = "验证码", paramType = "form", dataType = "string")
    })
    @LogAnnotation(opType = "新增一条收货地址", description = "某个用户新增一条收货地址", mavenModule = MavenModule.User)
    @RequestMapping(value = "/addOneUserAddress", method = RequestMethod.POST)
    public Map addOneUserAddress(HttpServletRequest request, int userId, int areaCode, String address,
                                 String realName, String phone, String code) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        //dubbo config是否开启短信验证
        /*Object if_message_verify = dubboConfigService.getConfig("if_message_verify");
        if(if_message_verify != null && (boolean)if_message_verify){
            //判断code是否正确
            if(!logMessageService.checkCode(phone, code)){
                return map.putFailure("验证码错误", -1);
            }
        }*/

        UserAddress userAddress = userAddressService.addOneUserAddress(userId, areaCode, address, realName, phone);
        if (userAddress == null) {
            return map.putFailure("新增失败", 0);
        }

        return map.putValue(userAddress, "新增成功");
    }

    @ApiOperation(value = "编辑一条收货地址", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "某个用户编辑一条收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "固定传1", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "areaCode", value = "地区码", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "address", value = "详细街道,门牌号", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "realName", value = "真实姓名", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "form", dataType = "string"),
            /*@ApiImplicitParam(name = "code", value = "验证码", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "ifHaveCode", value = "是否含有验证码", paramType = "form", dataType = "boolean")*/
    })
    @LogAnnotation(opType = "编辑一条收货地址", description = "某个用户编辑一条收货地址", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateOneUserAddress", method = RequestMethod.POST)
    public Map updateOneUserAddress(HttpServletRequest request, int userId, int id, int areaCode, String address,
                String realName, String phone, String code, Boolean ifHaveCode) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        /*if(ifHaveCode != null && ifHaveCode){
            //dubbo config是否开启短信验证
            Object if_message_verify = dubboConfigService.getConfig("if_message_verify");
            if(if_message_verify != null && (boolean)if_message_verify){
                //判断code是否正确
                if(!logMessageService.checkCode(phone, code)){
                    return map.putFailure("验证码错误", -1);
                }
            }
        }*/

        UserAddress userAddress = userAddressService.updateOneUserAddress(id, areaCode, address, realName, phone);
        if (userAddress == null) {
            return map.putFailure("编辑失败", 0);
        }

        return map.putValue(userAddress, "编辑成功");
    }

    @ApiOperation(value = "获取收货地址列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "某个用户获取收货地址列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "当前页", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页大小", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "address", value = "详细街道,门牌号(用于模糊查询,可不传)", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "orderBy", value = "传createTime,表示按创建时间排序", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "asc", value = "传false,表示按创建时间递减排序", paramType = "form", dataType = "string"),
    })
    @RequestMapping(value = "/getUserAddressPageList", method = RequestMethod.POST)
    public Map getUserAddressPageList(HttpServletRequest request, int userId,
                                      int current, int size, String address, String orderBy, Boolean asc) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Page<UserAddress> userAddressPage = userAddressService.getUserAddressPageList(current, size, userId, address, orderBy, asc);
        return map.putPage(userAddressPage, "获取成功");
    }

    @ApiOperation(value = "设为默认收货地址", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "用户将某个地址设为默认收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "uaId", value = "地址记录id", paramType = "form", dataType = "int")
    })
    @LogAnnotation(opType = "设为默认收货地址", description = "用户将某个地址设为默认收货地址", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateUserAddressAsDefault", method = RequestMethod.POST)
    public Map updateUserAddressAsDefault(HttpServletRequest request, int userId, int uaId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        boolean userAddress = userAddressService.updateUserAddressAsDefault(userId, uaId);
        if (!userAddress) {
            return map.putFailure("编辑失败", 0);
        }

        return map.putSuccess("编辑成功");
    }

    @ApiOperation(value = "删除收货地址", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "用户删除一条或多条收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "uaIds", value = "地址记录ids(是一个数组)", paramType = "form", dataType = "array")
    })
    @LogAnnotation(opType = "删除收货地址", description = "用户删除一条或多条收货地址", mavenModule = MavenModule.User)
    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    public Map deleteAddress(HttpServletRequest request, int userId, Integer[] uaIds) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        boolean userAddress = userAddressService.deleteAddress(userId, uaIds);
        if (!userAddress) {
            return map.putFailure("删除失败或没有要删除的记录", 0);
        }

        return map.putSuccess("删除成功");
    }

    /**
     * 用户认证
     */
    @ApiOperation(value = "获取一条认证信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "某个用户获取一条认证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "固定传1", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "记录id", paramType = "form", dataType = "int")
    })
    @RequestMapping(value = "/getOneUserVerify", method = RequestMethod.POST)
    public Map getOneUserVerify(HttpServletRequest request, int userId, int id) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        UserVerify userVerify = userVerifyService.getOneUserVerify(id);

        return map.putValue(userVerify == null ? "获取失败" : userVerify);
    }

    @ApiOperation(value = "新增一条用户认证", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "某个用户提交认证信息，用来开口袋店铺，以下是需要提交的基本认证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "姓名", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "验证码", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "storeName", value = "店铺名", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "region", value = "所属区域", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "reason", value = "申请理由", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "parentInviteCode", value = "邀请码", paramType = "form", dataType = "string")
    })
    @LogAnnotation(opType = "新增一条用户认证", description = "某个用户提交认证信息，用来开口袋店铺", mavenModule = MavenModule.User)
    @RequestMapping(value = "/addUserVerify", method = RequestMethod.POST)
    public Map addUserVerify(HttpServletRequest request, int userId, String phone, String name,
                             String code, String storeName, String region, String reason, String parentInviteCode) throws Exception {
        ResponseMap map = ResponseMap.getInstance();

        UserVerify userVerify = userVerifyService.addUserVerify(userId, phone, name,
                storeName, region, reason, parentInviteCode);
        if (userVerify == null) {
            return map.putFailure("提交失败", 0);
        }
        return map.putValue(userVerify, "提交成功");
    }

    @ApiIgnore
    @LogAnnotation(opType = "审核用户", description = "管理员审核用户认证信息", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateUserVerifyStatus", method = RequestMethod.POST)
    public Map updateUserVerifyStatus(HttpServletRequest request, int userId, int id, int verify) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        UserVerify userVerify = userVerifyService.updateUserVerifyStatus(id, verify);
        if (userVerify == null) {
            return map.putFailure("编辑失败", 0);
        }

        return map.putValue(userVerify, "编辑成功");
    }

    @ApiOperation(value = "修改审核不通过后的信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "某个用户修改审核不通过后的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "记录id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "姓名", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "unit", value = "所属单位", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "areaCode", value = "地区码（暂时固定传100000）", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "address", value = "店铺地址", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "验证码", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "storeName", value = "店铺名", paramType = "form", dataType = "string")
    })
    @LogAnnotation(opType = "修改审核不通过后的信息", description = "某个用户修改审核不通过后的信息", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateUserVerify", method = RequestMethod.POST)
    public Map updateUserVerify(HttpServletRequest request, int userId, int id, String phone, String name, String unit,
                                String code, int areaCode, String address, String storeName, String region, String reason) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        UserVerify userVerify = userVerifyService.updateUserVerify(id, phone, name, unit, areaCode, address, storeName, region, reason);
        if (userVerify == null) {
            return map.putFailure("编辑失败", 0);
        }

        return map.putValue(userVerify, "编辑成功");
    }

    @ApiOperation(value = "修改审核不通过后的信息，无code", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "某个用户修改审核不通过后的信息，无code")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "记录id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "姓名", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "unit", value = "所属单位", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "areaCode", value = "地区码（暂时固定传100000）", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "address", value = "店铺地址", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "storeName", value = "店铺名", paramType = "form", dataType = "string")
    })
    @LogAnnotation(opType = "修改审核不通过后的信息", description = "某个用户修改审核不通过后的信息", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateUserVerifyWithoutCode", method = RequestMethod.POST)
    public Map updateUserVerifyWithoutCode(HttpServletRequest request, int userId, int id, String phone, String name, String unit,
                                int areaCode, String address, String storeName, String region, String reason) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        UserVerify userVerify = userVerifyService.updateUserVerify(id, phone, name, unit, areaCode, address, storeName, region, reason);
        if (userVerify == null) {
            return map.putFailure("编辑失败", 0);
        }

        return map.putValue(userVerify, "编辑成功");
    }

    @ApiOperation(value = "获取认证信息列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "某个用户认证信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "当前页", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页大小", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "orderBy", value = "传createTime,表示按创建时间排序", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "asc", value = "传false,表示按创建时间递减排序", paramType = "form", dataType = "string"),
    })
    @RequestMapping(value = "/getUserVerifyPageListOwn", method = RequestMethod.POST)
    public Map getUserVerifyPageListOwn(HttpServletRequest request, int userId,
                                        int current, int size, String orderBy, Boolean asc) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Page<UserVerify> userVerifyPage = userVerifyService.getUserVerifyPageListOwn(current, size, userId, orderBy, asc);
        return map.putPage(userVerifyPage, "获取成功");
    }

    @ApiIgnore
    @RequestMapping(value = "/getUserVerifyPageListAdmin", method = RequestMethod.POST)
    public Map getUserVerifyPageListAdmin(HttpServletRequest request, int userId,
                                          int current, int size, String search, String orderBy, Boolean asc, Integer verify) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        if(!Arrays.asList(new Integer[]{0, 1, 2}).contains(verify)){
            return map.putFailure("获取失败", -1);
        }
        Page<UserVerify> userVerifyPage = userVerifyService.getUserVerifyPageListAdmin(current, size, search, orderBy, asc, verify);
        return map.putPage(userVerifyPage, "获取成功");
    }

    @ApiIgnore
    @RequestMapping(value = "/getUserVerifyPageListAdminMybatis", method = RequestMethod.POST)
    public Map getUserVerifyPageListAdminMybatis(HttpServletRequest request, int userId,
                                          int current, int size, String search, String orderBy, Boolean asc, Integer verify) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        if(!Arrays.asList(new Integer[]{0, 1, 2}).contains(verify)){
            return map.putFailure("获取失败", -1);
        }
        Page<UserVerifyVo> userVerifyPage = userVerifyService.getUserVerifyPageListAdminMybatis(current, size, search, orderBy, asc, verify);
        return map.putPage(userVerifyPage, "获取成功");
    }

    @ApiOperation(value = "根据openId获取用户信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "根据openId获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "固定传1", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "openId", value = "微信openId", paramType = "form", dataType = "int")
    })
    @RequestMapping(value = "/getUserByOpenId", method = RequestMethod.POST)
    public Map getUserByOpenId(HttpServletRequest request, int userId, String openId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        User user = userService.getUserByOpenId(openId);
        return map.putValue(user, "获取成功");
    }

    @ApiIgnore
    @RequestMapping(value = "/updateRemark", method = RequestMethod.POST)
    public Map updateRemark(HttpServletRequest request, int userId, int id, String remark) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        UserVerify user = userVerifyService.updateRemark(id, remark);
        return map.putValue(user, "编辑成功");
    }

}