package com.whut.tomasyao.weixin.util;

import edu.whut.pocket.weixin.model.button.Button;
import edu.whut.pocket.weixin.model.button.ClickButton;
import edu.whut.pocket.weixin.model.button.Menu;
import edu.whut.pocket.weixin.model.button.ViewButton;
import org.apache.log4j.Logger;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-07 13:36
 */

public class MenuUtil {
    private static final Logger logger = Logger.getLogger(MenuUtil.class);

    public MenuUtil() {
    }

    public static Menu getMenu() {
        ViewButton vb_1 = new ViewButton();
        vb_1.setName("微官网");
        vb_1.setType("view");
        vb_1.setUrl("http://pfyx.d9lab.net/");
        ViewButton vb_2 = new ViewButton();
        vb_2.setName("解码报告");
        vb_2.setType("view");
        vb_2.setUrl("http://www.ceks100.com/weixin/");
        ViewButton vb_3 = new ViewButton();
        vb_3.setName("自助解码");
        vb_3.setType("view");
        vb_3.setUrl("https://app.ceks100.com/jsp/index/autoDetect/auto.html");
        ClickButton cb_1 = new ClickButton();
        cb_1.setKey("help");
        cb_1.setName("帮助");
        cb_1.setType("click");
        Button b_1 = new Button();
        b_1.setName("更多");
        b_1.setSub_button(new Button[]{vb_3, cb_1});
        Menu menu = new Menu();
        menu.setButton(new Button[]{vb_1, vb_2, b_1});
        return menu;
    }

    public static int createMenu(Menu menu) {
        int status = 0;
        //json-jena影响了org.json的使用fuck
        /*String jsonMenu = (new JSONObject(menu)).toString();
        logger.info("菜单: " + jsonMenu);
        logger.info("access_token: " + TokenThread.access_token.getAccess_token());
        String path = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + TokenThread.access_token.getAccess_token();

        try {
            URL e = new URL(path);
            HttpURLConnection http = (HttpURLConnection)e.openConnection();
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(jsonMenu.getBytes("UTF-8"));
            os.close();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] bt = new byte[size];
            is.read(bt);
            String message = new String(bt, "UTF-8");
            logger.info("result of get access_token message: " + message);
            JSONObject jsonMsg = new JSONObject(message);
            status = Integer.parseInt(jsonMsg.get("errcode").toString());
        } catch (MalformedURLException var12) {
            var12.printStackTrace();
        } catch (IOException var13) {
            var13.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        return status;
    }
}
