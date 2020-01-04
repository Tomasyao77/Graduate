package com.whut.tomasyao.auth.dao.impl;

import com.whut.tomasyao.auth.dao.IAdminRedisVoDao;
import com.whut.tomasyao.auth.vo.AdminRedisVo;
import com.whut.tomasyao.base.dao.impl.RedisDaoImpl;
import org.springframework.stereotype.Component;

/**
 * Created by YTY on 2016/6/9.
 */
@Component
public class AdminRedisVoDaoImpl extends RedisDaoImpl<String, AdminRedisVo> implements IAdminRedisVoDao {

}
