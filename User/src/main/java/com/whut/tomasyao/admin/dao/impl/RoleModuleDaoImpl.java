package com.whut.tomasyao.admin.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 19:52
 */

import org.springframework.stereotype.Component;
import com.whut.tomasyao.base.dao.impl.BaseDaoImpl;
import com.whut.tomasyao.admin.model.RoleModule;
import com.whut.tomasyao.admin.dao.IRoleModuleDao;

@Component
public class RoleModuleDaoImpl extends BaseDaoImpl<RoleModule> implements IRoleModuleDao {
    public RoleModuleDaoImpl() {
        super(RoleModule.class);
    }
}
