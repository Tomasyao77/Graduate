package com.whut.tomasyao.config.model;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-04-10 16:33
 */

import com.whut.tomasyao.base.common.ConfigType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "config")
public class Config {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Column(name = "name_en")
    private String nameEn;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Config parent;
    @Enumerated
    @Column(name = "value_type")
    private ConfigType valueType;
    @Column(name = "create_time")
    private Date createTime;
    private String value;
    @Column(name = "is_deleted")
    private boolean isDeleted;

    public Config() {
    }

    public Config(String name, String nameEn, Config parent, ConfigType valueType, Date createTime,
                  String value, boolean isDeleted) {
        this.name = name;
        this.nameEn = nameEn;
        this.parent = parent;
        this.valueType = valueType;
        this.createTime = createTime;
        this.value = value;
        this.isDeleted = isDeleted;
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

    public Config getParent() {
        return parent;
    }

    public void setParent(Config parent) {
        this.parent = parent;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
