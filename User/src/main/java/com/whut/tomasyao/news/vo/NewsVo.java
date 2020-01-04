package com.whut.tomasyao.news.vo;

import edu.whut.pocket.base.common.StatusType;
import edu.whut.pocket.base.model.File;
import edu.whut.pocket.common.NewsItem;
import edu.whut.pocket.news.model.News;
import edu.whut.pocket.news.model.NewsCollect;

import java.util.Date;
import java.util.List;

public class NewsVo {
    private int id;
    private NewsItem newsItem;
    private String title;
    private File picture;
    private File pictureThumb;
    private String abstracts;
    private NewsCollect newsCollect;
    private int view;
    List<NewsContentVo> newsContents;
    private String content;
    private StatusType status;
    private Date createTime;

    public  static final String CONSTRUCT_HQL = "new edu.whut.pocket.news.vo.NewsVo(n.id, n.title, n.picture, n.abstracts,"+
                     "nc.content)";

    public NewsVo(int id, String title, File picture, File pictureThumb, String abstracts, int view, Date createTime) {
        this.id = id;
        this.title = title;
        this.picture = picture;
        this.pictureThumb = pictureThumb;
        this.abstracts = abstracts;
        this.view = view;
        this.createTime = createTime;
    }


    public NewsVo(NewsItem newsItem, int id, String title, File picture, File pictureThumb, String abstracts, NewsCollect newsCollect, int view, String content, StatusType status, Date createTime) {
        this.newsItem = newsItem;
        this.id = id;
        this.title = title;
        this.picture = picture;
        this.pictureThumb = pictureThumb;
        this.abstracts = abstracts;
        this.newsCollect = newsCollect;
        this.view = view;
        this.content = content;
        this.status = status;
        this.createTime = createTime;
    }

    public NewsVo(NewsItem newsItem, int id, String title, File picture, File pictureThumb, String abstracts, int view, String content, StatusType status, Date createTime) {
        this.newsItem = newsItem;
        this.id = id;
        this.title = title;
        this.picture = picture;
        this.pictureThumb = pictureThumb;
        this.abstracts = abstracts;
        this.view = view;
        this.content = content;
        this.status = status;
        this.createTime = createTime;
    }

    public NewsVo(NewsItem newsItem, int id, String title, File picture, File pictureThumb, String abstracts, int view, StatusType status, Date createTime) {
        this.newsItem = newsItem;
        this.id = id;
        this.title = title;
        this.picture = picture;
        this.pictureThumb = pictureThumb;
        this.abstracts = abstracts;
        this.view = view;
        this.status = status;
        this.createTime = createTime;
    }

    public NewsVo(News news, List<NewsContentVo> newsContents){
        this.id = news.getId();
        this.title = news.getTitle();
        this.picture = news.getPicture();
        this.pictureThumb = news.getPictureThumb();
        this.abstracts = news.getAbstracts();
        this.view = news.getView();
        this.createTime = news.getCreateTime();
        this.newsContents = newsContents;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public NewsCollect getNewsCollect() {
        return newsCollect;
    }

    public void setNewsCollect(NewsCollect newsCollect) {
        this.newsCollect = newsCollect;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public List<NewsContentVo> getNewsContents() {
        return newsContents;
    }

    public void setNewsContents(List<NewsContentVo> newsContents) {
        this.newsContents = newsContents;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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
}
