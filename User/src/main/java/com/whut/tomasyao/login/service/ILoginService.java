package com.whut.tomasyao.login.service;

import edu.whut.pocket.auth.vo.AdminRedisVo;
import edu.whut.pocket.auth.vo.UserRedisVo;
import edu.whut.pocket.base.model.User;

/**
 * edu.whut.change.login.service
 * Created by YTY on 2016/6/9.
 */
public interface ILoginService {

    //管理员登录
    AdminRedisVo loginAdmin(String token, String username, String password) throws Exception;
    //管理员退出登录
    void logoutAdmin(int userId, String token) throws Exception;
    //swagger登录
    boolean loginSwagger(String username, String password, String ip) throws Exception;
    UserRedisVo loginUser(String token, String phone, String code, Integer platform) throws Exception;
    UserRedisVo loginSmallCode(String token, String openId) throws Exception;
}
