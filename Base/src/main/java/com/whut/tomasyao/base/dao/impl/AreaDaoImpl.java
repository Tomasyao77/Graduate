package com.whut.tomasyao.base.dao.impl;

import com.whut.tomasyao.base.dao.IAreaDao;
import com.whut.tomasyao.base.model.Area;
import org.springframework.stereotype.Component;

/**
 * com.whut.athena.base.dao.Impl
 * Created by YTY on 2016/3/24.
 */
@Component
public class AreaDaoImpl extends BaseDaoImpl<Area> implements IAreaDao {
    public AreaDaoImpl() {
        super(Area.class);
    }
}
