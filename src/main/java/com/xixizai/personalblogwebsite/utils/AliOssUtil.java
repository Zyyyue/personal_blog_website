package com.xixizai.personalblogwebsite.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xixizai.personalblogwebsite.properties.AliOssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
public class AliOssUtil {

    @Autowired
    private AliOssProperties properties;

    /**
     * 上传文件到阿里云 OSS
     * @param bytes 文件字节数组
     * @param extension 文件后缀
     * @param fileName 文件名
     * @return 文件访问 URL
     */
    public String upload(byte[] bytes, String extension, String fileName) {
        try {
            // 创建 OSSClient
            OSS ossClient = new OSSClientBuilder()
                    .build(properties.getEndpoint(),
                            properties.getAccessKeyId(),
                            properties.getAccessKeySecret());

            // 上传文件
            ossClient.putObject(properties.getBucketName(),
                    fileName,
                    new ByteArrayInputStream(bytes));

            // 关闭客户端
            ossClient.shutdown();

            // 返回文件 URL
            return "https://" + properties.getBucketName() + "."
                    + properties.getEndpoint() + "/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("上传失败：" + e.getMessage());
        }
    }


}
