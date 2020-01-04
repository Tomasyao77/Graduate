package com.whut.tomasyao.base.service.impl;

import com.whut.tomasyao.base.dao.IAreaDao;
import com.whut.tomasyao.base.model.Area;
import com.whut.tomasyao.base.service.IAreaService;
import com.whut.tomasyao.base.util.HqlUtil;
import com.whut.tomasyao.base.vo.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * edu.whut.change.base.service.impl
 * Created by YTY on 2016/6/14.
 */
@Service
public class AreaServiceImpl implements IAreaService {
    @Autowired
    private IAreaDao areaDao;
    @Override
    public List<Area> getAreaBySearch(String search){
        List<String> conditions = new ArrayList<String>();
        Parameter p = new Parameter();
        if (search != null && !search.isEmpty()) {
            conditions.add("(a.name like :p0 or a.pinyin like :p0)");
            p.put("p0", search + "%");
        }
        return areaDao.findList(HqlUtil.formatHql("from Area a", conditions), p);
    }

    @Override
    public Area getAreaByCode(int code) {
        return areaDao.findOne("from Area a where a.code=:p0", new Parameter(code));
    }

    @Override
    public List<Area> getAreasByParentCode(int parentCode) {
        return areaDao.findList("from Area a where a.parentCode=:p0", new Parameter(parentCode));
    }

    @Override
    public List<Area> getAreasByLevel(String name, int level) {
        List<String> conditions = new ArrayList<String>();
        conditions.add("a.level=:p0");
        Parameter p = new Parameter(level);
        if (name != null && !name.isEmpty()) {
            conditions.add("(a.name like :p1 or a.pinyin like :p1)");
            p.put("p1", name + "%");
        }
        return areaDao.findList(HqlUtil.formatHql("from Area a", conditions), p);
    }

    @Override
    public List<Area> getHotCity() {
        return areaDao.findList("from Area a where a.hot = true and a.level = 2");
    }

    @Override
    public Area getAreaByLevel(String name, int level) {
        List<String> conditions = new ArrayList<String>();
        conditions.add("a.level=:p0");
        Parameter p = new Parameter(level);
        if (name != null && !name.isEmpty()) {
            conditions.add("(a.name like :p1 or a.pinyin like :p1)");
            p.put("p1", name + "%");
        }
        return areaDao.findOne(HqlUtil.formatHql("from Area a", conditions), p);
    }

    @Override
    public Area getNearestArea(double lng, double lat, int level) {
        return areaDao.findOne(
                "from Area a where a.level=:p0 order by ABS(a.lng-:p1)+ABS(a.lat-:p2) asc",
                new Parameter(level, lng, lat)
        );
    }
}
