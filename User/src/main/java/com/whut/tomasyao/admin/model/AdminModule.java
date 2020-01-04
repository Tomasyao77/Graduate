package com.whut.tomasyao.admin.model;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 20:09
 */

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "admin_module")
public class AdminModule {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "admin_id")
    private int adminId;
    @Column(name = "module_id")
    private int moduleId;
    @Column(name = "create_time")
    private Date createTime;

    public AdminModule() {
    }

    public AdminModule(int adminId, int moduleId, Date createTime) {
        this.adminId = adminId;
        this.moduleId = moduleId;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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
}
