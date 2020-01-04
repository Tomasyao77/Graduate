package com.whut.tomasyao.auth.dao.impl;

import edu.whut.pocket.auth.dao.IUserRedisVoDao;
import edu.whut.pocket.auth.vo.UserRedisVo;
import edu.whut.pocket.base.dao.impl.RedisDaoImpl;
import org.springframework.stereotype.Component;

/**
 * Created by YTY on 2016/6/9.
 */
@Component
public class UserRedisVoDaoImpl extends RedisDaoImpl<String, UserRedisVo> implements IUserRedisVoDao {

}
