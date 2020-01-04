package com.whut.tomasyao.file.mapper;

import com.whut.tomasyao.file.model.File;
import com.whut.tomasyao.file.vo.FileVo;

/**
 * Created by zouy on 18-7-12.
 */
public interface FileMapper {
    FileVo getFile(int id) throws Exception;
    Integer addFile(File file) throws Exception;
}
