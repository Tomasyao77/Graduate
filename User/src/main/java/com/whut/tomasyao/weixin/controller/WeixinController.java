package com.whut.tomasyao.weixin.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import edu.whut.pocket.base.util.EncryptUtil;
import edu.whut.pocket.base.vo.ResponseMap;
import edu.whut.pocket.dubbo.file.service.IDubboFileService;
import edu.whut.pocket.dubbo.store.service.IDubboStoreService;
import edu.whut.pocket.file.util.FileUtil;
import edu.whut.pocket.weixin.service.TokenThread;
import edu.whut.pocket.weixin.service.WeixinService;
import edu.whut.pocket.weixin.smallCode.CommonUtil;
import edu.whut.pocket.weixin.smallCode.SmallCodeConstant;
import edu.whut.pocket.weixin.util.AES;
import edu.whut.pocket.weixin.util.MenuUtil;
import edu.whut.pocket.weixin.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-07 13:40
 */
@Api(tags = {"微信"}, description = "公众号|小程序", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping({"/weixin"})
public class WeixinController {
    private static final Logger logger = Logger.getLogger(WeixinController.class);
    @Autowired
    private WeixinService weixinService;
    private String DNBX_TOKEN = "ceksToken";

    @Autowired
    private IDubboFileService dubboFileService;

    @Autowired
    private IDubboStoreService dubboStoreService;

    //微信公众号初始配置
    @ApiIgnore
    @RequestMapping(value = "/connect", method = {RequestMethod.GET, RequestMethod.POST})
    public void connectWeixin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter out = response.getWriter();

        try {
            String e;
            if(isGet) {
                e = request.getParameter("signature");
                String e1 = request.getParameter("timestamp");
                String nonce = request.getParameter("nonce");
                String echostr = request.getParameter("echostr");
                if(SignUtil.checkSignature(this.DNBX_TOKEN, e, e1, nonce)) {
                    logger.info("Connect the weixin server is successful.");
                    response.getWriter().write(echostr);
                } else {
                    logger.error("Failed to verify the signature!");
                }
            } else {
                e = "异常消息！";

                try {
                    e = this.weixinService.weixinPost(request);
                    out.write(e);
                    logger.info("The request completed successfully");
                    logger.info("to weixin server " + e);
                } catch (Exception var13) {
                    logger.error("Failed to convert the message from weixin!");
                }
            }
        } catch (Exception var14) {
            logger.error("Connect the weixin server is error.");
        } finally {
            out.close();
        }
    }

    //微信公众号创建菜单
    @ApiIgnore
    @RequestMapping(value = "/createMenu", method = {RequestMethod.POST})
    public void createMenu(HttpServletRequest request) throws IOException {
        int status = MenuUtil.createMenu(MenuUtil.getMenu());
        if(status == 0) {
            logger.info("菜单创建成功！");
        } else {
            logger.info("菜单创建失败！");
        }
    }

    //小程序获取openId
    @ApiOperation(value = "小程序获取openId", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "小程序获取openId, 同一用户对同一公众号openId唯一, 所传参数为code")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "code",paramType = "form",dataType = "string"),
    })
    @RequestMapping(value = "/getOpenId", method = {RequestMethod.POST})
    public Map getOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseMap map = ResponseMap.getInstance();
        String status = "1";
        String msg = "ok";
        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        try {
            String code = request.getParameter("code");
            if(StringUtils.isBlank(code)){
                status = "0";//失败状态
                msg = "code为空";
            }else {
                String requestUrl = WX_URL.replace("APPID", SmallCodeConstant.APPID).
                        replace("SECRET", SmallCodeConstant.APP_SECRECT).replace("JSCODE", code).
                        replace("authorization_code", SmallCodeConstant.AUTHORIZATION_CODE);
                logger.info(requestUrl);
                // 发起GET请求获取凭证
                JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
                if (jsonObject != null) {
                    try {
                        map.put("openid", jsonObject.getString("openid"));
                        map.put("session_key", jsonObject.getString("session_key"));
                    } catch (JSONException e) {
                        // 获取token失败
                        status = "0";
                        msg = "code无效";
                    }
                }else {
                    status = "0";
                    msg = "code无效";
                }
            }
            map.put("status", status);
            map.put("msg", msg);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return map;
    }

    //小程序获取手机号
    @ApiOperation(value = "小程序获取手机号", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "小程序获取手机号, 所传参数为encrypted session_key iv code")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "encrypted",value = "encrypted",paramType = "form",dataType = "string"),
            @ApiImplicitParam(name = "iv",value = "iv",paramType = "form",dataType = "string"),
            @ApiImplicitParam(name = "code",value = "code",paramType = "form",dataType = "string")
    })
    @RequestMapping(value = "/getPhoneNumber", method = {RequestMethod.POST})
    public Map getPhoneNumber(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseMap map = ResponseMap.getInstance();
        String encrypted = request.getParameter("encrypted");
        String iv = request.getParameter("iv");
        String code = request.getParameter("code");
        String session_key = "";//前端不传,后台获取
        String open_id = "";//前端不传,后台获取

        String status = "1";
        String msg = "ok";
        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&" +
                "grant_type=authorization_code";
        try {
            if (StringUtils.isBlank(code)) {
                status = "0";//失败状态
                msg = "code为空";
            } else {
                String requestUrl = WX_URL.replace("APPID", SmallCodeConstant.APPID).
                        replace("SECRET", SmallCodeConstant.APP_SECRECT).replace("JSCODE", code).
                        replace("authorization_code", SmallCodeConstant.AUTHORIZATION_CODE);
                logger.info(requestUrl);
                // 发起GET请求获取凭证
                JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
                if (jsonObject != null) {
                    try {
                        open_id = jsonObject.getString("openid");
                        session_key = jsonObject.getString("session_key");
                    } catch (JSONException e) {
                        // 获取token失败
                        status = "0";
                        msg = "code无效";
                    }
                } else {
                    status = "0";
                    msg = "code无效";
                }
            }
            map.put("status", status);
            map.put("msg", msg);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        String json = AES.wxDecrypt(encrypted, session_key, iv);
        logger.info(json);
        return map.putValue(json, open_id);//message: open_id
    }

    /**
     * 创建小程序码
     */
    @ApiOperation(value = "创建小程序码", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "创建店铺的小程序码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户id",paramType = "form",dataType = "int"),
            @ApiImplicitParam(name = "storeId",value = "店铺id",paramType = "form",dataType = "int")
    })
    @RequestMapping(value = "/createSmallCode", method = {RequestMethod.POST})
    public Map createSmallCode(HttpServletRequest request, int userId, HttpServletResponse response,
                               int storeId) throws IOException {
        ResponseMap map = ResponseMap.getInstance();
        String accessToken = TokenThread.getAccess_token().getAccess_token();
        String apiUrl = "https://api.weixin.qq.com/wxa/getwxacode?access_token=ACCESS_TOKEN"
                .replace("ACCESS_TOKEN", accessToken);
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
            httpURLConnection.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("path", "pages/shop/pocket_shop/pocket_shop?share=true&userId="+userId+"&storeId="+storeId);
            paramJson.put("width", 430);
            paramJson.put("auto_color", true);
            /**
             * line_color生效
             * paramJson.put("auto_color", false);
             * JSONObject lineColor = new JSONObject();
             * lineColor.put("r", 0);
             * lineColor.put("g", 0);
             * lineColor.put("b", 0);
             * paramJson.put("line_color", lineColor);
             * */
            paramJson.put("is_hyaline", true);

            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());

            //先在本地保存临时文件
            String fileName = EncryptUtil.md5("storeqrcode"+userId+"-"+storeId);
            String outFile = "/alfa/whut/webapps/tomcat-debug/kdtrade.d9lab.net/WEB-INF/classes/edu/whut/pocket/file/temp/";
            OutputStream os = new FileOutputStream(new File(outFile+fileName+".png"));
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            bis.close();
            os.close();
            //上传到fdfs
            File file = new File(outFile+fileName+".png");
            String picture = FileUtil.upload(String.valueOf(userId), file);
            edu.whut.pocket.base.model.File sqlFile = dubboFileService.addOneFile(picture);
            dubboStoreService.createSmallCode(storeId, sqlFile.getId());
            map.put("qrcode", picture);
            clearFiles(outFile+fileName+".png");

            //返回给浏览器下载
            /*response.setContentType("image/png;charset=utf-8");//设置文件MIME类型
            response.setHeader("Content-Disposition", "attachment;filename=" + storeId + ".png");//设置Content-Disposition
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            byte[] buf = new byte[1024];
            int L;
            while ((L = bis.read(buf)) != -1) {
                toClient.write(buf, 0, L);
            }
            bis.close();
            // 写完以后关闭文件流
            toClient.flush();
            toClient.close();*/
        } catch (Exception e) {
            e.printStackTrace();
            return map.putFailure("生成失败", -1);
        }

        return map.putSuccess("生成成功");//message: open_id
    }

    private static void clearFiles(String workspaceRootPath) {//使用时最好精确到文件
        File file = new File(workspaceRootPath);
        if (file.exists()) {
            deleteFile(file);
        }
    }
    private static void deleteFile(File file) {
        if (file.isDirectory()) {//递归调用
            File[] files = file.listFiles();
            if(files != null){
                for (File f : files) {
                    deleteFile(f);
                }
            }
        }
        file.delete();
    }

    //敏感文本检测
    @ApiOperation(value = "敏感文本检测", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "敏感文本检测")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "登录者id",paramType = "form",dataType = "int"),
            @ApiImplicitParam(name = "content",value = "文本内容",paramType = "form",dataType = "string")
    })
    @RequestMapping(value = "/msgSecCheck", method = {RequestMethod.POST})
    public Map msgSecCheck(HttpServletRequest request, int userId, HttpServletResponse response,
                           String content) throws IOException {
        ResponseMap map = ResponseMap.getInstance();
        String WX_URL = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token=ACCESS_TOKEN";
        String accessToken = TokenThread.getAccess_token().getAccess_token();
        String result = null;
        try {
            String requestUrl = WX_URL.replace("ACCESS_TOKEN", accessToken);
            JSONObject tempJson = new JSONObject();
            tempJson.put("content", content);
            String outputStr = tempJson.toString();
            JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", outputStr);
            result = jsonObject.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return map.putValue(result);
    }

}
