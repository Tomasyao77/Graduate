package com.whut.tomasyao.news.model;

import edu.whut.pocket.base.model.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yuxr .
 */

@Entity
@Table(name = "news_collect")
public class NewsCollect {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne(targetEntity = News.class)
    @JoinColumn(name = "news")
    private News news;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "create_time")
    private Date createTime;

    public NewsCollect() {
    }

    public NewsCollect(User user, News news, boolean isDeleted, Date createTime) {
        this.user = user;
        this.news = news;
        this.isDeleted = isDeleted;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
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
}
