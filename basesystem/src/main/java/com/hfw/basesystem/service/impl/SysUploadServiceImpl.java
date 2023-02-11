package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.entity.SysUpload;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysUploadService;
import com.hfw.common.support.SignUtil;
import com.hfw.common.util.DateUtil;
import com.hfw.common.util.StrUtil;
import com.hfw.plugins.objstore.Qiniu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 系统文件上传服务
 * @author farkle
 * @date 2023-02-10
 */
@Service("sysUploadService")
public class SysUploadServiceImpl implements SysUploadService {

    @Value("${server-url:}")
    private String serverUrl;
    @Value("${upload-path:}")
    private String uploadPath;
    @Resource
    private CommonDao commonDao;

    @Override
    public SysUpload upload(MultipartFile file) throws Exception {
        String md5 = SignUtil.md5(file.getBytes());
        SysUpload sysUpload = commonDao.selectOne(new SysUpload().setMd5(md5));
        if(sysUpload!=null){
            return sysUpload;
        }
        sysUpload = uploadLocal(file);
        //sysUpload = uploadQiniu(file);
        sysUpload.setMd5(md5);
        commonDao.insert(sysUpload);
        return sysUpload;
    }

    public SysUpload uploadLocal(MultipartFile file) throws IOException {
        String now = DateUtil.toString(new Date(),"yyyyMM");
        String uploadPath = this.uploadPath+ now;
        Path path = Paths.get(uploadPath);
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        String fileName = UUID.randomUUID().toString() +"."+ StrUtil.getFilenameExtension(file.getOriginalFilename());
        Path filePath = Paths.get(uploadPath, fileName);
        file.transferTo(filePath);

        SysUpload sysUpload = new SysUpload();
        sysUpload.setUrl(serverUrl+"/upload/"+now+"/"+fileName);
        sysUpload.setPath(filePath.toString());
        sysUpload.setFileName(file.getOriginalFilename());
        sysUpload.setFileSuffix( StrUtil.getFilenameExtension(sysUpload.getFileName()) );
        sysUpload.setUploadTime(LocalDateTime.now());
        sysUpload.setFileSize(file.getSize());
        return sysUpload;
    }

    public SysUpload uploadQiniu(MultipartFile file) throws IOException {
        Map<String, String> qiniu = Qiniu.upload(file);
        SysUpload sysUpload = new SysUpload();
        sysUpload.setUrl(qiniu.get("url"));
        sysUpload.setPath(qiniu.get("key"));
        sysUpload.setFileName(file.getOriginalFilename());
        sysUpload.setFileSuffix( StrUtil.getFilenameExtension(sysUpload.getFileName()) );
        sysUpload.setUploadTime(LocalDateTime.now());
        sysUpload.setFileSize(file.getSize());
        return sysUpload;
    }
}
