package com.whut.tomasyao.file.service;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-09-14 10:12
 */

import edu.whut.pocket.base.vo.Page;
import edu.whut.pocket.file.vo.FileVo;

public interface IFileManageService {

    //获取一条记录
    FileVo getOneFile(int id) throws Exception;

    Page<FileVo> getFilePage(int current, int size, String orderBy, Boolean asc,
                             Integer id, String url, Integer isDeleted) throws Exception;

    Integer deleteFile(int id) throws Exception;
}
