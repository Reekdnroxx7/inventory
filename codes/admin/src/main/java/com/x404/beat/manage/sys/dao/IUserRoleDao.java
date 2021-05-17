package com.x404.beat.manage.sys.dao;

import com.x404.beat.manage.sys.entity.Role;
import com.x404.beat.manage.sys.entity.User;
import com.x404.beat.manage.sys.entity.UserRole;
import com.x404.beat.core.hibernate.dao.IHibernateDao;

import java.util.List;


public interface IUserRoleDao extends IHibernateDao<UserRole> {

    /**
     * @param u
     * @return 可以授权的角色
     */
    List<Role> getAssginRoles(User u);

    /**
     * @param
     * @return 所有分配的角色；
     */
    List<UserRole> getAuthRoles(User u);

    void addSaveDelBatch(List<UserRole> delRoles, List<UserRole> saveRoles,
                         List<UserRole> updateRoles);

    /**
     * 删除角色时根据角色ID删除对应的用户角色信息
     */
    void deleteUserRoleByRoleId(String roleId);


}