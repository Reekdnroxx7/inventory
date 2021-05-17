package com.x404.beat.manage.sys.dao.impl;

import com.x404.beat.core.hibernate.dao.impl.HibernateDao;
import com.x404.beat.manage.sys.dao.IRoleMenuDao;
import com.x404.beat.manage.sys.entity.RoleMenu;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class RoleMenuDao extends HibernateDao<RoleMenu> implements IRoleMenuDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<RoleMenu> getRoleMenus(String roleId) {
        String sql = "select * from sys_role_menu   where role_id= ? ";
        Query query = getSession().createSQLQuery(sql).addEntity(RoleMenu.class);
        query.setParameter(0, roleId);
        return query.list();
    }

    @Override
    public void deleteRoleMenus(String roleId) {
        String sql = "delete from sys_role_menu  where role_id= ? ";
        Query query = getSession().createSQLQuery(sql).addEntity(RoleMenu.class);
        query.setParameter(0, roleId);
        query.executeUpdate();
    }

}
