package com.whut.tomasyao.admin.service;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 20:14
 */

import edu.whut.pocket.admin.model.Admin;
import edu.whut.pocket.admin.model.Module;
import edu.whut.pocket.admin.model.Role;
import edu.whut.pocket.admin.vo.ModuleVo;
import edu.whut.pocket.admin.vo.ModulesVo;
import edu.whut.pocket.base.vo.Page;

import java.util.Date;
import java.util.List;

public interface IAdminService {

    /**
     * 基础操作(CURD)
     * module
     * role
     * admin
     */

    /**module*/
    //获取模块
    Module getOneModule(int id) throws Exception;
    //新增模块
    Module addOneModule(String name, int parentId, String url, String icon, int type) throws Exception;
    //更新模块
    Module updateOneModule(int id, String name, String url, String icon, int type) throws Exception;
    boolean updateModuleIndex(Integer sId, Integer[] sIdSubIds, Integer dId, Integer[] dIdSubIds, int type) throws Exception;
    //禁用模块
    boolean deleteOneModule(int id) throws Exception;
    //删除模块
    boolean deleteOneModuleForce(int id) throws Exception;
    //(非root)获取模块列表
    List<Module> getModuleList(int userId, int type) throws Exception;
    //获取指定ids的模块列表
    List<Module> getClearIdsModuleList(List<Integer> moduleIds) throws Exception;
    //(root)获取指定ids的模块列表
    List<Module> getClearIdsModuleList(List<Integer> moduleIds, int role) throws Exception;
    //获取模块分页列表
    Page<Module> getModulePageList(int current, int size) throws Exception;
    //(root)mybatis获取模块列表
    List<ModuleVo> getModuleListMybatis(int userId, int type) throws Exception;
    //更新status，为0则root以下的管理员无法获取
    void updateModuleStatus(int id, boolean status) throws Exception;

    /**role*/
    //获取角色
    Role getOneRole(int id) throws Exception;
    //新增角色
    Role addOneRole(String name, String description, Integer[] moduleIds, int type) throws Exception;
    //禁用角色
    boolean deleteOneRole(int id) throws Exception;
    //删除角色
    boolean deleteOneRoleForce(int id) throws Exception;
    //获取角色列表
    List<Role> getRoleList(int type) throws Exception;
    //获取角色分页列表
    Page<Role> getRolePageList(int userId, int current, int size, String name, String orderBy, Boolean asc, int type) throws Exception;

    /**admin*/
    //获取管理员
    Admin getOneAdmin(int id) throws Exception;
    //新增管理员
    Admin addOneAdmin(String username, String password, Integer parentId,
                      int roleId, String contact, String name) throws Exception;
    //更新管理员
    Admin updateOneAdmin(int id, String name, String contact, int roleId) throws Exception;
    //禁/启用管理员
    boolean deleteOneAdmin(int id) throws Exception;
    //删除管理员
    boolean deleteOneAdminForce(int id) throws Exception;
    //获取管理员列表
    List<Admin> getAdminList() throws Exception;
    //获取管理员分页列表
    Page<Admin> getAdminPageList(int userId, int current, int size, String username, int roleId,
                                 Integer parentId, String orderBy, Boolean asc, int type) throws Exception;
    //新增管理员子账号
    Admin addOneAdminSub(String username, String password, Integer parentId,
                         int roleId, String contact, String name, Integer[] moduleIds) throws Exception;

    //修改密码
    Admin updateAdminPwd(int id, String oldPwd, String newPwd) throws Exception;

}
