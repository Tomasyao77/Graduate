package com.whut.tomasyao.user.vo;

import com.whut.tomasyao.file.vo.FileVo;

import java.util.Date;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-06-29 14:49
 */

public class UserVo {
    private int id;
    private String username;
    private String openId;
    private Date createTime;
    private String phone;
    private String name;
    private FileVo picture;
    private FileVo pictureThumb;

    public UserVo() {//要有空的构造方法,mybatis查询需要
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileVo getPicture() {
        return picture;
    }

    public void setPicture(FileVo picture) {
        this.picture = picture;
    }

    public FileVo getPictureThumb() {
        return pictureThumb;
    }

    public void setPictureThumb(FileVo pictureThumb) {
        this.pictureThumb = pictureThumb;
    }
}
