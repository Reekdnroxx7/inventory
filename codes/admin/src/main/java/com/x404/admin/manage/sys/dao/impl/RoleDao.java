/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.admin.manage.sys.dao.impl;

import com.x404.admin.core.hibernate.dao.impl.HibernateDao;
import com.x404.admin.manage.sys.dao.IRoleDao;
import com.x404.admin.manage.sys.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * 角色DAO接口
 *
 * @author ThinkGem
 * @version 2013-8-23
 */
@Repository
public class RoleDao extends HibernateDao<Role> implements IRoleDao {

}
