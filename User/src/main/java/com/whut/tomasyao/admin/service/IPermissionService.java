package com.whut.tomasyao.admin.service;

import com.whut.tomasyao.admin.model.AdminModule;
import com.whut.tomasyao.admin.model.Module;
import com.whut.tomasyao.admin.model.RoleModule;

import java.util.List;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 20:29
 */


public interface IPermissionService {

    /**
     * 高级操作(表关联)
     * module
     * role
     * admin
     */

    /**角色模块关联*/
    //获取角色_模块列表
    List<RoleModule> getRoleModuleList(int roleId) throws Exception;
    //获取角色_模块列表(为树型结构做准备)
    List<Module> getRoleModuleFormatList(int roleId) throws Exception;
    //编辑角色_模块
    boolean updateRoleModule(int roleId, String name, String description, Integer[] roleModuleIds) throws Exception;

    /**管理员子账号管理*/
    //获取管理员_模块列表
    List<AdminModule> getAdminModuleList(int adminId) throws Exception;
    //编辑管理员_模块
    boolean updateAdminModule(int adminId, String name, String contact, Integer[] adminModuleIds) throws Exception;

}
