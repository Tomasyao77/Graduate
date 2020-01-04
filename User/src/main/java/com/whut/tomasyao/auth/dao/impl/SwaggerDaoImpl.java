package com.whut.tomasyao.auth.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-05 11:04
 */

import org.springframework.stereotype.Component;
import edu.whut.pocket.base.dao.impl.BaseDaoImpl;
import edu.whut.pocket.auth.model.Swagger;
import edu.whut.pocket.auth.dao.ISwaggerDao;

@Component
public class SwaggerDaoImpl extends BaseDaoImpl<Swagger> implements ISwaggerDao {
    public SwaggerDaoImpl() {
        super(Swagger.class);
    }
}
