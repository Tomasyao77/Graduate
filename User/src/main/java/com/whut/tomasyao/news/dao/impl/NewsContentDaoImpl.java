package com.whut.tomasyao.news.dao.impl;

import edu.whut.pocket.base.dao.impl.BaseDaoImpl;
import edu.whut.pocket.news.dao.INewsContentDao;
import edu.whut.pocket.news.model.NewsContent;
import org.springframework.stereotype.Component;

@Component
public class NewsContentDaoImpl extends BaseDaoImpl<NewsContent> implements INewsContentDao {
    public NewsContentDaoImpl() {
        super(NewsContent.class);
    }
}
