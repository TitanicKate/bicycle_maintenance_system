package com.titanic.bicycle_maintenance_system.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class OSSUtil {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    @Value("${aliyun.oss.url-prefix}")
    private String urlPrefix;

    /**
     * 上传文件到 OSS
     * @param file 前端传来的文件
     * @param folder 存储文件夹（如 "images/"）
     * @return 上传后的文件 URL
     */
    public String uploadFile(MultipartFile file, String folder) throws IOException {
        // 1. 生成唯一文件名（避免重名）
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = folder + UUID.randomUUID() + suffix;  // 如 "images/xxx.jpg"

        // 2. 上传文件到 OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 上传文件流
            ossClient.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream()));
        } finally {
            ossClient.shutdown();  // 关闭客户端
        }

        // 3. 返回文件访问 URL
        return urlPrefix + fileName;
    }

    /**
     * 删除 OSS 上的文件
     * @param fileUrl 文件 URL（如 "https://xxx.oss-cn-beijing.aliyuncs.com/images/xxx.jpg"）
     */
    public void deleteFile(String fileUrl) {
        // 提取文件名（去掉 URL 前缀）
        String fileName = fileUrl.replace(urlPrefix, "");

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.deleteObject(bucketName, fileName);
        } finally {
            ossClient.shutdown();
        }
    }
}