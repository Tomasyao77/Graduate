package com.whut.tomasyao.message.service;

import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.message.model.LogMessage;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-09-15 15:35
 */

public interface ILogMessageService {

    Integer addOneLogMessage(String string) throws Exception;

    Page<LogMessage> getLogMessagePage(int current, int size, String orderBy, Boolean asc, String search) throws Exception;

    //判断验证码是否正确
    boolean checkCode(String phone, String code) throws Exception;
}
