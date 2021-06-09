package com.x404.admin.core.util;


import com.x404.admin.manage.sys.listener.OnlineListener;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/**
 * @author 张代浩
 */
public class DBTypeUtil {
    private static Logger log = Logger.getLogger(DBTypeUtil.class);

    /**
     * 获取数据库类型
     *
     * @return
     */
    public static String getDBType() {
        String retStr = "";
        ApplicationContext ctx = OnlineListener.getCtx();
        if (ctx == null) {
            return retStr;//如果ctx为空，则服务器异常了
        } else {
            LocalSessionFactoryBean sf = (LocalSessionFactoryBean) ctx.getBean("&sessionFactory");
            String dbdialect = sf.getHibernateProperties().getProperty("hibernate.dialect");
            log.debug(dbdialect);
            if (dbdialect.equals("org.hibernate.dialect.MySQLDialect")) {
                retStr = "mysql";
            } else if (dbdialect.contains("Oracle")) {//oracle有多个版本的方言
                retStr = "oracle";
            } else if (dbdialect.equals("org.hibernate.dialect.SQLServerDialect")) {
                retStr = "sqlserver";
            } else if (dbdialect.equals("org.hibernate.dialect.PostgreSQLDialect")) {
                retStr = "postgres";
            }
            return retStr;
        }
    }
}
