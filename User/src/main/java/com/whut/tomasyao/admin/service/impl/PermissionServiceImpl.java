package com.whut.tomasyao.admin.service.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-04-08 15:17
 */

import edu.whut.pocket.admin.dao.*;
import edu.whut.pocket.admin.model.*;
import edu.whut.pocket.admin.service.IAdminService;
import edu.whut.pocket.base.util.HqlUtil;
import edu.whut.pocket.base.vo.Parameter;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import edu.whut.pocket.admin.service.IPermissionService;

import java.util.*;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private IModuleDao moduleDao;
    @Autowired
    private IRoleDao roleDao;
    @Autowired
    private IAdminDao adminDao;
    @Autowired
    private IRoleModuleDao roleModuleDao;
    @Autowired
    private IAdminModuleDao adminModuleDao;
    @Autowired
    private IAdminService adminService;

    @Override
    public List<RoleModule> getRoleModuleList(int roleId) throws Exception {
        String hql = " from RoleModule r ";
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<>();

        condition.add(" r.isDeleted=:isDeleted ");
        p.put("isDeleted", false);
        condition.add(" r.roleId=:roleId ");
        p.put("roleId", roleId);

        return roleModuleDao.findList(HqlUtil.formatHql(hql, condition), p);
    }

    @Override
    public List<Module> getRoleModuleFormatList(int roleId) throws Exception {
        List<RoleModule> roleModules = getRoleModuleList(roleId);
        List<Integer> moduleIds = new ArrayList<>();
        for(RoleModule r : roleModules){
            moduleIds.add(r.getModuleId());
        }
        List<Module> unTotals = adminService.getClearIdsModuleList(moduleIds, roleId);//不包含parentId

        List<Integer> moduleParentIds = new ArrayList<>();
        for(Module m : unTotals){
            moduleParentIds.add(m.getParentId());
        }
        //再把parent list查询出来
        List<Module> allModuleOfRole = adminService.getClearIdsModuleList(moduleParentIds, roleId);
        allModuleOfRole.addAll(unTotals);

        return allModuleOfRole;
    }

    @Override
    public boolean updateRoleModule(int roleId, String name, String description, Integer[] roleModuleIds) throws Exception {
        if(roleDao.findOne(" from Role r where r.name='"+name+"' and r.id is not '"+roleId+"' ") != null){
            return false;
        }
        Date date = new Date();
        Role role = roleDao.getOne(roleId);
        role.setName(name);
        role.setDescription(description);
        role.setUpdateTime(date);

        /**
         * 如果有数组没有元素
         */
        //新数组为空直接删原数组 并直接return
        if(roleModuleIds.length == 0){
            roleModuleDao.deleteWithHql(" delete from RoleModule r where r.roleId=:p0 ", new Parameter(roleId));
            return true;
        }
        //原数组为空直接保存新数组 并直接return
        List<RoleModule> roleModuleList = roleModuleDao.findList(
                " from RoleModule r where r.roleId=:p0 and r.isDeleted=false ", new Parameter(roleId));
        if(roleModuleList.size() == 0){
            for(Integer i : roleModuleIds){
                roleModuleList.add(new RoleModule(roleId, i, date, false));
            }
            //batchSave
            roleModuleDao.batchSave(roleModuleList);
            return true;
        }
        /**
         * 构造待删除的list和待新增的list(不使用暴力遍历,复杂度太高O(n^2),而是用中间map以空间换时间)
         */
        //构造该角色原本的module id数组
        Integer[] originModuleArray = new Integer[roleModuleList.size()];
        for(int i=0; i<roleModuleList.size(); i++){
            originModuleArray[i] = roleModuleList.get(i).getModuleId();
        }
        //原来数组中应该被删掉的,和新数组中应该被新增的list
        List<Integer> willDelList = new ArrayList<>();
        List<RoleModule> willAddList = new ArrayList<>();
        /**
         * 先把originModuleArray放进map,再放roleModuleIds
         * roleModuleIds 1(代号)
         * originModuleArray 2(代号)
         */
        /*这段暂不需要
        Integer[] minIds, maxIds;
        if(roleModuleIds.length < originModuleArray.length){
            minIds = roleModuleIds; maxIds = originModuleArray;
        }else {
            minIds = originModuleArray; maxIds = roleModuleIds;
        }*/
        Map<Integer, int[]> map = new HashMap<>();//Integer: module id值 int[0]: 数量 int[1]: 代号1/2
        for(Integer i : originModuleArray){
            map.put(i, new int[]{1, 2});
        }
        for(Integer i : roleModuleIds){
            if(map.get(i) != null){//共同元素int[0]: 2 int[1] 3
                map.put(i, new int[]{2, 3});
            }else {
                map.put(i, new int[]{1, 1});
            }
        }
        //此时遍历map同时给willDelList和willAddList赋值
        for(Integer id : map.keySet()){
            if(map.get(id)[0] == 1 && map.get(id)[1] == 1){//roleModuleIds独有
                willAddList.add(new RoleModule(roleId, id, date, false));
            }else if(map.get(id)[0] == 1 && map.get(id)[1] == 2){//originModuleArray独有
                willDelList.add(id);
            }
        }
        /**
         * 该删的删 该增的增
         */
        if(willDelList.size() != 0){
            roleModuleDao.deleteWithHql(" delete from RoleModule r where r.roleId='"+roleId+"' " +
                    " and r.moduleId in (:p0) ", new Parameter(willDelList));
        }
        if(willAddList.size() != 0){
            roleModuleDao.batchSave(willAddList);
        }

        return true;
    }

    @Override
    public List<AdminModule> getAdminModuleList(int adminId) throws Exception {
        String hql = " from AdminModule a ";
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<>();

        condition.add(" a.adminId=:adminId ");
        p.put("adminId", adminId);

        return adminModuleDao.findList(HqlUtil.formatHql(hql, condition), p);
    }

    @Override
    public boolean updateAdminModule(int adminId, String name, String contact, Integer[] adminModuleIds) throws Exception {
        Date date = new Date();
        Admin admin = adminDao.getOne(adminId);
        admin.setName(name);
        admin.setContact(contact);
        admin.setUpdateTime(date);

        /**
         * 如果有数组没有元素
         */
        //新数组为空直接删原数组 并直接return
        if(adminModuleIds.length == 0){
            adminModuleDao.deleteWithHql(" delete from AdminModule a where a.adminId=:p0 ", new Parameter(adminId));
            return true;
        }
        //原数组为空直接保存新数组 并直接return
        List<AdminModule> adminModuleList = adminModuleDao.findList(
                " from AdminModule a where a.adminId=:p0 ", new Parameter(adminId));
        if(adminModuleList.size() == 0){
            for(Integer i : adminModuleIds){
                adminModuleList.add(new AdminModule(adminId, i, date));
            }
            //batchSave
            adminModuleDao.batchSave(adminModuleList);
            return true;
        }
        /**
         * 构造待删除的list和待新增的list(不使用暴力遍历,复杂度太高O(n^2),而是用中间map以空间换时间)
         */
        //构造该角色原本的module id数组
        Integer[] originModuleArray = new Integer[adminModuleList.size()];
        for(int i=0; i<adminModuleList.size(); i++){
            originModuleArray[i] = adminModuleList.get(i).getModuleId();
        }
        //原来数组中应该被删掉的,和新数组中应该被新增的list
        List<Integer> willDelList = new ArrayList<>();
        List<AdminModule> willAddList = new ArrayList<>();
        /**
         * 先把originModuleArray放进map,再放adminModuleIds
         * adminModuleIds 1(代号)
         * originModuleArray 2(代号)
         */
        Map<Integer, int[]> map = new HashMap<>();//Integer: module id值 int[0]: 数量 int[1]: 代号1/2
        for(Integer i : originModuleArray){
            map.put(i, new int[]{1, 2});
        }
        for(Integer i : adminModuleIds){
            if(map.get(i) != null){//共同元素int[0]: 2 int[1] 3
                map.put(i, new int[]{2, 3});
            }else {
                map.put(i, new int[]{1, 1});
            }
        }
        //此时遍历map同时给willDelList和willAddList赋值
        for(Integer id : map.keySet()){
            if(map.get(id)[0] == 1 && map.get(id)[1] == 1){//adminModuleIds独有
                willAddList.add(new AdminModule(adminId, id, date));
            }else if(map.get(id)[0] == 1 && map.get(id)[1] == 2){//originModuleArray独有
                willDelList.add(id);
            }
        }
        /**
         * 该删的删 该增的增
         */
        if(willDelList.size() != 0){
            adminModuleDao.deleteWithHql(" delete from AdminModule a where a.adminId='"+adminId+"' " +
                    " and a.moduleId in (:p0) ", new Parameter(willDelList));
        }
        if(willAddList.size() != 0){
            adminModuleDao.batchSave(willAddList);
        }

        return true;
    }
}
