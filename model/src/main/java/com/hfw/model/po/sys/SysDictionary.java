package com.hfw.model.po.sys;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import com.hfw.model.enums.sys.EnableState;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典表
 * @author farkle
 * @date 2026-02-10
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
    @TableField(insertValue = "$CURRENT_USER")
    private String createUser;

    /** 创建时间 */
    @TableField(insertValue = "$NOW")
    private java.time.LocalDateTime createTime;

    /** 更新账号 */
    @TableField(updateValue = "$CURRENT_USER")
    private String updateUser;

    /** 更新时间 */
    @TableField(updateValue = "$NOW")
    private java.time.LocalDateTime updateTime;


    public enum COLUMN implements Column<SysDictionary>{
        id,
        pid,leafFlag,childrenNum,state,dictKey, dictValue,sort,remark,createUser,createTime,
        updateUser,updateTime
    }
}
