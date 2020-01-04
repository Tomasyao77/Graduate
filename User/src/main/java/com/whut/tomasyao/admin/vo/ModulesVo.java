package com.whut.tomasyao.admin.vo;

import java.util.Date;
import java.util.List;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-04-02 19:54
 * hibernate
 */

public class ModulesVo {

    private int id;
    private String name;
    private int parentId;
    private String url;
    private String icon;
    private int index;
    private Date createTime;
    private Date updateTime;
    private List<ModulesVo> nodes;
    private int level;

    public ModulesVo() {
    }

    public ModulesVo(int id, String name, int parentId, String url, String icon, int index,
                     Date createTime, Date updateTime, List<ModulesVo> nodes, int level) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.url = url;
        this.icon = icon;
        this.index = index;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.nodes = nodes;
        this.level = level;
    }

    public List<ModulesVo> getNodes() {
        return nodes;
    }

    public void setNodes(List<ModulesVo> nodes) {
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
