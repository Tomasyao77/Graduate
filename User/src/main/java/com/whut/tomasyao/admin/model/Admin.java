package com.whut.tomasyao.admin.model;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 19:55
 */

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue
    private int id;
    private String username;
    @JsonIgnore
    private String password;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    private boolean status;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Admin parent;
    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role_id")
    private Role role;
    private String contact;
    private String name;

    public Admin() {
    }

    public Admin(String username, String password, Date createTime, Date updateTime, boolean status,
                 boolean isDeleted, Admin parent, Role role, String contact, String name) {
        this.username = username;
        this.password = password;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
        this.isDeleted = isDeleted;
        this.parent = parent;
        this.role = role;
        this.contact = contact;
        this.name = name;
    }

    public Admin getParent() {
        return parent;
    }

    public void setParent(Admin parent) {
        this.parent = parent;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
