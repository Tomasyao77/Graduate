package com.whut.tomasyao.base.model;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-04-16 16:22
 */

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "institution")
public class Institution {
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
    private String address;
    private Double lng;
    private Double lat;
    private String contact;
    private String content;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public Institution() {
    }

    public Institution(boolean status, File picture, File pictureThumb, String name, Area area,
                       String address, Double lng, Double lat, String contact, String content,
                       Date createTime, Date updateTime, boolean isDeleted) {
        this.status = status;
        this.picture = picture;
        this.pictureThumb = pictureThumb;
        this.name = name;
        this.area = area;
        this.address = address;
        this.lng = lng;
        this.lat = lat;
        this.contact = contact;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDeleted = isDeleted;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

}
