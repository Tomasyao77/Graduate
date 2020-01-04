package com.whut.tomasyao.news.model;

import javax.persistence.*;

@Entity
@Table(name = "news_content")
public class NewsContent {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "news_id")
    private int newsId;
    private String content;

    public NewsContent(){
    }

    public NewsContent(int id, int newsId, String content) {
        this.id= id;
        this.newsId = newsId;
        this.content = content;
    }

    public NewsContent(int newsId, String content) {
        this.newsId = newsId;
        this.content = content;
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

    public void setContent(String content) {
        this.content = content;
    }
}
