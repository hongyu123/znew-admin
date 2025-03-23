package com.hfw.model.entity;

import cn.xbatis.page.IPager;
import cn.xbatis.page.PagerField;
import com.hfw.model.enums.sys.SortByWay;
import com.hfw.model.utils.LimitedParamMap;

import java.util.List;
import java.util.Map;

public class Page<T> implements IPager<T> {
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
    private SortByWay sortByWay;

    public String getSortByField() {
        return sortByField;
    }
    public Page<T> setSortByField(String sortByField) {
        this.sortByField = sortByField;
        return this;
    }
    public SortByWay getSortByWay() {
        return sortByWay;
    }
    public Page<T> setSortByWay(SortByWay sortByWay) {
        this.sortByWay = sortByWay;
        return this;
    }
    public void sort(SortByWay sortByWay, String sortByField){
        this.sortByWay = sortByWay;
        this.sortByField = sortByField;
    }

    public enum ExecuteType{
        Page, NoneCount, NonePage;
    }
    private ExecuteType executeType = ExecuteType.Page;
    public static <T> Page<T> of(int pageNumber, int pageSize){
        Page<T> page = new Page<>();
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        return page;
    }
    public static <T> Page<T> of(int pageNumber, int pageSize, ExecuteType executeType){
        Page<T> page = new Page<>();
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        page.executeType = executeType;
        return page;
    }
    public static <T> Page<T> nonePage(){
        Page<T> page = new Page<>();
        page.executeType = ExecuteType.NonePage;
        return page;
    }

    private Map<String,String> params = new LimitedParamMap(20);
    public Map<String, String> getParams() {
        return params;
    }
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    private Integer total;
    private List<T> list;
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public <V> V get(PagerField<V> field) {
        if (PagerField.IS_EXECUTE_COUNT == field) {
            return (V) (Boolean)(ExecuteType.Page == this.executeType);
        }
        if (PagerField.NUMBER == field) {
            return (V) (Integer)(this.pageNumber);
        }
        if (PagerField.SIZE == field) {
            return (V) (Integer)(ExecuteType.NonePage == this.executeType  ? 10000 : this.pageSize);
        }
        throw new RuntimeException("not support field: " + field);
    }
    @Override
    public <V> void set(PagerField<V> field, V value) {
        if (PagerField.TOTAL == field) {
            this.total = (Integer) value;
            return;
        }
        if (PagerField.RESULTS == field) {
            this.list = (List<T>) value;
            return;
        }
        throw new RuntimeException("not support field: " + field);
    }

}
