package com.whut.tomasyao.login.service.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 19:01
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whut.tomasyao.admin.dao.IAdminDao;
import com.whut.tomasyao.admin.model.Admin;
import com.whut.tomasyao.admin.model.Module;
import com.whut.tomasyao.admin.service.IPermissionService;
import com.whut.tomasyao.admin.util.ModuleUtil;
import com.whut.tomasyao.admin.vo.ModulesVo;
import com.whut.tomasyao.auth.dao.IAdminRedisVoDao;
import com.whut.tomasyao.auth.dao.ISwaggerDao;
import com.whut.tomasyao.auth.dao.IUserRedisVoDao;
import com.whut.tomasyao.auth.model.Swagger;
import com.whut.tomasyao.auth.model.UserType;
import com.whut.tomasyao.auth.vo.AdminRedisVo;
import com.whut.tomasyao.auth.vo.UserRedisVo;
import com.whut.tomasyao.base.common.Constant;
import com.whut.tomasyao.base.model.User;
import com.whut.tomasyao.base.util.EncryptUtil;
import com.whut.tomasyao.login.model.UserPlatform;
import com.whut.tomasyao.message.service.ILogMessageService;
import com.whut.tomasyao.message.util.MessageUtil;
import com.whut.tomasyao.user.dao.IUserDao;
import com.whut.tomasyao.user.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.whut.tomasyao.login.service.ILoginService;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private IAdminDao adminDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IAdminRedisVoDao adminRedisVoDao;
    @Autowired
    private IUserRedisVoDao userRedisVoDao;
    @Autowired
    private ISwaggerDao swaggerDao;
    @Autowired
    private IUserService userService;

    @Override
    @SuppressWarnings("unchecked")
    public AdminRedisVo loginAdmin(String token, String username, String password) throws Exception {
        //查找对应用户
        Admin admin = adminDao.findOne(" from Admin a where a.username='"+username+"' and a.password='"+
                EncryptUtil.md5(password)+"' and a.status=true and a.isDeleted=false ");
        if(admin == null){
            return null;
        }
        //查找对应角色与权限
        List<Module> adminRoleModules = permissionService.getRoleModuleFormatList(admin.getRole().getId());
        List<ModulesVo> modulesVos = ModuleUtil.formatModuleList(adminRoleModules);//格式化成树型结构
        //权限id set集合(包含了parentId)
        Set<Integer> adminRoleModuleIds = new HashSet<>(adminRoleModules.size());
        for (Module adminModule : adminRoleModules) {
            adminRoleModuleIds.add(adminModule.getId());
        }
        //将登录用户的权限等信息写到redis
        AdminRedisVo adminRedisVo = new AdminRedisVo(admin.getId(), username, admin.getRole().getName(),
                new Date(), new ObjectMapper().writeValueAsString(modulesVos), adminRoleModuleIds);
        adminRedisVoDao.put(token, adminRedisVo, Constant.TOKEN_EXPIRE);
        return adminRedisVo;
    }

    @Override
    public void logoutAdmin(int userId, String token) throws Exception {
        if(token != null){
            adminRedisVoDao.delete(token);
        }
    }

    @Override
    public boolean loginSwagger(String username, String password, String ip) throws Exception {
        boolean result = username.equals("swag") && password.equals(EncryptUtil.md5("swag"));
        Swagger swagger = swaggerDao.findOne(" from Swagger s" +
                " where s.ip='"+ip+"' ");
        if(result){
            if(swagger == null){
                swaggerDao.save(new Swagger(ip, new Date(), new Date()));
            }else {
                swagger.setNum(swagger.getNum() + 1);//登录次数
                swagger.setUpdateTime(new Date());
            }
        }
        return result;
    }

    @Override
    public UserRedisVo loginUser(String token, String phone, String code, Integer platform) throws Exception {
        //查找对应用户
        User user = userDao.findOne(" from User u where u.phone='"+phone+"' and u.status=true ");
        if(user == null){//新用户,后台自动注册
            user = userService.addOneUser("", null, platform, "");
        }

        //将登录用户的相关信息写到redis
        UserRedisVo userRedisVo = new UserRedisVo(user.getId(), user.getUsername(), UserType.普通用户,
                UserPlatform.values()[platform], user);
        userRedisVoDao.put(token, userRedisVo, 15 * 24 * 3600);//app登录，信息保存15天
        return userRedisVo;
    }

    @Override
    public UserRedisVo loginSmallCode(String token, String openId) throws Exception {
        //查找对应用户
        User user = userDao.findOne(" from User u where u.openId='"+openId+"' and u.status=true ");
        if(user == null){
            return null;
        }

        //将登录用户的相关信息写到redis
        UserRedisVo userRedisVo = new UserRedisVo(user.getId(), user.getUsername(), UserType.普通用户,
                UserPlatform.values()[0], user);
        userRedisVoDao.put(token, userRedisVo, Constant.TOKEN_EXPIRE);
        return userRedisVo;
    }

}
