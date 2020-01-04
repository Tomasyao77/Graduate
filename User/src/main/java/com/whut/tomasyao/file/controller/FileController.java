package com.whut.tomasyao.file.controller;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-06-09 08:48
 */

import com.whut.tomasyao.file.util.FileUtil;
import com.whut.tomasyao.redirect.HttpUtil;
import io.swagger.annotations.ApiOperation;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.whut.tomasyao.file.service.IFileService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(value = "/file/redirect")
public class FileController {

    private static int offset = "/file/redirect/".length();
    private static Logger logger = Logger.getLogger(FileController.class);

    @ApiOperation(value = "重定向", hidden = true)
    @RequestMapping(value = "/**", method = RequestMethod.POST)
    public void redirect(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");//获取userId参数
        String url = request.getRequestURI();
        int base = request.getContextPath().length();//获取当前根目录的长度
        String host = url.substring(base + offset, url.indexOf("/",base + offset));//截取字符串，获取调用的服务器//例如trade
        logger.info("request.getRequestURI():"+request.getRequestURI()+"\tbase:"+base+"\thost:"+host);
        List<NameValuePair> additionalMap = new ArrayList<>();
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();//file
            List<MultipartFile> files = request.getFiles(fileName);
            for (MultipartFile file : files) {
                if (file != null && file.getSize() > 0) {
                    additionalMap.add(new BasicNameValuePair(fileName, FileUtil.upload(userId, file)));
                    if (file.getContentType() != null && file.getContentType().startsWith("image/")) {
                        //判断返回文件类型，是否为缩略图
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        //压缩图片
                        Thumbnails.of(new ByteArrayInputStream(file.getBytes()))
                                //.size(256, 256)
                                .scale(0.7f)
                                .toOutputStream(out);
                        additionalMap.add(new BasicNameValuePair(fileName + "Thumb", FileUtil.upload(userId,file,out)));
                    }
                } else {
                    additionalMap.add(new BasicNameValuePair(fileName, ""));
                }
            }
        }
        HttpUtil.httpRequest(host, url.substring(url.indexOf("/", base + offset)),
                request, response, additionalMap);//转发
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

}