package com.hfw.model.mybatis.jsqlparser;

import lombok.Setter;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Setter
public class SqlSelectVisitor extends SelectVisitorAdapter<Void> {
    public static final Logger monitorLog = LoggerFactory.getLogger("monitor_log");

    private FromItemVisitor<Void> fromItemVisitor;
    private ExpressionVisitor<Void> expressionVisitor;

    @Override
    public <S> Void visit(PlainSelect plainSelect, S context) {
        FromItem fromItem = plainSelect.getFromItem();
        Expression where = plainSelect.getWhere();
        List<Join> joins = plainSelect.getJoins();
        if(fromItem!=null){
            if(fromItem instanceof Table table){
                table.accept(fromItemVisitor, plainSelect);
            }else if(fromItem instanceof Select select){
                select.accept(this, context);
            }else{
                monitorLog.error("数据权限SQL解析表名失败:{}", fromItem);
            }
        }
        if(where!=null){
            where.accept(expressionVisitor, context);
        }
        if(joins!=null && !joins.isEmpty()){
            for(Join join : joins){
                join.getFromItem().accept(fromItemVisitor, plainSelect);
            }
        }
        return null;
    }

}
