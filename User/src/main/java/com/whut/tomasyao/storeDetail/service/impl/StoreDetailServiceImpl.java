package com.whut.tomasyao.storeDetail.service.impl;/*
 *@ClassName: StoreDetailServiceImpl
 *@Desctiption:
 *@Author: XWY
 *@Date: 2019/4/3 16:51
 *@Version: 1.0
 */

import edu.whut.pocket.base.model.File;
import edu.whut.pocket.storeDetail.mapper.StoreMapper;
import edu.whut.pocket.storeDetail.service.StoreDetailService;
import edu.whut.pocket.user.mapper.UserMapper;
import edu.whut.pocket.user.vo.UserVerifyVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreDetailServiceImpl implements StoreDetailService {

    public static final Logger logger = Logger.getLogger(StoreDetailServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public UserVerifyVo getStoreDetailByUserId(int userId) throws Exception {
        UserVerifyVo storeDetailByUserId = userMapper.getStoreDetailByUserId(userId);
        //该店的邀请码
        return storeDetailByUserId;
    }

    @Override
    public int getNextStoresCount(int storeId) throws Exception {
        //查出邀请码
        String inviteCodeByStoreId = storeMapper.getInviteCodeByStoreId(storeId);
        //根据邀请码哦查出邀请下一级店铺的数量
        int nextStoresCount = storeMapper.getNextStoresCount(inviteCodeByStoreId);
        return nextStoresCount;
    }

    @Override
    public List<UserVerifyVo> getNextStores(int userId) throws Exception {
        int storeId = getStoreIdByUserId(userId);
        //查出邀请码
        String inviteCodeByStoreId = storeMapper.getInviteCodeByStoreId(storeId);
        logger.info("inviteCodeByStoreId: " + inviteCodeByStoreId);
        //根据邀请码查出邀请的下一级店铺
        List<UserVerifyVo> nextStoresByInviteCode = storeMapper.getNextStoresByInviteCode(inviteCodeByStoreId);
        for (UserVerifyVo userVerifyVo : nextStoresByInviteCode){
            logger.info("该商户的id为： " + userVerifyVo.getStore_owen_id());
        }
        logger.info("查出了" + nextStoresByInviteCode.size() + "家下一级店铺");
        return nextStoresByInviteCode;
    }

    @Override
    public int getStoreIdByUserId(int userId) throws Exception {
        int storeId = storeMapper.getStoreIdByUserId(userId);
        return storeId;
    }

}
