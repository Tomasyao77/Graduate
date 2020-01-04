package com.whut.tomasyao.base.common;

/**
 * edu.whut.change.base.common
 * Created by YTY on 2016/6/15.
 */
public enum StatusType {
    /*禁用=拒绝 启用=通过*/
    禁用,启用;
    public static StatusType getStatusType(int type){
        switch (type){
            case 0:return 禁用;
            case 1:return 启用;
            default: return null;
        }
    }
}
