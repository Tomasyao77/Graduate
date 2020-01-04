package com.whut.tomasyao.admin.service.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 20:14
 */

import com.whut.tomasyao.admin.dao.*;
import com.whut.tomasyao.admin.mapper.AdminMapper;
import com.whut.tomasyao.admin.model.*;
import com.whut.tomasyao.admin.vo.ModuleVo;
import com.whut.tomasyao.admin.vo.ModulesVo;
import com.whut.tomasyao.base.common.AdminType;
import com.whut.tomasyao.base.util.EncryptUtil;
import com.whut.tomasyao.base.util.HqlUtil;
import com.whut.tomasyao.base.vo.Page;
import com.whut.tomasyao.base.vo.Parameter;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.whut.tomasyao.admin.service.IAdminService;

import java.util.*;

@Service
public class AdminServiceImpl implements IAdminService {

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
    private AdminMapper adminMapper;


    @Override
    public Module getOneModule(int id) throws Exception {
        return null;
    }

    @Override
    public Module addOneModule(String name, int parentId, String url, String icon, int type) throws Exception {
        if(moduleDao.findOne(" from Module m where m.name='"+name+"' and m.type='"+type+"' " +
                " and m.isDeleted=false ") != null){
            return null;
        }

        Module module = new Module(name, parentId, url, icon ,0,
                new Date(), new Date(), false, false, type);
        int finalIndex;
        if(parentId == 0){//增加父模块
            synchronized (AdminServiceImpl.class){
                Module tempModule = moduleDao.findOne(" from Module m order by m.index desc ");
                if(tempModule != null){
                    finalIndex = (tempModule.getIndex() / 10 + 1) * 10;
                    module.setIndex(finalIndex);
                }
            }
        }else {//增加子模块
            synchronized (AdminServiceImpl.class){
                Module pModule = moduleDao.getOne(parentId);
                int indexFloor = (pModule.getIndex() / 10) * 10;
                int indexCeil = (pModule.getIndex() / 10 + 1) * 10;
                Module tempModule = moduleDao.findOne(" from Module m where " +
                        " m.index>='"+indexFloor+"' and m.index<'"+indexCeil+"' order by m.index desc ");
                if(tempModule != null){
                    finalIndex = tempModule.getIndex() + 1;
                    module.setIndex(finalIndex);
                }
            }
        }

        moduleDao.save(module);
        return module;
    }

    @Override
    public Module updateOneModule(int id, String name, String url, String icon, int type) throws Exception {
        if(moduleDao.findOne(" from Module m where m.name='"+name+"' and m.id is not '"+id+"' " +
                " and m.isDeleted=false and m.type='"+type+"' ") != null){
            return null;
        }

        Module module = moduleDao.getOne(id);
        module.setName(name);
        module.setUrl(url);
        module.setIcon(icon);

        moduleDao.update(module);
        return module;
    }

    @Override
    public boolean updateModuleIndex(Integer sId, Integer[] sIdSubIds, Integer dId, Integer[] dIdSubIds,
                                    int type) throws Exception {
        //不考虑有些被标记删除的子模块
        if(type == 0){//同父移位
            Module destModule = moduleDao.getOne(dId);
            List<Module> destModuleList = moduleDao.findList(" from Module m where m.parentId='"+dId+"' and " +
                    " m.isDeleted=false ");
            Map<Integer, Integer> destModuleMap = new HashMap<>();
            int baseIndex = (destModule.getIndex() / 10) * 10;
            for(int i=0; i<dIdSubIds.length; i++){
                destModuleMap.put(dIdSubIds[i], baseIndex + i + 1);//最新排序
            }
            for(Module m : destModuleList){
                m.setIndex(destModuleMap.get(m.getId()));//不需要update语句
            }
        }else if(type == 1){//异父移位
            Module sourceModule = moduleDao.getOne(sId);
            List<Module> sourceModuleList = moduleDao.findList(" from Module m where m.parentId='"+sId+"' and " +
                    " m.isDeleted=false ");
            Map<Integer, Integer> sourceModuleMap = new HashMap<>();
            Module destModule = moduleDao.getOne(dId);
            List<Module> destModuleList = moduleDao.findList(" from Module m where m.id in (:p0) and " +
                    " m.isDeleted=false ", new Parameter(Arrays.asList(dIdSubIds)));
            Map<Integer, Integer> destModuleMap = new HashMap<>();
            //重新洗牌
            int baseSIndex = (sourceModule.getIndex() / 10) * 10;
            int baseDIndex = (destModule.getIndex() / 10) * 10;
            if(sIdSubIds != null && sIdSubIds.length > 0){//不一定有
                for(int i=0; i<sIdSubIds.length; i++){
                    sourceModuleMap.put(sIdSubIds[i], baseSIndex + i + 1);//最新排序
                }
            }
            for(int i=0; i<dIdSubIds.length; i++){
                destModuleMap.put(dIdSubIds[i], baseDIndex + i + 1);//最新排序
            }
            //更新数据库
            for(Module m : sourceModuleList){
                if(sourceModuleMap.get(m.getId()) != null){
                    m.setIndex(sourceModuleMap.get(m.getId()));//source只减少
                    m.setParentId(sId);
                }
            }
            for(Module m : destModuleList){
                m.setIndex(destModuleMap.get(m.getId()));//dest只增多
                m.setParentId(dId);
            }
        }else if(type == 2){//父父移位
            Map<Integer, Integer> destModuleMap = new HashMap<>();
            for(int i=0; i<dIdSubIds.length; i++){
                destModuleMap.put(dIdSubIds[i], i*10);//最新父id排序
            }
            //调用存储过程
            for(Integer i : destModuleMap.keySet()){
                moduleDao.updateModuleAllIndex(i, destModuleMap.get(i));
            }
        }

        return true;
    }

    @Override
    public boolean deleteOneModule(int id) throws Exception {
        Module module = moduleDao.getOne(id);
        module.setDeleted(true);
        if(module.getParentId() == 0){//删除子模块
            moduleDao.update(" update Module m set m.isDeleted=true where m.parentId='"+module.getId()+"' ");
            //同时角色与该模块的所有子模块相关联的记录也要删除
            List<Module> modules = moduleDao.findList(" from Module m where m.parentId='"+module.getId()+"' ");
            List<Integer> moduleIds = new ArrayList<>();
            for(Module m : modules){
                moduleIds.add(m.getId());
            }
            if(moduleIds.size() > 0){
                roleModuleDao.deleteWithHql(" delete RoleModule r where r.moduleId in (:p0) ", new Parameter(moduleIds));
            }
        }else {
            roleModuleDao.deleteWithHql(" delete RoleModule r where r.moduleId='"+module.getId()+"' ");
        }

        //子账号与该模块相关联的记录也要删除

        return true;
    }

    @Override
    public boolean deleteOneModuleForce(int id) throws Exception {
        return false;
    }

    @Override
    public List<Module> getModuleList(int userId, int type) throws Exception {
        String hql = " from Module m ";
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<>();

        if(userId !=6 && userId != 11){
            condition.add(" m.status=:status ");
            p.put("status", true);
        }
        condition.add(" m.isDeleted=:isDeleted ");
        p.put("isDeleted", false);
        condition.add(" m.type=:type ");
        p.put("type", type);

        return moduleDao.findList(HqlUtil.formatHql(hql, condition), p);
    }

    @Override
    public List<Module> getClearIdsModuleList(List<Integer> moduleIds) throws Exception {
        String hql = " from Module m ";
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<>();

        condition.add(" m.status=:status ");
        p.put("status", true);
        condition.add(" m.isDeleted=:isDeleted ");
        p.put("isDeleted", false);
        condition.add(" m.id in (:moduleIds) ");
        p.put("moduleIds", moduleIds);

        return moduleDao.findList(HqlUtil.formatHql(hql, condition), p);
    }

    @Override
    public List<Module> getClearIdsModuleList(List<Integer> moduleIds, int role) throws Exception {
        String hql = " from Module m ";
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<>();

        if(role != 6 && role != 7){
            condition.add(" m.status=:status ");
            p.put("status", true);
        }
        condition.add(" m.isDeleted=:isDeleted ");
        p.put("isDeleted", false);
        condition.add(" m.id in (:moduleIds) ");
        p.put("moduleIds", moduleIds);

        return moduleDao.findList(HqlUtil.formatHql(hql, condition), p);
    }

    @Override
    public Page<Module> getModulePageList(int current, int size) throws Exception {
        String hql = " from Module m ";
        String countHql = " select count(*) from Module m ";
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<String>();

        condition.add(" m.status=:status ");
        p.put("status", true);
        condition.add(" m.isDeleted=:isDeleted ");
        p.put("isDeleted", false);

        return moduleDao.findPage(current, size, HqlUtil.formatHql(hql, condition),
                HqlUtil.formatHql(countHql, condition), p);
    }

    @Override
    public List<ModuleVo> getModuleListMybatis(int userId, int type) throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("parent_id", 0);
        hashMap.put("type", type);

        return adminMapper.getModuleList(hashMap);
    }

    @Override
    public void updateModuleStatus(int id, boolean status) throws Exception {
        Module module = moduleDao.getOne(id);
        module.setStatus(status);
        if(module.getParentId() == 0){//如果是父模块，子模块继承操作
            moduleDao.update(" update Module m set m.status=:p0 where m.parentId=:p1 ",
                    new Parameter(status, module.getId()));
        }else {//如果子模块是开启，父模块也要开启
            if(status){
                moduleDao.update(" update Module m set m.status=:p0 where m.id=:p1 ",
                        new Parameter(true, module.getParentId()));
            }
        }
        moduleDao.update(module);
    }

    @Override
    public Role getOneRole(int id) throws Exception {
        return null;
    }

    @Override
    public Role addOneRole(String name, String description, Integer[] moduleIds, int type) throws Exception {
        if(roleDao.findOne(" from Role r where r.name='"+name+"'" +
                " and r.isDeleted=false ") != null){
            return null;
        }
        Role role = new Role(name, description, new Date(), new Date(), false, type);
        roleDao.save(role);
        //批量保存某一角色的权限列表
        List<Module> modules = moduleDao.findList(" from Module m where m.id in (:p0) " +
                " and m.isDeleted=false ", new Parameter(Arrays.asList(moduleIds)));
        List<RoleModule> roleModules = new ArrayList<>();

        int roleId = role.getId();
        Date date = new Date();
        for(Module m : modules){
            roleModules.add(new RoleModule(roleId, m.getId(), date, false));
        }
        roleModuleDao.batchSave(roleModules);
        return role;
    }

    @Override
    public boolean deleteOneRole(int id) throws Exception {
        Role role = roleDao.getOne(id);
        if(role.getName().equals("root") || role.getName().equals("super")){
            return false;
        }
        role.setDeleted(!role.isDeleted());
        return true;
    }

    @Override
    public boolean deleteOneRoleForce(int id) throws Exception {
        return false;
    }

    @Override
    public List<Role> getRoleList(int type) throws Exception {
        String hql = " from Role r ";
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<>();

        condition.add(" r.isDeleted=:isDeleted ");
        p.put("isDeleted", false);
        condition.add(" r.type=:type ");
        p.put("type", AdminType.values()[type]);

        return roleDao.findList(HqlUtil.formatHql(hql, condition), p);
    }

    @Override
    public Page<Role> getRolePageList(int userId, int current, int size, String name, String orderBy, Boolean asc, int type) throws Exception {
        String hql = " from Role r ";
        String countHql = " select count(*) from Role r ";
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<>();

        condition.add(" r.isDeleted=:isDeleted ");
        p.put("isDeleted", false);
        if(name!=null && !name.isEmpty()){
            condition.add(" r.name like :name ");
            p.put("name", "%"+name+"%");
        }
        condition.add(" r.type=:type ");
        p.put("type", AdminType.values()[type]);
        //如果登录的不是root或super获取的列表要排除(角色)这两项
        if(6 != userId && 11 != userId){
            condition.add(" r.id != 6 and r.id != 7 ");
        }

        return roleDao.findPage(current, size, HqlUtil.formatHql(hql, condition, orderBy, asc),
                HqlUtil.formatHql(countHql, condition), p);
    }

    @Override
    public Admin getOneAdmin(int id) throws Exception {
        return adminDao.getOne(id);
    }

    @Override
    public Admin addOneAdmin(String username, String password, Integer parentId,
                             int roleId, String contact, String name) throws Exception {
        if(adminDao.findOne(" from Admin a where a.username='"+username+"' " +
                " and a.isDeleted=false ") != null){
            return null;
        }
        Admin admin = new Admin(username, EncryptUtil.md5(password), new Date(), new Date(),
                true, false, adminDao.getOne(parentId), roleDao.getOne(roleId), contact, name);
        return adminDao.save(admin);
    }

    @Override
    public Admin updateOneAdmin(int id, String contact, String name, int roleId) throws Exception {
        Admin admin = adminDao.getOne(id);
        if(admin == null){
            return null;
        }
        admin.setContact(contact);
        admin.setName(name);
        admin.setRole(roleDao.getOne(roleId));
        admin.setUpdateTime(new Date());

        return admin;
    }

    @Override
    public boolean deleteOneAdmin(int id) throws Exception {
        Admin admin = adminDao.getOne(id);
        if(admin.getRole().getName().equals("root") ||
                admin.getRole().getName().equals("super")){
            return false;
        }
        admin.setStatus(!admin.isStatus());
        return true;
    }

    @Override
    public boolean deleteOneAdminForce(int id) throws Exception {
        //删除管理员
        Admin admin = adminDao.getOne(id);
        if(admin.getRole().getName().equals("root") ||
                admin.getRole().getName().equals("super")){
            return false;
        }
        admin.setDeleted(true);
        return true;
    }

    @Override
    public List<Admin> getAdminList() throws Exception {
        return null;
    }

    @Override
    public Page<Admin> getAdminPageList(int userId, int current, int size, String username, int roleId,
                                        Integer parentId, String orderBy, Boolean asc, int type) throws Exception {
        String hql = " from Admin a ";
        String countHql = " select count(*) from Admin a ";
        Parameter p = new Parameter();
        List<String> condition = new ArrayList<>();

        condition.add(" a.isDeleted=:isDeleted ");
        p.put("isDeleted", false);
        if(username!=null && !username.isEmpty()){
            condition.add(" a.username like :username ");
            p.put("username", "%"+username+"%");
        }
        //管理员类型： 0内容/1商户
        condition.add(" a.role.type=:roleType ");
        p.put("roleType", AdminType.values()[type]);
        if(roleId > 0){
            condition.add(" a.role.id=:roleId ");
            p.put("roleId", roleId);
        }
        //父子账号,现在不用
        if(parentId == 0){
            condition.add(" a.parent is null ");
        }else if(parentId > 0){
            condition.add(" a.parent.id=:parentId ");
            p.put("parentId", parentId);
        }
        //如果登录的不是root或super获取的列表要排除这两项
        if(6 != userId && 11 != userId){
            condition.add(" a.id != 6 and a.id != 11 ");
        }

        return adminDao.findPage(current, size, HqlUtil.formatHql(hql, condition, orderBy, asc),
                HqlUtil.formatHql(countHql, condition), p);
    }

    @Override
    public Admin addOneAdminSub(String username, String password, Integer parentId, int roleId,
                                String contact, String name, Integer[] moduleIds) throws Exception {
        Admin admin = addOneAdmin(username, password, parentId, roleId, contact, name);
        if(admin == null){
            return null;
        }
        //增加子账号后,对子账号_模块权限的表也要更新
        List<Module> modules = moduleDao.findList(" from Module m where m.id in (:p0) and " +
                " m.isDeleted=false ", new Parameter(Arrays.asList(moduleIds)));
        List<AdminModule> adminModules = new ArrayList<>();

        int adminId = admin.getId();
        Date date = new Date();
        for(Module m : modules){
            adminModules.add(new AdminModule(adminId, m.getId(), date));
        }
        adminModuleDao.batchSave(adminModules);

        return admin;
    }

    @Override
    public Admin updateAdminPwd(int id, String oldPwd, String newPwd) throws Exception {
        Admin admin = adminDao.getOne(id);
        if(EncryptUtil.md5(oldPwd).equals(admin.getPassword())){
            admin.setPassword(EncryptUtil.md5(newPwd));
            adminDao.update(admin);
            return admin;
        }
        return null;
    }
}
