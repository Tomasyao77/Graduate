package com.whut.tomasyao.log.controller;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-07-28 15:20
 */

import com.whut.tomasyao.base.vo.Page;
import com.whut.tomasyao.log.service.ILogUserService;
import com.whut.tomasyao.log.vo.LogUserVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import com.whut.tomasyao.base.vo.ResponseMap;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/user/log")
public class LogController {

    @Autowired
    private ILogUserService logUserService;

    @ApiIgnore
    @RequestMapping(value = "/getLogUserPage", method = RequestMethod.POST)
    public Map getLogUserPage(HttpServletRequest request, int userId,
                         int current, int size, String orderBy, Boolean asc,
                         Integer user_id, String clazz, String method, String maven_module,
                         String startDate, String endDate) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Page<LogUserVo> page = logUserService.getLogUserPage(current, size, orderBy, asc, user_id, clazz,
                method, maven_module, startDate, endDate);
        return map.putPage(page, "获取成功");
    }

}