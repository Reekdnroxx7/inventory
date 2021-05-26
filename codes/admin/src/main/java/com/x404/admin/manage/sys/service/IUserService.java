package com.x404.admin.manage.sys.service;

import com.x404.admin.manage.sys.entity.RoleMenu;
import com.x404.admin.manage.sys.entity.User;
import com.x404.admin.core.service.IHibernateService;

import java.util.List;

/**
 * @author 张代浩
 */
public interface IUserService extends IHibernateService<User> {

    public User checkUserExits(User user);

    public List<RoleMenu> getAccessMenus(User u);

//	List<UserRole> getAssginRoles(User u);

//	public List<RoleMenu> getAssignMenus(User u);
//	public List<Role> getUserRole(User user);
//	public void pwdInit(User user, String newPwd);
//	public int getUsersOfThisRole(String id);
}
