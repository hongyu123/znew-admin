package com.hfw.service.sys.gen;

import com.hfw.model.mybatis.Where;
import com.hfw.model.po.sys.SysGenColumn;
import com.hfw.model.po.sys.SysGenTable;
import com.hfw.service.component.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 表单生成记录服务
 * @author farkle
 * @date 2025-03-16
 */
@Service
public class SysGenTableService {
    @Autowired
    private CommonMapper commonMapper;

    public SysGenTable detail(Long id){
        SysGenTable table = commonMapper.selectByPk(SysGenTable.class, id);
        List<SysGenColumn> columnList = commonMapper.selectList(SysGenColumn.class, Where.<SysGenColumn>where().eq(SysGenColumn.COLUMN.tableName, table.getTableName()));
        table.setColumnList(columnList);
        return table;
    }

    public void saveGenFormRecord(SysGenTable table){
        commonMapper.delete(SysGenColumn.class, Where.<SysGenColumn>where().eq(SysGenColumn.COLUMN.tableName, table.getTableName()));
        if(table.getId()==null || table.getId()<=0){
            table.setId(null);
            commonMapper.insert(table);
        }
        commonMapper.insertBatch(table.getColumnList());
    }

}
