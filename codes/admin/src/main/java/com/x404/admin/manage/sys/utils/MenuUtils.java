package com.x404.admin.manage.sys.utils;

import com.x404.admin.core.util.ContextHolderUtils;
import com.x404.admin.core.util.SpringContextHolder;
import com.x404.admin.manage.sys.entity.Menu;
import com.x404.admin.manage.sys.tools.MenuTree;
import com.x404.admin.manage.sys.dao.IMenuDao;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import java.util.List;

public class MenuUtils {

    private IMenuDao menuDao;

    private static MenuUtils instance = new MenuUtils();

    private MenuTree menuTree;

    private MenuUtils() {

    }

    public static MenuUtils getInstance() {
        return instance;
    }

    public synchronized void refresh() {
        if (menuDao == null) {
            menuDao = SpringContextHolder.getBean("menuDao");
        }
        menuTree = new MenuTree();
        List<Menu> allMenus = menuDao.findAll();
        for (Menu menu : allMenus) {
            add(menu);
        }
        OperationUtils.getInstance().refresh();
    }

    private void add(Menu menu) {
        menuTree.addMenu(menu);
    }


    public Menu getMenuById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        if (menuTree == null) {
            refresh();
        }
        return menuTree.getById(id);
    }

    public List<? extends Menu> getChildList(String parentId) {
        if (menuTree == null) {
            refresh();

        }
        return menuTree.getChildList(parentId);
    }

    public MenuTree.ExMenu getExMenu(String parentId) {
        if (menuTree == null) {
            refresh();

        }
        return menuTree.getExMenu(parentId);
    }

    public Menu getCurrentMenu() {
        Cookie[] cookies = ContextHolderUtils.getRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
                continue;
            }
            if (cookie.getName().equalsIgnoreCase("currentMenuId")) {
                String menuId = cookie.getValue();
                return getMenuById(menuId);
            }
        }
        return null;
    }


}
