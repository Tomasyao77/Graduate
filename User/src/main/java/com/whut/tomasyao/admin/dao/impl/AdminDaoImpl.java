package com.whut.tomasyao.admin.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 20:13
 */

import org.springframework.stereotype.Component;
import com.whut.tomasyao.base.dao.impl.BaseDaoImpl;
import com.whut.tomasyao.admin.model.Admin;
import com.whut.tomasyao.admin.dao.IAdminDao;

@Component
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements IAdminDao {
    public AdminDaoImpl() {
        super(Admin.class);
    }
}
