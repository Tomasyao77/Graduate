package com.whut.tomasyao.login.dao.impl;

import edu.whut.pocket.base.dao.impl.RedisDaoImpl;
import edu.whut.pocket.login.dao.ISKRedisVoDao;
import org.springframework.stereotype.Component;

/**
 * Created by YTY on 2016/6/9.
 */
@Component
public class SKRedisVoDaoImpl extends RedisDaoImpl<String, String> implements ISKRedisVoDao {

}
