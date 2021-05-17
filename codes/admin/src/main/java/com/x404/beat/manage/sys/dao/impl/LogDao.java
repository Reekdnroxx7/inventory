/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.beat.manage.sys.dao.impl;

import com.x404.beat.core.hibernate.dao.impl.HibernateDao;
import com.x404.beat.manage.sys.dao.ILogDao;
import com.x404.beat.manage.sys.entity.Log;
import org.springframework.stereotype.Repository;

/**
 * 日志DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@Repository
public class LogDao extends HibernateDao<Log> implements ILogDao {

}
