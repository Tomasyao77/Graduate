package com.whut.tomasyao.admin.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 19:52
 */

import org.springframework.stereotype.Component;
import edu.whut.pocket.base.dao.impl.BaseDaoImpl;
import edu.whut.pocket.admin.model.RoleModule;
import edu.whut.pocket.admin.dao.IRoleModuleDao;

@Component
public class RoleModuleDaoImpl extends BaseDaoImpl<RoleModule> implements IRoleModuleDao {
    public RoleModuleDaoImpl() {
        super(RoleModule.class);
    }
}
