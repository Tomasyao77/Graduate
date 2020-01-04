package com.whut.tomasyao.admin.vo;

import java.util.Date;

public class AdminVo {
    private Integer institutionId;
    private String institutionName;
    private String picture;
    private String pictureThumb;
    private String areaMergerName;
    private Integer adminId;
    private String adminName;
    private Date adminCreateTime;

    public AdminVo() {
    }

    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureThumb() {
        return pictureThumb;
    }

    public void setPictureThumb(String pictureThumb) {
        this.pictureThumb = pictureThumb;
    }

    public String getAreaMergerName() {
        return areaMergerName;
    }

    public void setAreaMergerName(String areaMergerName) {
        this.areaMergerName = areaMergerName;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Date getAdminCreateTime() {
        return adminCreateTime;
    }

    public void setAdminCreateTime(Date adminCreateTime) {
        this.adminCreateTime = adminCreateTime;
    }
}
