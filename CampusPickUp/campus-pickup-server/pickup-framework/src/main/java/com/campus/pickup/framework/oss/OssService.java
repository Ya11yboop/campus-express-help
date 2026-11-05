package com.campus.pickup.framework.oss;

import com.aliyun.oss.OSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 阿里云 OSS 文件上传服务
 *
 * @author campus-pickup
 */
@Service
public class OssService {

    @Autowired
    private OSS ossClient;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    @Value("${aliyun.oss.domain}")
    private String domain;

    /**
     * 上传文件，返回可访问的 URL
     */
    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new IOException("文件名不合法");
        }
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = "campus-pickup/" + UUID.randomUUID().toString().replace("-", "") + ext;
        try (InputStream in = file.getInputStream()) {
            ossClient.putObject(bucketName, objectName, in);
        }
        return domain + "/" + objectName;
    }
}
