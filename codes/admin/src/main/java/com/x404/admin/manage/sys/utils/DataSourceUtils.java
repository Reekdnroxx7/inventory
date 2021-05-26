package com.x404.admin.manage.sys.utils;

import com.x404.admin.core.util.SpringContextHolder;

import javax.sql.DataSource;
import java.util.Map;


public class DataSourceUtils {
    public static Map<String, DataSource> getDataSources() {
        return SpringContextHolder.getBeansOfType(DataSource.class);
    }

    public static DataSource getDataSourceById(String id) {
        return SpringContextHolder.getBeansOfType(DataSource.class).get(id);
    }
}
