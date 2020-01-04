package com.whut.tomasyao.news.service.impl;//package edu.whut.pocket.news.service.impl;
//
//import edu.whut.pocket.base.model.File;
//import edu.whut.pocket.news.dao.ITopPicDao;
//import edu.whut.pocket.news.model.TopPic;
//import edu.whut.pocket.news.service.ITopPicService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by yuxr .
// */
//
//@Service
//public class TopPicServiceImpl implements ITopPicService {
//
//    @Autowired
//    private ITopPicDao topPicDao;
//
//    @Override
//    public TopPic addTopPic(File picture, File pictureThumb) throws Exception {
//        TopPic topPic = new TopPic(picture, pictureThumb, false, new Date());
//        topPicDao.save(topPic);
//        System.out.println(topPic.getId());
//        if (topPic.getId() >5)
//        {
//            TopPic topPic1 = topPicDao.getOne(topPic.getId()-5);
//            topPic1.setDeleted(true);
//        }
//        return topPic;
//    }
//
//    @Override
//    public List<TopPic> getTopPic() throws Exception {
//       //TopPic topPic = topPicDao.getOne(1);
//
//       return topPicDao.findList("select tp from TopPic tp where tp.isDeleted = false");
//    }
//
//}
