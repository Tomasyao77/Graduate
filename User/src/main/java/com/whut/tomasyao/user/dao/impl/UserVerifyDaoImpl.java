package com.whut.tomasyao.user.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-06-26 08:47
 */

import org.springframework.stereotype.Component;
import edu.whut.pocket.base.dao.impl.BaseDaoImpl;
import edu.whut.pocket.user.model.UserVerify;
import edu.whut.pocket.user.dao.IUserVerifyDao;

@Component
public class UserVerifyDaoImpl extends BaseDaoImpl<UserVerify> implements IUserVerifyDao {
    public UserVerifyDaoImpl() {
        super(UserVerify.class);
    }
}
