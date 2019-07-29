package com.company.mall.shop.controller;

import com.company.entity.Result;
import com.company.mall.common.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 文件上传处理controller
 * @author: chunguang.yao
 * @date: 2019-07-30 0:14
 */
@RestController
public class UploadController {

    // 文件服务器地址,配置为application.properties文件中
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    @RequestMapping("/upload")
    public Result upload(MultipartFile file) {
        // 1.获取文件扩展名
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        try {
            // 2.创建一个FstaDFS的客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
            // 3.执行上传处理
            String path = fastDFSClient.uploadFile(file.getBytes(), extName);
            // 4.拼接返回的服务器的存储路径 + ip地址，组成真正的url
            String url = FILE_SERVER_URL + path;
            // 上传成功，返回url
            return new Result(true, url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "上传失败!");
        }
    }
}
