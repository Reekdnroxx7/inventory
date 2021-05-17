package com.x404.beat.manage.sys.dao.impl;

import com.x404.beat.core.hibernate.dao.impl.HibernateDao;
import com.x404.beat.manage.sys.dao.IOrgDao;
import com.x404.beat.manage.sys.entity.Org;
import org.springframework.stereotype.Repository;

@Repository
public class OrgDao extends HibernateDao<Org> implements IOrgDao {

}
