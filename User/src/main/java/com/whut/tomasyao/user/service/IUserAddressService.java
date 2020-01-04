package com.whut.tomasyao.user.service;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-06-13 21:22
 */

import edu.whut.pocket.user.model.UserAddress;
import edu.whut.pocket.base.vo.Page;

public interface IUserAddressService {

    //获取一条记录
    UserAddress getOneUserAddress(int id) throws Exception;
    //新增一条记录
    UserAddress addOneUserAddress(int userId, int areaCode, String address, String realName, String phone) throws Exception;
    //编辑一条记录
    UserAddress updateOneUserAddress(int id, int areaCode, String address, String realName, String phone) throws Exception;
    //获取收货地址分页列表
    Page<UserAddress> getUserAddressPageList(int current, int size, int userId, String address, String orderBy, Boolean asc);
    //设为默认收货地址
    boolean updateUserAddressAsDefault(int userId, int uaId) throws Exception;
    //删除一条记录
    boolean deleteAddress(int userId, Integer[] uaIds) throws Exception;

}
