package com.whut.tomasyao.auth.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-05 11:04
 */

import org.springframework.stereotype.Component;
import com.whut.tomasyao.base.dao.impl.BaseDaoImpl;
import com.whut.tomasyao.auth.model.Swagger;
import com.whut.tomasyao.auth.dao.ISwaggerDao;

@Component
public class SwaggerDaoImpl extends BaseDaoImpl<Swagger> implements ISwaggerDao {
    public SwaggerDaoImpl() {
        super(Swagger.class);
    }
}
