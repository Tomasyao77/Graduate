package com.whut.tomasyao.file.mapper;

import edu.whut.pocket.file.vo.FileVo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zouy on 18-7-12.
 */
public interface FileMapper {
    FileVo getFile(int id) throws Exception;

    List<FileVo> getFilePage(HashMap<String, Object> hashMap) throws Exception;

    Integer findCountFilePage(HashMap<String, Object> hashMap) throws Exception;

    Integer deleteFile(int id) throws Exception;
}
