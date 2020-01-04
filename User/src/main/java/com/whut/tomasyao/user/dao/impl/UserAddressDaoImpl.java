package com.whut.tomasyao.user.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-06-13 21:24
 */

import org.springframework.stereotype.Component;
import edu.whut.pocket.base.dao.impl.BaseDaoImpl;
import edu.whut.pocket.user.model.UserAddress;
import edu.whut.pocket.user.dao.IUserAddressDao;

@Component
public class UserAddressDaoImpl extends BaseDaoImpl<UserAddress> implements IUserAddressDao {
    public UserAddressDaoImpl() {
        super(UserAddress.class);
    }
}
