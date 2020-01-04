package com.whut.tomasyao.news.vo;

import edu.whut.pocket.news.model.NewsCollect;
import edu.whut.pocket.news.model.NewsContent;

/**
 * Created by yuxr .
 */
public class NewsCollectVo {
    private NewsCollect newsCollect;
    private NewsContent newsContent;

    public NewsCollectVo() {
    }

    public NewsCollectVo(NewsCollect newsCollect, NewsContent newsContent) {
        this.newsCollect = newsCollect;
        this.newsContent = newsContent;
    }

    public NewsCollect getNewsCollect() {
        return newsCollect;
    }

    public void setNewsCollect(NewsCollect newsCollect) {
        this.newsCollect = newsCollect;
    }

    public NewsContent getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(NewsContent newsContent) {
        this.newsContent = newsContent;
    }
}
