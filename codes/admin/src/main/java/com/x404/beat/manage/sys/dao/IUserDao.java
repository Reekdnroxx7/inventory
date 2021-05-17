/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.beat.manage.sys.dao;

import com.x404.beat.core.hibernate.dao.IHibernateDao;
import com.x404.beat.manage.sys.entity.RoleMenu;
import com.x404.beat.manage.sys.entity.User;

import java.util.List;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
public interface IUserDao extends IHibernateDao<User> {
    public List<RoleMenu> getAccessMenus(User u);

//	public List<RoleMenu> getAssignMenus(User u);
}
