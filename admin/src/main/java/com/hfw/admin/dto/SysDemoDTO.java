package com.hfw.admin.dto;

import com.hfw.model.entity.SysDemo;
import lombok.Data;

/**
 * 系统示例表DTO
 */
@Data
public class SysDemoDTO extends SysDemo {

    /*************************查询用*****************************/


    /*************************显示用*****************************/


    public SysDemo toEntity(){
        SysDemo sysDemo = new SysDemo();
        sysDemo.setId(this.getId());
        sysDemo.setName(this.getName());
        sysDemo.setAge(this.getAge());
        sysDemo.setScore(this.getScore());
        sysDemo.setGender(this.getGender());
        sysDemo.setState(this.getState());
        sysDemo.setInterest(this.getInterest());
        sysDemo.setBirth(this.getBirth());
        sysDemo.setRegistTime(this.getRegistTime());
        sysDemo.setAvatar(this.getAvatar());
        sysDemo.setVideo(this.getVideo());
        sysDemo.setPhotos(this.getPhotos());
        sysDemo.setAttachment(this.getAttachment());
        sysDemo.setIntroduction(this.getIntroduction());
        sysDemo.setDetail(this.getDetail());
        return sysDemo;
    }
    public SysDemo toSave(){
        SysDemo sysDemo = this.toEntity();

        return sysDemo;
    }
    public SysDemo toUpdate(){
        SysDemo sysDemo = this.toEntity();

        return sysDemo;
    }

}