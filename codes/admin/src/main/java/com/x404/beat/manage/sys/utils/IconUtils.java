package com.x404.beat.manage.sys.utils;

import com.x404.beat.core.util.SpringContextHolder;
import com.x404.beat.manage.sys.entity.Icon;
import com.x404.beat.manage.sys.service.IIconService;

import java.util.List;

/**
 * @author xiechao
 *         使用本地HashMap作为缓存。不支持分布式。
 */
public class IconUtils {
    private static IconUtils instance = new IconUtils();
    private IIconService iconService;
    private List<Icon> allIcons;

    private IconUtils() {
        this.iconService = SpringContextHolder.getBean(IIconService.class);
    }

    public static IconUtils getInstance() {
        return instance;
    }

    public void refresh() {
        //this.iconService.findAll();  不能使用hibernate缓存
        this.allIcons = this.iconService.findByExample(new Icon()); //不是用hibernate 缓存
    }

    public List<Icon> getIcons() {
        if (allIcons == null) {
            refresh();
        }
        return allIcons;
    }
}
