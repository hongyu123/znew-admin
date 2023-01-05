package com.hfw.basesystem.service;

import com.hfw.basesystem.entity.SysContent;

/**
 * Sys服务
 * @author zyh
 * @date 2022-11-29
 */
public interface SysService {

    SysContent sysContent(Long id);
    SysContent sysContent(String content);
    int sysContent(SysContent content);
    int delSysContent(Long id);

}
