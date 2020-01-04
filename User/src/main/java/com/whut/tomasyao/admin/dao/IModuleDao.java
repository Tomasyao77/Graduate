package com.whut.tomasyao.admin.dao;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 19:34
 */

import edu.whut.pocket.base.dao.IBaseDao;
import edu.whut.pocket.admin.model.Module;

import java.util.List;

public interface IModuleDao extends IBaseDao<Module> {
    /**
     * hibernateTemplate调用存储过程
     */
    boolean updateModuleAllIndex(final int pId, final int baseIndex);
}
