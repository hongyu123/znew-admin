package com.hfw.basesystem.service;

import com.hfw.basesystem.entity.SysContent;

/**
 * 系统图文服务
 * @author farkle
 * @date 2022-11-29
 */
public interface SysContentService {

    SysContent detail(Long id);
    String content(Long id);
    Long save(String content);
    int edit(Long id, String content);
    int del(Long id);

}
