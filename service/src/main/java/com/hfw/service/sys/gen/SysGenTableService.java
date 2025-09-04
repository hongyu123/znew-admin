package com.hfw.service.sys.gen;

import cn.xbatis.core.sql.executor.chain.DeleteChain;
import cn.xbatis.core.sql.executor.chain.QueryChain;
import com.hfw.model.entity.Page;
import com.hfw.service.component.CommonMapper;
import com.hfw.model.po.sys.SysGenColumn;
import com.hfw.model.po.sys.SysGenTable;
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
    private SysGenTableMapper sysGenTableMapper;
    @Autowired
    private CommonMapper commonMapper;

    public Page<SysGenTable> page(Page<SysGenTable> page, SysGenTable po) {
        return sysGenTableMapper.page(page, po);
    }
    public SysGenTable detail(Long id){
        SysGenTable table = sysGenTableMapper.getById(id);
        List<SysGenColumn> columnList = QueryChain.of(commonMapper, SysGenColumn.class).eq(SysGenColumn::getTableName, table.getTableName()).list();
        table.setColumnList(columnList);
        return table;
    }

    public void saveGenFormRecord(SysGenTable table){
        table.getColumnList().forEach( c->c.setId(null));
        DeleteChain.of(commonMapper, SysGenColumn.class).eq(SysGenColumn::getTableName, table.getTableName()).execute();
        if(table.getId()==null || table.getId()<=0){
            table.setId(null);
            sysGenTableMapper.save(table);
        }
        commonMapper.saveBatch(table.getColumnList());
    }
    public int edit(SysGenTable sysGenTable){
        return sysGenTableMapper.update(sysGenTable);
    }
    public int del(Long id){
        return sysGenTableMapper.deleteById(id);
    }
    public int dels(List<Long> ids){
        return sysGenTableMapper.deleteByIds(ids);
    }

}
