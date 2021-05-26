package com.x404.admin.core.config;

import com.x404.admin.core.support.config.DynamicProperties;

import java.util.Properties;


public class SysConfig {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static Properties properties;
    private static String CONFIG_PATH = "classpath:sysConfig.properties";

    static {
        properties = new DynamicProperties(CONFIG_PATH);
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static boolean isRunMode(){
        return "true".equalsIgnoreCase(SysConfig.getProperty("RUN_MODE","false"));
    }


}
