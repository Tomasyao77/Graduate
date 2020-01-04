package com.whut.tomasyao.base.model;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-24 11:07
 */

import com.whut.tomasyao.base.common.FileType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "file")
public class File implements Serializable{
    @Id
    @GeneratedValue
    private int id;
    private String url;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Enumerated
    private FileType type;

    public File() {
    }

    public File(String url, Date createTime, boolean isDeleted) {
        this.url = url;
        this.createTime = createTime;
        this.isDeleted = isDeleted;
        this.type = FileType.图片;
    }

    public File(String url, Date createTime, boolean isDeleted, FileType type) {
        this.url = url;
        this.createTime = createTime;
        this.isDeleted = isDeleted;
        this.type = type;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
