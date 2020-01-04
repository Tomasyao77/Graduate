package com.whut.tomasyao.config.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-04-10 16:56
 */

import org.springframework.stereotype.Component;
import com.whut.tomasyao.base.dao.impl.BaseDaoImpl;
import com.whut.tomasyao.config.model.Config;
import com.whut.tomasyao.config.dao.IConfigDao;

@Component
public class ConfigDaoImpl extends BaseDaoImpl<Config> implements IConfigDao {
    public ConfigDaoImpl() {
        super(Config.class);
    }
}
