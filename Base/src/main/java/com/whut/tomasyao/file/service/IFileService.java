package com.whut.tomasyao.file.service;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-24 11:11
 */

import com.whut.tomasyao.base.model.File;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

public interface IFileService {

    //上传到fdfs
    List<String> uploadToFdfs(MultipartHttpServletRequest request) throws Exception;

    //上传图片及其压缩图到fdfs
    List<String[]> uploadWithThumbnails(MultipartHttpServletRequest request) throws Exception;

    //删除一条记录
    //void deleteOneFile(int id) throws Exception;

}
