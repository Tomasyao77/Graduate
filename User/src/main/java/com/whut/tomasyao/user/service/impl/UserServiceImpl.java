package com.whut.tomasyao.user.service.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-02-11 20:47
 */

import com.whut.tomasyao.base.model.File;
import com.whut.tomasyao.base.util.EncryptUtil;
import com.whut.tomasyao.base.util.HqlUtil;
import com.whut.tomasyao.base.util.StringUtil;
import com.whut.tomasyao.base.vo.Page;
import com.whut.tomasyao.base.vo.Parameter;
import com.whut.tomasyao.user.mapper.UserMapper;
import com.whut.tomasyao.user.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.whut.tomasyao.base.model.User;
import com.whut.tomasyao.user.service.IUserService;
import com.whut.tomasyao.user.dao.IUserDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVo getOneUser(int id) throws Exception {
        //return userDao.getOne(id);
        return userMapper.getUser(id);
    }

    @Override
    public UserVo getOneUser(int id, String phone) throws Exception {
        return userMapper.getUserTwo(id, phone);
    }

    @Override
    public Integer findCount(String phone) throws Exception {
        return userMapper.findCount(phone);
    }

    @Override
    public List<User> findList(String phone) throws Exception {
        return userMapper.findList(phone);
    }

    @Override
    public Page<User> findPage(String phone, int current, int size) throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        hashMap.put("firstIndex", size*(current-1));
        hashMap.put("size", size);
        List<User> userList = userMapper.findPage(hashMap);
        int count = userMapper.findCount(phone);
        return new Page<>(userList, count, current, size);
    }

    @Override
    public Integer updateUser(int id, String phone, String name) throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        hashMap.put("name", name);
        hashMap.put("id", id);
        userMapper.updateUser(hashMap);

        hashMap.put("phone", "555");
        hashMap.put("name", "555");
        hashMap.put("id", id);
        if(true){
            //throw new Exception("手动异常");//不会事物回滚
            //throw new RuntimeException("手动异常");//会事物回滚
        }
        userMapper.updateUser(hashMap);

        return 1;
    }

    @Override
    public Page<User> getUserPageList(int current, int size, int areaCode, int level,
                                      String name, String orderBy, Boolean asc) {
        /*int mask = (int) Math.pow(100, 3 - level);
        int areaTopCode = areaCode - areaCode % mask;*/
        List<String> conditions = HqlUtil.getConditions();
        /*conditions.add(" u.area.code>=:p0 ");
        conditions.add(" (u.area.code-:p0)<:p1 ");
        Parameter p = new Parameter(areaTopCode, mask);*/
        Parameter p = new Parameter();
        if (name != null && !name.isEmpty()) {
            conditions.add(" u.name like :name ");
            p.put("name", "%"+name+"%");
        }
        return userDao.findPage(current, size,
                HqlUtil.formatHql(" from User u ",
                        conditions, orderBy, asc),
                HqlUtil.formatHql(" select count(*) from User u ", conditions), p);
    }

    @Override
    public User addOneUser(String password, String openId, Integer platform, String wxName) throws Exception {
        User user = new User();
        if(openId != null && !openId.isEmpty()){
            if(userDao.findOne(" from User u where u.openId='"+openId+"' ") != null){
                return null;
            }
        }
        /**
         * 生成hash账号
         * md5(openId + platform + time + nonce)
         */
        synchronized (UserServiceImpl.class){
            String hashPre = "" + openId + platform + String.valueOf(new Date().getTime())
                    + StringUtil.getRandomString(10);
            String account = EncryptUtil.md5(hashPre);

            /*if(userDao.findOne(" from User u where u.username='"+account+"' ") != null){
                return null;
            }*/

            user.setUsername(account);
            user.setPassword(EncryptUtil.md5(password));
            user.setOpenId(openId);
            user.setCreateTime(new Date());
            user.setStatus(true);
            user.setWx_name(wxName);
            userDao.save(user);
        }
        return user;
    }

    @Override
    public User updateOneUser(int id, String phone, String name) throws Exception {
        User user = userDao.getOne(id);
        if(phone != null && !phone.isEmpty()){
            user.setPhone(phone);
        }
        if(name != null && !name.isEmpty()){
            user.setName(name);
        }
        return user;
    }

    @Override
    public User updateAvatar(int id, File picture, File pictureThumb) throws Exception {
        User user = userDao.getOne(id);
        if(picture != null){
            user.setPicture(picture);
        }
        if(pictureThumb != null){
            user.setPictureThumb(pictureThumb);
        }
        return user;
    }

    @Override
    public User getUserByOpenId(String openId) throws Exception {
        return userDao.findOne(" from User u where u.openId='"+openId+"' ");
    }

    @Override
    public Integer updateUserWx(int id, String wxName) throws Exception {
        return userDao.update(" update User u set u.wx_name='"+wxName+"' where u.id='"+id+"' ");
    }

}
