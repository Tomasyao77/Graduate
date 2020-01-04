package com.whut.tomasyao.admin.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 20:13
 */

import org.springframework.stereotype.Component;
import edu.whut.pocket.base.dao.impl.BaseDaoImpl;
import edu.whut.pocket.admin.model.Admin;
import edu.whut.pocket.admin.dao.IAdminDao;

@Component
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements IAdminDao {
    public AdminDaoImpl() {
        super(Admin.class);
    }
}
