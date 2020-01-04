package com.whut.tomasyao.user.service;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-02-11 20:46
 */

import edu.whut.pocket.base.model.File;
import edu.whut.pocket.base.model.User;
import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.user.vo.UserVo;

import java.util.List;

public interface IUserService {

    //获取一条记录
    UserVo getOneUser(int id) throws Exception;

    UserVo getOneUser(int id, String phone) throws Exception;

    Integer findCount(String phone) throws Exception;

    List<User> findList(String phone) throws Exception;

    Page<User> findPage(String phone, int current, int size) throws Exception;

    Integer updateUser(int id, String phone, String name) throws Exception;

    //获取用户分页列表
    Page<User> getUserPageList(int current, int size, int areaCode, int level,
                               String name, String orderBy, Boolean asc);

    //新增一条记录
    User addOneUser(String password, String openId, Integer platform, String wxName) throws Exception;

    //修改基本信息
    User updateOneUser(int id, String phone, String name) throws Exception;

    //修改用户头像
    User updateAvatar(int id, File picture, File pictureThumb) throws Exception;

    //通过openId获取用户
    User getUserByOpenId(String openId) throws Exception;

    //修改用户微信信息
    Integer updateUserWx(int id, String wxName) throws Exception;

}
