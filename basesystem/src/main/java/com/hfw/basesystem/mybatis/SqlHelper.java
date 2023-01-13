package com.hfw.basesystem.mybatis;

import com.hfw.common.support.GeneralException;
import com.hfw.common.util.StrUtil;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author farkle
 * @date 2022-06-14
 */
public class SqlHelper {
    /** 表名缓存 */
    private static ConcurrentHashMap<Class,String> TABLEMAP = new ConcurrentHashMap<>();
    /** 主键名缓存 */
    private static ConcurrentHashMap<Class,FieldEntry> PKMAP = new ConcurrentHashMap<>();
    /**
     * 逻辑字段缓存
     * 逻辑字段可能为空
     */
    private static ConcurrentHashMap<Class,FieldEntry> LOGICMAP = new ConcurrentHashMap<>();

    /**
     * select sql缓存
     * 类名+.+表别名 做key, sql做value
     */
    private static ConcurrentHashMap<String,String> SELECTSQLMAP = new ConcurrentHashMap<>();
    /**
     * where sql缓存
     * 类名+"."+条件对象toString值做key, sql做value
     */
    private static ConcurrentHashMap<String,String> WHERESQLMAP = new ConcurrentHashMap<>();
    /** insert sqlnode缓存 */
    private static ConcurrentHashMap<Class,MixedSqlNode> INSERT_SQLNODE_MAP = new ConcurrentHashMap<>();
    /**
     * update sqlNode缓存
     * 类名+.+表别名 做key
     */
    private static ConcurrentHashMap<String,MixedSqlNode> UPDATE_SQLNODE_MAP = new ConcurrentHashMap<>();
    /**
     * update all fields sqlNode缓存
     * 类名+.+表别名 做key
     */
    private static ConcurrentHashMap<String,MixedSqlNode> UPDATEALL_SQLNODE_MAP = new ConcurrentHashMap<>();
    /**
     * delete batch sqlNode缓存
     * 类名+.+表别名 做key
     */
    private static ConcurrentHashMap<String,MixedSqlNode> DELETEBATCH_SQLNODE_MAP = new ConcurrentHashMap<>();

    /**
     * where id=#{cond.id} 缓存
     */
    private static ConcurrentHashMap<String,String> COND_SQL_MAP = new ConcurrentHashMap<>();
    /**
     * set id=#{bean.id} 缓存
     */
    private static ConcurrentHashMap<String,String> COND_UPDATE_SQL_MAP = new ConcurrentHashMap<>();


    /** 表前缀 */
    private String tablePrefix = "";
    /** 表别名 */
    private String tableAlias = "";

    /** 逻辑删除默认字段名 */
    private String logicDeleteField = "delete_flag";
    /** 逻辑删除默认值 */
    private Object deletedValue = 1;
    /** 逻辑未删除默认值 */
    private Object notDeletedValue = 0;

    /**
     * 获取表名Entry
     * @param clazz
     * @return
     */
    public TableEntry getTableEntry(Class clazz){
        //缓存中获取
        String tableName = TABLEMAP.get(clazz);
        if(tableName!=null){
            return new TableEntry(tableName, this.getTableAlias());
        }
        //@Table注解获取
        Table table = (Table) clazz.getAnnotation(Table.class);
        if(table!=null){
            return new TableEntry(table.name(), this.getTableAlias());
        }
        //默认类名驼峰转下划线
        tableName = StrUtil.humpToLine( StrUtil.lowerCase(clazz.getSimpleName()) );
        if(StrUtil.hasText(this.tablePrefix) && !tableName.startsWith(this.tablePrefix)){
            tableName = this.tablePrefix+"_"+tableName;
        }
        return new TableEntry(tableName, this.getTableAlias());
    }
    /**
     * 获取表名+别名
     * @param clazz
     * @return 表名+空格+别名
     */
    public String getTableNameWithAlias(Class clazz){
        TableEntry tableEntry = this.getTableEntry(clazz);
        return tableEntry.name+" "+tableEntry.alias;
    }

    /**
     * 获取主键字段Entry
     * @param clazz
     * @return
     */
    public FieldEntry getPkEntry(Class clazz){
        // 缓存中获取
        FieldEntry pkEntry = PKMAP.get(clazz);
        if(pkEntry!=null){
            return pkEntry;
        }
        // 遍历属性获取
        Field pkField = null;
        Field idField = null;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            if(id!=null){
                pkField = field;
                break;
            }
            if("id".equals(field.getName())){
                pkField = field;
            }
        }
        if(pkField==null){
            pkField = idField;
        }
        if(pkField==null){
            throw new GeneralException(clazz.getName()+"没有指定主键字段!");
        }
        String pkColumn = this.getColumnName(pkField);
        pkEntry = new FieldEntry(pkColumn, pkField);
        PKMAP.put(clazz,pkEntry);
        return pkEntry;
    }
    /**
     * 获取表主键字段名
     * @param clazz
     * @return 表别名+.+表主键字段名
     */
    public String getPkColumnWithAlias(Class clazz){
        FieldEntry pkEntry = this.getPkEntry(clazz);
        return this.getTableAliasWithDot()+pkEntry.getColumn();
    }

    /**
     * 获取逻辑字段Entry
     * @param clazz
     * @return 可能返回空对象FieldEntry
     */
    public FieldEntry getLogicEntry(Class clazz){
        FieldEntry logicEntry = LOGICMAP.get(clazz);
        if(logicEntry!=null){
            return logicEntry;
        }
        Field logicField = null;
        String logicColumn = null;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if(isLogic(field)){
                logicField = field;
                break;
            }
        }
        if(logicField!=null){
            logicColumn = this.getColumnName(logicField);
        }
        logicEntry = new FieldEntry(logicColumn, logicField);
        LOGICMAP.put(clazz,logicEntry);
        return logicEntry;
    }
    /**
     * 根据类型获取逻辑字段的where条件
     * @param clazz
     * @return 逻辑字段名=逻辑字段值,如果没有逻辑字段返回null
     */
    public String getLogicCond(Class clazz){
        FieldEntry logicEntry = this.getLogicEntry(clazz);
        if(logicEntry.getColumn()==null){
            return null;
        }
        return this.getTableAliasWithDot()+logicEntry.getColumn()+"="+notDeletedValue;
    }


    //获取字段别名
    private String getTableAlias(){
        return this.tableAlias;
    }
    //获取字段别名+点
    private String getTableAliasWithDot(){
        if(StrUtil.hasText(this.tableAlias)){
            return this.tableAlias+".";
        }
        return "";
    }
    //获取数据库字段名
    private String getColumnName(Field field){
        Column column = field.getAnnotation(Column.class);
        if(column!=null){
            return column.name();
        }
        return StrUtil.humpToLine(field.getName());
    }
    /**
     * 判断一个字段是否是逻辑删除字段
     * @param field
     * @return
     */
    private boolean isLogic(Field field){
        if( logicDeleteField.equals(field.getName()) ){
            return true;
        }
        FieldLogic fieldLogic = field.getAnnotation(FieldLogic.class);
        return fieldLogic!=null;
    }
    /**
     * 判断一个字段是否忽略
     * @param field
     * @return
     */
    private boolean isIgnore(Field field){
        FieldIgnore fieldIgnore = field.getAnnotation(FieldIgnore.class);
        return fieldIgnore!=null;
    }


    /**
     * 获取select sql
     * @param clazz
     * @return t.name,t.age ...
     */
    public String getSelectSql(Class clazz){
        String key = clazz.getName()+"."+this.getTableAlias();
        String selectSql = SELECTSQLMAP.get(key);
        if(selectSql!=null){
            return selectSql;
        }
        selectSql = "";
        StringBuilder sb = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        if(fields==null || fields.length<=0){
            return selectSql;
        }
        String tableAliasWithDot = this.getTableAliasWithDot();
        for (Field field : fields){
            if(isIgnore(field)){
                continue;
            }
            sb.append(tableAliasWithDot+getColumnName(field)+",");
        }
        if(sb.length()>0){
            selectSql = sb.substring(0,sb.length()-1);
        }
        SELECTSQLMAP.put(key, selectSql);
        return selectSql;
    }


    /**
     * 根据对象获取where sql
     * @param t
     * @return name=#{name} and ..., 如果字段值都为空返回空串
     * @throws IllegalAccessException
     */
    public String getWhereSql(Object t) throws IllegalAccessException {
        if(t==null){
            return null;
        }
        String key = t.getClass().getName()+"."+t.toString();
        String whereSql = WHERESQLMAP.get(key);
        if(whereSql!=null){
            return whereSql;
        }
        Field[] fields = t.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        String tableAliasWithDot = this.getTableAliasWithDot();
        for (Field field : fields){
            String columnName = getColumnName(field);
            //逻辑字段
            if(this.isLogic(field)){
                sb.append("and "+tableAliasWithDot+ columnName+"="+notDeletedValue+" ");
                continue;
            }
            if(this.isIgnore(field)){
                continue;
            }
            field.setAccessible(true);
            if(field.get(t)!=null){
                sb.append("and "+tableAliasWithDot+ columnName+"=#{"+field.getName()+"} ");
            }
        }
        whereSql = "";
        if(sb.length()>0){
            whereSql = sb.substring(4,sb.length()-1);
        }
        WHERESQLMAP.put(key,whereSql);
        return whereSql;
    }
    public <T> String getWhereSql(Condition<T> condition, Class<T> clazz, Map<String,Object> params) throws Exception {
        StringBuilder sb = new StringBuilder();
        List<Condition<T>.Entry> condList = condition.getCondList();
        Field logicField = null;
        String tableAliasWithDot = this.getTableAliasWithDot();
        for(Condition<T>.Entry entry : condList){
            String attrName = StrUtil.lowerCase(entry.getFn().getMethodName().substring(3));
            Field field = clazz.getDeclaredField(attrName);
            //逻辑删除字段
            if(this.isLogic(field)){
                logicField = field;
                continue;
            }
            if(this.isIgnore(field)){
                continue;
            }
            params.put(attrName, entry.getValue());
            String columnName = this.getColumnName(field);
            sb.append("and "+tableAliasWithDot+ columnName+"=#{"+attrName+"} ");
        }
        //逻辑删除字段处理
        if(logicField==null){
            logicField = this.getLogicEntry(clazz).field;
        }
        if(logicField!=null){
            String columnName = this.getColumnName(logicField);
            sb.append("and "+tableAliasWithDot+ columnName+"="+notDeletedValue+" ");
        }
        String whereSql = "";
        if(sb.length()>0){
            whereSql = sb.substring(4,sb.length()-1);
        }
        return whereSql;
    }

    /**
     * 获取 insert sqlNode
     * @param t
     * @param configuration
     * @return
     * @throws IllegalAccessException
     */
    public MixedSqlNode getInsertSqlNode(Configuration configuration, Object t) throws IllegalAccessException {
        Class clazz = t.getClass();
        //逻辑删除字段设置默认值
        FieldEntry logicEntry = this.getLogicEntry(clazz);
        Field logicField = logicEntry.getField();
        if(logicField!=null){
            logicField.setAccessible(true);
            logicField.set(t,notDeletedValue);
        }
        //从缓存中获取
        MixedSqlNode mixedSqlNode = INSERT_SQLNODE_MAP.get(clazz);
        if(mixedSqlNode!=null){
            return mixedSqlNode;
        }
        List<SqlNode> context = new ArrayList<>();
        String insertTable = String.format("insert into %s(", this.getTableEntry(clazz).getName() );
        context.add( new StaticTextSqlNode(insertTable) );

        //设置sqlNode
        //<if test="name!=null">name,</if> ...
        //<if test="name!=null">#{name},</if> ...
        List<SqlNode> trimContext = new ArrayList<>();
        List<SqlNode> valuesTrimContext = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if(this.isIgnore(field)){
                continue;
            }
            String fieldName = field.getName();
            String columnName = this.getColumnName(field);
            trimContext.add( new IfSqlNode(new StaticTextSqlNode(columnName+","),fieldName+"!=null") );
            valuesTrimContext.add( new IfSqlNode(new StaticTextSqlNode("#{"+fieldName+"},"),fieldName+"!=null") );
        }
        TrimSqlNode trimSqlNode = new TrimSqlNode(configuration,new MixedSqlNode(trimContext),null,null,null,",");
        context.add(trimSqlNode);
        context.add(new StaticTextSqlNode(") values("));
        trimSqlNode = new TrimSqlNode(configuration,new MixedSqlNode(valuesTrimContext),null,null,null,",");
        context.add(trimSqlNode);
        context.add( new StaticTextSqlNode(")") );
        mixedSqlNode = new MixedSqlNode(context);
        INSERT_SQLNODE_MAP.put(clazz, mixedSqlNode);
        return mixedSqlNode;
    }

    /**
     * 获取 动态更新的sqlNode, 根据pk字段更新
     * @param configuration
     * @return
     */
    public MixedSqlNode getUpdateSqlNode(Configuration configuration, Object t) throws IllegalAccessException {
        Class clazz = t.getClass();
        String key = clazz.getName()+"."+this.getTableAlias();
        MixedSqlNode mixedSqlNode = UPDATE_SQLNODE_MAP.get(key);
        if(mixedSqlNode!=null){
            return mixedSqlNode;
        }
        List<SqlNode> context = new ArrayList<>();
        String update = "update "+this.getTableNameWithAlias(clazz)+" set ";
        context.add( new StaticTextSqlNode(update) );

        //<if test="name!=null">a.name=#{name},</if> ...
        List<SqlNode> setContext = new ArrayList<>();
        String tableAliasWithDot = this.getTableAliasWithDot();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if(this.isIgnore(field)){
                continue;
            }
            String fieldName = field.getName();
            String columnName = tableAliasWithDot+ this.getColumnName(field);
            setContext.add( new IfSqlNode(new StaticTextSqlNode(columnName+"=#{"+fieldName+"},"),fieldName+"!=null") );
        }
        TrimSqlNode trimSqlNode = new TrimSqlNode(configuration,new MixedSqlNode(setContext),null,null,null,",");
        context.add(trimSqlNode);

        // where a.id=#{id}
        FieldEntry pkEntry = getPkEntry(clazz);
        pkEntry.getField().setAccessible(true);
        if(pkEntry.getField().get(t)==null){
            throw new GeneralException(clazz.getName()+"主键字段值不能为空!");
        }
        String pkColumn = tableAliasWithDot+ pkEntry.getColumn();
        String where = " where "+pkColumn+"=#{"+pkEntry.getField().getName()+"}";
        context.add( new StaticTextSqlNode(where) );
        mixedSqlNode = new MixedSqlNode(context);
        UPDATE_SQLNODE_MAP.put(key,mixedSqlNode);
        return mixedSqlNode;
    }

    /**
     * 获取 所有字段更新的sqlNode, 根据pk字段更新
     * @return
     */
    public MixedSqlNode getUpdateAllSqlNode(Object t) throws IllegalAccessException {
        Class clazz = t.getClass();
        String key = clazz.getName()+"."+this.getTableAlias();
        MixedSqlNode mixedSqlNode = UPDATEALL_SQLNODE_MAP.get(key);
        if(mixedSqlNode!=null){
            return mixedSqlNode;
        }
        List<SqlNode> context = new ArrayList<>();
        String update = "update "+this.getTableNameWithAlias(clazz)+" set ";
        context.add( new StaticTextSqlNode(update) );

        //a.name=#{name}, ...
        boolean hasTableAlias = StrUtil.hasText(tableAlias);
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = new ArrayList<>();
        for (Field field : fields){
            if(!this.isIgnore(field) && !this.isLogic(field)){
                fieldList.add(field);
            }
        }
        if(fieldList.size()>0){
            for (int i = 0; i < fieldList.size()-1; i++) {
                Field field = fieldList.get(i);
                String columnName = "";
                if(hasTableAlias){
                    columnName += this.tableAlias+".";
                }
                columnName += this.getColumnName(field);
                context.add( new StaticTextSqlNode(columnName+"=#{"+field.getName()+"},") );
            }
            Field lastField = fieldList.get(fieldList.size()-1);
            String columnName = "";
            if(hasTableAlias){
                columnName += this.tableAlias+".";
            }
            columnName += this.getColumnName(lastField);
            context.add( new StaticTextSqlNode(columnName+"=#{"+lastField.getName()+"}") );
        }

        // where a.id=#{id}
        FieldEntry pkEntry = getPkEntry(clazz);
        pkEntry.getField().setAccessible(true);
        if(pkEntry.getField().get(t)==null){
            throw new GeneralException(clazz.getName()+"主键字段值不能为空!");
        }
        String pkColumn = hasTableAlias ?this.tableAlias+"."+pkEntry.getColumn() :pkEntry.getColumn();
        String where = " where "+pkColumn+"=#{"+pkEntry.getField().getName()+"}";
        context.add( new StaticTextSqlNode(where) );
        mixedSqlNode = new MixedSqlNode(context);
        UPDATEALL_SQLNODE_MAP.put(key,mixedSqlNode);
        return mixedSqlNode;
    }

    /**
     * 获取批量删除的sqlNode
     * @param configuration
     * @param clazz
     * @return
     */
    public MixedSqlNode getDeleteBatchSqlNode(Configuration configuration, Class clazz){
        //判断逻辑批量删除
        FieldEntry logicEntry = this.getLogicEntry(clazz);
        if(logicEntry.getColumn()!=null){
            return this.getLogicDeleteBatchSqlNode(configuration,clazz);
        }
        //物理批量删除
        String key = clazz.getName()+"."+this.getTableAlias();
        MixedSqlNode mixedSqlNode = DELETEBATCH_SQLNODE_MAP.get(key);
        if(mixedSqlNode!=null){
            return mixedSqlNode;
        }
        List<SqlNode> context = new ArrayList<>();
        String delete = String.format("delete from %s where %s in", this.getTableNameWithAlias(clazz), this.getPkColumnWithAlias(clazz));
        context.add( new StaticTextSqlNode(delete) );

        ForEachSqlNode forEachSqlNode = new ForEachSqlNode(configuration, new StaticTextSqlNode("#{item}"),
                "list", true, "index", "item", "(", ")", ",");
        context.add(forEachSqlNode);
        mixedSqlNode = new MixedSqlNode(context);
        DELETEBATCH_SQLNODE_MAP.put(key,mixedSqlNode);
        return mixedSqlNode;
    }
    /**
     * 获取逻辑批量删除的sqlNode
     * @param configuration
     * @param clazz
     * @return
     */
    public MixedSqlNode getLogicDeleteBatchSqlNode(Configuration configuration, Class clazz){
        String key = clazz.getName()+"."+this.getTableAlias();
        MixedSqlNode mixedSqlNode = DELETEBATCH_SQLNODE_MAP.get(key);
        if(mixedSqlNode!=null){
            return mixedSqlNode;
        }
        List<SqlNode> context = new ArrayList<>();
        FieldEntry logicEntry = this.getLogicEntry(clazz);
        String logicColumn = this.getTableAliasWithDot();
        if(logicEntry.getColumn()!=null){
            logicColumn += logicEntry.getColumn();
        }
        String delete = String.format("update %s set %s="+this.deletedValue+" where %s in",
                this.getTableNameWithAlias(clazz), logicColumn, this.getPkColumnWithAlias(clazz));
        context.add( new StaticTextSqlNode(delete) );

        List<SqlNode> foreachContext = new ArrayList<>();
        ForEachSqlNode forEachSqlNode = new ForEachSqlNode(configuration, new StaticTextSqlNode("#{item}"),
                "list", true, "index", "item", "(", ")", ",");
        context.add(forEachSqlNode);
        mixedSqlNode = new MixedSqlNode(context);
        DELETEBATCH_SQLNODE_MAP.put(key,mixedSqlNode);
        return mixedSqlNode;
    }

    /**
     * 获取删除sql
     * @param clazz
     * @return
     */
    public String getDeleteSql(Class clazz){
        FieldEntry logicEntry = this.getLogicEntry(clazz);
        if(logicEntry.getColumn()!=null){
            String logicColumn = this.getTableAliasWithDot()+logicEntry.getColumn();
            String sql = String.format("update %s set %s="+this.deletedValue, this.getTableNameWithAlias(clazz), logicColumn);
            return sql;
        }

        String sql = String.format("delete from %s", this.getTableNameWithAlias(clazz));
        return sql;
    }

    //获取条件更新的条件sql
    private String getCondSql(Object t) throws IllegalAccessException {
        if(t==null){
            return null;
        }
        String key = t.getClass().getName()+"."+t.toString();
        String whereSql = COND_SQL_MAP.get(key);
        if(whereSql!=null){
            return whereSql;
        }
        Field[] fields = t.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        String tableAliasWithDot = this.getTableAliasWithDot();
        for (Field field : fields){
            String columnName = getColumnName(field);
            //逻辑字段
            if(this.isLogic(field)){
                sb.append("and "+tableAliasWithDot+ columnName+"="+notDeletedValue+" ");
                continue;
            }
            if(this.isIgnore(field)){
                continue;
            }
            field.setAccessible(true);
            if(field.get(t)!=null){
                sb.append("and "+tableAliasWithDot+ columnName+"=#{cond."+field.getName()+"} ");
            }
        }
        whereSql = "";
        if(sb.length()>0){
            whereSql = sb.substring(4,sb.length()-1);
        }
        COND_SQL_MAP.put(key,whereSql);
        return whereSql;
    }
    //获取条件更新的update sql
    private String getCondUpdateSql(Object t) throws IllegalAccessException {
        if(t==null){
            return null;
        }
        String key = t.getClass().getName()+"."+t.toString();
        String whereSql = COND_UPDATE_SQL_MAP.get(key);
        if(whereSql!=null){
            return whereSql;
        }
        Field[] fields = t.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        String tableAliasWithDot = this.getTableAliasWithDot();
        for (Field field : fields){
            String columnName = getColumnName(field);
            //逻辑字段
            /*if(this.isLogic(field)){
                sb.append("and "+tableAliasWithDot+ columnName+"="+notDeletedValue+" ");
                continue;
            }*/
            if(this.isIgnore(field)){
                continue;
            }
            field.setAccessible(true);
            if(field.get(t)!=null){
                sb.append(tableAliasWithDot+ columnName+"=#{obj."+field.getName()+"},");
            }
        }
        whereSql = "";
        if(sb.length()>0){
            whereSql = sb.substring(0,sb.length()-1);
        }
        COND_UPDATE_SQL_MAP.put(key,whereSql);
        return whereSql;
    }

    /**
     * 获取条件更新的sql
     * @param obj 更新字段
     * @param cond 条件
     * @return
     * @throws IllegalAccessException
     */
    public String getCondUpdateSql(Object obj, Object cond) throws IllegalAccessException{
        return String.format("update %s set %s where %s", this.getTableNameWithAlias(obj.getClass()), this.getCondUpdateSql(obj), this.getCondSql(cond));
    }

    /**
     * 表Entry
     */
    public class TableEntry{
        /** 表名 */
        private String name;
        /** 别名 */
        private String alias;
        public TableEntry(String name, String alias){
            this.name = name;
            this.alias = alias;
        }
        public String getName(){
            return this.name;
        }
        public String getAlias(){
            return this.alias;
        }
    }

    /**
     * 字段Entry
     */
    public class FieldEntry{
        /** 表字段名 */
        private String column;
        /** 类字段 */
        private Field field;
        public FieldEntry(String column, Field field){
            this.column = column;
            this.field = field;
        }
        public String getColumn(){
            return this.column;
        }
        public Field getField(){
            return this.field;
        }
    }
}