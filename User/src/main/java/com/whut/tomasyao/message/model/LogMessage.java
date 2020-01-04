package com.whut.tomasyao.message.model;

import com.whut.tomasyao.message.common.SmsPlatform;
import com.whut.tomasyao.message.common.SmsType;

import java.util.Date;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-09-15 10:14
 * 短信日志记录
 */

public class LogMessage {
    private int id;
    private String phone;
    private SmsType type;
    private SmsPlatform platform;
    private String msg_content;
    private Date create_time;
    private Date update_time;
    private boolean success;

    public LogMessage() {
    }

    public LogMessage(String phone, SmsType type, SmsPlatform platform, String msg_content,
                      boolean success, Date create_time) {
        this.phone = phone;
        this.type = type;
        this.platform = platform;
        this.msg_content = msg_content;
        this.success = success;
        this.create_time = create_time;//短信发送时间
        this.update_time = new Date();//日志记录时间
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SmsType getType() {
        return type;
    }

    public void setType(SmsType type) {
        this.type = type;
    }

    public SmsPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(SmsPlatform platform) {
        this.platform = platform;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
