package com.whut.tomasyao.log.service.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-07-27 15:07
 */

import edu.whut.pocket.base.common.MavenModule;
import edu.whut.pocket.base.mybatis.params.ParamsMap;
import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.log.mapper.LogUserMapper;
import edu.whut.pocket.log.vo.LogUserVo;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import edu.whut.pocket.log.model.LogUser;
import edu.whut.pocket.log.service.ILogUserService;

import java.util.Date;
import java.util.List;

@Service
public class LogUserServiceImpl implements ILogUserService {

    @Autowired
    private LogUserMapper logUserMapper;


    @Override
    public Integer addLogUser(String json) throws Exception {
        LogUser logUser = new LogUser();
        JSONObject jsonObject = new JSONObject(json);
        //kafka消费者调用此方法将日志数据写入mysql,以下是解析json数据并构造成LogUser类
        logUser.setUser_id(jsonObject.getInt("user_id"));
        logUser.setCreate_time(new Date());
        logUser.setParams(jsonObject.getString("params"));
        logUser.setMethod_starttime(new Date(jsonObject.getLong("method_starttime")));
        logUser.setClazz(jsonObject.getString("clazz"));
        logUser.setMethod_timetaking((double)jsonObject.get("method_timetaking"));
        logUser.setMethod(jsonObject.getString("method"));
        logUser.setMaven_module(MavenModule.valueOf(jsonObject.getString("maven_module")));
        logUser.setIp(jsonObject.getString("ip"));
        logUser.setOp_type(jsonObject.getString("op_type"));
        logUser.setDescription(jsonObject.getString("description"));
        logUser.setResult(jsonObject.getString("result"));

        return logUserMapper.addLogUser(logUser);
    }

    @Override
    public Page<LogUserVo> getLogUserPage(int current, int size, String orderBy, Boolean asc,
                                          Integer user_id, String clazz, String method, String maven_module,
                                          String startDate, String endDate) throws Exception {
        //分页查询参数构造(其它业务分页查询与此类似,改变的最多是查询参数构造)
        ParamsMap hashMap = ParamsMap.getPageInstance(current, size, orderBy, asc);
        hashMap.put("user_id", user_id);
        hashMap.put("clazz", clazz);
        hashMap.put("method", method);
        hashMap.put("maven_module", maven_module);
        hashMap.put("startDate", startDate);
        hashMap.put("endDate", endDate);
        //开始查询记录和数量
        List<LogUserVo> list = logUserMapper.getLogUserPage(hashMap);
        Integer count = logUserMapper.findCountLogUserPage(hashMap);
        return new Page<>(list, count, current, size);
    }
}
