package com.whut.tomasyao.user.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-02-11 20:39
 */

import org.springframework.stereotype.Component;
import edu.whut.pocket.base.dao.impl.BaseDaoImpl;
import edu.whut.pocket.base.model.User;
import edu.whut.pocket.user.dao.IUserDao;

@Component
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {
    public UserDaoImpl() {
        super(User.class);
    }
}
