package com.whut.tomasyao.file.controller;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-09-14 10:11
 */

import edu.whut.pocket.auth.aspect.AuthAnnotation;
import edu.whut.pocket.auth.model.UserType;
import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.file.service.IFileManageService;
import edu.whut.pocket.file.vo.FileVo;
import edu.whut.pocket.kafka.util.KafkaProducerUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import edu.whut.pocket.base.vo.ResponseMap;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/fileManage")
public class FileManageController {

    @Autowired
    private IFileManageService fileManageService;

    @RequestMapping(value = "/getOneFile", method = RequestMethod.POST)
    public Map getOneFile(HttpServletRequest request, int userId, int id) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        FileVo result = fileManageService.getOneFile(id);
        return map.putValue(result, "获取成功");
    }

    @RequestMapping(value = "/getFilePage", method = RequestMethod.POST)
    public Map getFilePage(HttpServletRequest request, int userId, int current, int size, String orderBy, Boolean asc,
                           Integer id, String url, Integer isDeleted) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Page<FileVo> page = fileManageService.getFilePage(current, size, orderBy, asc, id, url, isDeleted);
        return map.putPage(page, "获取成功");
    }

    //手动物理删除fastdfs单个文件
    @AuthAnnotation(value = UserType.root, moduleId = 74)//74文件管理
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public Map deleteFile(HttpServletRequest request, int userId, int id) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        Integer result = fileManageService.deleteFile(id);
        if(result != null && result == 1){//逻辑删除成功后调用kafka触发物理删除
            FileVo fileVo = fileManageService.getOneFile(id);
            if(fileVo.isIs_deleted()){//确认是否被逻辑删除
                KafkaProducerUtil.producer("file", "deleteFile", fileVo.getUrl());
            }
        }
        return map.putValue(result, "删除成功");
    }

    //自动批量物理删除fastdfs文件
    @AuthAnnotation(value = UserType.root, moduleId = 74)
    @RequestMapping(value = "/deleteFiles", method = RequestMethod.POST)
    public Map deleteFiles(HttpServletRequest request, int userId) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        //每次获取100条未被删除的记录
        Page<FileVo> page = fileManageService.getFilePage(1, 100, null, null, null, null, 0);
        List<FileVo> list = page.getList();
        for(int i=0; i<page.getList().size(); i++){
            int id = list.get(i).getId();//文件记录id
            Integer result = fileManageService.deleteFile(id);
            if(result != null && result == 1){//逻辑删除成功后调用kafka触发物理删除
                FileVo fileVo = fileManageService.getOneFile(id);
                if(fileVo.isIs_deleted()){//确认是否被逻辑删除
                    KafkaProducerUtil.producer("file", "deleteFile", fileVo.getUrl());
                }
            }
        }

        return map.putValue("删除成功");
    }

}