package com.whut.tomasyao.file.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-24 11:11
 */

import org.springframework.stereotype.Component;
import com.whut.tomasyao.base.dao.impl.BaseDaoImpl;
import com.whut.tomasyao.base.model.File;
import com.whut.tomasyao.file.dao.IFileDao;

@Component
public class FileDaoImpl extends BaseDaoImpl<File> implements IFileDao {
    public FileDaoImpl() {
        super(File.class);
    }
}
