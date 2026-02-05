package com.hfw.model.mybatis;

public interface Column<T> {
    default String columnName(){
        return null;
    }
}