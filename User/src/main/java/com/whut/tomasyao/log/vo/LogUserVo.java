package com.whut.tomasyao.log.vo;

import edu.whut.pocket.base.common.MavenModule;
import edu.whut.pocket.user.vo.UserVo;

import java.util.Date;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-07-27 14:56
 */

public class LogUserVo {
    private int id;
    private UserVo user;
    private Date createTime;
    private String params;
    private Date methodStartTime;
    private String clazz;
    private double methodTimeTaking;
    private String method;
    private MavenModule mavenModule;
    private String ip;
    private String opType;
    private String description;
    private String result;

    public LogUserVo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Date getMethodStartTime() {
        return methodStartTime;
    }

    public void setMethodStartTime(Date methodStartTime) {
        this.methodStartTime = methodStartTime;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public double getMethodTimeTaking() {
        return methodTimeTaking;
    }

    public void setMethodTimeTaking(double methodTimeTaking) {
        this.methodTimeTaking = methodTimeTaking;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public MavenModule getMavenModule() {
        return mavenModule;
    }

    public void setMavenModule(MavenModule mavenModule) {
        this.mavenModule = mavenModule;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
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
