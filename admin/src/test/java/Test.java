import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;
import com.hfw.service.mybatis.jsqlparser.SqlExpressionVisitor;
import com.hfw.service.mybatis.jsqlparser.SqlFromItemVisitor;
import com.hfw.service.mybatis.jsqlparser.SqlSelectVisitor;
import com.hfw.service.mybatis.jsqlparser.SqlStatementVisitor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * https://jsqlparser.github.io/JSqlParser/
 */
@Slf4j
public class Test {
    public static void jSqlParser() throws JSQLParserException {
        Set<String> tables = Set.of("sys_demo");
        String dataScopeExpression ="user_id=1";
        String sql = "select * from t_user where id=1\n" +
                "union\n" +
                "select * from t_user where id=2";
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
        System.out.println(dataScopeSql);
    }

    public static void main(String[] args) throws Exception {
        Cache<String, Integer> cache = Caffeine.newBuilder().maximumSize(10240).expireAfterWrite(24, TimeUnit.HOURS).build();
        //cache.put("test",null);
        System.out.println(cache.getIfPresent("test"));

        Integer o =  cache.get("test", key -> {
            System.out.println("apply");
            System.out.println(key);
            return 1;
        });
        System.out.println(o);
    }

}
