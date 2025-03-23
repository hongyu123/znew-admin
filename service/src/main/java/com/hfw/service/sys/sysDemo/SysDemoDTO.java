package com.hfw.service.sys.sysDemo;

import com.alibaba.fastjson2.JSON;
import com.hfw.model.entity.UploadFile;
import com.hfw.model.po.sys.SysDemo;
import com.hfw.model.utils.BeanCopierUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter @Setter
public class SysDemoDTO extends SysDemo {

    public List<String> interestList;
    public List<String> getInterestList(){
        if(StringUtils.hasText(super.getInterest())){
            interestList = JSON.parseArray(super.getInterest(),String.class);
        }
        return interestList;
    }

    public List<UploadFile> photosList;
    public List<UploadFile> getPhotosList(){
        if(StringUtils.hasText(super.getPhotos())){
            photosList = JSON.parseArray(super.getPhotos(),UploadFile.class);
            UploadFile.addServerPrefix(photosList);
        }
        return photosList;
    }

    public List<UploadFile> attachmentList;
    public List<UploadFile> getAttachmentList(){
        if(StringUtils.hasText(super.getAttachment())){
            attachmentList = JSON.parseArray(super.getAttachment(),UploadFile.class);
            UploadFile.addServerPrefix(attachmentList);
        }
        return attachmentList;
    }

    public SysDemo toPo(){
        SysDemo po = new SysDemo();
        BeanCopierUtil.copyProperties(this, po);
        po.setAvatar(UploadFile.removeServerPrefix(this.avatarUrl));
        UploadFile.removeServerPrefix(photosList);
        UploadFile.removeServerPrefix(attachmentList);
        if(!CollectionUtils.isEmpty(interestList)){
            po.setInterest(JSON.toJSONString(interestList));
        }
        if(!CollectionUtils.isEmpty(photosList)){
            po.setPhotos(JSON.toJSONString(photosList));
        }
        if(!CollectionUtils.isEmpty(attachmentList)){
            po.setAttachment(JSON.toJSONString(attachmentList));
        }
        return po;
    }
    public static SysDemoDTO fromPo(SysDemo po){
        SysDemoDTO dto = new SysDemoDTO();
        BeanCopierUtil.copyProperties(po, dto);
        return dto;
    }

}
