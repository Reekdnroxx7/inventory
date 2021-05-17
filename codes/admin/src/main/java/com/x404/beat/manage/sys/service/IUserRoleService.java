package com.x404.beat.manage.sys.service;

import com.x404.beat.manage.sys.entity.Role;
import com.x404.beat.manage.sys.entity.UserRole;
import com.x404.beat.core.service.IHibernateService;
import com.x404.beat.manage.sys.entity.User;

import java.util.List;

/**
 * @author 张代浩
 */
public interface IUserRoleService extends IHibernateService<UserRole> {

    List<Role> getAssginRoles(User u);

    List<UserRole> getAuthRoles(User u);

    void addSaveDelBatch(List<UserRole> delRoles, List<UserRole> saveRoles,
                         List<UserRole> updateRoles);

    /**
     * 删除角色时根据角色ID删除对应的用户角色信息
     */
    void deleteUserRoleByRoleId(String roleId);


}
