package com.whut.tomasyao.redirect;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * edu.whut.change.base.util
 * Created by YTY on 2016/7/8.
 */
public class RedirectUtil {

    public static boolean DEBUG;

    public static String getServerHost() {
        return SERVER_HOST;
    }

    static String SERVER_HOST;
    static int SERVER_PORT;
    static String SERVER_PROTOCOL;
    static String SERVER_CHARSET;
    static Map<String, String> hostPrefixMap;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("server");
        SERVER_HOST = bundle.getString("host");
        SERVER_PORT = Integer.parseInt(bundle.getString("port"));
        SERVER_PROTOCOL = bundle.getString("protocol");
        SERVER_CHARSET = bundle.getString("charset");
        DEBUG = SERVER_HOST.equals("localhost");
        hostPrefixMap = new HashMap<String, String>();
        hostPrefixMap.put("user", bundle.getString("user"));
    }

    public static String getHost(String hostPrefix) {
        return getHost(hostPrefix, "");
    }

    public static String getHost(String hostPrefix, String url) {
        hostPrefix = hostPrefixMap.get(hostPrefix);//例如: trade
        String host = SERVER_HOST;
        if (hostPrefix != null && !hostPrefix.isEmpty()) {
            if (DEBUG) {
                //如果是user的话就不需要转发
                //DEBUG代表本地只需要改变url前缀即可
                url = "/" + hostPrefix + url;//例如: / + trade + /allProduction/getAllProductionPage
            } else {
                //host = hostPrefix + "." + host;//例如: app + . + ceks100.com
                if(hostPrefix.equals("trade")){
                    //服务器上的话改变host即可,不需要改变url前缀
                    host = "47.98.233.56";//例如: trade服务器
                }
            }
        }
        try {
            return new URL(SERVER_PROTOCOL, host, SERVER_PORT, url).toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return SERVER_PROTOCOL + "://" + host + ":" + SERVER_PORT + "url";
        }
    }
}
