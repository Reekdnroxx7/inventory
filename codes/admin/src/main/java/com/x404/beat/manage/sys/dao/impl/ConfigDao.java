package com.x404.beat.manage.sys.dao.impl;

import com.x404.beat.manage.sys.entity.RoleMenu;
import com.x404.beat.core.hibernate.dao.impl.HibernateDao;
import com.x404.beat.manage.sys.dao.IConfigDao;
import com.x404.beat.manage.sys.entity.Config;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;


@Repository
public class ConfigDao extends HibernateDao<Config> implements IConfigDao {

    @Override
    public void updateByKey(Config config) {
        String sql = "update sys_config set `value` = ? where `key` = ? ";
        Query query = getSession().createSQLQuery(sql).addEntity(RoleMenu.class);
        query.setParameter( 0, config.getValue());
        query.setParameter( 1, config.getKey());
        query.executeUpdate();
    }
}