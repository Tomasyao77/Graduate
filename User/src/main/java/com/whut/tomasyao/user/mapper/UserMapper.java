package com.whut.tomasyao.user.mapper;

import edu.whut.pocket.base.model.User;
import edu.whut.pocket.user.vo.UserVerifyVo;
import edu.whut.pocket.user.vo.UserVo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zouy on 18-6-29.
 */
@Repository
public interface UserMapper {
    UserVo getUser(int id);
    UserVo getUserTwo(int id, String phone);
    Integer findCount(String phone);
    List<User> findList(String phone);
    List<User> findPage(HashMap<String, Object> hashMap);
    Integer updateUser(HashMap<String, Object> hashMap);

    //用户认证
    List<UserVerifyVo> getUserVerifyPage(HashMap<String, Object> hashMap) throws Exception;

    Integer findCountUserVerifyPage(HashMap<String, Object> hashMap) throws Exception;

    //获取一条信息(店铺详情)
    UserVerifyVo getStoreDetailByUserId(int userId) throws Exception;

    //通过userId得到用户号码
    String getUserPhoneByUserId(int userId) throws Exception;
}
