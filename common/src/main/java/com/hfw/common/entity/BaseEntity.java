package com.hfw.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hfw.common.enums.SortByWay;

import java.io.Serializable;

/**
 * 基础实体类
 * @author farkle
 * @date 2022-04-26
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private int pageNumber = 1;
    @JsonIgnore
    private int pageSize = 10;
    public BaseEntity setPageNumber(int pageNumber) {
        if(pageNumber>0){
            this.pageNumber = pageNumber;
        }
        return this;
    }
    public BaseEntity setPageSize(int pageSize) {
        if(pageSize>0){
            this.pageSize = pageSize;
        }
        return this;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    public int getPageSize() {
        return pageSize;
    }

    @JsonIgnore
    public int getStart() {
        return (pageNumber-1) * pageSize;
    }
    @JsonIgnore
    public int getRows() {
        return this.pageSize;
    }

    /** 排序字段 */
    @JsonIgnore
    private String sortByField;

    /** 排序方式 */
    @JsonIgnore
    private SortByWay sortByWay;

    public String getSortByField() {
        return sortByField;
    }
    public BaseEntity setSortByField(String sortByField) {
        this.sortByField = sortByField;
        return this;
    }
    public SortByWay getSortByWay() {
        return sortByWay;
    }
    public BaseEntity setSortByWay(SortByWay sortByWay) {
        this.sortByWay = sortByWay;
        return this;
    }
}
