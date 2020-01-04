package com.whut.tomasyao.base.db;

/**
 * Created by QiuYW on 2016/5/24.
 */
public class DataSourceSwitcher {
    private static final ThreadLocal contextHolder = new ThreadLocal();
    public static void setDataSource(String dataSource){
        contextHolder.set(dataSource);
    }
    public static void setMaster(){
        clearDataSource();
    }
    public static void setSlave(){
        setDataSource("slave");
    }
    public static String getDataSource(){
        return (String) contextHolder.get();
    }
    public static void clearDataSource(){
        contextHolder.remove();
    }
}
