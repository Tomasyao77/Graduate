package com.whut.tomasyao.base.service;

import com.whut.tomasyao.base.model.Area;

import java.util.List;

/**
 * edu.whut.change.base.service
 * Created by YTY on 2016/6/14.
 */
public interface IAreaService {
    List<Area> getAreaBySearch(String search);

    Area getAreaByCode(int code);

    List<Area> getAreasByParentCode(int parentCode);

    List<Area> getAreasByLevel(String name, int level);

    List<Area> getHotCity();

    Area getAreaByLevel(String name, int level);

    Area getNearestArea(double lng, double lat, int level);
}
