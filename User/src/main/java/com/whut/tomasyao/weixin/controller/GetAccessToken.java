package com.whut.tomasyao.weixin.controller;

import edu.whut.pocket.weixin.service.TokenThread;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-07 13:40
 */

public class GetAccessToken {
    public GetAccessToken() {
    }

    private void init() {
        (new Thread(new TokenThread())).start();
    }
}
