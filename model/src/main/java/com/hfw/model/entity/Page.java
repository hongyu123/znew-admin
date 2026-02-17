package com.hfw.model.entity;

import com.github.pagehelper.PageHelper;
import com.hfw.model.mybatis.anno.Sort;
import com.hfw.model.utils.LimitedParamMap;
import com.hfw.model.utils.StrUtil;

import java.util.Map;

public class Page<T> {
    private int pageNumber = 1;
    private int pageSize = 10;
    public Page<T> setPageNumber(int pageNumber) {
        if(pageNumber>0){
            this.pageNumber = pageNumber;
        }
        return this;
    }
    public Page<T> setPageSize(int pageSize) {
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

    public int getStart() {
        return (pageNumber-1) * pageSize;
    }
    public int getRows() {
        return this.pageSize;
    }

    /** 排序字段 */
    private String sortByField;
    /** 排序方式 */
    private Sort sortByWay;

    public String getSortByField() {
        return sortByField;
    }
    public Page<T> setSortByField(String sortByField) {
        this.sortByField = sortByField;
        return this;
    }
    public Sort getSortByWay() {
        return sortByWay;
    }
    public Page<T> setSortByWay(Sort sortByWay) {
        this.sortByWay = sortByWay;
        return this;
    }
    public boolean sort(){
        return this.sortByWay!=null && StrUtil.hasText(this.sortByField);
    }

    private boolean count = true;
    public static <T> Page<T> of(int pageNumber, int pageSize){
        Page<T> page = new Page<>();
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        return page;
    }
    public static <T> Page<T> of(int pageNumber, int pageSize, boolean count){
        Page<T> page = new Page<>();
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        page.count = count;
        return page;
    }
    public static <T> Page<T> noPage(){
        Page<T> page = new Page<>();
        page.count = false;
        page.pageSize = -1;
        return page;
    }

    private Map<String,String> params = new LimitedParamMap(20);
    public Map<String, String> getParams() {
        return params;
    }
    public void setParams(Map<String, String> params) {
        this.params = params;
    }


    /**
     * PageHelper开启分页
     */
    public void startPage(){
        PageHelper.startPage(this.pageNumber, this.pageSize, this.count);
    }

}
