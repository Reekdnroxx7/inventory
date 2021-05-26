package com.x404.admin.manage.sys.service;

import com.x404.admin.core.service.IHibernateService;
import com.x404.admin.manage.sys.entity.Menu;

import java.util.List;

/**
 * @author 张代浩
 */
public interface IMenuService extends IHibernateService<Menu> {

    List<Menu> getChildMenus(String parentId);

}
