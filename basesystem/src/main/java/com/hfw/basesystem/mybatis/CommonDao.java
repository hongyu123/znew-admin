package com.hfw.basesystem.mybatis;

import com.hfw.common.entity.BaseEntity;
import com.hfw.common.support.GeneralException;
import com.hfw.common.support.ParamMap;
import com.hfw.common.entity.PageResult;
import com.hfw.common.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用Dao
 * 每次执行SQL都是创建一个新的MappedStatement的查询,因此Mybatis的一级缓存功能不可用
 * @author zyh
 * @date 2022-06-09
 */
@Slf4j
public class CommonDao{
    private SqlSessionFactory sqlSessionFactory;
    private SqlHelper sqlHelper;

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }
    public void setSqlHelper(SqlHelper sqlHelper){
        this.sqlHelper = sqlHelper;
    }

    //获取执行器
    private Executor getExecutor(){
        Configuration configuration = sqlSessionFactory.getConfiguration();
        Environment environment = configuration.getEnvironment();
        TransactionFactory transactionFactory = environment.getTransactionFactory();
        if(transactionFactory==null){
            transactionFactory = new ManagedTransactionFactory();
        }
        Transaction tx = transactionFactory.newTransaction(environment.getDataSource(), null, false);
        Executor executor = configuration.newExecutor(tx);
        return executor;
    }
    //获取批量数据提交的执行器
    private Executor getBatchExecutor(){
        Configuration configuration = sqlSessionFactory.getConfiguration();
        Environment environment = configuration.getEnvironment();
        TransactionFactory transactionFactory = environment.getTransactionFactory();
        if(transactionFactory==null){
            transactionFactory = new ManagedTransactionFactory();
        }
        Transaction tx = transactionFactory.newTransaction(environment.getDataSource(), null, false);
        Executor executor = configuration.newExecutor(tx, ExecutorType.BATCH);
        return executor;
    }

    private <T> T findOne(String statementId, Class<T> clazz, String sql, Object params) {
        List<T> list = this.findList(statementId,clazz,sql,params);
        if(list==null || list.size()<=0){
            return null;
        }
        return list.get(0);
    }
    private <T> List<T> findList(String statementId, Class<T> clazz, String sql, Object params) {
        Executor executor = getExecutor();
        try{
            Configuration configuration = sqlSessionFactory.getConfiguration();
            RawSqlSource sqlSource = new RawSqlSource(configuration, sql, clazz);
            List<ResultMap> resultMaps = new ArrayList<>();
            ResultMap inlineResultMap = new ResultMap.Builder(configuration,statementId + "-Inline",clazz,new ArrayList<>(),null).build();
            resultMaps.add(inlineResultMap);
            MappedStatement mappedStatement = new MappedStatement.Builder(configuration, statementId,  sqlSource, SqlCommandType.SELECT)
                    .resultMaps(resultMaps)
                    .build();
            return executor.query(mappedStatement, params, RowBounds.DEFAULT, null);
        }catch (SQLException e){
            throw new GeneralException(e);
            /*log.error("findList error", e);
            return null;*/
        }finally {
            executor.close(false);
        }
    }
    private <T> Long count(String statementId, Class<T> clazz, String sql, Object params) {
        Executor executor = getExecutor();
        try{
            Configuration configuration = sqlSessionFactory.getConfiguration();
            RawSqlSource sqlSource = new RawSqlSource(configuration, sql, clazz);
            List<ResultMap> resultMaps = new ArrayList<>();
            ResultMap inlineResultMap = new ResultMap.Builder(configuration,statementId + "-Inline",Long.class,new ArrayList<>(),null).build();
            resultMaps.add(inlineResultMap);
            MappedStatement mappedStatement = new MappedStatement.Builder(configuration, statementId,  sqlSource, SqlCommandType.SELECT)
                    .resultMaps(resultMaps)
                    .build();
            List<Long> list = executor.query(mappedStatement, params, RowBounds.DEFAULT, null);
            if(list==null || list.size()<=0){
                return 0L;
            }
            return list.get(0);
        }catch (SQLException e){
            throw new GeneralException(e);
           /* log.error("count error", e);
            return 0L;*/
        }finally {
            executor.close(false);
        }
    }

    /**
     * 根据主键查询
     * @param clazz
     * @param pk
     * @param <T>
     * @return
     */
    public <T> T findByPk(Class<T> clazz, Long pk)  {
        String sql = String.format("select %s from %s where %s=#{pk}",
                sqlHelper.getSelectSql(clazz), sqlHelper.getTableNameWithAlias(clazz), sqlHelper.getPkColumnWithAlias(clazz));
        String logicCond = sqlHelper.getLogicCond(clazz);
        if(logicCond!=null){
            sql += " and "+logicCond;
        }
        return this.findOne("com.hfw.basesystem.mybatis.CommonDao.findByPk", clazz, sql, pk);
    }

    /**
     * 查询所有数据
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> findALl(Class<T> clazz){
        String sql = String.format("select %s from %s", sqlHelper.getSelectSql(clazz), sqlHelper.getTableNameWithAlias(clazz));
        String logicCond = sqlHelper.getLogicCond(clazz);
        if(logicCond!=null){
            sql += " where "+logicCond;
        }
        return this.findList("com.hfw.basesystem.mybatis.CommonDao.findALl",clazz,sql,null);
    }

    /**
     * 查询一条数据
     * @param t
     * @param <T>
     * @return
     */
    public <T> T findOne(T t) {
        Class<T> clazz = (Class<T>) t.getClass();
        try {
            String sql = String.format("select %s from %s", sqlHelper.getSelectSql(clazz), sqlHelper.getTableNameWithAlias(clazz));
            String where = sqlHelper.getWhereSql(t);
            if(StrUtil.hasText(where)){
                sql += " where "+where;
            }
            return this.findOne("com.hfw.basesystem.mybatis.CommonDao.findOne", clazz, sql, t);
        } catch (IllegalAccessException e) {
            throw new GeneralException(e);
            /*log.error("findOne error", e);
            return null;*/
        }
    }

    /**
     * 查询一条数据
     * @param cond
     * @param <T>
     * @return
     */
    public <T> T findOneCond(Condition<T> cond) {
        try {
            Class<T> clazz = cond.getClazz();
            Map<String,Object> params = new HashMap<>();
            String sql = String.format("select %s from %s", sqlHelper.getSelectSql(clazz), sqlHelper.getTableNameWithAlias(clazz));
            String where = sqlHelper.getWhereSql(cond, clazz, params);
            if(StrUtil.hasText(where)){
                sql += " where "+where;
            }
            return this.findOne("com.hfw.basesystem.mybatis.CommonDao.findOneCond", clazz, sql, params);
        } catch (Exception e) {
            throw new GeneralException(e);
            /*log.error("findOne error", e);
            return null;*/
        }
    }

    /**
     * 查询列表
     * @param t
     * @param <T>
     * @return
     */
    public <T> List<T> list(T t){
        Class clazz = t.getClass();
        try {
            String sql = String.format("select %s from %s", sqlHelper.getSelectSql(clazz), sqlHelper.getTableNameWithAlias(clazz));
            String where = sqlHelper.getWhereSql(t);
            if(StrUtil.hasText(where)){
                sql += " where "+where;
            }
            if(t instanceof BaseEntity){
                BaseEntity base = (BaseEntity)t;
                if(StrUtil.hasText(base.getSortByField()) && base.getSortByWay()!=null){
                    sql += String.format(" order by %s %s",base.getSortByField(), base.getSortByWay().toString());
                }
            }
            return this.findList("com.hfw.basesystem.mybatis.CommonDao.list", clazz, sql, t);
        } catch (IllegalAccessException e) {
            throw new GeneralException(e);
            /*log.error("list error", e);
            return null;*/
        }
    }

    /**
     * 分页查询列表
     * @param t
     * @param pageNumber
     * @param pageSize
     * @param <T>
     * @return
     */
    public <T> List<T> list(T t, Integer pageNumber, Integer pageSize){
        Class clazz = t.getClass();
        PageResult page = new PageResult(pageNumber,pageSize);
        try {
            String where = sqlHelper.getWhereSql(t);
            if(StrUtil.hasText(where)){
                where = "where "+where;
            }
            String sql = String.format("select %s from %s %s",
                    sqlHelper.getSelectSql(clazz), sqlHelper.getTableNameWithAlias(clazz), where);
            if(t instanceof BaseEntity){
                BaseEntity base = (BaseEntity)t;
                if(StrUtil.hasText(base.getSortByField()) && base.getSortByWay()!=null){
                    sql += String.format(" order by %s %s",base.getSortByField(), base.getSortByWay().toString());
                }
            }
            sql += String.format(" limit %d,%d", page.getStart(),page.getRows());
            return this.findList("com.hfw.basesystem.mybatis.CommonDao.list_page", clazz, sql, t);
        } catch (IllegalAccessException e) {
            throw new GeneralException(e);
           /* log.error("list error", e);
            return null;*/
        }
    }

    /**
     * 查询列表
     * @param cond
     * @param <T>
     * @return
     */
    public <T> List<T> listCond(Condition<T> cond) {
        try {
            Class<T> clazz = cond.getClazz();
            Map<String,Object> params = new HashMap<>();
            String sql = String.format("select %s from %s", sqlHelper.getSelectSql(clazz),sqlHelper.getTableNameWithAlias(clazz));
            String where = sqlHelper.getWhereSql(cond, clazz, params);
            if(StrUtil.hasText(where)){
                sql += " where "+where;
            }
            return this.findList("com.hfw.basesystem.mybatis.CommonDao.listCond", clazz, sql, params);
        } catch (Exception e) {
            throw new GeneralException(e);
           /* log.error("listCond error", e);
            return null;*/
        }
    }

    /**
     * 分页查询列表
     * @param cond
     * @param pageNumber
     * @param pageSize
     * @param <T>
     * @return
     */
    public <T> List<T> listCond(Condition<T> cond, Integer pageNumber, Integer pageSize) {
        PageResult page = new PageResult(pageNumber,pageSize);
        try {
            Class<T> clazz = cond.getClazz();
            Map<String,Object> params = new HashMap<>();
            String where = sqlHelper.getWhereSql(cond, clazz, params);
            if(StrUtil.hasText(where)){
                where = "where "+where;
            }
            String sql = String.format("select %s from %s %s limit %d,%d",
                    sqlHelper.getSelectSql(clazz), sqlHelper.getTableNameWithAlias(clazz), where,
                    page.getStart(),page.getRows());
            return this.findList("com.hfw.basesystem.mybatis.CommonDao.listCond_page", clazz, sql, params);
        } catch (Exception e) {
            throw new GeneralException(e);
            /*log.error("listCond error", e);
            return null;*/
        }
    }

    /**
     * 查询数量
     * @param t
     * @param <T>
     * @return
     */
    public <T> Long count(T t){
        Class clazz = t.getClass();
        try {
            String sql = String.format("select count(1) from %s", sqlHelper.getTableNameWithAlias(clazz));
            String where = sqlHelper.getWhereSql(t);
            if(StrUtil.hasText(where)){
                sql += " where "+where;
            }
            return this.count("com.hfw.basesystem.mybatis.CommonDao.count", clazz, sql, t);
        } catch (IllegalAccessException e) {
            throw new GeneralException(e);
            /*log.error("count error", e);
            return 0L;*/
        }
    }

    /**
     * 查询数量
     * @param cond
     * @param <T>
     * @return
     */
    public <T> Long countCond(Condition<T> cond){
        try {
            Class<T> clazz = cond.getClazz();
            Map<String,Object> params = new HashMap<>();
            String sql = String.format("select count(1) from %s", sqlHelper.getTableNameWithAlias(clazz));
            String where = sqlHelper.getWhereSql(cond, clazz, params);
            if(StrUtil.hasText(where)){
                sql += " where "+where;
            }
            return this.count("com.hfw.basesystem.mybatis.CommonDao.countCond", clazz, sql, params);
        } catch (Exception e) {
            throw new GeneralException(e);
            /*log.error("countCond error", e);
            return 0L;*/
        }
    }

    /**
     * 分页查询
     * @param t
     * @param pageNumber
     * @param pageSize
     * @param <T>
     * @return
     */
    public <T> PageResult<T> page(T t, Integer pageNumber, Integer pageSize){
        Class clazz = t.getClass();
        PageResult<T> page = new PageResult(pageNumber,pageSize);
        try {
            String where = sqlHelper.getWhereSql(t);
            if(StrUtil.hasText(where)){
                where = "where "+where;
            }
            String tableName = sqlHelper.getTableNameWithAlias(clazz);
            String countSql = String.format("select count(1) from %s %s",tableName,where);

            String listSql = String.format("select %s from %s %s",
                    sqlHelper.getSelectSql(clazz), sqlHelper.getTableNameWithAlias(clazz), where);
            if(t instanceof BaseEntity){
                BaseEntity base = (BaseEntity)t;
                if(StrUtil.hasText(base.getSortByField()) && base.getSortByWay()!=null){
                    listSql += String.format(" order by %s %s",base.getSortByField(), base.getSortByWay().toString());
                }
            }
            listSql += String.format(" limit %d,%d", page.getStart(),page.getRows());
            //String listSql = String.format("select %s from %s %s limit %d,%d", sqlHelper.getSelectSql(clazz),tableName,where, page.getStart(),page.getRows());
            page.setTotal(this.count("com.hfw.basesystem.mybatis.CommonDao.page",clazz,countSql,t));
            if(page.getTotal()>0){
                page.setData(this.findList("com.hfw.basesystem.mybatis.CommonDao.page",clazz,listSql,t));
            }
            return page;
        } catch (IllegalAccessException e) {
            throw new GeneralException(e);
            /*log.error("page error", e);
            return null;*/
        }
    }

    /**
     * 分页查询
     * @param cond
     * @param pageNumber
     * @param pageSize
     * @param <T>
     * @return
     */
    public <T> PageResult<T> pageCond(Condition<T> cond, Integer pageNumber, Integer pageSize){
        PageResult<T> page = new PageResult(pageNumber,pageSize);
        try {
            Class<T> clazz = cond.getClazz();
            Map<String,Object> params = new HashMap<>();
            String where = sqlHelper.getWhereSql(cond, clazz, params);
            if(StrUtil.hasText(where)){
                where = "where "+where;
            }
            String tableName = sqlHelper.getTableNameWithAlias(clazz);
            String countSql = String.format("select count(1) from %s %s",tableName,where);
            String listSql = String.format("select %s from %s %s limit %d,%d", sqlHelper.getSelectSql(clazz),tableName,where, page.getStart(),page.getRows());
            page.setTotal(this.count("com.hfw.basesystem.mybatis.CommonDao.pageCond",clazz,countSql,params));
            if(page.getTotal()>0){
                page.setData(this.findList("com.hfw.basesystem.mybatis.CommonDao.pageCond",clazz,listSql,params));
            }
            return page;
        } catch (Exception e) {
            throw new GeneralException(e);
            /*log.error("pageCond error", e);
            return null;*/
        }
    }

    /**
     * 新增
     * @param t
     * @param <T>
     * @return
     */
    public <T> int insert(T t){
        Executor executor = getExecutor();
        try{
            Configuration configuration = sqlSessionFactory.getConfiguration();
            DynamicSqlSource sqlSource = new DynamicSqlSource(configuration, sqlHelper.getInsertSqlNode(configuration,t));
            SqlHelper.FieldEntry pkEntry = sqlHelper.getPkEntry(t.getClass());
            MappedStatement mappedStatement = new MappedStatement.Builder(configuration, "com.hfw.basesystem.mybatis.CommonDao.insert", sqlSource, SqlCommandType.INSERT)
                    .keyGenerator(Jdbc3KeyGenerator.INSTANCE)
                    .keyColumn(pkEntry.getColumn())
                    .keyProperty(pkEntry.getField().getName())
                    .build();
            return executor.update(mappedStatement, t);
        }catch (Exception e){
            throw new GeneralException(e);
            /*log.error("insert error", e);
            return 0;*/
        }finally {
            executor.close(false);
        }
    }

    /**
     * 批量新增
     * @param list
     * @param <T>
     * @return
     */
    public <T> int insertBatch(List<T> list){
        if(list==null || list.size()<=0){
            return 0;
        }
        Executor executor = getBatchExecutor();
        //int cnt = 0;
        try{
            Configuration configuration = sqlSessionFactory.getConfiguration();
            DynamicSqlSource sqlSource = new DynamicSqlSource(configuration, sqlHelper.getInsertSqlNode(configuration, list.get(0)));
            MappedStatement mappedStatement = new MappedStatement.Builder(configuration, "com.hfw.basesystem.mybatis.CommonDao.insertBatch", sqlSource, SqlCommandType.INSERT).build();
            for(T t : list){
                executor.update(mappedStatement, t);;
            }
            executor.commit(true);
        }catch (Exception e){
            throw new GeneralException(e);
            /*log.error("insertBatch error", e);
            return 0;*/
        }finally {
            executor.close(false);
        }
        return list.size();
    }

    /**
     * 根据主键更新
     * @return
     */
    public <T> int updateByPk(T t){
        Executor executor = getExecutor();
        try{
            Configuration configuration = sqlSessionFactory.getConfiguration();
            DynamicSqlSource sqlSource = new DynamicSqlSource(configuration, sqlHelper.getUpdateSqlNode(configuration, t));
            MappedStatement mappedStatement = new MappedStatement.Builder(configuration, "com.hfw.basesystem.mybatis.CommonDao.updateByPk", sqlSource, SqlCommandType.UPDATE).build();
            return executor.update(mappedStatement, t);
        }catch (Exception e){
            throw new GeneralException(e);
           /* log.error("updateByPk error", e);
            return 0;*/
        }finally {
            executor.close(false);
        }
    }

    /**
     * 根据主键更新所有字段
     * @return
     */
    public <T> int updateAllField(T t){
        Executor executor = getExecutor();
        try{
            Configuration configuration = sqlSessionFactory.getConfiguration();
            DynamicSqlSource sqlSource = new DynamicSqlSource(configuration, sqlHelper.getUpdateAllSqlNode(t));
            MappedStatement mappedStatement = new MappedStatement.Builder(configuration, "com.hfw.basesystem.mybatis.CommonDao.updateAllField", sqlSource, SqlCommandType.UPDATE).build();
            return executor.update(mappedStatement, t);
        }catch (Exception e){
            throw new GeneralException(e);
            /*log.error("updateAllField error", e);
            return 0;*/
        }finally {
            executor.close(false);
        }
    }

    /**
     * 条件更新有风险,可能会因为少写条件引发全局更新,暂不提供.建议先查询然后根据id更新
     * @param t
     * @param cond
     * @param <T>
     * @return
     */
    public <T> int update(T t, T cond){
        //throw new GeneralException("条件更新有风险,可能会因为少写条件引发全局更新,暂不提供.建议先查询然后根据id更新");
        Map<String,Object> map = ParamMap.create().put("obj",t).put("cond",cond);
        Executor executor = getExecutor();
        try{
            Configuration configuration = sqlSessionFactory.getConfiguration();
            String sql = sqlHelper.getCondUpdateSql(t, cond);
            RawSqlSource sqlSource = new RawSqlSource(configuration, sql, map.getClass());
            MappedStatement mappedStatement = new MappedStatement.Builder(configuration, "com.hfw.basesystem.mybatis.CommonDao.update", sqlSource, SqlCommandType.UPDATE).build();
            return executor.update(mappedStatement,map);
        }catch (Exception e){
            throw new GeneralException(e);
            /*log.error("deleteByPk error", e);
            return 0;*/
        }finally {
            executor.close(false);
        }
    }
    /**
     * 条件更新有风险,可能会因为少写条件引发全局更新,暂不提供.建议先查询然后根据id更新
     * @param t
     * @param cond
     * @param <T>
     * @return
     */
    public <T> int updateCond(T t, Condition<T> cond){
        throw new GeneralException("条件更新有风险,可能会因为少写条件引发全局更新,暂不提供.建议先查询然后根据id更新");
    }
    /* 批量操作只有新增速度快,因此批量更新不提供
    public <T> int updateBatch(List<T> list){
        return 0;
    }
    */

    /**
     * 根据主键删除
     * @param clazz
     * @param pk
     * @return
     */
    public int deleteByPk(Class clazz, Long pk){
        if(pk==null){
            return 0;
        }
        Executor executor = getExecutor();
        try{
            Configuration configuration = sqlSessionFactory.getConfiguration();
            String sql = sqlHelper.getDeleteSql(clazz);
            sql += String.format(" where %s=#{pk}", sqlHelper.getPkColumnWithAlias(clazz));
            RawSqlSource sqlSource = new RawSqlSource(configuration, sql, clazz);
            MappedStatement mappedStatement = new MappedStatement.Builder(configuration, "com.hfw.basesystem.mybatis.CommonDao.deleteByPk", sqlSource, SqlCommandType.DELETE).build();
            return executor.update(mappedStatement, pk);
        }catch (Exception e){
            throw new GeneralException(e);
            /*log.error("deleteByPk error", e);
            return 0;*/
        }finally {
            executor.close(false);
        }
    }
    public int deleteBatch(Class clazz, List<Long> ids){
        if(ids==null || ids.size()<=0){
            return 0;
        }
        Executor executor = getExecutor();
        try{
            Map<String,Object> params = new HashMap<>();
            params.put("list",ids);
            Configuration configuration = sqlSessionFactory.getConfiguration();
            DynamicSqlSource sqlSource = new DynamicSqlSource(configuration, sqlHelper.getDeleteBatchSqlNode(configuration, clazz));
            MappedStatement mappedStatement = new MappedStatement.Builder(configuration, "com.hfw.basesystem.mybatis.CommonDao.deleteBatch", sqlSource, SqlCommandType.DELETE).build();
            return executor.update(mappedStatement, params);
        }catch (Exception e){
            throw new GeneralException(e);
            /*log.error("deleteBatch error", e);
            return 0;*/
        }finally {
            executor.close(false);
        }
    }

    /**
     * 条件删除有风险,可能会因为少写条件引发全局删除,暂不提供.建议先查询然后根据id删除
     * @param t
     * @param <T>
     * @return
     */
    public <T> int delete(T t){
        //throw new GeneralException("条件删除有风险,可能会因为少写条件引发全局删除,暂不提供.建议先查询然后根据id删除");
        Class clazz = t.getClass();
        Executor executor = getExecutor();
        try{
            Configuration configuration = sqlSessionFactory.getConfiguration();
            String sql = sqlHelper.getDeleteSql(clazz);
            sql += " where " + sqlHelper.getWhereSql(t);
            RawSqlSource sqlSource = new RawSqlSource(configuration, sql, clazz);
            MappedStatement mappedStatement = new MappedStatement.Builder(configuration, "com.hfw.basesystem.mybatis.CommonDao.delete", sqlSource, SqlCommandType.DELETE).build();
            return executor.update(mappedStatement, t);
        }catch (Exception e){
            throw new GeneralException(e);
            /*log.error("deleteByPk error", e);
            return 0;*/
        }finally {
            executor.close(false);
        }
    }
    /**
     * 条件删除有风险,可能会因为少写条件引发全局删除,暂不提供.建议先查询然后根据id删除
     * @param cond
     * @param <T>
     * @return
     */
    public <T> int deleteCond(Condition<T> cond){
        throw new GeneralException("条件删除有风险,可能会因为少写条件引发全局删除,暂不提供.建议先查询然后根据id删除");
    }

}
