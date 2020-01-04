package com.whut.tomasyao.config.vo;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-04-11 11:06
 */

import com.whut.tomasyao.base.common.ConfigType;

import java.util.Date;

public class ConfigVo {

    private int id;
    private String name;
    private String name_en;
    private ConfigType value_type;
    private Date create_time;
    private String value;
    private Integer parent_id;

    public ConfigVo() {
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

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public ConfigType getValue_type() {
        return value_type;
    }

    public void setValue_type(ConfigType value_type) {
        this.value_type = value_type;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }
}
