package com.whut.tomasyao.log.model;

import com.whut.tomasyao.base.common.MavenModule;

import java.util.Date;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-07-27 14:56
 * 用户操作日志记录
 */

public class LogUser {
    private int id;
    private int user_id;
    private Date create_time;
    private String params;
    private Date method_starttime;
    private String clazz;
    private double method_timetaking;
    private String method;
    private MavenModule maven_module;
    private String ip;
    private String op_type;
    private String description;
    private String result;

    public LogUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Date getMethod_starttime() {
        return method_starttime;
    }

    public void setMethod_starttime(Date method_starttime) {
        this.method_starttime = method_starttime;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public double getMethod_timetaking() {
        return method_timetaking;
    }

    public void setMethod_timetaking(double method_timetaking) {
        this.method_timetaking = method_timetaking;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public MavenModule getMaven_module() {
        return maven_module;
    }

    public void setMaven_module(MavenModule maven_module) {
        this.maven_module = maven_module;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOp_type() {
        return op_type;
    }

    public void setOp_type(String op_type) {
        this.op_type = op_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
