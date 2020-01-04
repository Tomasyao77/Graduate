package com.whut.tomasyao.news.model;//package edu.whut.pocket.news.model;
//
//import edu.whut.pocket.base.model.File;
//
//import javax.persistence.*;
//import java.util.Date;
//
///**
// * Created by yuxr .
// */
//@Entity
//@Table(name = "topPic")
//public class TopPic {
//    @Id
//    @GeneratedValue
//    private int id;
//
//    @ManyToOne(targetEntity = File.class)
//    @JoinColumn(name = "top_picture")
//    private File topPic;
//    @ManyToOne(targetEntity = File.class)
//    @JoinColumn(name = "top_picture_thumb")
//    private File topPicThumb;
//    @Column(name = "is_deleted")
//    private boolean isDeleted;
//    @Column(name = "create_time")
//    private Date createTime;
//
//    public TopPic(){}
//
//    public TopPic(File topPic, File topPicThumb, boolean isDeleted, Date createTime) {
//        this.topPic = topPic;
//        this.topPicThumb = topPicThumb;
//        this.isDeleted = isDeleted;
//        this.createTime = createTime;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public File getTopPic() {
//        return topPic;
//    }
//
//    public void setTopPic(File topPic) {
//        this.topPic = topPic;
//    }
//
//    public File getTopPicThumb() {
//        return topPicThumb;
//    }
//
//    public void setTopPicThumb(File topPicThumb) {
//        this.topPicThumb = topPicThumb;
//    }
//
//    public boolean isDeleted() {
//        return isDeleted;
//    }
//
//    public void setDeleted(boolean deleted) {
//        isDeleted = deleted;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//}
