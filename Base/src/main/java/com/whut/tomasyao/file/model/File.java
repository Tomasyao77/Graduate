package com.whut.tomasyao.file.model;

import com.whut.tomasyao.base.common.FileType;

import java.util.Date;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-07-17 09:45
 */

public class File {
    private int id;
    private String url;
    private Date create_time;
    private boolean is_deleted;
    private FileType type;
    private Integer age;

    public File() {
    }

    public File(String url) {
        this.url = url;
        this.create_time = new Date();
        this.is_deleted = false;
        this.type = FileType.图片;
    }

    public File(String url, int type) {
        this.url = url;
        this.create_time = new Date();
        this.is_deleted = false;
        this.type = FileType.values()[type];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
