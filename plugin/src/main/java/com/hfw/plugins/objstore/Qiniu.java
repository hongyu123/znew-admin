package com.hfw.plugins.objstore;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Qiniu {
    private static String accesskey = "";
    private static String secretkey = "";
    private static String bucket = "";
    private static String domain = "";

    /**
     * 获取七牛云token
     *
     * @return clientAction
     */
    public static Map<String, String> token() {
        Auth auth = Auth.create(accesskey, secretkey);
        String token = auth.uploadToken(bucket);
        Map<String, String> info = new HashMap<>();
        info.put("token", token);
        info.put("domain", domain);
        return info;
    }

    /**
     * 文件上传
     *
     * @param upfile
     * @return url =domain+key
     * @throws IOException
     */
    public static Map<String, String> upload(MultipartFile upfile) throws IOException {
        String filename = upfile.getOriginalFilename();
        String key = UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf('.'));
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accesskey, secretkey);
        String token = auth.uploadToken(bucket);
        uploadManager.put(upfile.getInputStream(), upfile.getSize(), key, token, null, null, false);

        Map<String, String> info = new HashMap<>();
        info.put("url", domain + key);
        info.put("key", key);
        info.put("domain", domain);
        return info;
    }

}