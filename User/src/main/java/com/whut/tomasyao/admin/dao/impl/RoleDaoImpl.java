package com.whut.tomasyao.admin.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 19:45
 */

import org.springframework.stereotype.Component;
import com.whut.tomasyao.base.dao.impl.BaseDaoImpl;
import com.whut.tomasyao.admin.model.Role;
import com.whut.tomasyao.admin.dao.IRoleDao;

@Component
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao {
    public RoleDaoImpl() {
        super(Role.class);
    }
}
