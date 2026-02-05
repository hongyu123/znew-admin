package com.hfw.model.mybatis.jsqlparser;

import lombok.Setter;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitorAdapter;
import net.sf.jsqlparser.statement.select.PlainSelect;

import java.util.Set;

@Setter
public class SqlFromItemVisitor extends FromItemVisitorAdapter<Void> {
    private Set<String> tables;
    private String expression;
    public SqlFromItemVisitor(Set<String> tables, String expression){
        this.tables = tables;
        this.expression = expression;
    }

    @Override
    public <S> Void visit(Table table, S context) {
        String name = table.getName();
        if(!tables.contains(name)){
            return null;
        }
        Alias alias = table.getAlias();
        //System.out.println(name+" "+alias);
        if(context instanceof PlainSelect plainSelect){
            String aliasName = alias!=null ?alias.getName()+"." :"";
            String cond = aliasName+expression;
            try {
                Expression where = plainSelect.getWhere();
                if(where==null){
                    plainSelect.setWhere(CCJSqlParserUtil.parseExpression(cond));
                }else{
                    AndExpression andExpression = new AndExpression();
                    andExpression.setLeftExpression(where);
                    andExpression.setRightExpression(CCJSqlParserUtil.parseExpression(cond));
                    plainSelect.setWhere(andExpression);
                }
            }catch (JSQLParserException e){
                SqlSelectVisitor.monitorLog.error("数据权限SQL表达式解析失败", e);
            }
        }
        return null;
    }

}
