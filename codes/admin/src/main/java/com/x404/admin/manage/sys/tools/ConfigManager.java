package com.x404.admin.manage.sys.tools;

import com.x404.admin.manage.sys.entity.Config;
import com.x404.admin.manage.sys.service.IConfigService;
import com.x404.admin.core.util.SpringContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author xiechao
 *         字典单例
 *         使用本地HashMap作为缓存。不支持分布式。
 */
public class ConfigManager {

    private static ConfigManager instance = new ConfigManager();
    private IConfigService configService;
    private Properties properties;

    public static ConfigManager getInstance() {
        return instance;
    }

    private ConfigManager() {
        this.configService = SpringContextHolder.getBean(IConfigService.class);
    }

    public synchronized void refresh() {
        this.properties = new Properties();
        List<Config> configs = configService.findAll();
        for (Config c : configs) {
            add(c);
        }
    }

    private void add(Config c) {
        this.properties.put(c.getKey(), c.getValue());
    }

    public String getProperty(String key) {
        if (properties == null) {
            refresh();
        }
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        if (properties == null) {
            refresh();
        }
        return properties.getProperty(key, defaultValue);
    }

    public Map<String,String> getAllConfig(){
        if(properties == null){
            refresh();
        }
        return (Map<String, String>) properties.clone();
    }


    public boolean containsKey(String key) {
        if(properties == null){
            refresh();
        }
        return properties.containsKey(key);
    }
}
