package com.hfw.model.po;

import cn.xbatis.db.annotations.Table;
import com.hfw.model.enums.PictureEnum;
import lombok.Getter;
import lombok.Setter;

/**
* 系统图片
* @author farkle
* @date 2022-11-25
*/
@Getter @Setter
@Table("sys_picture")
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