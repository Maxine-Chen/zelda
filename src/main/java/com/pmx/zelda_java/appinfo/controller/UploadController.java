package com.pmx.zelda_java.appinfo.controller;

import com.pmx.zelda_java.config.AjaxResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传控制器
 */
@RestController
@RequestMapping("/appinfo/upload")
public class UploadController {

    @Value("${upload.path:/uploads/}")
    private String uploadPath;

    @PostMapping("/image")
    public Object uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return AjaxResult.fail("请选择要上传的文件");
        }

        try {
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                return AjaxResult.fail("文件名无效");
            }

            // 获取文件扩展名
            int lastDotIndex = originalFilename.lastIndexOf(".");
            if (lastDotIndex == -1 || lastDotIndex == originalFilename.length() - 1) {
                return AjaxResult.fail("文件格式无效");
            }
            String ext = originalFilename.substring(lastDotIndex).toLowerCase();
            
            // 验证文件类型
            if (!ext.matches("\\.(jpg|jpeg|png|gif|bmp|webp)$")) {
                return AjaxResult.fail("只支持图片格式文件");
            }

            // 生成新的文件名
            String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String newFilename = UUID.randomUUID().toString().replace("-", "") + ext;
            
            // 创建日期目录
            String datePath = uploadPath + dateStr + "/";
            File dateDir = new File(datePath);
            if (!dateDir.exists()) {
                dateDir.mkdirs();
            }

            // 保存文件
            String filePath = datePath + newFilename;
            File dest = new File(filePath);
            file.transferTo(dest);

            // 返回相对路径
            String relativePath = "/" + dateStr + "/" + newFilename;
            return AjaxResult.success(relativePath);

        } catch (IOException e) {
            // Use proper logging instead of printStackTrace in production
            return AjaxResult.fail("上传失败，请稍后重试");
        }
    }
}
