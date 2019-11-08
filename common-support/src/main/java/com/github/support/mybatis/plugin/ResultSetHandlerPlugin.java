package com.github.support.mybatis.plugin;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.Statement;
import java.util.Properties;

/**
 * <p>ResultSetHandler拦截器</p>
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class}) })
public class ResultSetHandlerPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        Statement stmt = (Statement) invocation.getArgs()[0];
        if (target instanceof DefaultResultSetHandler) {
            DefaultResultSetHandler resultSetHandler = (DefaultResultSetHandler) target;
            System.out.println(resultSetHandler);
            System.out.println(stmt);
            return invocation.proceed();
        }
        return invocation.proceed();
    }


    @Override
    public void setProperties(Properties properties) {
        System.out.println("设置属性");
    }
}
