package com.whut.tomasyao.user.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-02-11 20:39
 */

import org.springframework.stereotype.Component;
import com.whut.tomasyao.base.dao.impl.BaseDaoImpl;
import com.whut.tomasyao.base.model.User;
import com.whut.tomasyao.user.dao.IUserDao;

@Component
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {
    public UserDaoImpl() {
        super(User.class);
    }
}
