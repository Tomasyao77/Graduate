package com.whut.tomasyao.weixin.service;

import com.whut.tomasyao.weixin.model.token.AccessToken;
import com.whut.tomasyao.weixin.smallCode.SmallCodeConstant;
import com.whut.tomasyao.weixin.util.AccessTokenUtil;
import org.apache.log4j.Logger;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-07 13:34
 */

public class TokenThread implements Runnable{
    private static final Logger logger = Logger.getLogger(TokenThread.class);
    public static final String appID = SmallCodeConstant.APPID;
    public static final String appScret = SmallCodeConstant.APP_SECRECT;
    public static AccessToken access_token = null;

    public TokenThread() {
    }

    public static AccessToken getAccess_token() {
        return access_token;
    }

    @Override
    public void run() {
        while(true) {
            try {
                logger.info("开始获取access_token");
                access_token = AccessTokenUtil.getAccessToken(appID, appScret);
                if(null != access_token) {
                    logger.info("accessToken获取成功：" + access_token.getExpires_in());
                    Thread.sleep((long)((access_token.getExpires_in() - 200) * 1000));
                } else {
                    Thread.sleep(60000L);
                }
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }
        }
    }
}
