package com.whut.tomasyao.admin.model;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 19:29
 */

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "module")
public class Module {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Column(name = "parent_id")
    private int parentId;
    private String url;
    private String icon;
    @Column(name = "index1")
    private int index;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    private boolean status;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    private int type;

    public Module() {
    }

    public Module(String name, int parentId, String url, String icon, int index, Date createTime,
                  Date updateTime, boolean status, boolean isDeleted, int type) {
        this.name = name;
        this.parentId = parentId;
        this.url = url;
        this.icon = icon;
        this.index = index;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
        this.isDeleted = isDeleted;
        this.type = type;
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
