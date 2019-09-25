package com.duli.ecommercebackground.controller;

import com.dulli.commom.pojo.MultiResultBean;
import com.dulli.commom.pojo.ResultBean;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@RequestMapping("file")
public class FileUploadController {

    @Value("${image.server}")
    private String image_server;


    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping("upload")
    @ResponseBody
    public ResultBean upload(MultipartFile file) {
        //获取到文件对象，将文件对象上传到FastDFS上
        String originalFilename = file.getOriginalFilename();
        //以什么结尾
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        //文件上传
        StorePath storePath = null;
        try {
            storePath = fastFileStorageClient.uploadFile(file.getInputStream(),file.getSize(),extName,null);
            String fullPath = storePath.getFullPath();
            return ResultBean.success(new StringBuilder(image_server).append(fullPath).toString());
        } catch (IOException e) {
            e.printStackTrace();
            return ResultBean.error("稍等，网络正忙！");
        }
    }

    @RequestMapping("multiUpload")
    @ResponseBody
    public MultiResultBean upload(MultipartFile[] files) {
        MultiResultBean multiResultBean = new MultiResultBean();
        String[] data = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            //获取到文件对象，将文件对象上传到FastDFS上
            String originalFilename = files[i].getOriginalFilename();
            //以什么结尾
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            //文件上传
            StorePath storePath = null;
            try {
                storePath = fastFileStorageClient.uploadFile(files[i].getInputStream(),files[i].getSize(),extName,null);
                String fullPath = storePath.getFullPath();
                data[i] = new StringBuilder(image_server).append(fullPath).toString();
            } catch (IOException e) {
                e.printStackTrace();
                multiResultBean.setErrno("-1");
                return multiResultBean;
            }
        }
        multiResultBean.setErrno("0");
        multiResultBean.setData(data);
        return multiResultBean;
    }
}
