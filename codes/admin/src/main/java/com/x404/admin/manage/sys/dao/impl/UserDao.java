/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.admin.manage.sys.dao.impl;

import com.x404.admin.core.hibernate.dao.impl.HibernateDao;
import com.x404.admin.manage.sys.dao.IUserDao;
import com.x404.admin.manage.sys.entity.User;
import com.x404.admin.manage.sys.entity.RoleMenu;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@Repository
public class UserDao extends HibernateDao<User> implements IUserDao
{

    @SuppressWarnings("unchecked")
    @Override
    public List<RoleMenu> getAccessMenus(User u) {
        String sql = "select * from sys_role_menu rm inner join sys_user_role ur on rm.role_id = ur.role_id where ur.user_id = ? ";
        Query query = getSession().createSQLQuery(sql).addEntity(RoleMenu.class);
        query.setParameter( 0, u.getId());
        return query.list();
    }


}
