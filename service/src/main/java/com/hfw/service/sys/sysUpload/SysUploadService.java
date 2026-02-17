package com.hfw.service.sys.sysUpload;

import com.hfw.model.mybatis.Where;
import com.hfw.model.po.sys.SysUpload;
import com.hfw.model.utils.LocalDateUtil;
import com.hfw.model.utils.SignUtil;
import com.hfw.model.utils.StrUtil;
import com.hfw.service.component.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SysUploadService {
    @Autowired
    private CommonMapper commonMapper;
    @Value("${upload.storage-path}")
    private String storagePath;

    private SysUpload uploadLocal(MultipartFile file) throws IOException {
        String now = LocalDateUtil.format(LocalDate.now(), "yyyyMM");
        String uploadPath = this.storagePath + "/" + now;
        Path path = Paths.get(uploadPath);
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        String fileName = UUID.randomUUID() +"."+ StrUtil.getFilenameExtension(file.getOriginalFilename());
        Path filePath = Paths.get(uploadPath, fileName);
        file.transferTo(filePath);

        SysUpload sysUpload = new SysUpload();
        sysUpload.setUrl("/"+now+"/"+fileName);
        sysUpload.setPath(filePath.toString());
        sysUpload.setName(file.getOriginalFilename());
        sysUpload.setFileSuffix( StrUtil.getFilenameExtension(sysUpload.getName()) );
        sysUpload.setUploadTime(LocalDateTime.now());
        sysUpload.setFileSize(file.getSize());
        return sysUpload;
    }

    public SysUpload upload(MultipartFile file) throws Exception {
        String md5 = SignUtil.md5(file.getBytes());
        SysUpload sysUpload = commonMapper.selectOne(SysUpload.class, Where.<SysUpload>where().eq(SysUpload.COLUMN.md5, md5));
        if(sysUpload==null){
            sysUpload = uploadLocal(file);
            sysUpload.setMd5(md5);
            commonMapper.insert(sysUpload);
        }
        sysUpload.setUrl(SysUpload.addServerPrefix(sysUpload.getUrl()));
        return sysUpload;
    }

}
