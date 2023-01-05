package com.hfw.basesystem.mapper;

import com.hfw.basesystem.entity.AppArticle;
import com.hfw.basesystem.entity.AppVersion;
import com.hfw.basesystem.enums.DeviceEnum;
import org.apache.ibatis.annotations.Select;

/**
 * @author zyh
 * @date 2022-12-02
 */
public interface AppMapper {

    /**
     * 获取设备的最后一个版本
     * @param device
     * @return
     */
    @Select("select * from app_version  where device=#{device} order by id desc limit 1")
    AppVersion lastVersion(DeviceEnum device);

    /**
     * 获取文章
     * @param id
     * @return
     */
    @Select("select a.*, c.content from app_article a left join sys_content c on a.content_id=c.id where a.id=#{id}")
    AppArticle appArticle(Long id);

}
