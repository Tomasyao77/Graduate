package com.whut.tomasyao.news.controller;

import edu.whut.pocket.base.common.MavenModule;
import edu.whut.pocket.base.common.StatusType;
import edu.whut.pocket.base.model.File;
import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.base.vo.ResponseMap;
import edu.whut.pocket.common.NewsItem;
import edu.whut.pocket.dubbo.file.service.IDubboFileService;
import edu.whut.pocket.file.service.IFileService;
import edu.whut.pocket.log.aspect.LogAnnotation;
import edu.whut.pocket.news.model.News;
import edu.whut.pocket.news.model.NewsCollect;
//import edu.whut.pocket.news.model.TopPic;
import edu.whut.pocket.news.service.INewsService;
//import edu.whut.pocket.news.service.ITopPicService;
import edu.whut.pocket.news.vo.NewsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = {"商家"}, description = "资讯接口", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(value = "/news")
public class NewsController {

    @Autowired
    private IDubboFileService dubboFileService;

    @Autowired
    private INewsService newsService;

//    @Autowired
//    private ITopPicService topPicService;

    @Autowired
    private IFileService fileService;

    @ApiOperation(value = "获取资讯列表",httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "固定传1", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "固定传15", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "search", value = "根据标题搜索", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "newsItem", value = "0,1,2,3", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "orderBy", value = "orderBy", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "asc", value = "asc", paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/getNewsPageList",method = RequestMethod.POST)
    public Map getNewsPageList(HttpServletRequest request, int current, int size, Integer userId, String search, Integer newsItem, String orderBy, boolean asc){
        ResponseMap map = ResponseMap.getInstance();
        try {
            Page<NewsVo> news = newsService.getNewsPageList(current, size, userId, search, newsItem, orderBy, asc);
            return map.putPage(news,"交互成功");
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("交互失败", 1);
        }
    }

    @ApiOperation(value = "获取资讯列表",httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "固定传1", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "固定传15", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "search", value = "根据标题搜索", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "orderBy", value = "orderBy", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "asc", value = "asc", paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/getLocalNewsPageList",method = RequestMethod.POST)
    public Map getLocalNewsPageList(HttpServletRequest request, int current, int size, Integer userId, String search, Integer newsItem, Integer status, String orderBy, boolean asc){
        ResponseMap map = ResponseMap.getInstance();
        try {
            Page<NewsVo> news = newsService.getLocalNewsPageList(current, size, userId, search, newsItem, status, orderBy, asc);
            return map.putPage(news,"交互成功");
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("交互失败", 1);
        }
    }

    @ApiIgnore//富文本使用
    @LogAnnotation(opType = "富文本添加图片", description = "富文本新增图片", mavenModule = MavenModule.User)
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public void editorUpload(HttpServletRequest request, HttpServletResponse response, String wangEditorH5File, String fileThumb) {
        try {
            File picture = null, pictureThumb = null;
            //List<String[]> resultFiles = fileService.uploadWithThumbnails(response);
            System.out.println(wangEditorH5File);
//            System.out.println(fileThumb);
            if (wangEditorH5File != null) {
                picture = dubboFileService.addOneFile(wangEditorH5File);
                dubboFileService.addOneFile(fileThumb);
            }
            PrintWriter out = response.getWriter();
            out.print(picture.getUrl());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

//    @ApiOperation(value = "获取顶部图片",httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE, notes = "资讯标题要求不同，需要重复可修改")
//    @RequestMapping(value = "/getTopPic", method = RequestMethod.POST)
//    public Map getTopPic(HttpServletRequest request)
//    {
//        ResponseMap map = ResponseMap.getInstance();
//        try {
//            List<TopPic> topPics = topPicService.getTopPic();
//            return map.putList(topPics);
//        } catch(Exception e) {
//            e.printStackTrace();
//            return map.putFailure("交互失败", 1);
//        }
//    }

//    @ApiIgnore
//    @LogAnnotation(opType = "添加顶部图片", description = "添加顶部图片", mavenModule = MavenModule.User)
//    @RequestMapping(value = "/addTopPic", method = RequestMethod.POST)
//    public Map addTopPic(HttpServletRequest request, int userId, String file, String fileThumb)
//    {
//        ResponseMap map = ResponseMap.getInstance();
//        System.out.println(file);
//        try{
//            File picture = null, pictureThumb = null;
//            //List<String[]> resultFiles = fileService.uploadWithThumbnails(request);
//            if(file != null ) {
//                picture = dubboFileService.addOneFile(file);
//                pictureThumb = dubboFileService.addOneFile(fileThumb);
//            }
//            TopPic topPic = topPicService.addTopPic(picture, pictureThumb);
//            return map.putValue(topPic,"新增成功");
//        }catch (Exception e) {
//            e.printStackTrace();
//            return map.putFailure("新增失败", 1);
//        }
//    }



    @ApiOperation(value = "资讯操作",httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE, notes = "资讯标题要求不同，需要重复可修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "资讯标题", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "file", value = "资讯封面图片", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "fileThumb", value = "资讯封面图片压缩图", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "abstracts", value = "资讯摘要", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "content", value = "资讯内容", paramType = "form", dataType = "string[]")
    })
    @LogAnnotation(opType = "新增新闻", description = "新增资讯", mavenModule = MavenModule.User)
    @RequestMapping(value = "/addOneNews", method = RequestMethod.POST)
    public Map addOneNews(HttpServletRequest request, int userId, int newsItem, String title, String file, String fileThumb, String abstracts, String[] content)
    {
        ResponseMap map = ResponseMap.getInstance();
        try{
            File picture = null, pictureThumb = null;
            //List<String[]> resultFiles = fileService.uploadWithThumbnails(request);
            if(file != null && fileThumb !=null) {
                picture = dubboFileService.addOneFile(file);
                pictureThumb = dubboFileService.addOneFile(fileThumb);
            }

            NewsVo news = newsService.addOneNews(NewsItem.values()[newsItem], title, picture, pictureThumb, abstracts, content);
            return map.putValue(news,"新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("新增失败", 1);
        }
    }

    @ApiOperation(value = "删除资讯", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资讯标id", paramType = "form", dataType = "int"),
    })
    @LogAnnotation(opType = "删除新闻", description = "删除资讯", mavenModule = MavenModule.User)
    @RequestMapping(value = "/deleteOneNews",method = RequestMethod.POST)
    public Map deleteOneNews(HttpServletRequest request, int userId, int id){
        ResponseMap map = ResponseMap.getInstance();
        try{
            newsService.deleteOneNews(id);
            return map.putSuccess("交互成功");
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("交互失败", 1);
        }
    }

    @ApiOperation(value = "编辑修改资讯", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资讯id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "title", value = "资讯标题", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "file", value = "资讯封面图片", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "fileThumb", value = "资讯封面图片压缩图", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "abstracts", value = "资讯摘要", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "content", value = "资讯内容", paramType = "form", dataType = "string[]")
    })
    @LogAnnotation(opType = "修改新闻", description = "修改资讯", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateOneNews",method = RequestMethod.POST)
    public Map updateOneNews(HttpServletRequest request, int userId, int id, Integer newsItem, String title, String file, String fileThumb, String abstracts, String[] content){
        ResponseMap map = ResponseMap.getInstance();
        try{
            File picture = null, pictureThumb = null;
            //List<String[]> resultFiles = fileService.uploadWithThumbnails(request);
            if(file != null && fileThumb != null){
                picture = dubboFileService.addOneFile(file);
                pictureThumb = dubboFileService.addOneFile(fileThumb);
            }
            NewsVo news = newsService.updateOneNews(id, newsItem, title, picture, pictureThumb, abstracts, content);
            return map.putValue(news,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("修改失败", 1);
        }
    }

    @ApiOperation(value = "编辑修改资讯", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资讯id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "title", value = "资讯标题", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "file", value = "资讯封面图片", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "fileThumb", value = "资讯封面图片压缩图", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "abstracts", value = "资讯摘要", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "content", value = "资讯内容", paramType = "form", dataType = "string[]")
    })
    @LogAnnotation(opType = "修改新闻", description = "修改资讯", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateNewsStatus",method = RequestMethod.POST)
    public Map updateNewsStatus(HttpServletRequest request, int userId, int newsId, int status){
        ResponseMap map = ResponseMap.getInstance();
        try{
            News news = newsService.updateNewsStatus(newsId, StatusType.values()[status]);
            return map.putValue(news,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("修改失败", 1);
        }
    }

    @ApiOperation(value = "商家修改资讯浏览量", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Id", value = "资讯id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "newsId", value = "资讯id", paramType = "form", dataType = "int")
    })
    @LogAnnotation(opType = "改变浏览量", description = "点击后新增浏览量", mavenModule = MavenModule.User)
    @RequestMapping(value = "/fixNewsProperty", method = RequestMethod.POST)
    public Map fixNewsProperty(HttpServletRequest request, int userId, Integer newsId) {
        ResponseMap map = ResponseMap.getInstance();
        try {
            return map.putValue(newsService.fixNewsProperty(newsId));
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("交互失败", 1);
        }
    }

    @ApiOperation(value = "资讯操作",httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "newsId", value = "资讯id", paramType = "form", dataType = "int")
    })
    @LogAnnotation(opType = "收藏资讯", description = "收藏资讯", mavenModule = MavenModule.User)
    @RequestMapping(value = "/collectNews", method = RequestMethod.POST)
    public Map collectNews(HttpServletRequest request, int userId, int newsId) throws Exception
    {
        ResponseMap map = ResponseMap.getInstance();
        try{
            NewsCollect newsCollect = newsService.collectNews(userId, newsId);
            if (newsCollect != null) {
                return map.putValue(newsCollect, "收藏成功");
            }else
                return map.putFailure("您已收藏", 1);
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("收藏失败", 2);
        }
    }

    @ApiOperation(value = "资讯操作",httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "newsId", value = "资讯id", paramType = "form", dataType = "int")
    })
    @LogAnnotation(opType = "删除收藏资讯", description = "删除收藏资讯", mavenModule = MavenModule.User)
    @RequestMapping(value = "/cancelNews", method = RequestMethod.POST)
    public Map cancelNews(HttpServletRequest request, int userId, int newsId) throws Exception
    {
        ResponseMap map = ResponseMap.getInstance();
        try{
            newsService.cancelNews(userId, newsId);
            return map.putSuccess("取消收藏成功");
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("取消收藏失败", 1);
        }
    }

    @ApiOperation(value = "资讯操作",httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "固定传1", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "固定传15", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "orderBy", value = "orderBy", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "asc", value = "asc", paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/getNewsCollectPageList", method = RequestMethod.POST)
    public Map getNewsCollectPageList(HttpServletRequest request, int current, int size, int userId, String orderBy, boolean asc) throws Exception
    {
        ResponseMap map = ResponseMap.getInstance();
        try{
            Page<News> newsPage = newsService.getNewsCollectPageList(current, size, userId, orderBy, asc);
            return map.putPage(newsPage, "获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("获取失败", 1);
        }
    }

    @ApiOperation(value = "资讯操作",httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newsId", value = "用户id", paramType = "form", dataType = "int")
    })
    @RequestMapping(value = "/getNewsView", method = RequestMethod.POST)
    public Map getNewsView(HttpServletRequest request, int newsId) throws Exception
    {
        ResponseMap map = ResponseMap.getInstance();
        try{
            Integer view = newsService.getNewsView(newsId);
            return map.putValue(view, "获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("获取失败", 1);
        }
    }

    @ApiOperation(value = "资讯操作",httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "newsId", value = "资讯id", paramType = "form", dataType = "int")
    })
    @RequestMapping(value = "/getOneNews", method = RequestMethod.POST)
    public Map getOneNews(HttpServletRequest request, int userId, int newsId) throws Exception
    {
        ResponseMap map = ResponseMap.getInstance();
        try{
            NewsVo newsVo = newsService.getOneNews(userId, newsId);
            return map.putValue(newsVo, "获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("获取失败", 1);
        }
    }

}
