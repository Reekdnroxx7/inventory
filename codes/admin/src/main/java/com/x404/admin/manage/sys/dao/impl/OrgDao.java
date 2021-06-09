package com.x404.admin.manage.sys.dao.impl;

import com.x404.module.basedao.hibernate.impl.HibernateDao;
import com.x404.admin.manage.sys.dao.IOrgDao;
import com.x404.admin.manage.sys.entity.Org;
import org.springframework.stereotype.Repository;

@Repository
public class OrgDao extends HibernateDao<Org> implements IOrgDao {

}
