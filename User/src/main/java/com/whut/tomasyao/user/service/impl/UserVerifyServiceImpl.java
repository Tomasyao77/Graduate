package com.whut.tomasyao.user.service.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-06-26 08:47
 */

import com.whut.tomasyao.base.common.VerifyType;
import com.whut.tomasyao.base.model.User;
import com.whut.tomasyao.base.mybatis.params.ParamsMap;
import com.whut.tomasyao.base.service.IAreaService;
import com.whut.tomasyao.base.util.HqlUtil;
import com.whut.tomasyao.base.vo.Page;
import com.whut.tomasyao.base.vo.Parameter;
import com.whut.tomasyao.message.common.SmsPlatform;
import com.whut.tomasyao.message.common.SmsType;
import com.whut.tomasyao.message.model.Message;
import com.whut.tomasyao.message.util.MessageUtil;
import com.whut.tomasyao.user.dao.IUserDao;
import com.whut.tomasyao.user.dao.IUserVerifyDao;
import com.whut.tomasyao.user.mapper.UserMapper;
import com.whut.tomasyao.user.model.UserVerify;
import com.whut.tomasyao.user.service.IUserVerifyService;
import com.whut.tomasyao.user.vo.UserVerifyVo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserVerifyServiceImpl implements IUserVerifyService {

    @Autowired
    private IUserVerifyDao userVerifyDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IAreaService areaService;
    @Autowired
    private UserMapper userMapper;

    private static Logger log = Logger.getLogger(UserVerifyServiceImpl.class);

    @Override
    public UserVerify getOneUserVerify(int id) throws Exception {
        return userVerifyDao.getOne(id);
    }

    @Override
    public UserVerify addUserVerify(int userId, String phone, String name, String storeName,
                                    String region, String reason, String parentInviteCode) throws Exception {
        User user = userDao.getOne(userId);
        if (user == null) {//若无此用户则返回空
            return null;
        }
        //一个用户只能一条认证
        UserVerify userVerifyTemp = userVerifyDao.findOne(" from UserVerify u where u.user.id='" + userId + "' ");
        if (userVerifyTemp != null) {
            userVerifyTemp.setPhone(phone);
            userVerifyTemp.setName(name);
            userVerifyTemp.setVerify(VerifyType.待审核);
            userVerifyTemp.setStoreName(storeName);
            userVerifyTemp.setRegion(region);
            userVerifyTemp.setReason(reason);
            //userVerifyTemp.setParentInviteCode(parentInviteCode);//重新认证不需要邀请码
            userVerifyDao.update(userVerifyTemp);
            return userVerifyTemp;
        }
        UserVerify userVerify = new UserVerify(user, phone, name, storeName, region, reason, parentInviteCode);
        return userVerifyDao.save(userVerify);
    }

    @Override
    public UserVerify updateUserVerifyStatus(int id, int verify) throws Exception {
        UserVerify userVerify = userVerifyDao.getOne(id);
        Integer[] integers = new Integer[]{0, 1, 2};
        if (userVerify == null || !Arrays.asList(integers).contains(verify)) {
            return null;
        }
        userVerify.setVerify(VerifyType.values()[verify]);
        userVerify.setUpdateTime(new Date());

        if (verify == 1) {//如果审核通过，改变user表相关信息
            User user = userDao.getOne(userVerify.getUser().getId());
            if (user != null) {
                user.setPhone(userVerify.getPhone());
                user.setName(userVerify.getName());
                userDao.update(user);
            }
        } else if (verify == 2) {//审核不通过
        }
        return userVerify;
    }

    @Override
    public UserVerify updateUserVerify(int id, String phone, String name, String unit, int areaCode, String address,
                                       String storeName, String region, String reason) throws Exception {
        UserVerify userVerify = userVerifyDao.findOne(" from UserVerify u where u.id='" + id + "' ");
        if (userVerify == null) {
            return null;
        }
        userVerify.setPhone(phone);
        userVerify.setName(name);
        userVerify.setUnit(unit);
        userVerify.setVerify(VerifyType.待审核);
        userVerify.setUpdateTime(new Date());
        userVerify.setStoreName(storeName);
        userVerify.setArea(areaService.getAreaByCode(areaCode));
        userVerify.setAddress(address);
        userVerify.setRegion(region);
        userVerify.setReason(reason);
        userVerifyDao.update(userVerify);
        return userVerify;
    }

    @Override
    public UserVerify updateRemark(int id, String remark) throws Exception {
        UserVerify userVerify = userVerifyDao.findOne(" from UserVerify u where u.id='" + id + "' ");
        if(userVerify != null){
            if(remark != null && !remark.isEmpty()){
                userVerify.setRemark(remark);
                userVerifyDao.update(userVerify);
            }
        }
        return userVerify;
    }

    @Override
    public Page<UserVerify> getUserVerifyPageListOwn(int current, int size, int userId, String orderBy, boolean asc) throws Exception {
        List<String> conditions = HqlUtil.getConditions();
        Parameter p = new Parameter();
        conditions.add(" u.user.id = :userId ");
        p.put("userId", userId);
        return userVerifyDao.findPage(current, size,
                HqlUtil.formatHql(" from UserVerify u ",
                        conditions, orderBy, asc),
                HqlUtil.formatHql(" select count(*) from UserVerify u ", conditions), p);
    }

    @Override
    public Page<UserVerify> getUserVerifyPageListAdmin(int current, int size, String search, String orderBy, boolean asc, Integer verify) throws Exception {
        List<String> conditions = HqlUtil.getConditions();
        Parameter p = new Parameter();
        if (search != null && !search.isEmpty()) {
            conditions.add(" u.user.name like :search ");
            p.put("search", "%" + search + "%");
        }
        conditions.add(" u.verify = :verify ");
        p.put("verify", VerifyType.values()[verify]);

        return userVerifyDao.findPage(current, size,
                HqlUtil.formatHql(" from UserVerify u ",
                        conditions, orderBy, asc),
                HqlUtil.formatHql(" select count(*) from UserVerify u ", conditions), p);
    }

    @Override
    public Page<UserVerifyVo> getUserVerifyPageListOwnMybatis(int current, int size, int userId,
                                                              String orderBy, boolean asc) throws Exception {
        //分页查询参数构造(其它业务分页查询与此类似,改变的最多是查询参数构造)
//        ParamsMap hashMap = ParamsMap.getPageInstance(current, size, orderBy, asc);
//        hashMap.put("store", store);
//        //开始查询记录和数量
//        List<StoreHotProductVo> list = storeMapper.getStoreHotProductPage(hashMap);
//        Integer count = storeMapper.findCountStoreHotProductPage(hashMap);
//        return new Page<>(list, count, current, size);
        return null;
    }

    @Override
    public Page<UserVerifyVo> getUserVerifyPageListAdminMybatis(int current, int size, String search, String orderBy,
                                                                boolean asc, Integer verify) throws Exception {
        //分页查询参数构造(其它业务分页查询与此类似,改变的最多是查询参数构造)
        ParamsMap hashMap = ParamsMap.getPageInstance(current, size, orderBy, asc);
        hashMap.put("search", search);
        hashMap.put("verify", verify);
        //开始查询记录和数量
        List<UserVerifyVo> list = userMapper.getUserVerifyPage(hashMap);
        Integer count = userMapper.findCountUserVerifyPage(hashMap);
        return new Page<>(list, count, current, size);
    }
}
