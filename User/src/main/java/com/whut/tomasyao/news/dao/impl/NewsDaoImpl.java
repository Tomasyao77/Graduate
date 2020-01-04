package com.whut.tomasyao.news.dao.impl;


import edu.whut.pocket.base.dao.impl.BaseDaoImpl;
import edu.whut.pocket.news.dao.INewsDao;
import edu.whut.pocket.news.model.News;
import org.springframework.stereotype.Component;

@Component
public class NewsDaoImpl extends BaseDaoImpl<News> implements INewsDao{
    public NewsDaoImpl(){
        super(News.class);
    }
}
