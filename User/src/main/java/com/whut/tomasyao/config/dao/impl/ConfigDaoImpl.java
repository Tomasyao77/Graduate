package com.whut.tomasyao.config.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-04-10 16:56
 */

import org.springframework.stereotype.Component;
import edu.whut.pocket.base.dao.impl.BaseDaoImpl;
import edu.whut.pocket.config.model.Config;
import edu.whut.pocket.config.dao.IConfigDao;

@Component
public class ConfigDaoImpl extends BaseDaoImpl<Config> implements IConfigDao {
    public ConfigDaoImpl() {
        super(Config.class);
    }
}
