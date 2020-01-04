package com.whut.tomasyao.auth.model;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-05 11:02
 */

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "swagger")
public class Swagger {
    @Id
    @GeneratedValue
    private int id;
    private String ip;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    private int num;

    public Swagger() {
    }

    public Swagger(String ip, Date createTime, Date updateTime) {
        this.ip = ip;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.num = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
