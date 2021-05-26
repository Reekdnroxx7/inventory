package com.x404.admin.manage.sys.listener;

import javax.servlet.ServletContextEvent;


/**
 * 系统初始化监听器,在系统启动时运行,进行一些初始化工作
 *
 * @author laien
 */
public class InitListener implements javax.servlet.ServletContextListener {


    public void contextDestroyed(ServletContextEvent arg0) {

    }


    public void contextInitialized(ServletContextEvent event) {
//        AccountManager.getInstance().refresh();

    }

}
