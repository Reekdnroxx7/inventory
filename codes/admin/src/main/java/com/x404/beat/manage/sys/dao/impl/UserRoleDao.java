/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.beat.manage.sys.dao.impl;

import com.x404.beat.core.hibernate.dao.impl.HibernateDao;
import com.x404.beat.manage.sys.entity.Role;
import com.x404.beat.manage.sys.entity.User;
import com.x404.beat.manage.sys.entity.UserRole;
import com.x404.beat.manage.sys.dao.IUserRoleDao;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@Repository
public class UserRoleDao extends HibernateDao<UserRole> implements IUserRoleDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> getAssginRoles(User u) {
        Criteria criteria = getSession().createCriteria(UserRole.class);
        criteria.add(Restrictions.eq("user", u));
        criteria.add(Restrictions.ne("roleType", "2"));
        criteria.addOrder(Order.asc("role"));
        List<UserRole> list = criteria.list();
        List<Role> reRoles = new ArrayList<Role>(list.size());
        for (UserRole ur : list) {
            reRoles.add(ur.getRole());
        }
        return reRoles;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserRole> getAuthRoles(User u) {
        Criteria criteria = getSession().createCriteria(UserRole.class);
        criteria.add(Restrictions.eq("user", u));
        criteria.addOrder(Order.asc("role"));
        return criteria.list();
    }

    @Override
    public void addSaveDelBatch(List<UserRole> delRoles,
                                List<UserRole> saveRoles, List<UserRole> updateRoles) {
        Session session = getSession();
        for (UserRole del : delRoles) {
            session.delete(del);
        }
        for (UserRole save : saveRoles) {
            session.save(save);
        }
        for (UserRole update : updateRoles) {
            session.update(update);
        }
        session.flush();
        session.clear();
    }

    @Override
    public void deleteUserRoleByRoleId(String roleId) {
        String sql = "delete from sys_user_role  where role_id= ? ";
        Query query = getSession().createSQLQuery(sql).addEntity(UserRole.class);
        query.setParameter(0, roleId);
        query.executeUpdate();

    }


}
