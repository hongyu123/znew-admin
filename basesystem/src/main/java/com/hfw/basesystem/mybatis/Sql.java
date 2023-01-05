package com.hfw.basesystem.mybatis;
import lombok.Data;

@Data
public class Sql {
    public Sql(String sql) {
        this.sql = sql;
    }

    public Sql(String sql, Object p1) {
        this.sql = sql;
        this.p1 = p1;
    }

    public Sql(String sql, Object p1, Object p2) {
        this.sql = sql;
        this.p1 = p1;
        this.p2 = p2;
    }

    public Sql(String sql, Object p1, Object p2, Object p3) {
        this.sql = sql;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Sql(String sql, Object p1, Object p2, Object p3, Object p4) {
        this.sql = sql;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
    }

    public Sql(String sql, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.sql = sql;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
    }

    private String sql;
    private Object p1;
    private Object p2;
    private Object p3;
    private Object p4;
    private Object p5;
}
