package com.whut.tomasyao.user.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-06-13 21:24
 */

import org.springframework.stereotype.Component;
import com.whut.tomasyao.base.dao.impl.BaseDaoImpl;
import com.whut.tomasyao.user.model.UserAddress;
import com.whut.tomasyao.user.dao.IUserAddressDao;

@Component
public class UserAddressDaoImpl extends BaseDaoImpl<UserAddress> implements IUserAddressDao {
    public UserAddressDaoImpl() {
        super(UserAddress.class);
    }
}
