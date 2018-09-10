package com.maomiyibian.microservice.provider.interceptor;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

/**
 * TODO: 分页拦截器
 *
 * @author 萧竣匀
 * @date 2018/7/5 20:16
 */

@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class,Integer.class})})
@Component
public class PageInterceptor implements Interceptor {
    private static final Logger logger=LoggerFactory.getLogger(PageInterceptor.class);

    private String dialect;

    /**
     * 默认ObjectFactory
     */
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    /**
     * 默认ObjectWrapperFactory
     */
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    /**
     * 默认ReflectorFactory
     */
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime=System.currentTimeMillis();
        logger.info("分页拦截器已就绪");
        try{
            StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
            MetaObject metaObject=MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
            MappedStatement mappedStatement=(MappedStatement)metaObject.getValue("delegate.mappedStatement");
            //获取到sql语句Id
            String sqlId=mappedStatement.getId();
            //再次判断是否需要拦截，只有sqlID以ByPage结尾时才进行分页拦截
            String endWith="ByPage";
            if (sqlId.endsWith(endWith)){
                logger.info("已执行拦截操作,开始分页");
                //获取参数，拿到分页对象

                BoundSql boundSql = statementHandler.getBoundSql();
                /*Map<String,Object> parameterMap =(Map<String, Object>) boundSql.getParameterObject();
                Page page=(Page) parameterMap.get("page");
                // 原始的SQL语句
                String sql = boundSql.getSql();
                //构造分页语句
                int offset=(page.getPageCurrent()- 1) * page.getPageSize();
                int pages=offset+page.getPageSize();
                String pageSql=this.generatePageSql(dialect,sql,offset,pages);
                //查询总条数
                String countSql = "select count(*) from (" + sql + ") temp";
                Connection connection = (Connection)invocation.getArgs()[0];
                PreparedStatement countStatement = connection.prepareStatement(countSql);
                ParameterHandler parameterHandler = (ParameterHandler)metaObject.getValue("delegate.parameterHandler");
                parameterHandler.setParameters(countStatement);
                ResultSet res = countStatement.executeQuery();
                metaObject.setValue("delegate.boundSql.sql", pageSql);
                //填充分页数据，返回结果
                if(res.next()) {
                    page.setTotal(res.getInt(1));
                }
                if(res!=null) {
                    res.close();
                }
                if(countStatement !=null) {
                    countStatement.close();
                }
                //求出总页数
                page.setPageCount((page.getTotal()-1)/(page.getPageSize()+1));
                */logger.info("拦截器执行完毕，耗时: {} ms",(System.currentTimeMillis()-startTime));
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("分页拦截器出现异常，拦截失败---------"+e.getMessage());
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {return Plugin.wrap(target, this);}

    @Override
    public void setProperties(Properties properties) {}


    /**
     *
     * @param sql 原始sql语句
     * @param offset 分页起点
     * @param pages  分页结束点
     * @return
     */
    private String generatePageSql(String dialect,String sql,int offset,int pages){
        StringBuffer stringBuffer=new StringBuffer(sql.length()+100);
        /*if (dialect.equalsIgnoreCase("mysql")){*/
            stringBuffer.append("SELECT * FROM ( ");
            stringBuffer.append(sql);
            stringBuffer.append(") temp WHERE temp.id BETWEEN ");
            stringBuffer.append(offset);
            stringBuffer.append(" AND ") ;
            stringBuffer.append(pages);
            return  stringBuffer.toString();
        /*}else {
            logger.error("暂未实现其他分页拦截支持--------------");
            return  null;
        }*/
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }
}
