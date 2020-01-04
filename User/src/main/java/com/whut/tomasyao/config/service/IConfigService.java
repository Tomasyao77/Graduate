package com.whut.tomasyao.config.service;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-04-10 16:57
 */

import com.whut.tomasyao.base.vo.Page;
import com.whut.tomasyao.config.model.Config;

import java.util.List;

public interface IConfigService {

    //获取一条记录
    Config getOneConfig(int id) throws Exception;
    //新增一条记录
    Config addOneConfig(String name, String nameEn, int parentId, String valueType, String value) throws Exception;
    //编辑一条记录
    Config updateOneConfig(int id, String name, String nameEn, String valueType, String value, Integer parentId) throws Exception;
    //获取设置列表
    List<Config> getConfigTypeList() throws Exception;
    //获取设置分页列表
    Page<Config> getConfigPageList(int current, int size, String search, int parentId, String orderBy, Boolean asc) throws Exception;
}
