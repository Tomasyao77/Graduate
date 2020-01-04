package com.whut.tomasyao.admin.model;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 19:42
 */

import edu.whut.pocket.base.common.AdminType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    private String description;
    @Enumerated
    private AdminType type;

    public Role() {
    }

    public Role(String name, String description, Date createTime, Date updateTime, boolean isDeleted, int type) {
        this.name = name;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDeleted = isDeleted;
        this.description = description;
        this.type = AdminType.values()[type];
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AdminType getType() {
        return type;
    }

    public void setType(AdminType type) {
        this.type = type;
    }
}
