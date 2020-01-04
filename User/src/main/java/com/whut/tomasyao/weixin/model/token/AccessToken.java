package com.whut.tomasyao.weixin.model.token;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-07 13:33
 */

public class AccessToken {
    private String access_token;
    private int expires_in;

    public AccessToken() {
    }

    public String getAccess_token() {
        return this.access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
