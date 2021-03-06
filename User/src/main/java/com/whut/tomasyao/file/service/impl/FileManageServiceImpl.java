package com.whut.tomasyao.file.service.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-09-14 10:18
 */

import com.whut.tomasyao.base.model.File;
import com.whut.tomasyao.base.mybatis.params.ParamsMap;
import com.whut.tomasyao.base.vo.Page;
import com.whut.tomasyao.file.dao.IFileDao;
import com.whut.tomasyao.file.mapper.FileMapper;
import com.whut.tomasyao.file.vo.FileVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.whut.tomasyao.file.service.IFileManageService;

import java.util.List;

@Service
public class FileManageServiceImpl implements IFileManageService {

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private IFileDao fileDao;

    @Override
    public FileVo getOneFile(int id) throws Exception {
        return fileMapper.getFile(id);
    }

    @Override
    public Page<FileVo> getFilePage(int current, int size, String orderBy, Boolean asc,
                                    Integer id, Integer searchAge, Integer isDeleted) throws Exception {
        //分页查询参数构造(其它业务分页查询与此类似,改变的最多是查询参数构造)
        ParamsMap hashMap = ParamsMap.getPageInstance(current, size, orderBy, asc);
        hashMap.put("id", id);
        hashMap.put("searchAge", searchAge);
        hashMap.put("isDeleted", isDeleted);
        //开始查询记录和数量
        List<FileVo> list = fileMapper.getFilePage(hashMap);
        Integer count = fileMapper.findCountFilePage(hashMap);
        Page<FileVo> page = new Page<>(list, count, current, size);
        return page;
    }

    @Override
    public Integer deleteFile(int id) throws Exception {
        return fileMapper.deleteFile(id);
    }

    @Override
    public Integer addOneFile(String url) throws Exception {
        File file = new File(url);
        fileDao.save(file);
        return file.getId();
    }

    @Override
    public void updateOneFile(int id, Integer age) throws Exception {
        File file = fileDao.getOne(id);
        file.setAge(age);
        fileDao.update(file);
    }
}
