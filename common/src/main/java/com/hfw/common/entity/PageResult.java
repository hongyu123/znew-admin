package com.hfw.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hfw.common.entity.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 分页api接口返回实体
 * @author zyh
 * @date 2022-04-15
 */
@Data
public class PageResult<T>{
    private int code=1;
    private String message="";
    private List<T> data;

    private Integer pageNumber = 1;
    private Integer pageSize = 10;
    private Long total;

    public PageResult(){
    }

    public PageResult(Integer pageNumber, Integer pageSize){
        if(pageNumber!=null && pageNumber>0){
            this.pageNumber = pageNumber;
        }
        if(pageSize!=null && pageSize>0){
            this.pageSize = pageSize;
        }
    }
    public PageResult(Integer pageNumber,Integer pageSize, Long total,List<T> data){
        this(pageNumber,pageSize);
        this.total = total;
        this.data = data;
    }

    @JsonIgnore
    public int getStart() {
        return (pageNumber-1) * pageSize;
    }
    @JsonIgnore
    public int getRows() {
        return this.pageSize;
    }
    @JsonIgnore
    public int getEnd(){
        return pageNumber*pageSize-1;
    }

    public PageResult(BaseEntity baseEntity){
        this(baseEntity.getPageNumber(), baseEntity.getPageSize());
    }

    /**
     * PageHelper开启分页
     */
    public void startPage(){
        PageHelper.startPage(this.pageNumber, this.pageSize);
    }
    public void startPageNoCount(){
        PageHelper.startPage(this.pageNumber, this.pageSize, false);
    }
    public void setList(List<T> list){
        if(list instanceof Page){
            Page<T> page = (Page<T>) list;
            this.total = page.getTotal();
            this.data = page.getResult();
        }else{
            this.data = list;
        }
    }
}
