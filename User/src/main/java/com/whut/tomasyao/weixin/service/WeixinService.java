package com.whut.tomasyao.weixin.service;

import com.whut.tomasyao.weixin.model.TextMessage;
import com.whut.tomasyao.weixin.util.MessageUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-07 13:34
 */
@Service
public class WeixinService {
    private static final Logger logger = Logger.getLogger(WeixinService.class);

    public WeixinService() {
    }

    public String weixinPost(HttpServletRequest request) {
        String respMessage = null;

        try {
            Map e = MessageUtil.xmlToMap(request);
            String fromUserName = (String)e.get("FromUserName");
            String toUserName = (String)e.get("ToUserName");
            String msgType = (String)e.get("MsgType");
            String content = (String)e.get("Content");
            logger.info("公众号openid:" + fromUserName + ", 用户openid is:" + toUserName + ", 消息类型:" + msgType);
            if(msgType.equals("text")) {
                if(content.equals("xxx")) {
                    ;
                }

                TextMessage eventType = new TextMessage();
                eventType.setContent("1.【微官网】查看我们的官方网站\n2.【解码报告】查看您的最新皮肤解码报告\n3.【更多】如需帮助请联系我们,自助解码正在积极研发中\n");
                eventType.setToUserName(fromUserName);
                eventType.setFromUserName(toUserName);
                eventType.setCreateTime((new Date()).getTime() + "");
                eventType.setMsgType(msgType);
                respMessage = MessageUtil.textMessageToXml(eventType);
            } else if(msgType.equals("event")) {
                String eventType1 = (String)e.get("Event");
                if(eventType1.equals("subscribe")) {
                    TextMessage eventKey = new TextMessage();
                    eventKey.setContent("欢迎关注，xxx");
                    eventKey.setToUserName(fromUserName);
                    eventKey.setFromUserName(toUserName);
                    eventKey.setCreateTime((new Date()).getTime() + "");
                    eventKey.setMsgType("text");
                    respMessage = MessageUtil.textMessageToXml(eventKey);
                } else if(!eventType1.equals("unsubscribe") && eventType1.equals("CLICK")) {
                    String eventKey1 = (String)e.get("EventKey");
                    if(eventKey1.equals("help")) {
                        TextMessage text = new TextMessage();
                        text.setContent("联系我们：\n电话：027-87290335\n传真：027-87290335\n邮箱：imicofwhut@126.com\n地址：武汉市洪山区珞狮路122号");
                        text.setToUserName(fromUserName);
                        text.setFromUserName(toUserName);
                        text.setCreateTime((new Date()).getTime() + "");
                        text.setMsgType("text");
                        respMessage = MessageUtil.textMessageToXml(text);
                    }
                }
            }
        } catch (Exception var11) {
            logger.error("error......");
        }

        return respMessage;
    }
}
