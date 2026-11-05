package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.oss.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * 文件上传控制器（阿里云 OSS + 本地存储）
 */
@RestController
@RequestMapping("/upload")
public class SysUploadController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public AjaxResult upload(@RequestParam("file") MultipartFile file) {
        try {
            String url = ossService.upload(file);
            AjaxResult result = AjaxResult.success("上传成功");
            result.put("url", url);
            return result;
        } catch (Exception e) {
            return AjaxResult.error("上传失败：" + e.getMessage());
        }
    }

    /** 本地文件上传（OSS 不可用时使用） */
    @PostMapping("/local")
    public AjaxResult uploadLocal(@RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String ext = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID().toString().replace("-", "") + ext;
            File dir = new File("d:/zuoye/CampusPickUp/campus-pickup-server/uploads");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file.transferTo(new File(dir, filename));
            AjaxResult result = AjaxResult.success("上传成功");
            result.put("url", "http://localhost:8080/uploads/" + filename);
            return result;
        } catch (Exception e) {
            return AjaxResult.error("上传失败：" + e.getMessage());
        }
    }
}
