package com.whut.tomasyao.config.service.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-04-10 16:57
 */

import com.whut.tomasyao.base.common.ConfigType;
import com.whut.tomasyao.base.util.HqlUtil;
import com.whut.tomasyao.base.vo.Page;
import com.whut.tomasyao.base.vo.Parameter;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.whut.tomasyao.config.model.Config;
import com.whut.tomasyao.config.service.IConfigService;
import com.whut.tomasyao.config.dao.IConfigDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ConfigServiceImpl implements IConfigService {

    @Autowired
    private IConfigDao configDao;

    @Override
    public Config getOneConfig(int id) throws Exception {
        return null;
    }

    @Override
    public Config addOneConfig(String name, String nameEn, int parentId,
                               String valueType, String value) throws Exception {
        if(configDao.findOne(" from Config c where c.name='"+name+"' or c.nameEn='"+nameEn+"' ") != null){
            return null;
        }

        if(parentId == 0){
            Config config = new Config(name, nameEn, null, null, new Date(),
                    null, false);
            return configDao.save(config);
        }else if(parentId > 0){
            Config config = new Config(name, nameEn, configDao.getOne(parentId), ConfigType.valueOf(valueType),
                    new Date(), value, false);
            return configDao.save(config);
        }

        return null;
    }

    @Override
    public Config updateOneConfig(int id, String name, String nameEn, String valueType, String value,
                                  Integer parentId) throws Exception {
        if(configDao.findOne(" from Config c where (c.name='"+name+"' or c.nameEn='"+nameEn+"')" +
                " and c.id is not '"+id+"' ") != null){
            return null;
        }
        Config config = configDao.getOne(id);
        config.setName(name);
        config.setNameEn(nameEn);
        if(valueType!=null && !valueType.isEmpty()){
            config.setValueType(ConfigType.valueOf(valueType));
        }
        if(value!=null && !value.isEmpty()){
            config.setValue(value);
        }
        if(parentId != null){
            config.setParent(configDao.getOne(parentId));
        }

        return config;
    }

    @Override
    public List<Config> getConfigTypeList() throws Exception {
        String hql = " from Config c ";
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<>();

        condition.add(" c.isDeleted=:isDeleted ");
        p.put("isDeleted", false);
        condition.add(" c.parent is null ");

        return configDao.findList(HqlUtil.formatHql(hql, condition), p);
    }

    @Override
    public Page<Config> getConfigPageList(int current, int size, String search, int parentId,
                                          String orderBy, Boolean asc) throws Exception {
        String hql = " select new com.whut.tomasyao.config.vo.ConfigHibernateVo(" +
                "c.id,c.name,c.nameEn,c.valueType,c.createTime,c.value,ci.id,ci.name,ci.nameEn)" +
                " from Config c inner join c.parent ci ";//自连接查询parent属性
        if(parentId == 0){
            hql = " from Config c ";//parent is null查询设置类别列表
        }
        String countHql = " select count(*) from Config c ";
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<>();

        condition.add(" c.isDeleted=:isDeleted ");
        p.put("isDeleted", false);
        if(search!=null && !search.isEmpty()){
            condition.add(" (c.name like :search or c.nameEn like :search) ");
            p.put("search", "%"+search+"%");
        }
        if(parentId == 0){
            condition.add(" c.parent is null ");
        }else if(parentId > 0){
            condition.add(" c.parent.id=:parentId ");
            p.put("parentId", parentId);
        }else {
            condition.add(" c.parent is not null ");
        }
        //排序,特殊情况
        if (orderBy != null && !orderBy.isEmpty()) {
            orderBy = "c." + orderBy;
        }

        return configDao.findPage(current, size, HqlUtil.formatHql(hql, condition, orderBy, asc),
                HqlUtil.formatHql(countHql, condition), p);
    }
}
