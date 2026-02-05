package com.hfw.model.mybatis.jsqlparser;

import lombok.Setter;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitor;

@Setter
public class SqlStatementVisitor extends StatementVisitorAdapter<Void> {
    private SelectVisitor<Void> selectVisitor;

    @Override
    public <S> Void visit(Select select, S context) {
        select.accept(selectVisitor,context);
        return null;
    }

}
