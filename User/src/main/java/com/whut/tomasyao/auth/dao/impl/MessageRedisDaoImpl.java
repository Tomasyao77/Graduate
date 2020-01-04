package com.whut.tomasyao.auth.dao.impl;

import edu.whut.pocket.auth.dao.IMessageRedisDao;
import edu.whut.pocket.base.dao.impl.RedisDaoImpl;
import org.springframework.stereotype.Component;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-09-15 17:06
 */
@Component
public class MessageRedisDaoImpl extends RedisDaoImpl<String, String> implements IMessageRedisDao {

}
