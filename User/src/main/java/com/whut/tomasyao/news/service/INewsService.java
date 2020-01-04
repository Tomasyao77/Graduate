package com.whut.tomasyao.news.service;

import edu.whut.pocket.base.common.StatusType;
import edu.whut.pocket.base.model.File;
import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.common.NewsItem;
import edu.whut.pocket.news.model.News;
import edu.whut.pocket.news.model.NewsCollect;
import edu.whut.pocket.news.vo.NewsVo;

import java.sql.Blob;
import java.util.Date;

public interface INewsService {

    Page<NewsVo> getNewsPageList(int current, int size, Integer userId, String search, Integer newsItem, String orderBy, boolean asc) throws Exception;

    Page<NewsVo> getLocalNewsPageList(int current, int size, Integer userId, String search, Integer newsItem, Integer status, String orderBy, boolean asc) throws Exception;

    NewsVo addOneNews(NewsItem newsItem, String title, File picture, File pictureThumb, String abstracts, String[] content) throws Exception;

    void deleteOneNews(int id) throws Exception;

    NewsVo updateOneNews(int id, Integer newsItem, String title, File picture, File pictureThumb, String abstracts, String[] content) throws Exception;

    News updateNewsStatus(int newsId, StatusType status) throws Exception;

    boolean fixNewsProperty(Integer newsId) throws Exception;

    NewsCollect collectNews(int userId, int newsId) throws Exception;

    void cancelNews(int userId, int newsId) throws Exception;

    Page<News> getNewsCollectPageList(int current, int size, int userId, String orderBy, boolean asc) throws Exception;

    Integer getNewsView(int newsId) throws Exception;

    NewsVo getOneNews(int userId, int newsId) throws Exception;
}
