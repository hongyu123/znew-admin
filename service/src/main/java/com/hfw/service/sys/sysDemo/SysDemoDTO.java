package com.hfw.service.sys.sysDemo;

import com.hfw.model.entity.UploadFile;
import com.hfw.model.po.sys.SysDemo;
import com.hfw.model.utils.BeanCopierUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SysDemoDTO extends SysDemo {
    public List<UploadFile> photosList;
    public List<UploadFile> getPhotosList(){
        this.photosList = UploadFile.mapToFile(super.getPhotos());
        return photosList;
    }

    public List<UploadFile> attachmentList;
    public List<UploadFile> getAttachmentList(){
        this.attachmentList = UploadFile.mapToFile(super.getAttachment());
        return attachmentList;
    }

    public SysDemo toPo(){
        SysDemo po = new SysDemo();
        BeanCopierUtil.copyProperties(this, po);
        po.setAvatar(UploadFile.removeServerPrefix(this.avatarUrl));
        po.setPhotos(UploadFile.fileToMap(this.photosList));
        po.setAttachment(UploadFile.fileToMap(this.attachmentList));
        return po;
    }
    public static SysDemoDTO fromPo(SysDemo po){
        SysDemoDTO dto = new SysDemoDTO();
        BeanCopierUtil.copyProperties(po, dto);
        return dto;
    }

}
