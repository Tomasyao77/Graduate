package com.whut.tomasyao.user.vo;

import com.whut.tomasyao.file.vo.FileVo;

import java.util.Date;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-07-14 14:41
 * 店铺信息包括了店主信息
 */
public class StoreVo {
    private int id;
    private String name;
    private UserVo user;
    private Date createTime;
    private String description;
    private String headUrl;
    private String sellTotal;
    private String storeGoodRatio;
    private String storeGrade;
    private int status;
    private FileVo qrCode;
    private String storeInviteCode;
    private String parentInviteCode;
    private int isHeadStore;    //是否为总店

    public StoreVo() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getSellTotal() {
        return sellTotal;
    }

    public void setSellTotal(String sellTotal) {
        this.sellTotal = sellTotal;
    }

    public String getStoreGoodRatio() {
        return storeGoodRatio;
    }

    public void setStoreGoodRatio(String storeGoodRatio) {
        this.storeGoodRatio = storeGoodRatio;
    }

    public String getStoreGrade() {
        return storeGrade;
    }

    public void setStoreGrade(String storeGrade) {
        this.storeGrade = storeGrade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public FileVo getQrCode() {
        return qrCode;
    }

    public void setQrCode(FileVo qrCode) {
        this.qrCode = qrCode;
    }

    public String getStoreInviteCode() {
        return storeInviteCode;
    }

    public void setStoreInviteCode(String storeInviteCode) {
        this.storeInviteCode = storeInviteCode;
    }

    public String getParentInviteCode() {
        return parentInviteCode;
    }

    public void setParentInviteCode(String parentInviteCode) {
        this.parentInviteCode = parentInviteCode;
    }

    public int getIsHeadStore() {
        return isHeadStore;
    }

    public void setIsHeadStore(int isHeadStore) {
        this.isHeadStore = isHeadStore;
    }
}
