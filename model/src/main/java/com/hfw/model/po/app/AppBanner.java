package com.hfw.model.po.app;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import com.hfw.model.enums.app.AppBannerEnum;
import lombok.Getter;
import lombok.Setter;

/**
* app轮播图
* @author farkle
* @date 2023-01-29
*/
@Getter @Setter
@Table("app_banner")
public class AppBanner {
    @TableId
    private Long id;

    /** 标题 */
    private String title;

    /** 图片 */
    private String picture;

    /** 类型 */
    private AppBannerEnum type;

    /** 链接地址 */
    private String linkUrl;

    /** 跳转目标id */
    private Long targetId;

    /** 创建时间 */
    private java.time.LocalDateTime createTime;

    /** 更新时间 */
    private java.time.LocalDateTime updateTime;

}
