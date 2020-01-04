package com.whut.tomasyao.base.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

/**
 * com.vv.vs.base.constant
 * Created by YTY on 2015/11/26.
 */
public class Constant {
    public static final String SYNC_DETECTION_CTRL = "/detection/synDetection";

    public static final String APP_IDENTIFY_COOKIE_VALUE = "ceks100";

    public static final String ADMIN_ROLE_LEVEL_BASE = "root";

    public static final String REDIS_STATISTICS_TIME = "statistics_time";

    public static final String TIME_FORMAT = "HH:mm";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat TIME_SECOND_FORMAT = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final int TOKEN_EXPIRE = 1 * 24 * 3600;//1天
    public static final int TOKEN_EXPIRE_30 = 30 * 24 * 3600;//30天

    public static final int DEFAULT_PICTURE_THUMB = 4;
    public static final int DEFAULT_PICTURE = 3;
    public static final int DEFAULT_PORTRAIT_THUMB = 2;
    public static final int DEFAULT_PORTRAIT = 1;

    public static final double LAT_PER_DEGREE = 111.7126915;
    public static final double LNG_PER_DEGREE = 102.83474258;


    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /*设备记录*/
    public static final int SAVE_LOG_COUNT = 1000;
    public static final String SERVER_INNER_IP = "10.162.89.181";

    public static final int USER_STATISTICS = 6;
    public static final int USER_STATISTICS_LOGIN = 16;


    public static final int EXPERT_STATISTICS = 17;
    public static final int EXPERT_STATISTICS_LOGIN = 18;
    public static final String ADMIN_ATTRIBUTE = "ADMIN_ATTRIBUTE";

    public static final String EDIT_BAIKE = "bksh";
    public static final String EDIT_NEW_EXPERT = "htxzzj";
    public static final String EDIT_UPDATE_EXPERT= "htzjxg";
    public static final String EDIT_EXPERTCONFIG_APP = "zjszxg";
    public static final String EDIT_EXPET_APP = "zjsh";

    public static final String WECHAT_DETECTION_PWD_CONFIG_NAME = "wechat_detection_pwd";
}
