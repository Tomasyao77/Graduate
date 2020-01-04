package com.whut.tomasyao.message.util;

import edu.whut.pocket.auth.dao.IMessageRedisDao;
import edu.whut.pocket.base.util.EncryptUtil;
import edu.whut.pocket.message.model.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-09-15 10:40
 * 封装一层，便于在多个短信平台切换
 */

public class MessageUtil {
    @Autowired
    private IMessageRedisDao messageRedisDao;//存储短信code到redis

    //map是额外参数，便于细粒度控制
    public static boolean send(Message msg, Map<String, Object> map){
        boolean flag;
        //可以在这里获取系统设置调用哪个平台,暂时用YiTD即可

        flag = YiTDSmsUtil.send(msg);
        return flag;
    }

    //构造redis key
    public static String buildRedisKey(String phone){
        String key = "smsCode-"+phone;
        try {
            key =  EncryptUtil.md5(key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return key;
    }

    //判断是否是有效的手机号
    public static boolean isPhone(String phone){
        if(phone == null) return false;
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        return phone.trim().matches(regex);
    }

    //判断是否是6位数字验证码
    public static boolean isSixCode(String code){
        return code != null && code.trim().matches("^\\d{6}$");
    }

}
