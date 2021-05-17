package com.x404.beat.manage.sys.dao;

import com.x404.beat.manage.sys.entity.RoleMenu;
import com.x404.beat.core.hibernate.dao.IHibernateDao;

import java.util.List;

public interface IRoleMenuDao extends IHibernateDao<RoleMenu> {
    List<RoleMenu> getRoleMenus(String roleId);

    void deleteRoleMenus(String roleId);

}
