package com.hfw.basesystem.entity;

import com.hfw.common.entity.BaseEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
* app建议反馈
* @author farkle
* @date 2022-11-25
*/
@Getter @Setter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "app_advice")
public class AppAdvice extends BaseEntity {
    /** id **/
    private Long id;

    /** 用户id **/
    private Long userId;

    @NotBlank(message = "问题分类不能为空")
    /** 问题分类 **/
    private String category;

    /** 图片 **/
    private String picture;

    @NotBlank(message = "建议内容不能为空")
    @Length(max = 500, message = "反馈内容最多500字符")
    /** 建议内容 **/
    private String content;

    /** 联系电话 **/
    private String phone;

    /** 创建时间(反馈时间) */
    private LocalDateTime createTime;

    /** 是否已读 */
    private Integer readFlag;
}