package com.whut.tomasyao.user.vo;

import java.util.Date;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-11-12 10:18
 */

public class UserVerifyVo {
    private int id;
    private UserVo user;
    private int store_owen_id;
    private int is_head_store;
    private String phone;
    private String name;
    private Date createTime;
    private Date updateTime;
    private int verify;
    private String storeName;
    private String region;
    private String reason;
    private String parentInviteCode;
    private String remark;
    private StoreVo store;

    public UserVerifyVo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public int getStore_owen_id() {
        return store_owen_id;
    }

    public void setStore_owen_id(int store_owen_id) {
        this.store_owen_id = store_owen_id;
    }

    public int getIs_head_store() {
        return is_head_store;
    }

    public void setIs_head_store(int is_head_store) {
        this.is_head_store = is_head_store;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getVerify() {
        return verify;
    }

    public void setVerify(int verify) {
        this.verify = verify;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getParentInviteCode() {
        return parentInviteCode;
    }

    public void setParentInviteCode(String parentInviteCode) {
        this.parentInviteCode = parentInviteCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public StoreVo getStore() {
        return store;
    }

    public void setStore(StoreVo store) {
        this.store = store;
    }
}
