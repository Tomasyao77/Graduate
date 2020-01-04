package com.whut.tomasyao.news.model;

import edu.whut.pocket.base.common.StatusType;
import edu.whut.pocket.base.model.File;
import edu.whut.pocket.common.NewsItem;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "news_item")
    private NewsItem newsItem;
    private String title;

    @ManyToOne(targetEntity = File.class)
    @JoinColumn(name = "picture")
    private File picture;

    @ManyToOne(targetEntity = File.class)
    @JoinColumn(name = "picture_thumb")
    private File pictureThumb;

    private String abstracts;
    //private String content;

    private int view;
    private StatusType status;
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "content_url")
    private String contentUrl;

    public News(){

    }

    public News(String title, File picture, File pictureThumb, String abstracts, int view, String content, Date createTime) {
        this.title = title;
        this.picture = picture;
        this.pictureThumb = pictureThumb;
        this.abstracts = abstracts;
        this.view = view;
        //this.content = content;
       // this.isDeleted = isDeleted;
        this.createTime = createTime;
    }

    public News(String title, File picture, File pictureThumb, String abstracts, int view, Date createTime) {
        this.title = title;
        this.picture = picture;
        this.pictureThumb = pictureThumb;
        this.abstracts = abstracts;
        this.view = view;
        this.createTime = createTime;
    }

    public News(NewsItem newsItem, String title, File picture, File pictureThumb, String abstracts, StatusType status, Date createTime) {
        this.newsItem = newsItem;
        this.title = title;
        this.picture = picture;
        this.pictureThumb = pictureThumb;
        this.abstracts = abstracts;
        this.status = status;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public File getPictureThumb() {
        return pictureThumb;
    }

    public void setPictureThumb(File pictureThumb) {
        this.pictureThumb = pictureThumb;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }


    public NewsItem getNewsItem() {
        return newsItem;
    }

    public void setNewsItem(NewsItem newsItem) {
        this.newsItem = newsItem;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
}
