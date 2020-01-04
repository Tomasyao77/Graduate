package com.whut.tomasyao.user.service.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-06-13 21:23
 */

import edu.whut.pocket.base.model.User;
import edu.whut.pocket.base.service.IAreaService;
import edu.whut.pocket.base.util.HqlUtil;
import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.base.vo.Parameter;
import edu.whut.pocket.user.dao.IUserDao;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import edu.whut.pocket.user.model.UserAddress;
import edu.whut.pocket.user.service.IUserAddressService;
import edu.whut.pocket.user.dao.IUserAddressDao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserAddressServiceImpl implements IUserAddressService {

    @Autowired
    private IUserAddressDao userAddressDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IAreaService areaService;

    @Override
    public UserAddress getOneUserAddress(int id) throws Exception {
        return userAddressDao.getOne(id);
    }

    @Override
    public UserAddress addOneUserAddress(int userId, int areaCode, String address, String realName, String phone) throws Exception {
        User user = userDao.getOne(userId);
        if(user == null || address == null || address.isEmpty()){
            return null;
        }
        UserAddress userAddress = new UserAddress(user, areaService.getAreaByCode(areaCode),
                null, null, address, realName, phone);
        return userAddressDao.save(userAddress);
    }

    @Override
    public UserAddress updateOneUserAddress(int id, int areaCode, String address, String realName, String phone) throws Exception {
        UserAddress userAddress = userAddressDao.getOne(id);
        if(userAddress == null || address == null || address.isEmpty()){
            return null;
        }
        userAddress.setArea(areaService.getAreaByCode(areaCode));
        userAddress.setAddress(address);
        userAddress.setRealName(realName);
        userAddress.setPhone(phone);
        userAddress.setUpdateTime(new Date());
        userAddressDao.update(userAddress);
        return userAddress;
    }

    @Override
    public Page<UserAddress> getUserAddressPageList(int current, int size, int userId, String address,
                                                    String orderBy, Boolean asc) {
        List<String> conditions = HqlUtil.getConditions();
        Parameter p = new Parameter();
        if (address != null && !address.isEmpty()) {
            conditions.add(" u.address like :address ");
            p.put("address", "%"+address+"%");
        }
        conditions.add(" u.user.id = :userId ");
        p.put("userId", userId);
        conditions.add(" u.isDeleted = :isDeleted ");
        p.put("isDeleted", false);
        return userAddressDao.findPage(current, size,
                HqlUtil.formatHql(" from UserAddress u ",
                        conditions, orderBy, asc),
                HqlUtil.formatHql(" select count(*) from UserAddress u ", conditions), p);
    }

    @Override
    public boolean updateUserAddressAsDefault(int userId, int uaId) throws Exception {
        userAddressDao.update(" update UserAddress u set u.isDefault=false where u.user.id='"+userId+"' ");
        userAddressDao.update(" update UserAddress u set u.isDefault=true where u.user.id='"+userId+"' "+
                " and u.id='"+uaId+"' ");
        return true;
    }

    @Override
    public boolean deleteAddress(int userId, Integer[] uaIds) throws Exception {
        if(uaIds == null || uaIds.length == 0){
            return false;
        }
        userAddressDao.update(" update UserAddress u set u.isDeleted=true where u.id in (:p0) " +
                " and u.user.id='"+userId+"' ", new Parameter(Arrays.asList(uaIds)));
        return true;
    }

}
