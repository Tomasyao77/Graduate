package com.whut.tomasyao.message.util;

import edu.whut.pocket.kafka.util.KafkaProducerUtil;
import edu.whut.pocket.message.model.Message;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-09-15 10:40
 */

public class YiTDSmsUtil {
    /*
    //旧的
    private static final String SMS_SEND_URL = "http://121.40.78.35:8080/yxthttp/sms/sendUTF8";
    private static final String USER_ID = "11086";
    private static final String SMS_CLIENT = "ceks";
    private static final String SMS_PASSWORD = "7TSetw";*/
    //新的
    private static final String SMS_SEND_URL = "http://api.yitd.cn/sms/sendUTF8";
    private static final String USER_ID = "10066";
    private static final String SMS_CLIENT = "3ceks2";
    private static final String SMS_PASSWORD = "JXR3n4";  //以前的密码：hcWY5S

    public static final Logger logger = Logger.getLogger(YiTDSmsUtil.class);

    private static LinkedBlockingQueue<Message> smsLinkedBlockingQueue = new LinkedBlockingQueue<>();
    /*public volatile static ThreadPoolExecutor producer_executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(5));*/
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(30, 50, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(20));

    private void initSmsUtil() {
        new Thread(new SendSmsRunnable()).start();
    }

    private class SendSmsRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                Message message;
                try {
                    message = smsLinkedBlockingQueue.take();

                    MyTask myTask = new MyTask(message);
                    executor.execute(myTask);

                    /*//发短信
                    Map<String, Object> result = sendMessage(message);
                    //map转json
                    JSONObject jsonObject = new JSONObject(result);
                    //日志记录，发给kafka
                    if (result != null) {
                        KafkaProducerUtil.producer("logMessage", "sendMessage", jsonObject.toString());
                    }*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyTask implements Runnable {
        private Message message;

        public MyTask(Message message) {
            this.message = message;
        }

        @Override
        public void run() {
            //发短信
            Map<String, Object> result = sendMessage(message);
            //map转json
            JSONObject jsonObject = new JSONObject(result);
            //日志记录，发给kafka
            if (result != null) {
                KafkaProducerUtil.producer("logMessage", "sendMessage", jsonObject.toString());
            }
        }
    }

    //后台触发//使用了静态变量，多线程会有问题要用syncronized同步代码块smsLinkedBlockingQueue.put(msg);//?是吗，不会吧
    public static boolean send(Message msg) {
        if (!MessageUtil.isPhone(msg.getPhone())) {
            return false;
        }
        try {
            synchronized(YiTDSmsUtil.class){
                smsLinkedBlockingQueue.put(msg);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    //具体发短信//没用使用静态变量，不会出现线程安全问题
    private static Map<String, Object> sendMessage(Message msg) {
        //数据预处理
        String phone = msg.getPhone();
        String content = msg.getContent();

        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(SMS_SEND_URL);
        try {
            post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            //请求体
            NameValuePair[] data = {
                    new NameValuePair("action", "send"), new NameValuePair("userid", USER_ID),
                    new NameValuePair("account", SMS_CLIENT), new NameValuePair("password", SMS_PASSWORD),
                    new NameValuePair("mobile", phone), new NameValuePair("content", content)};
            post.setRequestBody(data);
            client.executeMethod(post);
            InputStream inputStream = post.getResponseBodyAsStream();
            Map<String, Object> map = xmlToMap(inputStream);
            for(String s : map.keySet()){
                logger.info(s+" "+map.get(s));
            }
            //额外的返回参数
            if (map == null) {
                map = new HashMap<>();
            }
            map.put("phone", msg.getPhone());
            map.put("type", msg.getType().toString());
            map.put("platform", msg.getPlatform().toString());
            map.put("code", msg.getCode());
            map.put("text", msg.getText());
            map.put("content", msg.getContent());
            map.put("createTime", msg.getCreateTime().getTime());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return null;
    }

    private static Map<String, Object> xmlToMap(InputStream input) throws IOException {
        HashMap map = new HashMap();
        SAXReader reader = new SAXReader();
        InputStream ins = input;

        Document doc = null;

        try {
            doc = reader.read(ins);
            Element e1 = doc.getRootElement();
            List list = e1.elements();
            Iterator var7 = list.iterator();

            while (var7.hasNext()) {
                Element e = (Element) var7.next();
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

}
