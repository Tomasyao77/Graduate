package com.whut.tomasyao.admin.controller;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-02-12 13:59
 */

import edu.whut.pocket.admin.model.AdminModule;
import edu.whut.pocket.admin.model.Module;
import edu.whut.pocket.admin.model.Role;
import edu.whut.pocket.admin.model.RoleModule;
import edu.whut.pocket.admin.service.IAdminService;
import edu.whut.pocket.admin.service.IPermissionService;
import edu.whut.pocket.admin.util.ModuleUtil;
import edu.whut.pocket.admin.vo.ModulesVo;
import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.base.vo.ResponseMap;
import io.swagger.annotations.Api;
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
@Api(tags = {"权限管理"}, description = "模块|角色|(子)账号", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/user/permission")
public class PermissionController {
    private static final Logger logger = Logger.getLogger(PermissionController.class);

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping(value = "/getRoleModuleList", method = RequestMethod.POST)
    public Map getRoleModuleList(HttpServletRequest request, int userId, int roleId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        List<RoleModule> roleModules = permissionService.getRoleModuleList(roleId);
        return map.putList(roleModules, "获取成功");
    }

    @RequestMapping(value = "/getRoleModuleFormatList", method = RequestMethod.POST)
    public Map getRoleModuleFormatList(HttpServletRequest request, int userId, int roleId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        List<Module> roleModules = permissionService.getRoleModuleFormatList(roleId);
        List<ModulesVo> modulesVos = ModuleUtil.formatModuleList(roleModules);//格式化成树型结构
        return map.putList(modulesVos, "获取成功");
    }

    @ApiIgnore
    @RequestMapping(value = "/updateRoleModule", method = RequestMethod.POST)
    public Map updateRoleModule(HttpServletRequest request, int userId, int roleId, String name,
                                String description, Integer[] roleModuleIds) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        boolean result = permissionService.updateRoleModule(roleId, name, description, roleModuleIds);
        if(!result){
            return map.putFailure("编辑失败", 0);
        }
        return map.putSuccess("编辑成功");
    }

    @RequestMapping(value = "/getAdminModuleList", method = RequestMethod.POST)
    public Map getAdminModuleList(HttpServletRequest request, int userId, int adminId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        List<AdminModule> roleModules = permissionService.getAdminModuleList(adminId);
        return map.putList(roleModules, "获取成功");
    }

    @ApiIgnore
    @RequestMapping(value = "/updateAdminModule", method = RequestMethod.POST)
    public Map updateAdminModule(HttpServletRequest request, int userId, int adminId, String name,
                                 String contact, Integer[] adminModuleIds) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        boolean result = permissionService.updateAdminModule(adminId, name, contact, adminModuleIds);
        if(!result){
            return map.putFailure("编辑失败", 0);
        }
        return map.putSuccess("编辑成功");
    }

}