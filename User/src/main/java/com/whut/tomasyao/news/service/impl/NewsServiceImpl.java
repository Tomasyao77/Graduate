package com.whut.tomasyao.news.service.impl;

import edu.whut.pocket.base.common.StatusType;
import edu.whut.pocket.base.model.File;
import edu.whut.pocket.base.model.User;
import edu.whut.pocket.base.util.CheckUtil;
import edu.whut.pocket.base.util.HqlUtil;
import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.base.vo.Parameter;
import edu.whut.pocket.common.NewsItem;
import edu.whut.pocket.news.dao.INewsCollectDao;
import edu.whut.pocket.news.dao.INewsContentDao;
import edu.whut.pocket.news.dao.INewsDao;
import edu.whut.pocket.news.model.News;

import edu.whut.pocket.news.model.NewsCollect;
import edu.whut.pocket.news.model.NewsContent;
import edu.whut.pocket.news.service.INewsService;
import edu.whut.pocket.news.util.RichTextToHtml;
import edu.whut.pocket.news.vo.NewsCollectVo;
import edu.whut.pocket.news.vo.NewsContentVo;
import edu.whut.pocket.news.vo.NewsVo;
import edu.whut.pocket.user.dao.IUserDao;
import org.apache.yetus.audience.InterfaceAudience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImpl implements INewsService {

    @Autowired
    private INewsDao newsDao;

    @Autowired
    private INewsContentDao newsContentDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private INewsCollectDao newsCollectDao;

    @Override
    public Page<NewsVo> getNewsPageList(int current, int size, Integer userId, String search, Integer newsItem, String orderBy, boolean asc) throws Exception{
        String hql = "select n from News n ";
        String countHql = "select count(*) from News n";
        System.out.println(current);
        System.out.println(size);
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<String>();
        condition.add("n.isDeleted=:isDeleted ");
        p.put("isDeleted", false);
        condition.add("n.status=:status ");
        p.put("status", StatusType.启用);
        if (newsItem != null){
            condition.add("(n.newsItem = :newsItem)");
            p.put("newsItem", NewsItem.values()[newsItem]);
        }

        if (CheckUtil.isNotEmpty(search)) {
            condition.add("(n.title like :search)");
            p.put("search","%"+ search.trim() +"%");
        }

        Page newPage = newsDao.findPageWithAny(current, size, HqlUtil.formatHql(hql, condition,"n.createTime", asc),
                HqlUtil.formatHql(countHql, condition),p);

        //将数组中的List取出来，List中的数组变成对象
        List newVo = new ArrayList();//用来存放修改的List
        List newList = newPage.getList();//获取生成的List
        for (int i =0; i<newList.size(); i++)
        {
            News news = (News)newList.get(i);//获取List中的每一条记录
            NewsContent newsContent = newsContentDao.findOne("select nc from NewsContent nc where nc.newsId =:p0", new Parameter(news.getId()));//取出对应id的内容记录
            if (userId != null) {
                NewsCollect newsCollect = newsCollectDao.findOne("select nct from NewsCollect nct where nct.user.id =:p0 and nct.news.id =:p1 and nct.isDeleted =false", new Parameter(userId, news.getId()));
                NewsVo newsVo = new NewsVo(news.getNewsItem(), news.getId(), news.getTitle(), news.getPicture(),news.getPictureThumb(),news.getAbstracts(), newsCollect, news.getView(), newsContent.getContent(), news.getStatus(), news.getCreateTime());
                newVo.add(newsVo);
            }else {
//                if(newsContent != null) {
                    NewsVo newsVo = new NewsVo(news.getNewsItem(), news.getId(), news.getTitle(), news.getPicture(), news.getPictureThumb(), news.getAbstracts(), news.getView(), newsContent.getContent(), news.getStatus(), news.getCreateTime());
                    newVo.add(newsVo);
//                }else {
//                    NewsVo newsVo = new NewsVo(news.getNewsItem(), news.getId(), news.getTitle(), news.getPicture(), news.getPictureThumb(), news.getAbstracts(), news.getView(), news.getStatus(), news.getCreateTime());
//                    newVo.add(newsVo);
//                }
            }

        }
        //将设置的List放入newPage
        newPage.setList(newVo);
        return newPage;

//        return newsDao.findPageWithAny(current, size, HqlUtil.formatHql(hql,condition, "n.createTime",false),

//                   HqlUtil.formatHql(countHql, condition), p);
    }

    @Override
    public Page<NewsVo> getLocalNewsPageList(int current, int size, Integer userId, String search, Integer newsItem, Integer status, String orderBy, boolean asc) throws Exception{
        String hql = "select n from News n ";
        String countHql = "select count(*) from News n";
        System.out.println(current);
        System.out.println(size);
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<String>();
        condition.add("n.isDeleted=:isDeleted ");
        p.put("isDeleted", false);

        if (newsItem != null){
            condition.add("(n.newsItem = :newsItem)");
            p.put("newsItem", NewsItem.values()[newsItem]);
        }

        if (status != null){
            condition.add("(n.status = :status)");
            p.put("status", StatusType.values()[status]);
        }

        if (CheckUtil.isNotEmpty(search)) {
            condition.add("(n.title like :search)");
            p.put("search","%"+ search.trim() +"%");
        }

        Page newPage = newsDao.findPageWithAny(current, size, HqlUtil.formatHql(hql, condition,"n.createTime", false),
                HqlUtil.formatHql(countHql, condition),p);

        //将数组中的List取出来，List中的数组变成对象
        List newVo = new ArrayList();//用来存放修改的List
        List newList = newPage.getList();//获取生成的List
        for (int i =0; i<newList.size(); i++)
        {
            News news = (News)newList.get(i);//获取List中的每一条记录
            NewsContent newsContent = newsContentDao.findOne("select nc from NewsContent nc where nc.newsId =:p0", new Parameter(news.getId()));//取出对应id的内容记录
            if(newsContent != null) {
                NewsVo newsVo = new NewsVo(news.getNewsItem(), news.getId(), news.getTitle(), news.getPicture(), news.getPictureThumb(), news.getAbstracts(), news.getView(), newsContent.getContent(), news.getStatus(), news.getCreateTime());
                newVo.add(newsVo);
            }else {
                NewsVo newsVo = new NewsVo(news.getNewsItem(), news.getId(), news.getTitle(), news.getPicture(), news.getPictureThumb(), news.getAbstracts(), news.getView(), news.getStatus(), news.getCreateTime());
                newVo.add(newsVo);
            }

        }
        //将设置的List放入newPage
        newPage.setList(newVo);
        return newPage;

//        return newsDao.findPageWithAny(current, size, HqlUtil.formatHql(hql,condition, "n.createTime",false),
//                   HqlUtil.formatHql(countHql, condition), p);
    }

    @Override
    public NewsVo addOneNews(NewsItem newsItem, String title, File picture, File pictureThumb, String abstracts, String[] content) throws Exception{
        News news = new News(newsItem, title, picture, pictureThumb, abstracts, StatusType.禁用, new Date());
        newsDao.save(news);
        List<NewsContentVo> newsContents = new ArrayList<>();
        for (int i = 0; i < content.length; i++) {
            NewsContent newsContent = new NewsContent(news.getId(), content[i]);
            newsContentDao.save(newsContent);
            newsContents.add(new NewsContentVo(newsContent));
        }
        //!!!注意，根本不需要这样做，用模板页面实现
        //content_url util将富文本转成html文件并上传到fdfs
        /*StringBuilder tempContent = new StringBuilder();
        for(int i = 0; i < content.length; i++){
            tempContent.append(content[i]);
        }
        RichTextToHtml richTextToHtml = new RichTextToHtml();
        String url = richTextToHtml.transfer(tempContent.toString());
        newsDao.update(" update News n set n.contentUrl=:p0 where n.id=:p1", new Parameter(url, news.getId()));*/
        return new NewsVo(news, newsContents);
    }

    @Override
    public void deleteOneNews(int id) throws Exception{

        News news = newsDao.getOne(id);
        news.setDeleted(true);
        newsDao.save(news);

//        newsContentDao.deleteWithHql("delete from NewsContent nc where nc.newsId=" + newsId);
//        newsDao.deleteWithHql("delete from News n where n.id=" + newsId);
    }


//    @Override
//    public void updateOneNews(int id, String title, File picture, File pictureThumb, String abstracts, String[] content) throws Exception{
//        News news = newsDao.getOne(id);
//        news.setTitle(title);
//        if (picture != null){
//            news.setPicture(picture);
//        }
//        news.setAbstracts(abstracts);
//        //newsDao.save(news);
//
////        newsContentDao.deleteWithHql("delete from NewsContent n where n.newsId=" + id);
////        List<NewsContentVo> newsContents = new ArrayList<NewsContentVo>();
////        for (int i = 0; i < content.length; i++) {
////            NewsContent newsContent = new NewsContent(news.getId(), content[i]);
////            newsContentDao.save(newsContent);
////            newsContents.add(new NewsContentVo(newsContent));
////
//
////            System.out.println(news.getTitle());
////            System.out.println(newsContent.getNewsId());
////            System.out.println(newsContents);
////
////        }
//        NewsContent newsContent = newsContentDao.getOne(news.getId());
//        for (int i = 0; i < content.length; i++){
//            System.out.println(content[i]);
//            newsContent.setContent(content[i]);
//        }
//
//        newsDao.update(news);
//        newsContentDao.update(newsContent);
//        //return new NewsVo(news, newsContents);
//    }

    @Override
    public NewsVo updateOneNews(int id, Integer newsItem, String title, File picture, File pictureThumb, String abstracts, String[] content) throws Exception{
//        News news = newsDao.findOne("from News n where n.title ="+ title);
//        int id = news.getId();
        News news = newsDao.getOne(id);
        if (newsItem != null){
            news.setNewsItem(NewsItem.values()[newsItem]);
        }
        news.setTitle(title);
        if (picture != null){
            news.setPicture(picture);
            news.setPictureThumb(pictureThumb);
        }
        news.setAbstracts(abstracts);

//        //尝试
//        int newsId = news.getId();
//        List<NewsContentVo> newsContents = new ArrayList<NewsContentVo>();
//        NewsContent newsContent = newsContentDao.getOne(newsId);
//        for (int i = 0; i < content.length; i++) {
//            newsContent.setContent(content[i]);
//
//            newsContents.add(new NewsContentVo(newsContent));
//        }


        newsContentDao.deleteWithHql("delete from NewsContent nc where nc.newsId=" + id);
        List<NewsContentVo> newsContents = new ArrayList<NewsContentVo>();
        for (int i = 0; i < content.length; i++) {
            NewsContent newsContent = new NewsContent(news.getId(), content[i]);
            newsContentDao.save(newsContent);
            newsContents.add(new NewsContentVo(newsContent));
        }
        //content_url util将富文本转成html文件并上传到fdfs
        StringBuilder tempContent = new StringBuilder();
        for(int i = 0; i < content.length; i++){
            tempContent.append(content[i]);
        }
        RichTextToHtml richTextToHtml = new RichTextToHtml();
        String url = richTextToHtml.transfer(tempContent.toString());
        newsDao.update(" update News n set n.contentUrl=:p0 where n.id=:p1", new Parameter(url, news.getId()));
        return new NewsVo(news, newsContents);

    }

    @Override
    public News updateNewsStatus(int newsId, StatusType status) throws Exception{
        News news = newsDao.getOne(newsId);
        news.setStatus(status);
        newsDao.update(news);
        return news;
    }

    @Override
    public boolean fixNewsProperty(Integer newsId) throws Exception {
        News news = newsDao.getOne(newsId);
        int view = news.getView()+1;
        news.setView(view);
        newsDao.save(news);
        return true;
    }

    @Override
    public NewsCollect collectNews(int userId, int newsId) throws Exception{
        User user = userDao.getOne(userId);
        News news = newsDao.getOne(newsId);
        NewsCollect newsCollect = new NewsCollect(user, news, false, new Date());
        NewsCollect collect = newsCollectDao.findOne("select nct from NewsCollect nct where nct.user.id =:p0 and nct.news.id =:p1 and nct.isDeleted =false", new Parameter(userId, newsId));
        if (collect == null) {
            newsCollectDao.save(newsCollect);
            return newsCollect;
        }else
            return null;
    }

    @Override
    public void cancelNews(int userId, int newsId) throws Exception{
        NewsCollect newsCollect = newsCollectDao.findOne("select nct from NewsCollect nct where nct.user.id =:p0 and nct.news.id =:p1 and nct.isDeleted =false", new Parameter(userId, newsId));
        newsCollect.setDeleted(true);
    }

    @Override
    public Page<News> getNewsCollectPageList(int current, int size, int userId, String orderBy, boolean asc) throws Exception{
        String hql = "select nct from NewsCollect nct ";
        String countHql = "select count(*) from NewsCollect nct";
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<String>();

        condition.add("nct.user.id =:userId");
        p.put("userId", userId);

        condition.add("nct.isDeleted =:isDeleted");
        p.put("isDeleted", false);


        Page newPage = newsDao.findPageWithAny(current, size, HqlUtil.formatHql(hql, condition,"nct.createTime", false),
                HqlUtil.formatHql(countHql, condition),p);

        //将数组中的List取出来，List中的数组变成对象
        List newsCollectVoList = new ArrayList();//用来存放修改的List
        List newsCollectList = newPage.getList();//获取生成的List
        for (int i =0; i<newsCollectList.size(); i++)
        {
            NewsCollect newsCollect = (NewsCollect)newsCollectList.get(i);//获取List中的每一条记录
            NewsContent newsContent = newsContentDao.findOne("select nc from NewsContent nc where nc.newsId =:p0", new Parameter(newsCollect.getNews().getId()));//取出对应id的内容记录
            NewsCollectVo newsCollectVo = new NewsCollectVo(newsCollect, newsContent);
            newsCollectVoList.add(newsCollectVo);
        }
        //将设置的List放入newPage
        newPage.setList(newsCollectVoList);
        return newPage;
    }

    @Override
    public Integer getNewsView(int newsId) throws Exception{
        News news = newsDao.getOne(newsId);
        return news.getView();
    }

    @Override
    public NewsVo getOneNews(int userId, int newsId) throws Exception{
        News news = newsDao.getOne(newsId);
        NewsCollect newsCollect = newsCollectDao.findOne("select nct from NewsCollect nct where nct.news.id =:p0 and nct.user.id = :p1 and nct.isDeleted=false", new Parameter(newsId ,userId));
        NewsContent newsContent = newsContentDao.findOne("select nc from NewsContent nc where nc.newsId =:p0", new Parameter(newsId));
        NewsVo newsVo = new NewsVo(news.getNewsItem(), news.getId(), news.getTitle(), news.getPicture(),news.getPictureThumb(),news.getAbstracts(), newsCollect,
                                    news.getView(), newsContent.getContent(), news.getStatus(), news.getCreateTime());
        return newsVo;
    }

}
