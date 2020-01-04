package com.whut.tomasyao.auth.dao.impl;

import com.whut.tomasyao.auth.dao.IUserRedisVoDao;
import com.whut.tomasyao.auth.vo.UserRedisVo;
import com.whut.tomasyao.base.dao.impl.RedisDaoImpl;
import org.springframework.stereotype.Component;

/**
 * Created by YTY on 2016/6/9.
 */
@Component
public class UserRedisVoDaoImpl extends RedisDaoImpl<String, UserRedisVo> implements IUserRedisVoDao {

}
