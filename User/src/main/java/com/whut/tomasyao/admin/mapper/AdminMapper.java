package com.whut.tomasyao.admin.mapper;

import com.whut.tomasyao.admin.vo.AdminVo;
import com.whut.tomasyao.admin.vo.ModuleVo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface AdminMapper {
    AdminVo getAdminMB(int id);

    List<ModuleVo> getModuleList(HashMap<String, Object> hashMap) throws Exception;
}
