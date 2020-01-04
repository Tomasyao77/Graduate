package com.whut.tomasyao.base.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * com.whut.athena.common.model
 * Created by YTY on 2016/3/23.
 */
@Entity
@Table(name = "area")
public class Area implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private int code;
    @Column(name = "parent_code")
    private int parentCode;
    private String name;
    private double lng;
    private double lat;
    private int level;
    @Column(name = "city_code")
    private String cityCode;
    @Column(name = "zip_code")
    private String zipCode;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "merger_name")
    private String mergerName;
    private String pinyin;
    private boolean hot;
    public Area() {
    }
    public Area(int id, int code, String mergeName) {
        this.id = id;
        this.code = code;
        this.mergerName = mergeName;
    }

    public Area(int code, int parentCode, String name, double lng, double lat, int level, String cityCode, String zipCode, String shortName, String mergerName, String pinyin) {
        this.code = code;
        this.parentCode = parentCode;
        this.name = name;
        this.lng = lng;
        this.lat = lat;
        this.level = level;
        this.cityCode = cityCode;
        this.zipCode = zipCode;
        this.shortName = shortName;
        this.mergerName = mergerName;
        this.pinyin = pinyin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getParentCode() {
        return parentCode;
    }

    public void setParentCode(int parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCityCode() {
        return cityCode == null ? "" : cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getZipCode() {
        return zipCode == null ? "" : zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getShortName() {
        return shortName == null ? "" : shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }

    public String getPinyin() {
        return pinyin == null ? "" : pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }
}
