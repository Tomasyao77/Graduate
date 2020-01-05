package com.whut.tomasyao.auth.vo;


import com.whut.tomasyao.auth.model.UserType;
import com.whut.tomasyao.base.model.User;
import com.whut.tomasyao.login.model.UserPlatform;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * edu.whut.change.login.model
 * Created by YTY on 2016/6/9.
 */
public class UserRedisVo implements Serializable {
    private static final long serialVersionUID = 63791L;
    private int id;
    private String username;
    private UserType type;//UserType
    private Date visitTime;//访问时间
    private UserPlatform platform;//小程序 android ios
    private User user;//用户详情

    public UserRedisVo() {
    }

    public UserRedisVo(int id, String username, UserType type, UserPlatform platform, User user) {
        this.id = id;
        this.username = username;
        this.type = type;
        this.platform = platform;
        this.user = user;
        this.visitTime = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public UserPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(UserPlatform platform) {
        this.platform = platform;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
