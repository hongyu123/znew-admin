package com.hfw.model.entity;

import cn.xbatis.core.mybatis.mapper.context.Pager;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页api接口返回实体
 * @author farkle
 * @date 2022-04-15
 */
@Getter @Setter
public class PageResult<T> {
    private int code=1;
    private String message="";
    private List<T> data;

    private Integer pageNumber = 1;
    private Integer pageSize = 10;
    private Integer total;

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
    public PageResult(Integer pageNumber,Integer pageSize, Integer total,List<T> data){
        this(pageNumber,pageSize);
        this.total = total;
        this.data = data;
    }

    public int start() {
        return (pageNumber-1) * pageSize;
    }
    public int rows() {
        return this.pageSize;
    }
    public int end(){
        return pageNumber*pageSize-1;
    }

    public static <T> PageResult<T> of(Pager<T> pager){
        return new PageResult<>(pager.getNumber(),pager.getSize(),pager.getTotal(),pager.getResults());
    }
    public static <T> PageResult<T> of(Page<T> page){
        return new PageResult<>(page.getPageNumber(),page.getPageSize(),page.getTotal(),page.getList());
    }

}
