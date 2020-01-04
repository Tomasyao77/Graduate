package com.whut.tomasyao.storeDetail.vo;/*
 *@ClassName: StoreDetailVo
 *@Desctiption:
 *@Author: XWY
 *@Date: 2019/4/7 18:48
 *@Version: 1.0
 */

import edu.whut.pocket.user.vo.UserVerifyVo;

import java.util.List;

public class StoreDetailVo {
    private UserVerifyVo userVerifyVo;
    //    private List<UserVerifyVo> nextStores;
    private int nextStoresCount;
    private String storeBannerUrl;

    public StoreDetailVo() {
    }

    public UserVerifyVo getUserVerifyVo() {
        return userVerifyVo;
    }

    public void setUserVerifyVo(UserVerifyVo userVerifyVo) {
        this.userVerifyVo = userVerifyVo;
    }

    public int getNextStoresCount() {
        return nextStoresCount;
    }

    public void setNextStoresCount(int nextStoresCount) {
        this.nextStoresCount = nextStoresCount;
    }

    public String getStoreBannerUrl() {
        return storeBannerUrl;
    }

    public void setStoreBannerUrl(String storeBannerUrl) {
        this.storeBannerUrl = storeBannerUrl;
    }
}
