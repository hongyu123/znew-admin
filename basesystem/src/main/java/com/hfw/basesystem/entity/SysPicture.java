package com.hfw.basesystem.entity;

import com.hfw.basesystem.enums.PictureEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Table;

/**
* 系统图片
* @author farkle
* @date 2022-11-25
*/
@Data
@Accessors(chain = true)
@Table(name = "sys_picture")
public class SysPicture {
    /** id **/
    private Long id;

    /** 图片分类 **/
    private PictureEnum type;

    /** 图片 **/
    private String url;

    /** 分类目标id **/
    private Long targetId;

}