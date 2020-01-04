package com.whut.tomasyao.login.controller;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-02-12 13:59
 */

import com.whut.tomasyao.auth.util.LoginUtil;
import com.whut.tomasyao.auth.vo.AdminRedisVo;
import com.whut.tomasyao.auth.vo.UserRedisVo;
import com.whut.tomasyao.base.common.Constant;
import com.whut.tomasyao.base.common.MavenModule;
import com.whut.tomasyao.base.vo.ResponseMap;
import com.whut.tomasyao.kafka.util.KafkaProducerUtil;
import com.whut.tomasyao.log.aspect.LogAnnotation;
import com.whut.tomasyao.login.dao.ISKRedisVoDao;
import com.whut.tomasyao.login.service.ILoginService;
import com.whut.tomasyao.message.service.ILogMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Api(tags = {"登录"}, description = "登录", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/user/login")
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    private ILoginService loginService;
    @Autowired
    private ISKRedisVoDao redisVoDao;
    @Autowired
    ILogMessageService logMessageService;

    @ApiIgnore
    @LogAnnotation(opType = "管理员登录", opUser = -1, description = "内容管理员或商户管理员登录", mavenModule = MavenModule.User)
    @RequestMapping(value = "/loginAdmin", method = RequestMethod.POST)
    public Map loginAdmin(HttpServletRequest request, HttpServletResponse response,
                          String username, String password) {
        ResponseMap map = ResponseMap.getInstance();
        String token = UUID.randomUUID().toString();
        AdminRedisVo adminRedisVo;
        try {
            adminRedisVo = loginService.loginAdmin(token, username, password);
            if (adminRedisVo != null) {
                Cookie cookie = new Cookie("token", token);
                cookie.setPath("/");
                response.addCookie(cookie);
                map.put("user_id", adminRedisVo.getId());
                return map.putValue(adminRedisVo);
            } else {
                return map.putFailure("登录失败", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("登录失败", 0);
        }
    }

    @ApiIgnore
    @RequestMapping(value = "/logoutAdmin", method = RequestMethod.POST)
    public Map logoutAdmin(HttpServletRequest request, int userId, String token) {
        ResponseMap map = ResponseMap.getInstance();
        try {
            loginService.logoutAdmin(userId, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map.putSuccess();
    }

    @ApiIgnore
    @RequestMapping(value = "/loginSwagger", method = RequestMethod.POST)
    public Map loginSwagger(HttpServletRequest request, String username, String password) {
        ResponseMap map = ResponseMap.getInstance();

        try {
            String ip = LoginUtil.getIpAddress(request);
            boolean result = loginService.loginSwagger(username, password, ip);
            if (result) {
                return map.putSuccess("登录成功");
            } else {
                return map.putFailure("登录失败", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("登录失败", 0);
        }
    }

    /**
     * 普通用户登录(小程序 android ios)
     */
    @ApiOperation(value = "APP登录", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Android或iOS登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "11位合法手机号", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "code", value = "6位验证码", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "platform", value = "用户平台类型(0小程序 1安卓 2iOS)", paramType = "form", dataType = "int", required = true)
    })
    @LogAnnotation(opType = "用户登录", opUser = -1, description = "普通用户登录(小程序 android ios)", mavenModule = MavenModule.User)
    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public Map loginUser(HttpServletRequest request, HttpServletResponse response,
                         String phone, String code, Integer platform) {
        ResponseMap map = ResponseMap.getInstance();
        if (!Arrays.asList(new Integer[]{0, 1, 2}).contains(platform)) {
            return map.putFailure("登录失败,用户来源不明", 0);
        }


        String token = UUID.randomUUID().toString();
        UserRedisVo user;
        try {
            //验证手机号和验证码
            if(!logMessageService.checkCode(phone, code)){
                return map.putFailure("手机号或验证码错误", -2);
            }
            user = loginService.loginUser(token, phone, code, platform);
            if (user != null) {
                Cookie cookie = new Cookie("token", token);
                cookie.setPath("/");
                response.addCookie(cookie);
                map.put("user_id", user.getId());
                map.put("token", token);
                return map.putValue(user);
            } else {
                return map.putFailure("注册用户失败", -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("登录失败", 0);
        }
    }

    /**
     * 普通用户登录(小程序)
     */
    @LogAnnotation(opType = "用户登录", opUser = -1, description = "普通用户登录(小程序)", mavenModule = MavenModule.User)
    @RequestMapping(value = "/loginSmallCode", method = RequestMethod.POST)
    public Map loginSmallCode(HttpServletRequest request, HttpServletResponse response,
                              String openId) {
        ResponseMap map = ResponseMap.getInstance();
        String token = UUID.randomUUID().toString();
        UserRedisVo user;
        try {
            user = loginService.loginSmallCode(token, openId);
            if (user != null) {
                Cookie cookie = new Cookie("token", token);
                cookie.setPath("/");
                response.addCookie(cookie);
                map.put("user_id", user.getId());
                map.put("token", token);
                return map.putValue(user);
            } else {
                return map.putFailure("登录失败", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("登录失败", 0);
        }
    }

    /**
     * 测试
     */
    @ApiIgnore
    @RequestMapping(value = "/testKafka", method = RequestMethod.POST)
    public synchronized Map testKafka(HttpServletRequest request, int userId) {
        ResponseMap map = ResponseMap.getInstance();
        KafkaProducerUtil.producer("testConsumer", "testBlock", userId+"");
        return map.putSuccess("成功");
    }

}