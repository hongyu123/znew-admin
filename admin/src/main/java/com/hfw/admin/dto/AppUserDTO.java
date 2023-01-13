package com.hfw.admin.dto;

import com.hfw.basesystem.entity.AppUser;
import lombok.Data;

/**
 * app用户DTO
 */
@Data
public class AppUserDTO extends AppUser {

    /*************************查询用*****************************/
    private String nickname_like;
    private String phone_like;

    /*************************显示用*****************************/


    public AppUser toEntity(){
        AppUser appUser = new AppUser();
        appUser.setNickname(this.getNickname());
        appUser.setAvatar(this.getAvatar());
        appUser.setPhone(this.getPhone());
        appUser.setGender(this.getGender());
        appUser.setBirth(this.getBirth());
        appUser.setAddress(this.getAddress());
        appUser.setIntegral(this.getIntegral());
        return appUser;
    }

}