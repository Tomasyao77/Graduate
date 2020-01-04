package com.whut.tomasyao.log.mapper;

import com.whut.tomasyao.log.model.LogUser;
import com.whut.tomasyao.log.vo.LogUserVo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zouy on 18-7-27.
 */
public interface LogUserMapper {
    //写入一条日志
    Integer addLogUser(LogUser logUser) throws Exception;

    List<LogUserVo> getLogUserPage(HashMap<String, Object> hashMap) throws Exception;

    Integer findCountLogUserPage(HashMap<String, Object> hashMap) throws Exception;
}
