package com.whut.tomasyao.config.mapper;

import edu.whut.pocket.config.vo.ConfigVo;

/**
 * Created by zouy on 18-9-17.
 */
public interface ConfigMapper {
    ConfigVo getConfig(String nameEn) throws Exception;
}
