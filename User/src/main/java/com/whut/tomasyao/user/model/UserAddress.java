package com.whut.tomasyao.user.model;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-06-13 21:16
 */

import com.whut.tomasyao.base.model.Area;
import com.whut.tomasyao.base.model.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_address")
public class UserAddress {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "area", referencedColumnName = "code")
    private Area area;
    private Double lng;
    private Double lat;
    private String address;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "real_name")
    private String realName;
    private String phone;
    @Column(name = "is_default")
    private boolean isDefault;

    public UserAddress() {
    }

    public UserAddress(User user, Area area, Double lng, Double lat, String address,
                       String realName, String phone) {
        this.user = user;
        this.area = area;
        this.lng = lng;
        this.lat = lat;
        this.address = address;
        this.isDeleted = false;
        this.createTime = new Date();
        this.realName = realName;
        this.phone = phone;
        this.isDefault = false;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
