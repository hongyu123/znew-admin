package com.hfw.model.entity;

import com.github.pagehelper.Page;
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
    private long total;

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
    public PageResult(Integer pageNumber,Integer pageSize, long total,List<T> data){
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

    public static <T> PageResult<T> of(List<T> list){
        if(list instanceof Page<T> page){
            return new PageResult<>(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getResult());
        }
        return new PageResult<>(0,0,0,list);
    }

}
