package com.x404.admin.manage.sys.service.impl;

import com.x404.admin.core.hibernate.query.HibernateQuery;
import com.x404.admin.core.page.ExPageList;
import com.x404.admin.manage.sys.dao.IUserRoleDao;
import com.x404.admin.manage.sys.entity.User;
import com.x404.admin.manage.sys.service.IUserRoleService;
import com.x404.admin.manage.sys.entity.Role;
import com.x404.admin.manage.sys.entity.UserRole;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class UserRoleService implements IUserRoleService
{

    @Autowired
    private IUserRoleDao userRoleDao;

    public IUserRoleDao getUserRoleDao() {
        return userRoleDao;
    }

    public void setUserRoleDao(IUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    public Session getSession() {
        return userRoleDao.getSession();
    }

    public List<Role> getAssginRoles(User u) {
        return userRoleDao.getAssginRoles(u);
    }

    public void saveOrUpdate(UserRole entity) {
        userRoleDao.saveOrUpdate(entity);
    }

    public ExPageList<UserRole> getPageList(HibernateQuery cq,
                                            ExPageList<UserRole> page) {
        return userRoleDao.getPageList(cq, page);
    }

    public Serializable save(UserRole entity) {
        return userRoleDao.save(entity);
    }

    public List<UserRole> getAuthRoles(User u) {
        return userRoleDao.getAuthRoles(u);
    }

    public void saveBatch(List<UserRole> entityList) {
        userRoleDao.saveBatch(entityList);
    }

    public List<UserRole> findHql(String hql, Object... param) {
        return userRoleDao.findHql(hql, param);
    }

    public int deleteById(Serializable id) {
        return userRoleDao.deleteById(id);
    }

    public int executeHql(String hql, Object... param) {
        return userRoleDao.executeHql(hql, param);
    }

    public void addSaveDelBatch(List<UserRole> delRoles,
                                List<UserRole> saveRoles, List<UserRole> updateRoles) {
        userRoleDao.addSaveDelBatch(delRoles, saveRoles, updateRoles);
    }

    public void updateByIdSelective(UserRole entity) {
        userRoleDao.updateByIdSelective(entity);
    }

    public List<UserRole> query(HibernateQuery cq) {
        return userRoleDao.query(cq);
    }

    public UserRole findById(Serializable id) {
        return userRoleDao.findById(id);
    }

    public List<UserRole> findByExample(UserRole entity) {
        return userRoleDao.findByExample(entity);
    }

    public List<UserRole> findAll() {
        return userRoleDao.findAll();
    }

    public void deleteUserRoleByRoleId(String roleId) {
        userRoleDao.deleteUserRoleByRoleId(roleId);
    }

    public void delete(UserRole entity) {
        userRoleDao.delete(entity);
    }


}
