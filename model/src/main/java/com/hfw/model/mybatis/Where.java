package com.hfw.model.mybatis;

import com.hfw.model.entity.Page;
import com.hfw.model.mybatis.anno.Sort;
import com.hfw.model.mybatis.anno.UpdateStrategy;
import lombok.Getter;

import java.util.*;

@Getter
public class Where<T> {
    public record Condition<T>(Column<T> column, Object value, String operator){
    }

    private List<Condition<T>> condList;
    private List<Order<T>> orderList;
    private Map<String,Object> params = new HashMap<>();
    private UpdateStrategy updateStrategy;
    private Column<?>[] nullUpdateColumns;
    private int limit;
    private int offset;

    public static <T> Where<T> where() {
        Where<T> where = new Where<>();
        where.condList = new ArrayList<>();
        return where;
    }
    public Where<T> eq(Column<T> column, Object val){
        condList.add(new Condition<>(column, val, "="));
        return this;
    }
    public Where<T> eq(boolean condition, Column<T> column, Object val){
        if(condition){
            condList.add(new Condition<>(column, val, "="));
        }
        return this;
    }
    public Where<T> ne(Column<T> column, Object val){
        condList.add(new Condition<>(column, val, "!="));
        return this;
    }

    public Where<T> gt(Column<T> column, Object val){
        condList.add(new Condition<>(column, val, ">"));
        return this;
    }
    public Where<T> ge(Column<T> column, Object val){
        condList.add(new Condition<>(column, val, ">="));
        return this;
    }
    public Where<T> ge(boolean condition, Column<T> column, Object val){
        if(condition){
            condList.add(new Condition<>(column, val, ">="));
        }
        return this;
    }
    public Where<T> lt(Column<T> column, Object val){
        condList.add(new Condition<>(column, val, "<"));
        return this;
    }
    public <V> Where<T> le(Column<T> column, Object val){
        condList.add(new Condition<>(column, val, "<="));
        return this;
    }
    public <V> Where<T> le(boolean condition, Column<T> column, Object val){
        if(condition){
            condList.add(new Condition<>(column, val, "<="));
        }
        return this;
    }

    public Where<T> like(Column<T> column, Object val){
        condList.add(new Condition<>(column, val, "like"));
        return this;
    }
    public Where<T> like(boolean condition, Column<T> column, Object val){
        if(condition){
            condList.add(new Condition<>(column, val, "like"));
        }
        return this;
    }
    public Where<T> notLike(Column<T> column, Object val){
        condList.add(new Condition<>(column, val, "not like"));
        return this;
    }

    public Where<T> isNull(Column<T> column, Object val){
        condList.add(new Condition<>(column, val, "is null"));
        return this;
    }

    public Where<T> in(Column<T> column, Collection<?> value){
        condList.add(new Condition<>(column, value, "in"));
        return this;
    }
    public Where<T> in(Column<T> column, Object... values){
        condList.add(new Condition<>(column, List.of(values), "in"));
        return this;
    }
    public Where<T> notIn(Column<T> column, Collection<?> value){
        condList.add(new Condition<>(column, value, "not in"));
        return this;
    }

    public Where<T> orderBy(Order<T> order){
        if(orderList==null){
            orderList = new ArrayList<>();
        }
        orderList.add(order);
        return this;
    }
    public Where<T> orderBy(Column<T> column){
        orderBy(Order.by(column, Sort.ASC));
        return this;
    }
    public Where<T> orderBy(Column<T> column, Sort sort){
        orderBy(Order.by(column, sort));
        return this;
    }
    public boolean orderBy(Page<T> page){
        boolean sort = page.sort();
        if(sort){
            orderBy(Order.by(new Column<>() {
                @Override
                public String columnName() {
                    return page.getSortByField();
                }
            }, page.getSortByWay()));
        }
        return sort;
    }

    @SafeVarargs
    public final Where<T> updateStrategy(UpdateStrategy updateStrategy, Column<T>... column){
        this.updateStrategy = updateStrategy;
        this.nullUpdateColumns = column;
        return this;
    }

    public Where<T> limit(int limit){
        this.limit = limit;
        return this;
    }
    public Where<T> offset(int offset){
        this.offset = offset;
        return this;
    }

}
