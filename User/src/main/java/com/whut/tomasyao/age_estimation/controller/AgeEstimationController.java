package com.whut.tomasyao.age_estimation.controller;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-02-12 13:59
 */

import com.whut.tomasyao.age_estimation.util.ShellExcutor;
import com.whut.tomasyao.base.vo.ResponseMap;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/age")
public class AgeEstimationController {
    private static final Logger logger = Logger.getLogger(AgeEstimationController.class);

    /**
     * 调用sh脚本
     */
    @ApiIgnore
    @RequestMapping(value = "/estimation", method = RequestMethod.POST)
    public Map getOneUser(HttpServletRequest request, int userId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        ShellExcutor shellExcutor = new ShellExcutor();
        Map<String, String> result = shellExcutor.service("");

        return map.putValue(result.get("age_value"));
    }




}