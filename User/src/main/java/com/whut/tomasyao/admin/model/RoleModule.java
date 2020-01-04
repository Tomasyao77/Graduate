package com.whut.tomasyao.admin.model;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 19:49
 */

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "role_module")
public class RoleModule {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "role_id")
    private int roleId;
    @Column(name = "module_id")
    private int moduleId;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "btn_auth")
    private String btnAuth;

    public RoleModule() {
    }

    public RoleModule(int roleId, int moduleId, Date createTime, boolean isDeleted) {
        this.roleId = roleId;
        this.moduleId = moduleId;
        this.createTime = createTime;
        this.isDeleted = isDeleted;
        this.btnAuth = "0123";//默认拥有增0 删1 改2 禁用3 权限
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getBtnAuth() {
        return btnAuth;
    }

    public void setBtnAuth(String btnAuth) {
        this.btnAuth = btnAuth;
    }
}
