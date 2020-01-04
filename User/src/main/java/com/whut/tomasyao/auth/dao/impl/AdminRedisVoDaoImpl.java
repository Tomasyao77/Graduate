package com.whut.tomasyao.auth.dao.impl;

import edu.whut.pocket.auth.dao.IAdminRedisVoDao;
import edu.whut.pocket.auth.vo.AdminRedisVo;
import edu.whut.pocket.base.dao.impl.RedisDaoImpl;
import org.springframework.stereotype.Component;

/**
 * Created by YTY on 2016/6/9.
 */
@Component
public class AdminRedisVoDaoImpl extends RedisDaoImpl<String, AdminRedisVo> implements IAdminRedisVoDao {

}
