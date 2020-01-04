package com.whut.tomasyao.config.mapper;

import com.whut.tomasyao.config.vo.ConfigVo;

/**
 * Created by zouy on 18-9-17.
 */
public interface ConfigMapper {
    ConfigVo getConfig(String nameEn) throws Exception;
}
