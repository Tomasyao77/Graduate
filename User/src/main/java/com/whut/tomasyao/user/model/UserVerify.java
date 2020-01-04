package com.whut.tomasyao.user.model;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-06-13 21:16
 */

import edu.whut.pocket.base.common.VerifyType;
import edu.whut.pocket.base.model.Area;
import edu.whut.pocket.base.model.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_verify")
public class UserVerify {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String phone;
    @Column(name = "real_name")
    private String name;
    private String unit;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Enumerated
    private VerifyType verify;
    @ManyToOne
    @JoinColumn(name = "area", referencedColumnName = "code")
    private Area area;
    private String address;
    private Double lng;
    private Double lat;
    @Column(name = "store_name")
    private String storeName;
    private String region;
    private String reason;
    @Column(name = "parent_invite_code")
    private String parentInviteCode;
    private String remark;//备注

    public UserVerify() {
    }

    public UserVerify(User user, String phone, String name, String storeName,
                      String region, String reason, String parentInviteCode) {
        this.user = user;
        this.phone = phone;
        this.name = name;
        this.createTime = new Date();
        this.verify = VerifyType.待审核;
        this.storeName = storeName;
        this.region = region;
        this.reason = reason;
        this.parentInviteCode = parentInviteCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public VerifyType getVerify() {
        return verify;
    }

    public void setVerify(VerifyType verify) {
        this.verify = verify;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
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
}
