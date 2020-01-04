package com.whut.tomasyao.storeDetail.service;

import com.alibaba.dubbo.rpc.filter.EchoFilter;
import edu.whut.pocket.base.model.File;
import edu.whut.pocket.user.vo.StoreVo;
import edu.whut.pocket.user.vo.UserVerifyVo;

import java.util.List;

public interface StoreDetailService {
    UserVerifyVo getStoreDetailByUserId(int useId) throws Exception;

    List<UserVerifyVo> getNextStores(int storeId) throws Exception;
    int  getNextStoresCount(int storeId) throws Exception;
    int getStoreIdByUserId(int userId) throws Exception;
}
