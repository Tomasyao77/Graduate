package com.whut.tomasyao.age_estimation.controller;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-02-12 13:59
 */

import com.whut.tomasyao.age_estimation.util.ShellExcutor;
import com.whut.tomasyao.age_estimation.util.ShellExcutor1;
import com.whut.tomasyao.base.vo.ResponseMap;
import com.whut.tomasyao.file.service.IFileManageService;
import com.whut.tomasyao.file.util.FileUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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

    @ApiIgnore
    @RequestMapping(value = "/face_aging", method = RequestMethod.POST)
    public Map face_aging(HttpServletRequest request, int userId, String file, Integer age, Integer gender) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        //保存图片
        Integer fileId = fileManageService.addOneFile(file);

        ShellExcutor1 shellExcutor = new ShellExcutor1();
        Map<String, String> result = shellExcutor.service(file, age, gender);
        fileManageService.updateOneFile(fileId, age);

        //保存年龄面貌合成结果并删除临时文件
        String filePath = result.get("result_url");
        java.io.File tmpFile = new java.io.File(filePath);
        String savePathOfFaceAging = FileUtil.upload(userId+"", tmpFile);
        System.out.println("年龄面貌合成临时文件是否被删除：" + deleteFile(filePath));

        return map.putValue(savePathOfFaceAging, "年龄面貌合成任务成功");
    }

    public static boolean deleteFile(String pathname){
        boolean result = false;
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
            result = true;
        }
        return result;
    }
}