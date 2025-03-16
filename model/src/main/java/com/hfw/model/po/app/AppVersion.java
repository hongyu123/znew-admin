package com.hfw.model.po.app;

import cn.xbatis.db.annotations.Ignore;
import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import com.hfw.model.enums.app.DeviceEnum;
import lombok.Getter;
import lombok.Setter;

/**
* app版本管理
* @author farkle
* @date 2022-11-25
*/
@Getter @Setter
@Table("app_version")
public class AppVersion {
    @TableId
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

    /** 是否有更新(1是0否) */
    @Ignore
    private Integer hasUpdate;

}
