package com.hfw.basesystem.entity;

import com.hfw.basesystem.enums.AppBannerEnum;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* app轮播图
* @author farkle
* @date 2023-01-29
*/
@Data
@Accessors(chain = true)
@Table(name = "app_banner")
public class AppBanner extends BaseEntity {

    /** id */
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    private Long id;

    /** 标题 */
    @NotBlank(message = "标题不能为空", groups = ValidGroup.Add.class)
    @Length(max = 200,message = "标题最多200字符")
    private String title;

    /** 图片 */
    @NotBlank(message = "图片不能为空", groups = ValidGroup.Add.class)
    @Length(max = 255,message = "图片最多255字符")
    private String picture;

    /** 类型 */
    @NotNull(message = "类型不能为空", groups = ValidGroup.Add.class)
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