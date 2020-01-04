package com.whut.tomasyao.base.db;


import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by QiuYW on 2016/5/24.
 * 主要为了处理读写分离
 */
public class DataSourceAdvice implements MethodBeforeAdvice, AfterReturningAdvice {

    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {

    }

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        //通过反射获取即将执行的方法名,如果以get开头,则连接从数据库,表示读
        if (method.getName().startsWith("get")) {
            DataSourceSwitcher.setSlave();
        } else {
            DataSourceSwitcher.setMaster();
        }

    }

}
