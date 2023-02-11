package com.hfw.basesystem.service;

import com.hfw.basesystem.entity.SysUpload;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统文件上传服务
 * @author farkle
 * @date 2023-02-10
 */
public interface SysUploadService {

    SysUpload upload(MultipartFile file) throws Exception;

}
