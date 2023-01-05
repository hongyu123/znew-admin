package com.hfw.basesystem.dto;

import com.hfw.basesystem.entity.AppVersion;
import lombok.Data;

/**
 * app版本管理DTO
 */
@Data
public class AppVersionDTO extends AppVersion {

    /*************************查询用*****************************/


    /*************************显示用*****************************/


    public AppVersion toEntity(){
        AppVersion appVersion = new AppVersion();
        appVersion.setId(this.getId());
        appVersion.setDevice(this.getDevice());
        appVersion.setVersion(this.getVersion());
        appVersion.setDescription(this.getDescription());
        appVersion.setDownloadUrl(this.getDownloadUrl());
        return appVersion;
    }

}