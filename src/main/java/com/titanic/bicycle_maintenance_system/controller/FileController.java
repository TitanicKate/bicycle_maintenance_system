package com.titanic.bicycle_maintenance_system.controller;

import com.titanic.bicycle_maintenance_system.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {

    @Autowired
    private OSSUtil ossUtil;

    /**
     * 上传图片到 OSS（通过后端中转）
     */
    @PostMapping("/upload/image")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 上传到 OSS 的 "images/" 文件夹
            return ossUtil.uploadFile(file, "avatar/");
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }
}