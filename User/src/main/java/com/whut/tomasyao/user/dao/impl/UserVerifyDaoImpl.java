package com.whut.tomasyao.user.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-06-26 08:47
 */

import org.springframework.stereotype.Component;
import com.whut.tomasyao.base.dao.impl.BaseDaoImpl;
import com.whut.tomasyao.user.model.UserVerify;
import com.whut.tomasyao.user.dao.IUserVerifyDao;

@Component
public class UserVerifyDaoImpl extends BaseDaoImpl<UserVerify> implements IUserVerifyDao {
    public UserVerifyDaoImpl() {
        super(UserVerify.class);
    }
}
