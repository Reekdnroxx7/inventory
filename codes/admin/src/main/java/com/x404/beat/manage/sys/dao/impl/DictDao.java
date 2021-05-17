/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.beat.manage.sys.dao.impl;

import com.x404.beat.core.hibernate.dao.impl.HibernateDao;
import com.x404.beat.manage.sys.dao.IDictDao;
import com.x404.beat.manage.sys.entity.Dict;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@Repository
public class DictDao extends HibernateDao<Dict> implements IDictDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<Dict> findAll() {
        return getSession().createCriteria(Dict.class).addOrder(Order.asc("dictCode")).list();
    }

}
