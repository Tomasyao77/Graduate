package com.whut.tomasyao.admin.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 20:12
 */

import org.springframework.stereotype.Component;
import com.whut.tomasyao.base.dao.impl.BaseDaoImpl;
import com.whut.tomasyao.admin.model.AdminModule;
import com.whut.tomasyao.admin.dao.IAdminModuleDao;

@Component
public class AdminModuleDaoImpl extends BaseDaoImpl<AdminModule> implements IAdminModuleDao {
    public AdminModuleDaoImpl() {
        super(AdminModule.class);
    }
}
