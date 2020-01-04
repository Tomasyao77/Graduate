package com.whut.tomasyao.admin.mapper;

import edu.whut.pocket.admin.vo.AdminVo;
import edu.whut.pocket.admin.vo.ModuleVo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface AdminMapper {
    AdminVo getAdminMB(int id);

    List<ModuleVo> getModuleList(HashMap<String, Object> hashMap) throws Exception;
}
