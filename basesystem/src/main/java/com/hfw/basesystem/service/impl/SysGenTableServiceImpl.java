package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.entity.SysGenColumn;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysGenTableService;
import com.hfw.basesystem.mapper.SysGenTableMapper;
import com.hfw.basesystem.entity.SysGenTable;
import com.hfw.common.entity.PageResult;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 表单生成记录服务实现
 * @author 
 * @date 2023-01-04
 */
@Service("sysGenTableService")
public class SysGenTableServiceImpl implements SysGenTableService {

    @Resource
    private SysGenTableMapper sysGenTableMapper;
    @Resource
    private CommonDao commonDao;

    @Override
    public PageResult<SysGenTable> page(SysGenTable sysGenTable) {
        PageResult<SysGenTable> page = new PageResult<>(sysGenTable);
        page.startPage();
        List<SysGenTable> list = sysGenTableMapper.list(sysGenTable);
        page.setList(list);
        return page;
    }

    @Override
    public SysGenTable detail(Long id){
        SysGenTable sysGenTable = commonDao.selectByPk(SysGenTable.class, id);
        List<SysGenColumn> columnList = commonDao.select( SysGenColumn.builder().tableName(sysGenTable.getTableName()).build() );
        sysGenTable.setColumnList(columnList);
        return sysGenTable;
    }

    @Override
    public void saveGenFormRecord(SysGenTable table){
        table.getColumnList().forEach( c->c.setId(null));
        //commonDao.delete(new SysGenTable().setTableName(table.getTableName()));
        commonDao.delete( SysGenColumn.builder().tableName(table.getTableName()).build() );
        if(table.getId()==null || table.getId()<=0){
            table.setId(null);
            commonDao.insert(table);
        }
        commonDao.insertBatch(table.getColumnList());
    }


}