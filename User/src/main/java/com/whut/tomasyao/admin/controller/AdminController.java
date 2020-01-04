package com.whut.tomasyao.admin.controller;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-02-12 13:59
 */

import edu.whut.pocket.admin.mapper.AdminMapper;
import edu.whut.pocket.admin.model.Admin;
import edu.whut.pocket.admin.model.Module;
import edu.whut.pocket.admin.model.Role;
import edu.whut.pocket.admin.service.IAdminService;
import edu.whut.pocket.admin.util.ModuleUtil;
import edu.whut.pocket.admin.vo.AdminVo;
import edu.whut.pocket.admin.vo.ModuleVo;
import edu.whut.pocket.admin.vo.ModulesVo;
import edu.whut.pocket.base.common.MavenModule;
import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.base.vo.ResponseMap;
import edu.whut.pocket.log.aspect.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@ApiIgnore
@Api(tags = {"管理员"}, description = "登录|管理机构|分配权限|数据统计|推送短信", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/user/admin")
public class AdminController {
    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    private IAdminService adminService;
    @Autowired
    private AdminMapper adminMapper;

    @LogAnnotation(opType = "新增模块", description = "新增一个模块", mavenModule = MavenModule.User)
    @RequestMapping(value = "/addOneModule", method = RequestMethod.POST)
    public Map addOneModule(HttpServletRequest request, int userId, String name, int parentId, String url,
                            String icon, int type) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Module module = adminService.addOneModule(name, parentId, url, icon, type);
        if(module == null){
            return map.putFailure("新增失败", -1);
        }

        return map.putValue(module, "新增成功");
    }

    @LogAnnotation(opType = "更新模块", description = "更新一个模块", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateOneModule", method = RequestMethod.POST)
    public Map updateOneModule(HttpServletRequest request, int userId, int id, String name, String url,
                            String icon, int type) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Module module = adminService.updateOneModule(id, name, url, icon, type);
        if(module == null){
           return map.putFailure("更新失败", -1);
        }

        return map.putValue(module, "更新成功");
    }

    @LogAnnotation(opType = "修改模块排序", description = "修改模块排序", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateModuleIndex", method = RequestMethod.POST)
    public Map updateModuleIndex(HttpServletRequest request,int userId, Integer sId, Integer[] sIdSubIds,
                                 Integer dId, Integer[] dIdSubIds, int type) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        boolean result = adminService.updateModuleIndex(sId, sIdSubIds, dId, dIdSubIds, type);
        if(!result){
            return map.putFailure("更新失败", -1);
        }

        return map.putSuccess("更新成功");
    }

    @LogAnnotation(opType = "删除模块", description = "删除一个模块", mavenModule = MavenModule.User)
    @RequestMapping(value = "/deleteOneModule", method = RequestMethod.POST)
    public Map deleteOneModule(HttpServletRequest request,int userId, int id) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        boolean result = adminService.deleteOneModule(id);
        if(!result){
            return map.putFailure("删除失败", -1);
        }

        return map.putSuccess("删除成功");
    }

    @RequestMapping(value = "/getModuleList", method = RequestMethod.POST)
    public Map getModuleList(HttpServletRequest request, int userId, int type) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        List<Module> modules = adminService.getModuleList(userId, type);
        List<ModulesVo> modulesVos = ModuleUtil.formatModuleList(modules);//格式化成树型结构
        return map.putList(modulesVos, "获取成功");
    }

    /**无用 用上面那个*/
    @ApiIgnore
    @RequestMapping(value = "/getModulePageList", method = RequestMethod.POST)
    public Map getModulePageList(HttpServletRequest request, int userId, int current, int size) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Page<Module> modulePage = adminService.getModulePageList(current, size);
        ModuleUtil.formatModuleList(modulePage.getList());
        return map.putPage(modulePage, "获取成功");
    }

    @ApiIgnore
    @RequestMapping(value = "/updateModuleStatus", method = RequestMethod.POST)
    public Map updateModuleStatus(HttpServletRequest request, int userId, int id, boolean status) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        adminService.updateModuleStatus(id, status);
        return map.putSuccess("编辑成功");
    }

    @LogAnnotation(opType = "新增角色", description = "新增一个角色", mavenModule = MavenModule.User)
    @RequestMapping(value = "/addOneRole", method = RequestMethod.POST)
    public Map addOneRole(HttpServletRequest request, int userId, String name,
                          String description, Integer[] moduleIds, int type) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Role role = adminService.addOneRole(name, description, moduleIds, type);
        if(role == null){
            return map.putFailure("新增失败", -1);
        }

        return map.putValue(role, "新增成功");
    }

    @RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
    public Map getRoleList(HttpServletRequest request, int userId, int type) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        List<Role> roleList = adminService.getRoleList(type);
        return map.putList(roleList, "获取成功");
    }

    /*@RequestMapping(value = "/getRoleFormatList", method = RequestMethod.POST)
    public Map getRoleFormatList(HttpServletRequest request, int userId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        List<Role> roleList = adminService.getRoleList();
        List<ModulesVo> modulesVos = ModuleUtil.formatModuleList(roleList);//格式化成树型结构
        return map.putList(roleList, "获取成功");
    }*/

    @RequestMapping(value = "/getRolePageList", method = RequestMethod.POST)
    public Map getRolePageList(HttpServletRequest request, int userId, int current, int size,
                               String name, String orderBy, Boolean asc, int type) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Page<Role> rolePage = adminService.getRolePageList(userId, current, size, name, orderBy, asc, type);
        return map.putPage(rolePage, "获取成功");
    }

    @LogAnnotation(opType = "删除角色", description = "删除一个角色", mavenModule = MavenModule.User)
    @RequestMapping(value = "/deleteOneRole", method = RequestMethod.POST)
    public Map deleteOneRole(HttpServletRequest request, int userId, int id) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        boolean result = adminService.deleteOneRole(id);
        if(!result){
            return map.putFailure("删除失败", -1);
        }

        return map.putSuccess("删除成功");
    }

    @ApiOperation(value = "获取账号", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "获取一个管理员,内容管理员或商户管理员"
    )
    @RequestMapping(value = "/getOneAdmin", method = RequestMethod.POST)
    public Map getOneAdmin(HttpServletRequest request, int userId, int id) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Admin admin = adminService.getOneAdmin(id);
        if(admin == null){
            return map.putFailure("获取失败", -1);
        }

        return map.putValue(admin, "获取成功");
    }

    @LogAnnotation(opType = "新增管理员", description = "新增一个管理员", mavenModule = MavenModule.User)
    @RequestMapping(value = "/addOneAdmin", method = RequestMethod.POST)
    public Map addOneAdmin(HttpServletRequest request, int userId, String username, String password,
                           Integer parentId, int roleId, String contact, String name) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Admin admin = adminService.addOneAdmin(username, password, parentId, roleId, contact, name);
        if(admin == null){
            return map.putFailure("新增失败", -1);
        }

        return map.putValue(admin, "新增成功");
    }

    @LogAnnotation(opType = "更新管理员", description = "更新一个管理员", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateOneAdmin", method = RequestMethod.POST)
    public Map updateOneAdmin(HttpServletRequest request, int userId, int id,
                              String contact, String name, int roleId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Admin admin = adminService.updateOneAdmin(id, contact, name, roleId);
        if(admin == null){
            return map.putFailure("编辑失败", -1);
        }

        return map.putValue(admin, "编辑成功");
    }

    @LogAnnotation(opType = "删除管理员", description = "删除一个管理员", mavenModule = MavenModule.User)
    @RequestMapping(value = "/deleteOneAdmin", method = RequestMethod.POST)
    public Map deleteOneAdmin(HttpServletRequest request, int userId, int id) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        boolean result = adminService.deleteOneAdmin(id);
        if(!result){
            return map.putFailure("编辑失败", -1);
        }

        return map.putSuccess("编辑成功");
    }

    @ApiIgnore
    @LogAnnotation(opType = "物理删除管理员", description = "物理删除一个管理员", mavenModule = MavenModule.User)
    @RequestMapping(value = "/deleteOneAdminForce", method = RequestMethod.POST)
    public Map deleteOneAdminForce(HttpServletRequest request, int userId, int id) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        boolean result = adminService.deleteOneAdminForce(id);
        if(!result){
            return map.putFailure("删除失败", -1);
        }

        return map.putSuccess("删除成功");
    }

    @RequestMapping(value = "/getAdminPageList", method = RequestMethod.POST)
    public Map getAdminPageList(HttpServletRequest request, int userId, int current, int size, String username,
                                int roleId, Integer parentId, String orderBy, Boolean asc, int type) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Page<Admin> adminPage = adminService.getAdminPageList(userId, current, size, username, roleId, parentId, orderBy, asc, type);
        return map.putPage(adminPage, "获取成功");
    }

    @LogAnnotation(opType = "新增子账号", description = "新增一个子账号", mavenModule = MavenModule.User)
    @RequestMapping(value = "/addOneAdminSub", method = RequestMethod.POST)
    public Map addOneAdminSub(HttpServletRequest request, int userId, String username, String password,
                           Integer parentId, int roleId, String contact, String name, Integer[] moduleIds) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Admin admin = adminService.addOneAdminSub(username, password, parentId, roleId, contact, name, moduleIds);
        if(admin == null){
            return map.putFailure("新增失败", -1);
        }

        return map.putValue(admin, "新增成功");
    }

    @LogAnnotation(opType = "更改密码", description = "更改管理员密码", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateAdminPwd", method = RequestMethod.POST)
    public Map updateAdminPwd(HttpServletRequest request, int userId, int id, String oldPwd, String newPwd) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Admin admin = adminService.updateAdminPwd(id, oldPwd, newPwd);
        if(admin == null){
            return map.putFailure("更新失败", -1);
        }

        return map.putValue(admin, "更新成功");
    }

    /**
     * mybatis
     */
    @RequestMapping(value = "/getAdminMB", method = RequestMethod.POST)
    public Map getAdminMB(HttpServletRequest request, int userId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        AdminVo adminVo = adminMapper.getAdminMB(userId);

        return map.putValue(adminVo, "获取成功");
    }

    @RequestMapping(value = "/getModuleListMybatis", method = RequestMethod.POST)
    public Map getModuleListMybatis(HttpServletRequest request, int userId, int type) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        List<ModuleVo> list = adminService.getModuleListMybatis(userId, type);

        return map.putList(list, "获取成功");
    }

}