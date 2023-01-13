package com.hfw.basesystem.entity;

import com.hfw.basesystem.enums.DeviceEnum;
import com.hfw.basesystem.mybatis.FieldIgnore;
import com.hfw.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Table;

/**
* app版本管理
* @author farkle
* @date 2022-11-25
*/
@Data
@Accessors(chain = true)
@Table(name = "app_version")
public class AppVersion extends BaseEntity {
    /** id */
    private Long id;

    /** 设备(android,ios) */
    private DeviceEnum device;

    /** 版本 */
    private String version;

    /** 描述 **/
    private String description;

    /** apk下载url */
    private String downloadUrl;

    /** 是否强制更新(1是0否) */
    private Integer forceUpdate;

    @FieldIgnore
    /** 是否有更新(1是0否) */
    private Integer hasUpdate;
}