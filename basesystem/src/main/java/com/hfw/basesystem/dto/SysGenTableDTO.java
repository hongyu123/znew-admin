package com.hfw.basesystem.dto;

import com.hfw.basesystem.entity.SysGenTable;
import lombok.Data;

/**
 * 表单生成记录DTO
 */
@Data
public class SysGenTableDTO extends SysGenTable {

    /*************************查询用*****************************/


    /*************************显示用*****************************/


    public SysGenTable toEntity(){
        SysGenTable sysGenTable = new SysGenTable();
        sysGenTable.setId(this.getId());
        sysGenTable.setTableName(this.getTableName());
        sysGenTable.setTableRemark(this.getTableRemark());
        return sysGenTable;
    }

}