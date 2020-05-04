package com.whut.tomasyao.file.service;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-09-14 10:12
 */

import com.whut.tomasyao.base.vo.Page;
import com.whut.tomasyao.file.vo.FileVo;

public interface IFileManageService {

    //获取一条记录
    FileVo getOneFile(int id) throws Exception;

    Page<FileVo> getFilePage(int current, int size, String orderBy, Boolean asc,
                             Integer id, Integer searchAge, Integer isDeleted) throws Exception;

    Integer deleteFile(int id) throws Exception;

    Integer addOneFile(String url) throws Exception;

    void updateOneFile(int id, Integer age) throws Exception;
}
