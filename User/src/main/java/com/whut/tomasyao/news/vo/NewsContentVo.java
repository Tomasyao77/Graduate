package com.whut.tomasyao.news.vo;

import edu.whut.pocket.news.model.NewsContent;
//import edu.whut.pocket.news.util.SynchronizeUtil;

public class NewsContentVo {
    private int id;
    private int newsId;
    private String content;


    public NewsContentVo() {
    }

    public NewsContentVo(int id, int newsId, String content) {
        this.id = id;
        this.newsId = newsId;
        this.content = content;
    }

    public NewsContentVo(NewsContent newsContent) {
        this.id = newsContent.getId();
        this.newsId = newsContent.getNewsId();
        this.content = newsContent.getContent();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getContent() {
        return content;
    }

//    public String getContent() {
//        return content.replaceAll("(<img.*?src=\")(?!http)", "$1" + SynchronizeUtil.getBaseUrl()).replaceAll("(<a.*?href=\")", "$1" + SynchronizeUtil.getBaseUrl());
//
//    }

    public void setContent(String content) {
        this.content = content;
    }
}
