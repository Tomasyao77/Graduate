package com.whut.tomasyao.config.controller;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-02-12 13:59
 */

import edu.whut.pocket.base.common.MavenModule;
import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.base.vo.ResponseMap;
import edu.whut.pocket.config.model.Config;
import edu.whut.pocket.config.service.IConfigService;
import edu.whut.pocket.dubbo.config.service.IDubboConfigService;
import edu.whut.pocket.log.aspect.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

@Api(tags = {"系统设置"}, description = "平台全局设置", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/user/config")
public class ConfigController {

    @Autowired
    private IConfigService configService;
    @Autowired
    private IDubboConfigService dubboConfigService;

    private static final Logger logger = Logger.getLogger(ConfigController.class);

    @ApiIgnore
    @LogAnnotation(opType = "新增系统设置", description = "新增一条系统设置", mavenModule = MavenModule.User)
    @RequestMapping(value = "/addOneConfig", method = RequestMethod.POST)
    public Map addOneConfig(HttpServletRequest request, int userId, String name, String nameEn, int parentId,
                            String valueType, String value) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Config config = configService.addOneConfig(name, nameEn, parentId, valueType, value);
        if(config == null){
            return map.putFailure("新增失败", -1);
        }

        return map.putValue(config, "新增成功");
    }

    @ApiIgnore
    @LogAnnotation(opType = "更新系统设置", description = "更新一条系统设置", mavenModule = MavenModule.User)
    @RequestMapping(value = "/updateOneConfig", method = RequestMethod.POST)
    public Map updateOneConfig(HttpServletRequest request, int userId, int id, String name, String nameEn,
                               String valueType, String value, Integer parentId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Config config = configService.updateOneConfig(id, name, nameEn, valueType, value, parentId);
        if(config == null){
            return map.putFailure("编辑失败", -1);
        }

        return map.putValue(config, "编辑成功");
    }

    @ApiIgnore
    @RequestMapping(value = "/getConfigTypeList", method = RequestMethod.POST)
    public Map getConfigTypeList(HttpServletRequest request, int userId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        List<Config> configTypeList = configService.getConfigTypeList();
        return map.putList(configTypeList, "获取成功");
    }

    @ApiIgnore
    @RequestMapping(value = "/getConfigPageList", method = RequestMethod.POST)
    public Map getConfigPageList(HttpServletRequest request, int userId, int current, int size,
                                 String search, int parentId, String orderBy, Boolean asc) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Page<Config> configPage = configService.getConfigPageList(current, size, search, parentId, orderBy, asc);
        return map.putPage(configPage, "获取成功");
    }

    @ApiOperation(value = "获取系统设置", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "根据英文名称获取一条系统设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "登录者id", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "nameEn", value = "英文名称", paramType = "form", dataType = "string", required = true)
    })
    @RequestMapping(value = "/getConfig", method = RequestMethod.POST)
    public Map getConfig(HttpServletRequest request, int userId, String nameEn) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Object config = dubboConfigService.getConfig(nameEn);
        if(config == null){
            return map.putFailure("获取失败", -1);
        }

        return map.putValue(config, "获取成功");
    }

}