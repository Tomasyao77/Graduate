package com.whut.tomasyao.admin.vo;

import java.util.Date;
import java.util.List;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-04-02 19:54
 * mybatis
 */

public class ModuleVo {

    private int id;
    private String name;
    private int parentId;
    private String url;
    private String icon;
    private int index;
    private int type;
    private int level;//前端根据这个判断是否可增加树的层级
    private boolean status;
    private Date createTime;
    private Date updateTime;
    private List<ModuleVo> nodes;

    public ModuleVo() {
    }

    public List<ModuleVo> getNodes() {
        return nodes;
    }

    public void setNodes(List<ModuleVo> nodes) {
        this.nodes = nodes;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
