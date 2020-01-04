package com.whut.tomasyao.news.util;//package edu.whut.pocket.news.util;
//
//import edu.whut.change.baike.model.BaikeSection;
//import edu.whut.pocket.news.model.NewsContent;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * edu.whut.change.baike.util
// * Created by YTY on 2016/11/18.
// */
//public class SynchronizeUtil {
//    private static int maxCount;
//    private static String baseUrl;
//
//    static {
//        ResourceBundle bundle = ResourceBundle.getBundle("synchronize");
//        maxCount = Integer.parseInt(bundle.getString("max_count"));
//        baseUrl = bundle.getString("url");
//    }
//
//    public static enum SynchronizeType {
//        module, baike_module, baike
//    }
//
//    private static SynchronizeType currentType;
//    private static int count;
//    private static int current;
//
//    public static SynchronizeType getCurrentType() {
//        return currentType;
//    }
//
//    public static void setCurrentType(SynchronizeType currentType) {
//        SynchronizeUtil.currentType = currentType;
//        System.out.println("currentType = " + currentType);
//    }
//
//    public static long getCount() {
//        return count;
//    }
//
//    public static int getMaxCount() {
//        return maxCount;
//    }
//
//    public static String getBaseUrl() {
//        return baseUrl;
//    }
//
//    public static void setCount(int count) {
//        SynchronizeUtil.count = count;
//        System.out.println("count = " + count);
//    }
//
//    public static int getCurrent() {
//        return current;
//    }
//
//    public static void setCurrent(int current) {
//        SynchronizeUtil.current = current;
//    }
//
//    public static void increaseCurrent() {
//        SynchronizeUtil.current++;
//        System.out.println("current = " + current);
//    }
//
//
//    public static Date getUpdateTime(SynchronizeType type) throws Exception {
//        ResourceBundle bundle = ResourceBundle.getBundle("synchronize");
//        return new Date(Long.parseLong(bundle.getString("update_time_" + type.name())));
//    }
//
//    public static void setUpdateTime(Date date, SynchronizeType type) throws Exception {
//        String path = Thread.currentThread().getContextClassLoader().getResource("synchronize.properties").getPath();
//        Properties pro = new Properties();
//        pro.load(new FileInputStream(path));
//        pro.setProperty("update_time_" + type.name(), Long.toString(date.getTime()));
//        FileOutputStream fos = new FileOutputStream(path);
//        pro.store(fos, "update_time_" + type.name());
//        fos.close();
//    }
//
//    public static String convertRelativeUrl(String content) {
//        return content.replaceAll("(<img.*?src=\")http://[^/]*", "$1").replaceAll("(<a.*?href=\")http://[^/]*", "$1");
//    }
//
//    public static List<NewsContent> getNewsContents(int newsId, String content) {
//        Pattern pattern = Pattern.compile("<div class=\"hdwiki_tmml\">(.*?)</div>");
//        Matcher matcher = pattern.matcher(content);
////        List<String> titles = new ArrayList<String>();
////        while (matcher.find()) {
////            titles.add(matcher.group(1));
////        }
//        List<String> contents = new ArrayList<String>();
//        contents.addAll(Arrays.asList(pattern.split(content)));
//        contents.remove(0);
//        List<NewsContent> newsContents = new ArrayList<NewsContent>(5);
//        for (int i = 0; i < 5 && i < contents.size(); i++) {
//            newsContents.add(new NewsContent( newsId, contents.get(i)));
//        }
//        return newsContents;
//    }
//
//    public static String getPicture(String content) {
//        Pattern pattern = Pattern.compile("<img.*?src=\"([^\"]+)");
//        Matcher matcher = pattern.matcher(content);
//        String img = null;
//        if (matcher.find()) {
//            img = matcher.group(1);
//        }
//        return img;
//    }
//}
