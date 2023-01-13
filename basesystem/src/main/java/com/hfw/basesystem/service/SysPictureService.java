package com.hfw.basesystem.service;

import com.hfw.basesystem.entity.SysPicture;
import com.hfw.basesystem.enums.PictureEnum;

import java.util.List;

/**
 * 系统图片服务
 * @author farkle
 * @date 2023-01-09
 */
public interface SysPictureService {

    /**
     * 图片列表
     * @param targetId
     * @param type
     * @return
     */
    List<SysPicture> list(Long targetId, PictureEnum type);

    /**
     * 保存图片列表
     * @param targetId
     * @param type
     * @param pictureList
     * @return
     */
    int save(Long targetId, PictureEnum type, List<SysPicture> pictureList);

    /**
     * 编辑图片列表
     * @param targetId
     * @param type
     * @param pictureList
     * @return
     */
    int edit(Long targetId, PictureEnum type, List<SysPicture> pictureList);

    /**
     * 删除图片列表
     * @param targetId
     * @param type
     * @return
     */
    int del(Long targetId, PictureEnum type);
}
