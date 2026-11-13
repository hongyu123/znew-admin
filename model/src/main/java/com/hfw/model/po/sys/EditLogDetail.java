package com.hfw.model.po.sys;

import com.hfw.model.enums.sys.BaseEnum;
import com.hfw.model.utils.LocalDateUtil;
import com.hfw.model.utils.NumberUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 修改日志,详细到子表字段 修改前->修改后
 */
@Slf4j
@Getter
@Setter
//@Document("t_edit_log")
public class EditLogDetail {
    private Long id;
    // 标题
    private String title;
    // 编辑目标ID
    private Long targetId;
    // 字段记录
    private List<FieldLog> editFields;
    // 表格记录
    private List<TableLog> editTables;

    public record FieldLog(String field, String fieldName, String oldValue, String newValue, Boolean changed){
    }
    public record TableHead(String prop, String label){
    }
    public record TableLog(String tableName, List<TableHead> headers, List<Map<String,FieldLog>> values){
    }


    private static Map<Class<?>, List<Field>> LOG_FIELDS = new ConcurrentHashMap<>();
    private static Map<Class<?>, Field> ID_FIELDS = new ConcurrentHashMap<>();
    private List<Field> logFields(Class<?> cls){
        return LOG_FIELDS.computeIfAbsent(cls, c -> {
            List<Field> logFields = new ArrayList<>();
            Field[] fields = c.getDeclaredFields();
            for(Field field : fields) {
                field.setAccessible(true);
                EditLog anno = field.getAnnotation(EditLog.class);
                if (anno != null) {
                    logFields.add(field);
                }
            }
            return logFields;
        });
    }
    private String getFiledValue(Object fieldValue){
        if(fieldValue==null){
            return "";
        }
        if(fieldValue instanceof BaseEnum e){
            return e.getDesc();
        }
        if(fieldValue instanceof LocalDate date){
            return LocalDateUtil.format(date);
        }
        if(fieldValue instanceof LocalDateTime time){
            return LocalDateUtil.format(time);
        }
        if(fieldValue instanceof BigDecimal decimal){
            return NumberUtil.scale2(decimal);
        }
        return fieldValue.toString();
    }
    private Field idField(Class<?> cls){
        return ID_FIELDS.computeIfAbsent(cls, c -> {
            try {
                Field field = c.getDeclaredField("id");
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });
    }


    // 设置编辑的字段值和信息
    public List<FieldLog> fieldLog(Object obj, Object obj_new){
        if(obj==null || obj_new==null){
            return null;
        }
        List<Field> fields = this.logFields(obj.getClass());
        List<FieldLog> editFields = new ArrayList<>();
        for (Field field : fields){
            try {
                String oldValue = this.getFiledValue(field.get(obj));
                String newValue = this.getFiledValue(field.get(obj_new));
                EditLog anno = field.getAnnotation(EditLog.class);
                FieldLog fieldLog = new FieldLog(field.getName(), anno.value(), oldValue, newValue, !oldValue.equals(newValue));
                editFields.add(fieldLog);
            } catch (Exception e) {
                log.error("logEditFields", e);
            }
        }
        return editFields;
    }

    // 获取表格的一行数据
    private Map<String,FieldLog> tableValue(Object obj){
        Map<String,FieldLog> row = new HashMap<>();
        List<Field> fields = this.logFields(obj.getClass());
        for(Field field : fields){
            try {
                String newValue = this.getFiledValue(field.get(obj));
                EditLog anno = field.getAnnotation(EditLog.class);
                FieldLog fieldLog = new FieldLog(field.getName(), anno.value(), "", newValue, false);
                row.put(field.getName(), fieldLog);
            }catch (Exception e){
                log.error("tableValue", e);
            }
        }
        return row;
    }
    private Map<String,FieldLog> tableValue(Object obj, Object obj_new){
        Map<String,FieldLog> row = new HashMap<>();
        List<Field> fields = this.logFields(obj.getClass());
        for(Field field : fields){
            try {
                String oldValue = this.getFiledValue(field.get(obj));
                String newValue = this.getFiledValue(field.get(obj_new));
                EditLog anno = field.getAnnotation(EditLog.class);
                FieldLog fieldLog = new FieldLog(field.getName(), anno.value(), oldValue, newValue, !oldValue.equals(newValue));
                row.put(field.getName(), fieldLog);
            }catch (Exception e){
                log.error("tableValue", e);
            }
        }
        return row;
    }
    // 获取编辑的表格数据
    public <T> TableLog tableLog(String title, Class<T> cls, List<T> list, List<T> list_new){
        Map<Long,T> map = new HashMap<>();
        if(list!=null && !list.isEmpty()){
            for(T t : list){
                try {
                    Field idField = this.idField(cls);
                    Long id = (Long) idField.get(t);
                    map.put(id, t);
                }catch (Exception e){
                    log.error("idField", e);
                }
            }
        }

        List<Map<String,FieldLog>> values = new ArrayList<>();
        if(list_new!=null && !list_new.isEmpty()){
            for(T t : list_new){
                try {
                    Field idField = this.idField(cls);
                    Long id = (Long) idField.get(t);
                    T old = map.remove(id);
                    if(old == null){
                        // 新增
                        Map<String, FieldLog> row = this.tableValue(t);
                        row.put("_operate", new FieldLog("_operate", "操作类型", "", "add", false));
                        values.add(row);
                    }else{
                        // 编辑
                        Map<String, FieldLog> row = this.tableValue(old, t);
                        row.put("_operate", new FieldLog("_operate", "操作类型", "", "edit", false));
                        values.add(row);
                    }
                }catch (Exception e){
                    log.error("idField new", e);
                }
            }
        }
        // 删除
        for(T t : map.values()){
            Map<String, FieldLog> row = this.tableValue(t);
            row.put("_operate", new FieldLog("_operate", "操作类型", "", "del", false));
            values.add(row);
        }

        List<Field> logFields = this.logFields(cls);
        List<TableHead> fields = new ArrayList<>(logFields.size()+1);
        //fields.add(new TableHead("_operate", "类型"));
        logFields.forEach(f->fields.add(new TableHead(f.getName(), f.getAnnotation(EditLog.class).value())));
        return new TableLog(title, fields, values);
    }

}
