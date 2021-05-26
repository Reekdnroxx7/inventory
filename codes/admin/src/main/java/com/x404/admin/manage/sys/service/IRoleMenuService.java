package com.x404.admin.manage.sys.service;

import com.x404.admin.manage.sys.entity.RoleMenu;
import com.x404.admin.core.service.IHibernateService;

import java.util.List;

public interface IRoleMenuService extends IHibernateService<RoleMenu> {
    List<RoleMenu> getRoleMenus(String roleId);

    void deleteRoleMenus(String roleId);
}
