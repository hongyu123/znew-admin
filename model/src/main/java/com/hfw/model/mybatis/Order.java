package com.hfw.model.mybatis;


import com.hfw.model.mybatis.anno.Sort;

public record Order<T>(Column<T> column, Sort sort){
    public static <T> Order<T> by(Column<T> column, Sort sort){
        return new Order<>(column, sort);
    }
    public static <T> Order<T> by(Column<T> column){
        return new Order<>(column, Sort.ASC);
    }
    public static <T> Order<T> asc(Column<T> column){
        return new Order<>(column, Sort.ASC);
    }
    public static <T> Order<T> desc(Column<T> column){
        return new Order<>(column, Sort.DESC);
    }
}