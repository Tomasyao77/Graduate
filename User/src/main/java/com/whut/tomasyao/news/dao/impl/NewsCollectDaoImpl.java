package com.whut.tomasyao.news.dao.impl;

import edu.whut.pocket.base.dao.impl.BaseDaoImpl;
import edu.whut.pocket.news.dao.INewsCollectDao;
import edu.whut.pocket.news.model.NewsCollect;
import org.springframework.stereotype.Component;

/**
 * Created by yuxr .
 */

@Component
public class NewsCollectDaoImpl extends BaseDaoImpl<NewsCollect> implements INewsCollectDao {
    public NewsCollectDaoImpl() {
        super(NewsCollect.class);
    }
}
