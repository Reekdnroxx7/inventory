package com.x404.admin.manage.sys.dao;

import com.x404.module.basedao.hibernate.IHibernateDao;
import com.x404.admin.manage.sys.entity.Config;


public interface IConfigDao extends IHibernateDao<Config> {

    void updateByKey(Config config);

}