package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Ignore;
import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* 表单生成记录
* @author farkle
* @date 2023-01-04
*/
@Getter @Setter
@Table("sys_gen_table")
public class SysGenTable {
    @TableId
    private Long id;

    /** 表名 **/
    private String tableName;

    /** 表注释 **/
    private String tableRemark;

    /** bean名称 */
    @Ignore
    private String beanName;
    /** 实体类名 */
    @Ignore
    private String className;

    /** 字段列表 */
    @Ignore
    private List<SysGenColumn> columnList;
    /** 是否有富文本 */
    @Ignore
    private boolean richtext = false;
    @Ignore
    private boolean date = false;
    @Ignore
    private boolean datetime = false;
    @Ignore
    private boolean picture = false;
    @Ignore
    private boolean pictures = false;
    @Ignore
    private boolean file = false;
    @Ignore
    private boolean fileInput = false;
    @Ignore
    private boolean map = false;
    @Ignore
    private boolean video = false;

}
