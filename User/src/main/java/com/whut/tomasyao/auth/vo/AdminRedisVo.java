package com.whut.tomasyao.auth.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * edu.whut.change.login.model
 * Created by YTY on 2016/6/9.
 */
public class AdminRedisVo implements Serializable {

    private static final long serialVersionUID = 63790L;
    private int id;
    private String username;
    private String type;//role name
    private Date visitTime;//访问时间
    private String moduleJson;
    private Set<Integer> moduleSet;

    public AdminRedisVo() {
    }

    public AdminRedisVo(int id, String username, String type, Date visitTime,
                        String moduleJson, Set<Integer> moduleSet) {
        this.id = id;
        this.username = username;
        this.type = type;
        this.visitTime = visitTime;
        this.moduleJson = moduleJson;
        this.moduleSet = moduleSet;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getModuleJson() {
        return moduleJson;
    }

    public void setModuleJson(String moduleJson) {
        this.moduleJson = moduleJson;
    }

    public Set<Integer> getModuleSet() {
        return moduleSet;
    }

    public void setModuleSet(Set<Integer> moduleSet) {
        this.moduleSet = moduleSet;
    }
}
