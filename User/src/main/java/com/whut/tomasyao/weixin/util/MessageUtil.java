package com.whut.tomasyao.weixin.util;

import com.thoughtworks.xstream.XStream;
import edu.whut.pocket.weixin.model.TextMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-07 13:36
 */

public class MessageUtil {
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    public static final String REQ_MESSAGE_TYPE_LINK = "link";
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    public static final String EVENT_TYPE_CLICK = "CLICK";

    public MessageUtil() {
    }

    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException {
        HashMap map = new HashMap();
        SAXReader reader = new SAXReader();
        ServletInputStream ins = null;

        try {
            ins = request.getInputStream();
        } catch (IOException var13) {
            var13.printStackTrace();
        }

        Document doc = null;

        try {
            doc = reader.read(ins);
            Element e1 = doc.getRootElement();
            List list = e1.elements();
            Iterator var7 = list.iterator();

            while(var7.hasNext()) {
                Element e = (Element)var7.next();
                map.put(e.getName(), e.getText());
            }

            HashMap var16 = map;
            return var16;
        } catch (DocumentException var14) {
            var14.printStackTrace();
        } finally {
            ins.close();
        }

        return null;
    }

    public static String textMessageToXml(TextMessage textMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }
}
