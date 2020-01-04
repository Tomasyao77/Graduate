package com.whut.tomasyao.admin.util;

import com.whut.tomasyao.admin.model.Module;
import com.whut.tomasyao.admin.vo.ModulesVo;

import java.util.*;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-04-02 19:52
 */

public class ModuleUtil {

    public static List<ModulesVo> formatModuleList(List<Module> modules){
        //原始map
        Map<Integer, Module> mMap = new HashMap<>();
        for(Module m : modules){
            mMap.put(m.getId(), m);
        }

        List<ModulesVo> modulesVos = new ArrayList<>();
        Map<Integer, ModulesVo> mVoMap = new HashMap<>();

        //父子嵌套
        for(Module m : modules){
            ModulesVo modulesVo = new ModulesVo(m.getId(),m.getName(), m.getParentId(), m.getUrl(),
                    m.getIcon(), m.getIndex(), m.getCreateTime(), m.getUpdateTime(), new ArrayList<>(), 0);

            if(m.getParentId() == 0){
                if(mVoMap.get(m.getId()) == null){//不能重复put
                    mVoMap.put(m.getId(), modulesVo);//level为0的元素(父容器)
                    modulesVos.add(modulesVo);
                }
            }else {
                ModulesVo parentModuleVo = mVoMap.get(m.getParentId());
                if(parentModuleVo == null) {//如果parentModuleVo存在则直接追加,否则先将parentModule加进去
                    Module p = mMap.get(m.getParentId());
                    ModulesVo tempVo = new ModulesVo(p.getId(), p.getName(), p.getParentId(), p.getUrl(),
                            p.getIcon(), p.getIndex(), p.getCreateTime(), p.getUpdateTime(), new ArrayList<>(), 0);
                    mVoMap.put(tempVo.getId(), tempVo);//level为0的元素(父容器)
                    modulesVos.add(tempVo);
                }
                parentModuleVo = mVoMap.get(m.getParentId());//再次获取就有了parentModuleVo
                modulesVo.setLevel(1);
                parentModuleVo.getNodes().add(modulesVo);

            }
        }

        //按父索引排序
        modulesVos.sort(new Comparator<ModulesVo>() {
            @Override
            public int compare(ModulesVo o1, ModulesVo o2) {
                return o1.getIndex() < o2.getIndex() ? -1 : 1;
            }
        });

        //按子索引排序
        for(ModulesVo m : modulesVos){
            if(m.getNodes().size() > 1){
                m.getNodes().sort(new Comparator<ModulesVo>() {
                    @Override
                    public int compare(ModulesVo o1, ModulesVo o2) {
                        return o1.getIndex() < o2.getIndex() ? -1 : 1;
                    }
                });
            }
        }

        return modulesVos;
    }

}
