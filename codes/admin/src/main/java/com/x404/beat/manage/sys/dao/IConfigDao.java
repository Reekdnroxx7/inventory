package com.x404.beat.manage.sys.dao;

import com.x404.beat.core.hibernate.dao.IHibernateDao;
import com.x404.beat.manage.sys.entity.Config;


public interface IConfigDao extends IHibernateDao<Config> {

    void updateByKey(Config config);

}