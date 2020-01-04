package com.whut.tomasyao.message.controller;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-09-15 17:06
 */

import com.whut.tomasyao.auth.dao.IMessageRedisDao;
import com.whut.tomasyao.base.common.MavenModule;
import com.whut.tomasyao.base.util.StringUtil;
import com.whut.tomasyao.base.vo.Page;
import com.whut.tomasyao.log.aspect.LogAnnotation;
import com.whut.tomasyao.message.model.LogMessage;
import com.whut.tomasyao.message.service.ILogMessageService;
import com.whut.tomasyao.message.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import com.whut.tomasyao.base.vo.ResponseMap;
import com.whut.tomasyao.message.model.Message;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"短信"}, description = "验证码|普通短信", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private ILogMessageService logMessageService;
    @Autowired
    private IMessageRedisDao messageRedisDao;//存储短信code到redis

    @ApiOperation(value = "获取验证码", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "开店认证的时候输入手机号,点击获取验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "phone", value = "手机号（11位）", paramType = "form", dataType = "string", required = true)
    })
    @LogAnnotation(opType = "用户注册", description = "小程序获取到用户openId后调用此接口注册用户", mavenModule = MavenModule.User)
    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    public Map sendMessage(HttpServletRequest request, int userId, String phone) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        String code = StringUtil.getRandomCode(6);
        Message message = new Message(phone, code, "【变购链】您的验证码为： ");
        boolean result = MessageUtil.send(message, null);
        if(!result){
            return map.putFailure("发送失败", 0);
        }
        messageRedisDao.put(MessageUtil.buildRedisKey(phone), code, 5 * 60);//有效期5分钟
        return map.putSuccess("发送成功");
    }

    @ApiIgnore
    @RequestMapping(value = "/getLogMessagePage", method = RequestMethod.POST)
    public Map getLogMessagePage(HttpServletRequest request, int userId, int current, int size,
                                 String orderBy, Boolean asc, String search) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Page<LogMessage> page = logMessageService.getLogMessagePage(current, size, orderBy, asc ,search);
        return map.putPage(page, "获取成功");
    }

}