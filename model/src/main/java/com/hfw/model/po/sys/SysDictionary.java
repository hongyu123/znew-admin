package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Ignore;
import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableField;
import cn.xbatis.db.annotations.TableId;
import com.hfw.model.enums.sys.EnableState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* 字典表
* @author farkle
* @date 2025-03-29
*/
@Getter @Setter
@Table("sys_dictionary")
public class SysDictionary {

    @TableId
    private Long id;

    /** 分类父id */
    private Long pid;

    /** 叶子节点(叶子节点存字典值,非叶子节点存字典类别) */
    private Integer leafFlag;

    /** 子节点数量 */
    private Integer childrenNum;
    public boolean getHasChildren(){
        return childrenNum!=null && childrenNum>0;
    }

    /** 状态 */
    private EnableState state;
    public String getStateDesc(){
        return state==null ?"":state.getDesc();
    }

    /** dict_key */
    private String dictKey;

    /** dict_value */
    private String dictValue;

    /** 排序值 */
    private Integer sort;

    /** 备注 */
    private String remark;

    /** 创建账号 */
    @TableField(defaultValue = "{CREATE_USER}")
    private String createUser;

    /** 创建时间 */
    @TableField(defaultValue = "{CREATE_TIME}")
    private java.time.LocalDateTime createTime;

    /** 更新账号 */
    @TableField(updateDefaultValue = "{UPDATE_USER}")
    private String updateUser;

    /** 更新时间 */
    @TableField(updateDefaultValue = "{UPDATE_TIME}")
    private java.time.LocalDateTime updateTime;

}
