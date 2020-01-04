package com.whut.tomasyao.admin.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 19:45
 */

import org.springframework.stereotype.Component;
import edu.whut.pocket.base.dao.impl.BaseDaoImpl;
import edu.whut.pocket.admin.model.Role;
import edu.whut.pocket.admin.dao.IRoleDao;

@Component
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao {
    public RoleDaoImpl() {
        super(Role.class);
    }
}
