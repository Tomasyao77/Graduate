package com.whut.tomasyao.weixin.model;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-07 13:33
 */

public class TextMessage {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;

    public TextMessage() {
    }

    public TextMessage(String toUserName, String fromUserName, String createTime, String msgType, String content, String msgId) {
        this.ToUserName = toUserName;
        this.FromUserName = fromUserName;
        this.CreateTime = createTime;
        this.MsgType = msgType;
        this.Content = content;
        this.MsgId = msgId;
    }

    public String getToUserName() {
        return this.ToUserName;
    }

    public void setToUserName(String toUserName) {
        this.ToUserName = toUserName;
    }

    public String getFromUserName() {
        return this.FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.FromUserName = fromUserName;
    }

    public String getCreateTime() {
        return this.CreateTime;
    }

    public void setCreateTime(String createTime) {
        this.CreateTime = createTime;
    }

    public String getMsgType() {
        return this.MsgType;
    }

    public void setMsgType(String msgType) {
        this.MsgType = msgType;
    }

    public String getContent() {
        return this.Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }

    public String getMsgId() {
        return this.MsgId;
    }

    public void setMsgId(String msgId) {
        this.MsgId = msgId;
    }
}
