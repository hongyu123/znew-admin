package com.hfw.model.mybatis;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.utils.StrUtil;
import org.apache.ibatis.jdbc.SQL;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SqlHelper {
    private final static Map<Class<?>,TableInfo> TABLE_MAP = new ConcurrentHashMap<>();
    /** 表前缀 */
    private String tablePrefix = "";
    private String pkField = "id";
    private String logicDeleteField = "deleted";

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
    public void setPkField(String pkField) {
        this.pkField = pkField;
    }
    public void setLogicDeleteField(String logicDeleteField) {
        this.logicDeleteField = logicDeleteField;
    }

    private void resolveTableName(Class<?> tableClass, TableInfo tableInfo){
        //@Table注解获取
        Table table = tableClass.getAnnotation(Table.class);
        if(table!=null){
            tableInfo.setTableName(table.value());
            tableInfo.setSchema(table.schema());
            return;
        }
        //默认类名驼峰转下划线
        String tableName = StrUtil.humpToLine( StrUtil.lowerCase(tableClass.getSimpleName()) );
        tableInfo.setTableName(tableName);
    }
    private String getColumnName(Field field){
        TableField tableField = field.getAnnotation(TableField.class);
        if(tableField!=null){
            String value = tableField.value();
            if(value!=null && !value.isBlank()){
                return value;
            }
        }
        return StrUtil.humpToLine(field.getName());
    }

    private TableInfo resolveTable(Class<?> tableClass) throws Exception {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableClass(tableClass);
        tableInfo.setColumnMap(new LinkedHashMap<>());
        this.resolveTableName(tableClass, tableInfo);
        this.resolveColumns(tableInfo);
        if(tableInfo.getPk()==null){
            throw new SQLException(tableClass.getName()+"没有指定主键字段!");
        }
        return tableInfo;
    }
    private void resolveColumns(TableInfo tableInfo) throws Exception{
        Class<?> tableClass = tableInfo.getTableClass();
        Map<String, ColumnInfo> columnMap = tableInfo.getColumnMap();
        Map<String, Field> fieldsMap = new HashMap<>();
        Field[] fields = tableClass.getDeclaredFields();
        for (Field field : fields) {
            fieldsMap.put(field.getName(), field);
        }
        /*
        1.getClasses得到该类及其父类所有的public的内部类。
        2.getDeclaredClasses得到该类所有的内部类，除去父类的。
         */
        Class<?>[] innerClasses = tableClass.getDeclaredClasses();
        for(Class<?> innerClass : innerClasses){
            if("COLUMN".equals(innerClass.getSimpleName()) && innerClass.isEnum()){
                Method method = innerClass.getMethod("values");
                Enum<?>[] enums = (Enum<?>[])method.invoke(null);
                StringJoiner joiner = new StringJoiner(",");
                List<ColumnInfo> mapColumns  = new ArrayList<>();
                for (Enum<?> e : enums){
                    String fieldName  = e.name();
                    Field field = fieldsMap.get(fieldName);
                    if(field!=null){
                        field.setAccessible(true);
                    }else{
                        throw new SQLException("%s 枚举列[%s]没有对应的映射字段".formatted(innerClass.getName(), fieldName));
                    }
                    String columnName;
                    if(e instanceof Column<?> en && StrUtil.hasText(en.columnName())){
                        columnName = en.columnName();
                    }else if(field!=null){
                        columnName = this.getColumnName(field);
                    }else{
                        columnName = StrUtil.humpToLine(fieldName);
                    }
                    ColumnInfo columnInfo = new ColumnInfo();
                    columnInfo.setColumnName(columnName);
                    columnInfo.setFieldName(fieldName);
                    columnInfo.setField(field);
                    if(isPK(columnInfo)){
                        columnInfo.setPk(true);
                        tableInfo.setPk(columnInfo);
                    }else if(isTableLogic(columnInfo, tableInfo)){
                        columnInfo.setLogicDelete(true);
                        tableInfo.setLogicDelete(columnInfo);
                        dynamicFill(columnInfo);
                    }else {
                        dynamicFill(columnInfo);
                    }
                    columnMap.put(fieldName, columnInfo);
                    joiner.add(columnName);
                    if(!columnInfo.getColumnName().equals(StrUtil.humpToLine(columnInfo.getFieldName()))){
                        mapColumns.add(columnInfo);
                    }
                }
                MybatisGlobalConfig.registerDynamicResultMap(tableClass, mapColumns);
                tableInfo.setSelectColumns(joiner.toString());
                return;
            }
        }
    }
    private boolean isPK(ColumnInfo column){
        Field field = column.getField();
        if(field!=null){
            TableId tableId = field.getAnnotation(TableId.class);
            if(tableId!=null){
                column.setIdType(tableId.value());
                return true;
            }
        }
        return pkField.equals(column.getFieldName());
    }
    private boolean isTableLogic(ColumnInfo column, TableInfo tableInfo){
        Field field = column.getField();
        if(field!=null){
            LogicDelete logicDelete = field.getAnnotation(LogicDelete.class);
            if(logicDelete!=null){
                column.setBeforeValue(logicDelete.beforeValue());
                column.setDeletedValue(logicDelete.deletedValue());
                return true;
            }
        }
        return tableInfo.getLogicDelete()==null && logicDeleteField.equals(column.getFieldName());
    }
    private void dynamicFill(ColumnInfo column){
        Field field = column.getField();
        if(field==null){
            return;
        }
        TableField tableField = field.getAnnotation(TableField.class);
        if(tableField!=null){
            column.setInsertValue(tableField.insertValue());
            column.setUpdateValue(tableField.updateValue());
        }
    }

    private TableInfo getTableInfo(Class<?> tableClass) throws Exception {
        TableInfo tableInfo = TABLE_MAP.get(tableClass);
        if(tableInfo==null){
            tableInfo = this.resolveTable(tableClass);
            TABLE_MAP.put(tableClass, tableInfo);
        }
        return tableInfo;
    }
    private String columnName(Column<?> column, TableInfo tableInfo) throws SQLException {
        String columnName = null;
        if(column instanceof Enum<?> e){
            ColumnInfo columnInfo = tableInfo.getColumnMap().get(e.name());
            if(columnInfo!=null){
                columnName = columnInfo.getColumnName();
            }else{
                throw new SQLException("%s 枚举列[%s]没有对应的映射字段".formatted(tableInfo.getTableClass(), e.name()));
            }
        }
        if(columnName==null){
            columnName = column.columnName();
        }
        return columnName;
    }
    private String fieldName(Column<?> column) {
        String columnName = column.columnName();
        if(columnName!=null && !columnName.isBlank()){
            return columnName;
        }
        if(column instanceof Enum<?> e){
            columnName = e.name();
        }
        return columnName;
    }
    private void applyWhere(TableInfo tableInfo, SQL sql, Where<?> where, String whereParams) throws SQLException {
        Map<String, Object> params = where.getParams();
        List<? extends Where.Condition<?>> condList = where.getCondList();
        if(condList!=null && !condList.isEmpty()){
            for(Where.Condition<?> cond : condList){
                String columnName = this.columnName(cond.column(), tableInfo);
                params.put(columnName, cond.value());
                if(cond.operator().contains("in") && cond.value() instanceof Collection<?> values){
                    StringJoiner joiner = new StringJoiner(",");
                    for (int i = 0; i < values.size(); i++) {
                        joiner.add("#{%sparams.%s[%s]}".formatted(whereParams, columnName, i));
                    }
                    sql.WHERE("%s %s (%s)".formatted(columnName, cond.operator(), joiner.toString()));
                }else{
                    sql.WHERE("%s %s #{%sparams.%s}".formatted(columnName, cond.operator(), whereParams, columnName));
                }
            }
        }
    }

    public String selectByPk(Class<?> tableClass) throws Exception {
        TableInfo tableInfo = this.getTableInfo(tableClass);
        ColumnInfo pk = tableInfo.getPk();
        SQL sql = new SQL()
                .SELECT(tableInfo.getSelectColumns())
                .FROM(tableInfo.tableName())
                .WHERE("%s=#{pk}".formatted(pk.getColumnName()));
        ColumnInfo logicDelete = tableInfo.getLogicDelete();
        if(logicDelete!=null){
            sql.WHERE("%s=%s".formatted(logicDelete.getColumnName(), logicDelete.getBeforeValue()));
        }
        return sql.toString();
    }

    public String selectList(Class<?> tableClass, Where<?> where) throws Exception{
        TableInfo tableInfo = this.getTableInfo(tableClass);
        SQL sql = new SQL()
                .SELECT(tableInfo.getSelectColumns())
                .FROM(tableInfo.tableName());
        this.applyWhere(tableInfo, sql, where, "where.");
        ColumnInfo logicDelete = tableInfo.getLogicDelete();
        if(logicDelete!=null){
            sql.WHERE("%s=%s".formatted(logicDelete.getColumnName(), logicDelete.getBeforeValue()));
        }
        List<? extends Order<?>> orderList = where.getOrderList();
        if(orderList!=null && !orderList.isEmpty()){
            for (Order<?> order :  orderList){
                String columnName = this.columnName(order.column(), tableInfo);
                sql.ORDER_BY(columnName+" "+order.sort());
            }
        }
        if(where.getLimit()>0){
            sql.LIMIT(where.getLimit());
            if(where.getOffset()>0){
                sql.OFFSET(where.getOffset());
            }
        }
        return sql.toString();
    }

    public String count(Class<?> tableClass, Where<?> where) throws Exception{
        TableInfo tableInfo = this.getTableInfo(tableClass);
        SQL sql = new SQL()
                .SELECT("count(*)")
                .FROM(tableInfo.tableName());
        this.applyWhere(tableInfo, sql, where,"where.");
        ColumnInfo logicDelete = tableInfo.getLogicDelete();
        if(logicDelete!=null){
            sql.WHERE("%s=%s".formatted(logicDelete.getColumnName(), logicDelete.getBeforeValue()));
        }
        return sql.toString();
    }

    public String insert(Object entity) throws Exception {
        TableInfo tableInfo = this.getTableInfo(entity.getClass());
        SQL sql = new SQL()
                .INSERT_INTO(tableInfo.tableName());
        Collection<ColumnInfo> columnList = tableInfo.getColumnMap().values();
        for(ColumnInfo column : columnList){
            Field field = column.getField();
            if(field!=null){
                if(field.get(entity)!=null){
                    sql.INTO_COLUMNS(column.getColumnName());
                    sql.INTO_VALUES("#{%s}".formatted(column.getFieldName()));
                }else if(column.insertFill()){
                    sql.INTO_COLUMNS(column.getColumnName());
                    String insertValue = column.getInsertValue();
                    if(insertValue.startsWith("$")){
                        field.set(entity, MybatisGlobalConfig.getDynamic(insertValue, field.getType()));
                        sql.INTO_VALUES("#{%s}".formatted(column.getFieldName()));
                    }else {
                        sql.INTO_VALUES(insertValue);
                    }
                }
            }else{
                throw new SQLException("%s 枚举列[%s]没有对应的映射字段".formatted(tableInfo.getTableClass(), column.getFieldName()));
            }
        }
        return sql.toString();
    }

    public String insertBatch(List<?> list) throws Exception{
        Object entity = list.get(0);
        TableInfo tableInfo = this.getTableInfo(entity.getClass());
        SQL sql = new SQL()
                .INSERT_INTO(tableInfo.tableName());
        StringJoiner joiner = new StringJoiner(",");
        Collection<ColumnInfo> columnList = tableInfo.getColumnMap().values();
        Map<Field,Object> insertFills = new HashMap<>();
        for(ColumnInfo column : columnList){
            if(!column.getPk() || IdType.INPUT==column.getIdType()){
                Field field = column.getField();
                if(field!=null){
                    sql.INTO_COLUMNS(column.getColumnName());
                    if(column.insertFill()){
                        String insertValue = column.getInsertValue();
                        if(insertValue.startsWith("$")){
                            joiner.add("#{list[0].%s}".formatted(column.getFieldName()));
                            insertFills.put(field, MybatisGlobalConfig.getDynamic(insertValue, field.getType()));
                        }else{
                            joiner.add(insertValue);
                        }
                    }else{
                        joiner.add("#{list[0].%s}".formatted(column.getFieldName()));
                    }
                }else{
                    throw new SQLException("%s 枚举列[%s]没有对应的映射字段".formatted(tableInfo.getTableClass(), column.getFieldName()));
                }
            }
        }
        String values = joiner.toString();
        StringBuilder sqlBuilder = new StringBuilder(sql.toString());
        sqlBuilder.append(" VALUES ");
        sqlBuilder.append("(").append(values).append(")");
        Set<Map.Entry<Field, Object>> entrySet = insertFills.entrySet();
        for (int i = 1; i < list.size(); i++) {
            for (Map.Entry<Field, Object> entry : entrySet){
                entry.getKey().set(list.get(i), entry.getValue());
            }
            String v = values.replaceAll("\\[0]", "[" + i + "]");
            sqlBuilder.append(" ,(").append(v).append(")");
        }
        return sqlBuilder.toString();
    }

    private SQL update(TableInfo tableInfo, Object entity, UpdateStrategy updateStrategy, Set<String> nullUpdateFields) throws Exception{
        if(updateStrategy==null){
            updateStrategy = UpdateStrategy.NotEmpty;
        }
        SQL sql = new SQL()
                .UPDATE(tableInfo.tableName());
        Collection<ColumnInfo> columnList = tableInfo.getColumnMap().values();
        boolean set = false;
        for(ColumnInfo column : columnList) {
            if(!column.getPk()){
                Field field = column.getField();
                if(field!=null) {
                    Object value = field.get(entity);
                    if (value!=null && !(UpdateStrategy.NotEmpty==updateStrategy && value instanceof CharSequence str && str.isEmpty()) ) {
                        sql.SET("%s=#{entity.%s}".formatted(column.getColumnName(), column.getFieldName()));
                        set = true;
                    }else if(column.updateFill()){
                        field.set(entity, MybatisGlobalConfig.getDynamic(column.getUpdateValue(), field.getType()));
                        sql.SET("%s=#{entity.%s}".formatted(column.getColumnName(), column.getFieldName()));
                        set = true;
                    }else if(UpdateStrategy.NullUpdate==updateStrategy && nullUpdateFields.contains(column.getFieldName())){
                        sql.SET("%s=NULL".formatted(column.getColumnName()));
                        set = true;
                    }
                }else{
                    throw new SQLException("%s 枚举列[%s]没有对应的映射字段".formatted(tableInfo.getTableClass(), column.getFieldName()));
                }

            }
        }
        if(!set){
            ColumnInfo pk = tableInfo.getPk();
            sql.SET("%s=%s".formatted(pk.getColumnName(), pk.getColumnName()));
        }
        return sql;
    }
    private Set<String> nullUpdateFields(Column<?>[] nullUpdateColumns){
        Set<String> nullUpdateFields = new HashSet<>();
        if(nullUpdateColumns!=null && nullUpdateColumns.length>0){
            for(Column<?> column : nullUpdateColumns){
                nullUpdateFields.add( this.fieldName(column) );
            }
        }
        return nullUpdateFields;
    }
    public String updateByPk(Object entity, UpdateStrategy updateStrategy, Column<?>[] nullUpdateColumns) throws Exception {
        TableInfo tableInfo = this.getTableInfo(entity.getClass());
        SQL sql = this.update(tableInfo, entity, updateStrategy, this.nullUpdateFields(nullUpdateColumns));
        ColumnInfo pk = tableInfo.getPk();
        sql.WHERE("%s=#{entity.%s}".formatted(pk.getColumnName(), pk.getFieldName()));
        ColumnInfo logicDelete = tableInfo.getLogicDelete();
        if(logicDelete!=null){
            sql.WHERE("%s=%s".formatted(logicDelete.getColumnName(), logicDelete.getBeforeValue()));
        }
        return sql.toString();
    }

    public String update(Object entity, Where<?> where) throws Exception {
        TableInfo tableInfo = this.getTableInfo(entity.getClass());
        SQL sql = this.update(tableInfo, entity, where.getUpdateStrategy(), this.nullUpdateFields(where.getNullUpdateColumns()));
        this.applyWhere(tableInfo, sql, where, "where.");
        ColumnInfo logicDelete = tableInfo.getLogicDelete();
        if(logicDelete!=null){
            sql.WHERE("%s=%s".formatted(logicDelete.getColumnName(), logicDelete.getBeforeValue()));
        }
        return sql.toString();
    }

    public String deleteByPk(Class<?> tableClass) throws Exception {
        TableInfo tableInfo = this.getTableInfo(tableClass);
        ColumnInfo pk = tableInfo.getPk();
        ColumnInfo logicDelete = tableInfo.getLogicDelete();
        if(logicDelete!=null){
            SQL sql = new SQL()
                    .UPDATE(tableInfo.tableName())
                    .SET("%s=%s".formatted(logicDelete.getColumnName(), logicDelete.getDeletedValue()))
                    .WHERE("%s=#{pk}".formatted(pk.getColumnName()));
            sql.WHERE("%s=%s".formatted(logicDelete.getColumnName(), logicDelete.getBeforeValue()));
            return sql.toString();
        }
        SQL sql = new SQL()
                .DELETE_FROM(tableInfo.tableName())
                .WHERE("%s=#{pk}".formatted(pk.getColumnName()));
        return sql.toString();
    }

    public String deleteByPks(Class<?> tableClass, Collection<? extends Serializable> pks) throws Exception {
        TableInfo tableInfo = this.getTableInfo(tableClass);
        ColumnInfo pk = tableInfo.getPk();
        StringJoiner joiner = new StringJoiner(",");
        for (int i = 0; i < pks.size(); i++) {
            joiner.add("#{pks[%s]}".formatted(i));
        }
        ColumnInfo logicDelete = tableInfo.getLogicDelete();
        if(logicDelete!=null){
            SQL sql = new SQL()
                    .UPDATE(tableInfo.tableName())
                    .SET("%s=%s".formatted(logicDelete.getColumnName(), logicDelete.getDeletedValue()))
                    .WHERE("%s in (%s)".formatted(pk.getColumnName(), joiner.toString()));
            sql.WHERE("%s=%s".formatted(logicDelete.getColumnName(), logicDelete.getBeforeValue()));
            return sql.toString();
        }
        SQL sql = new SQL()
                .DELETE_FROM(tableInfo.tableName())
                .WHERE("%s in (%s)".formatted(pk.getColumnName(), joiner.toString()));
        return sql.toString();
    }

    public String delete(Class<?> tableClass, Where<?> where) throws Exception{
        TableInfo tableInfo = this.getTableInfo(tableClass);
        ColumnInfo logicDelete = tableInfo.getLogicDelete();
        if(logicDelete!=null){
            SQL sql = new SQL()
                    .UPDATE(tableInfo.tableName())
                    .SET("%s=%s".formatted(logicDelete.getColumnName(), logicDelete.getDeletedValue()));
            this.applyWhere(tableInfo, sql, where, "where.");
            sql.WHERE("%s=%s".formatted(logicDelete.getColumnName(), logicDelete.getBeforeValue()));
            return sql.toString();
        }
        SQL sql = new SQL()
                .DELETE_FROM(tableInfo.tableName());
        this.applyWhere(tableInfo, sql, where, "where.");
        return sql.toString();
    }

}
