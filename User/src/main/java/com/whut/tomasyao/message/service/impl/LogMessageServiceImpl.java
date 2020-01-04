package com.whut.tomasyao.message.service.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-09-15 15:36
 */

import edu.whut.pocket.auth.dao.IMessageRedisDao;
import edu.whut.pocket.base.mybatis.params.ParamsMap;
import edu.whut.pocket.base.util.EncryptUtil;
import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.message.common.SmsPlatform;
import edu.whut.pocket.message.common.SmsType;
import edu.whut.pocket.message.mapper.LogMessageMapper;
import edu.whut.pocket.message.util.MessageUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import edu.whut.pocket.message.model.LogMessage;
import edu.whut.pocket.message.service.ILogMessageService;

import java.util.Date;
import java.util.List;

@Service
public class LogMessageServiceImpl implements ILogMessageService {

    @Autowired
    private LogMessageMapper messageMapper;
    @Autowired
    private IMessageRedisDao messageRedisDao;//存储短信code到redis

    @Override
    public Integer addOneLogMessage(String string) throws Exception {//mapstring
        JSONObject json = new JSONObject(string);
        JSONObject msgContent = new JSONObject();
        LogMessage logMessage = null;
        try {
            msgContent.put("message", json.getString("message"));
            msgContent.put("remainpoint", json.getString("remainpoint"));
            msgContent.put("returnstatus", json.getString("returnstatus"));
            msgContent.put("successCounts", json.getString("successCounts"));
            msgContent.put("taskID", json.getString("taskID"));
            msgContent.put("code", json.getString("code"));
            msgContent.put("text", json.getString("text"));
            msgContent.put("content", json.getString("content"));

            boolean success = json.getString("message").equals("ok");
            Date createTime = new Date(json.getLong("createTime"));

            logMessage = new LogMessage(json.getString("phone"), SmsType.valueOf(json.getString("type")),
                    SmsPlatform.valueOf(json.getString("platform")), msgContent.toString(), success, createTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(logMessage == null){
            return 0;
        }
        return messageMapper.addOne(logMessage);
    }

    @Override
    public Page<LogMessage> getLogMessagePage(int current, int size, String orderBy, Boolean asc,
                                              String search) throws Exception {
        //分页查询参数构造(其它业务分页查询与此类似,改变的最多是查询参数构造)
        ParamsMap hashMap = ParamsMap.getPageInstance(current, size, orderBy, asc);
        hashMap.put("phone", search);
        //开始查询记录和数量
        List<LogMessage> list = messageMapper.getLogMessagePage(hashMap);
        Integer count = messageMapper.findCountLogMessagePage(hashMap);
        Page<LogMessage> page = new Page<>(list, count, current, size);
        return page;
    }

    @Override
    public boolean checkCode(String phone, String code) throws Exception {
        boolean flag;
        if (!MessageUtil.isPhone(phone) || !MessageUtil.isSixCode(code)) {
            return false;
        }
        String key = "smsCode-"+phone;
        key =  EncryptUtil.md5(key);
        flag = code.equals(messageRedisDao.get(key));
        if(flag){//验证成功要删除这条记录
            messageRedisDao.delete(key);
        }

        return flag;
    }
}
