package com.hfw.api.dto;

import com.hfw.common.enums.Gender;
import com.hfw.basesystem.entity.AppUser;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 编辑app用户模型
 * @author farkle
 * @date 2022-11-26
 */
@Data
public class AppUserEditDTO {

    /** 昵称 */
    @Length(max = 100, message = "昵称最多100字符")
    private String nickname;

    /** 头像 */
    private String avatar;

    /** 性别 */
    private Gender gender;

    /** 生日 */
    private java.time.LocalDate birth;

    /** 地址 */
    @Length(max = 50, message = "地址过长")
    private String address;

    public static AppUser to(AppUserEditDTO dto){
        AppUser appUser = new AppUser();
        appUser.setNickname(dto.getNickname());
        appUser.setAvatar(dto.getAvatar());
        appUser.setGender(dto.getGender());
        appUser.setBirth(dto.getBirth());
        appUser.setAddress(dto.getAddress());
        return appUser;
    }

}
