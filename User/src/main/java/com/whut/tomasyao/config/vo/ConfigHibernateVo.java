package com.whut.tomasyao.config.vo;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-04-11 11:06
 */

import edu.whut.pocket.base.common.ConfigType;

import java.util.Date;

public class ConfigHibernateVo {

    private int id;
    private String name;
    private String nameEn;
    private ConfigType valueType;
    private Date createTime;
    private String value;

    private int parentId;
    private String parentName;
    private String parentNameEn;

    public ConfigHibernateVo() {
    }

    public ConfigHibernateVo(int id, String name, String nameEn, ConfigType valueType, Date createTime,
                    String value, int parentId, String parentName, String parentNameEn) {
        this.id = id;
        this.name = name;
        this.nameEn = nameEn;
        this.valueType = valueType;
        this.createTime = createTime;
        this.value = value;
        this.parentId = parentId;
        this.parentName = parentName;
        this.parentNameEn = parentNameEn;
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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public ConfigType getValueType() {
        return valueType;
    }

    public void setValueType(ConfigType valueType) {
        this.valueType = valueType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentNameEn() {
        return parentNameEn;
    }

    public void setParentNameEn(String parentNameEn) {
        this.parentNameEn = parentNameEn;
    }

}
