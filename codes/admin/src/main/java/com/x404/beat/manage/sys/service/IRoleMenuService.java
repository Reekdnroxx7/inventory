package com.x404.beat.manage.sys.service;

import com.x404.beat.manage.sys.entity.RoleMenu;
import com.x404.beat.core.service.IHibernateService;

import java.util.List;

public interface IRoleMenuService extends IHibernateService<RoleMenu> {
    List<RoleMenu> getRoleMenus(String roleId);

    void deleteRoleMenus(String roleId);
}
