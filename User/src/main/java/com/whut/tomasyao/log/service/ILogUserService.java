package com.whut.tomasyao.log.service;

import com.whut.tomasyao.base.vo.Page;
import com.whut.tomasyao.log.vo.LogUserVo;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-07-27 15:06
 */

public interface ILogUserService {

    //写入一条日志
    Integer addLogUser(String json) throws Exception;

    Page<LogUserVo> getLogUserPage(int current, int size, String orderBy, Boolean asc,
                                   Integer user_id, String clazz, String method, String maven_module,
                                   String startDate, String endDate) throws Exception;
}
