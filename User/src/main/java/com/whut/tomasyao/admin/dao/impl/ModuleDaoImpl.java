package com.whut.tomasyao.admin.dao.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-28 19:34
 */

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;
import edu.whut.pocket.base.dao.impl.BaseDaoImpl;
import edu.whut.pocket.admin.model.Module;
import edu.whut.pocket.admin.dao.IModuleDao;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModuleDaoImpl extends BaseDaoImpl<Module> implements IModuleDao {
    public ModuleDaoImpl() {
        super(Module.class);
    }

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public boolean updateModuleAllIndex(final int pId, final int baseIndex) {
        Object object = hibernateTemplate.executeWithNativeSession(
                new HibernateCallback<Object>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public Object doInHibernate(Session session) throws HibernateException {
                        Query query = session.createSQLQuery(
                                "{call PROC_UPDATE_MODULE_ALLINDEX("+pId+","+baseIndex+")}");
                        query.executeUpdate();
                        return new Object();
                    }
                });

        return true;
    }

}
