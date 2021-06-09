package com.x404.admin.manage.sys.dao;

import com.x404.admin.manage.sys.entity.RoleMenu;
import com.x404.module.basedao.hibernate.IHibernateDao;

import java.util.List;

public interface IRoleMenuDao extends IHibernateDao<RoleMenu> {
    List<RoleMenu> getRoleMenus(String roleId);

    void deleteRoleMenus(String roleId);

}
