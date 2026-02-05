package com.hfw.service.component;

import cn.dev33.satoken.stp.StpUtil;
import com.hfw.model.enums.sys.DataScope;
import com.hfw.model.po.sys.SysDataScope;
import com.hfw.service.dto.LoginUser;
import com.hfw.model.mybatis.jsqlparser.SqlExpressionVisitor;
import com.hfw.model.mybatis.jsqlparser.SqlFromItemVisitor;
import com.hfw.model.mybatis.jsqlparser.SqlSelectVisitor;
import com.hfw.model.mybatis.jsqlparser.SqlStatementVisitor;
import com.hfw.service.sys.sysDataScope.SysDataScopeService;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Properties;
import java.util.Set;

@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class DataScopeInterceptor implements Interceptor {
    public static final Logger monitorLog = LoggerFactory.getLogger("monitor_log");
    private final static Set<String> tables = Set.of("sys_demo");

    private String dataScopeExpression(){
        boolean login = StpUtil.isLogin();
        if(!login){
            return null;
        }
        LoginUser loginUser = LoginUser.getLoginUser();
        if(loginUser==null || loginUser.getId()==1 || !StringUtils.hasText(loginUser.getDataScopeKey())){
            return null;
        }
        SysDataScopeService sysDataScopeService = AppHolder.getApplicationContext().getBean(SysDataScopeService.class);
        SysDataScope sysDataScope = sysDataScopeService.recentDataScopeByCache(loginUser.getDataScopeKey(), loginUser.getId(), loginUser.getOrgId());
        if(sysDataScope==null || sysDataScope.getId()==0){
            return null;
        }
        if(DataScope.User==sysDataScope.getDataScope()){
            return "user_id="+loginUser.getId();
        }else if(DataScope.Organization==sysDataScope.getDataScope()){
            return "org_id="+loginUser.getOrgId();
        }else if(!StringUtils.hasText(sysDataScope.getCustomIds())){
            //return null;
            return "org_id=0";
        }
        return "org_id in (%s)".formatted(sysDataScope.getCustomIds());
    }

    private boolean containsTable(String sql){
        for (String table : tables){
            if(sql.contains(table)){
                return true;
            }
        }
        return false;
    }
    private String dataScopeSql(String sql, String dataScopeExpression) throws JSQLParserException {
        SqlStatementVisitor statementVisitor = new SqlStatementVisitor();
        SqlSelectVisitor selectVisitor = new SqlSelectVisitor();
        SqlFromItemVisitor fromItemVisitor = new SqlFromItemVisitor(tables, dataScopeExpression);
        SqlExpressionVisitor expressionVisitor = new SqlExpressionVisitor();

        statementVisitor.setSelectVisitor(selectVisitor);
        selectVisitor.setFromItemVisitor(fromItemVisitor);
        selectVisitor.setExpressionVisitor(expressionVisitor);
        expressionVisitor.setSelectVisitor(selectVisitor);
        Statement stmt = CCJSqlParserUtil.parse(sql);
        String originSql = stmt.toString();
        stmt.accept(statementVisitor);
        String dataScopeSql = stmt.toString();
        if(dataScopeSql.equals(originSql) && containsTable(originSql)){
            monitorLog.error("数据权限SQL增强失败,{}", originSql);
        }
        return dataScopeSql;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String dataScopeExpression = this.dataScopeExpression();
        if(dataScopeExpression==null){
            return invocation.proceed();
        }

        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];
        Executor executor = (Executor) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        //由于逻辑关系，只会进入一次
        if (args.length == 4) {
            //4 个参数时
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            //6 个参数时
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }

        String sql = boundSql.getSql();
        String dataScopeSql= this.dataScopeSql(sql, dataScopeExpression);
        BoundSql dataScopeBoundSql = new BoundSql(ms.getConfiguration(), dataScopeSql, boundSql.getParameterMappings(), parameter);
        return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, dataScopeBoundSql);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
