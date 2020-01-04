package com.whut.tomasyao.user.service;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-06-26 08:47
 */

import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.user.model.UserVerify;
import edu.whut.pocket.user.vo.UserVerifyVo;

public interface IUserVerifyService {

    //获取一条记录
    UserVerify getOneUserVerify(int id) throws Exception;
    //提交审核
    UserVerify addUserVerify(int userId, String phone, String name,
                             String storeName, String region, String reason, String parentInviteCode) throws Exception;
    //改变审核状态
    UserVerify updateUserVerifyStatus(int id, int verify) throws Exception;
    //修改审核信息
    UserVerify updateUserVerify(int id, String phone, String name, String unit, int areaCode, String address,
                                String storeName, String region, String reason) throws Exception;
    //修改备注
    UserVerify updateRemark(int id, String remark) throws Exception;

    /**
     * hibernate
     */
    //用户获取自己的审核列表
    Page<UserVerify> getUserVerifyPageListOwn(int current, int size, int userId, String orderBy, boolean asc) throws Exception;
    //管理员获取全部的审核列表
    Page<UserVerify> getUserVerifyPageListAdmin(int current, int size, String search, String orderBy, boolean asc, Integer verify) throws Exception;
    /**
     * mybatis
     */
    //用户获取自己的审核列表
    Page<UserVerifyVo> getUserVerifyPageListOwnMybatis(int current, int size, int userId, String orderBy, boolean asc) throws Exception;
    //管理员获取全部的审核列表
    Page<UserVerifyVo> getUserVerifyPageListAdminMybatis(int current, int size, String search, String orderBy, boolean asc, Integer verify) throws Exception;
}
