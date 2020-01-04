package com.whut.tomasyao.weixin.util;

import edu.whut.pocket.weixin.model.token.AccessToken;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-07 13:35
 */

public class AccessTokenUtil {
    private static final Logger logger = Logger.getLogger(AccessTokenUtil.class);

    public AccessTokenUtil() {
    }

    public static AccessToken getAccessToken(String appID, String appScret) {
        AccessToken token = new AccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appID + "&secret=" + appScret;

        try {
            URL e = new URL(url);
            HttpURLConnection http = (HttpURLConnection)e.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] b = new byte[size];
            is.read(b);
            String message = new String(b, "UTF-8");
            logger.info("getAccess_token message: " + message);
            JSONObject json = new JSONObject(message);
            token.setAccess_token(json.getString("access_token"));
            token.setExpires_in((new Integer(json.get("expires_in").toString())).intValue());
        } catch (MalformedURLException var11) {
            var11.printStackTrace();
        } catch (IOException var12) {
            var12.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return token;
    }
}
