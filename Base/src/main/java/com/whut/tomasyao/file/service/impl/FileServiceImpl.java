package com.whut.tomasyao.file.service.impl;
/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-24 11:14
 */

import com.whut.tomasyao.file.service.IFileService;
import com.whut.tomasyao.file.util.FileUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FileServiceImpl implements IFileService {

    @Override
    public List<String> uploadToFdfs(MultipartHttpServletRequest request) throws Exception {
        List<String> resultFiles = new ArrayList<>();
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            List<MultipartFile> files = request.getFiles(fileName);
            for (MultipartFile file : files) {
                if (file != null && file.getSize() > 0) {
                    String result =  FileUtil.upload(null, file);//上传到fdfs
                    resultFiles.add(result);
                }
            }
        }
        return resultFiles;
    }

    @Override
    public List<String[]> uploadWithThumbnails(MultipartHttpServletRequest request) throws Exception {
        List<String[]> resultFiles = new ArrayList<>();
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            List<MultipartFile> files = request.getFiles(fileName);
            for (MultipartFile file : files) {
                if (file != null && file.getSize() > 0) {
                    String result =  FileUtil.upload(null, file);//上传到fdfs
                    String resultThumb = "";
                    if (file.getContentType() != null && file.getContentType().startsWith("image/")) {//是图片才压缩
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        //压缩图片
                        Thumbnails.of(new ByteArrayInputStream(file.getBytes()))
                                //.size(256, 256)
                                .scale(0.7f)
                                .toOutputStream(out);
                        resultThumb = FileUtil.upload(null,file,out);//上传到fdfs
                    }
                    String originFile = result;
                    String thumbFile = resultThumb;
                    resultFiles.add(new String[]{originFile, thumbFile});
                }
            }
        }
        return resultFiles;
    }

}
