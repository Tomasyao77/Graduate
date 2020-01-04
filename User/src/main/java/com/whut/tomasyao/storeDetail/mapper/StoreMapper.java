package com.whut.tomasyao.storeDetail.mapper;

import edu.whut.pocket.user.vo.StoreVo;
import edu.whut.pocket.user.vo.UserVerifyVo;
import edu.whut.pocket.user.vo.UserVo;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface StoreMapper {
    UserVo getUser(int id);

    String getInviteCodeByStoreId(int storeId) throws Exception;
    int getNextStoresCount(String inviteCode) throws Exception;
    List<UserVerifyVo> getNextStoresByInviteCode(String inviteCode) throws Exception;
    int getStoreIdByUserId(int userId) throws Exception;

}
