package com.hfw.service.sys.sysDemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hfw.model.entity.UploadFile;
import com.hfw.model.jackson.ObjectMapperUtil;
import com.hfw.model.po.sys.SysDemo;
import com.hfw.model.utils.BeanCopierUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SysDemoDTO extends SysDemo {
    public List<UploadFile> photosList;
    public List<UploadFile> attachmentList;

    public SysDemo toPo(){
        UploadFile.removeServerPrefix(photosList);
        UploadFile.removeServerPrefix(attachmentList);
        ObjectMapper objectMapper = ObjectMapperUtil.singleton();
        ArrayNode photos = objectMapper.convertValue(photosList, ArrayNode.class);
        ArrayNode attachments = objectMapper.convertValue(attachmentList, ArrayNode.class);

        SysDemo po = new SysDemo();
        BeanCopierUtil.copyProperties(this, po);
        po.setAvatar(UploadFile.removeServerPrefix(this.avatarUrl));
        po.setPhotos(photos);
        po.setAttachment(attachments);
        return po;
    }
    public static SysDemoDTO fromPo(SysDemo po){
        ObjectMapper objectMapper = ObjectMapperUtil.singleton();
        List<UploadFile> photosList = objectMapper.convertValue(po.getPhotos(), objectMapper.getTypeFactory().constructParametricType(ArrayList.class, UploadFile.class));
        List<UploadFile> attachmentList = objectMapper.convertValue(po.getAttachment(), objectMapper.getTypeFactory().constructParametricType(ArrayList.class, UploadFile.class));
        UploadFile.addServerPrefix(photosList);
        UploadFile.addServerPrefix(attachmentList);

        SysDemoDTO dto = new SysDemoDTO();
        BeanCopierUtil.copyProperties(po, dto);
        dto.photosList = photosList;
        dto.attachmentList = attachmentList;
        return dto;
    }

}
