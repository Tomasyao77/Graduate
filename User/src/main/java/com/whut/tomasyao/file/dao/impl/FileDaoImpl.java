package com.whut.tomasyao.file.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-24 11:11
 */

import org.springframework.stereotype.Component;
import edu.whut.pocket.base.dao.impl.BaseDaoImpl;
import edu.whut.pocket.base.model.File;
import edu.whut.pocket.file.dao.IFileDao;

@Component
public class FileDaoImpl extends BaseDaoImpl<File> implements IFileDao {
    public FileDaoImpl() {
        super(File.class);
    }
}
