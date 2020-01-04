package com.whut.tomasyao.base.controller;

import com.whut.tomasyao.base.service.IAreaService;
import com.whut.tomasyao.base.vo.ResponseMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * com.whut.athena.base.controller
 * Created by YTY on 2016/3/26.
 */

@Api(tags = {"地区"}, description = "省市区", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/server/area")
public class AreaController {
    @Autowired
    private IAreaService areaService;

    @ApiOperation(value = "根据地区码获取省|市|区", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "例如parentCode=100000表示获取全国所有省,parentCode=130000表示获取河北省的所有市," +
                    "parentCode=130100表示获取石家庄市下的所有区")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentCode",value = "父级地区码",paramType = "form",dataType = "int")
    })
    @RequestMapping(value = "/getByParentCode", method = RequestMethod.POST)
    public Map get(int parentCode) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        return map.putList(areaService.getAreasByParentCode(parentCode));
    }

    @ApiIgnore
    @RequestMapping(value = "/getByLevel", method = RequestMethod.POST)
     public Map getByLevel(String name, int level) {
        ResponseMap map = ResponseMap.getInstance();
        return map.putList(areaService.getAreasByLevel(name, level));
    }

    @ApiIgnore
    @RequestMapping(value = "/getHotCity", method = RequestMethod.POST)
    public Map getHotCity() {
        return ResponseMap.getInstance().putList(areaService.getHotCity());
    }

    @ApiIgnore
    @RequestMapping(value = "/getAreaBySearch", method = RequestMethod.POST)
    public Map getAreaBySearch(String search) {
        return ResponseMap.getInstance().putList(areaService.getAreaBySearch(search));
    }
}
