package com.whut.tomasyao.age_estimation.controller;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-02-12 13:59
 */

import com.whut.tomasyao.age_estimation.util.ShellExcutor;
import com.whut.tomasyao.base.model.File;
import com.whut.tomasyao.base.vo.ResponseMap;
import com.whut.tomasyao.file.dao.IFileDao;
import com.whut.tomasyao.file.service.IFileManageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private IFileManageService fileManageService;

    /**
     * 调用sh脚本
     */
    @ApiIgnore
    @RequestMapping(value = "/estimation", method = RequestMethod.POST)
    public Map estimation(HttpServletRequest request, int userId, String file) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        //保存图片
        Integer fileId = fileManageService.addOneFile(file);

        ShellExcutor shellExcutor = new ShellExcutor();
        Map<String, String> result = shellExcutor.service(file);
        fileManageService.updateOneFile(fileId, Integer.valueOf(result.get("age_value")));

        return map.putValue(result.get("age_value"));
    }




}