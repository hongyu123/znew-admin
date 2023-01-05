package com.hfw.basesystem.dto;

import com.hfw.basesystem.entity.AppAdvice;
import lombok.Data;

/**
 * app建议反馈DTO
 */
@Data
public class AppAdviceDTO extends AppAdvice {

    /*************************查询用*****************************/
    private String phone_like;

    /*************************显示用*****************************/
    private String userNickname;
    private String userPhone;
    private String userPhoto;

    public AppAdvice toEntity(){
        AppAdvice appAdvice = new AppAdvice();
        appAdvice.setId(this.getId());
        appAdvice.setUserId(this.getUserId());
        appAdvice.setCategory(this.getCategory());
        appAdvice.setPicture(this.getPicture());
        appAdvice.setContent(this.getContent());
        appAdvice.setPhone(this.getPhone());
        appAdvice.setCreateTime(this.getCreateTime());
        return appAdvice;
    }

}