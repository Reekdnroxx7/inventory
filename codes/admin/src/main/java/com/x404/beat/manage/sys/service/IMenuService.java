package com.x404.beat.manage.sys.service;

import com.x404.beat.core.service.IHibernateService;
import com.x404.beat.manage.sys.entity.Menu;

import java.util.List;

/**
 * @author 张代浩
 */
public interface IMenuService extends IHibernateService<Menu> {

    List<Menu> getChildMenus(String parentId);

}
