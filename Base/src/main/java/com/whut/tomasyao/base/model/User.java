package com.whut.tomasyao.base.model;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-02-11 20:39
 */

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private int id;
    private boolean status;
    @ManyToOne(targetEntity = File.class)
    @JoinColumn(name = "picture")
    private File picture;
    @ManyToOne(targetEntity = File.class)
    @JoinColumn(name = "picture_thumb")
    private File pictureThumb;
    private String name;
    @ManyToOne
    @JoinColumn(name = "area", referencedColumnName = "code")
    private Area area;
    @Column(name = "create_time")
    private Date createTime;
    private String phone;
    private String username;
    @JsonIgnore
    private String password;
    private boolean verify;
    @Column(name = "open_id")
    private String openId;
    private String address;
    private String description;
    private Double lng;
    private Double lat;
    private String wx_name;

    public User() {
    }

    public User(boolean status, File picture, File pictureThumb, String name, Area area, Date createTime,
                String phone, String username, String password, String openId) {
        this.status = status;
        this.picture = picture;
        this.pictureThumb = pictureThumb;
        this.name = name;
        this.area = area;
        this.createTime = createTime;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.verify = false;
        this.openId = openId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public File getPictureThumb() {
        return pictureThumb;
    }

    public void setPictureThumb(File pictureThumb) {
        this.pictureThumb = pictureThumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getWx_name() {
        return wx_name;
    }

    public void setWx_name(String wx_name) {
        this.wx_name = wx_name;
    }
}
