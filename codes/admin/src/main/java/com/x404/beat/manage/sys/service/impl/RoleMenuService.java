package com.x404.beat.manage.sys.service.impl;

import com.x404.beat.core.hibernate.query.HibernateQuery;
import com.x404.beat.manage.sys.dao.IRoleMenuDao;
import com.x404.beat.manage.sys.entity.RoleMenu;
import com.x404.beat.manage.sys.service.IRoleMenuService;
import com.x404.beat.core.page.ExPageList;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class RoleMenuService implements IRoleMenuService {
    @Autowired
    private IRoleMenuDao roleMenuDao;

    public IRoleMenuDao getRoleMenuDao() {
        return roleMenuDao;
    }

    public void setRoleMenuDao(IRoleMenuDao roleMenuDao) {
        this.roleMenuDao = roleMenuDao;
    }

    public List<RoleMenu> getRoleMenus(String roleId) {
        return roleMenuDao.getRoleMenus(roleId);
    }

    public void deleteRoleMenus(String roleId) {
        roleMenuDao.deleteRoleMenus(roleId);
    }

    public Session getSession() {
        return roleMenuDao.getSession();
    }

    public void saveOrUpdate(RoleMenu entity) {
        roleMenuDao.saveOrUpdate(entity);
    }

    public ExPageList<RoleMenu> getPageList(HibernateQuery cq,
                                            ExPageList<RoleMenu> page) {
        return roleMenuDao.getPageList(cq, page);
    }

    public Serializable save(RoleMenu entity) {
        return roleMenuDao.save(entity);
    }

    public void saveBatch(List<RoleMenu> entityList) {
        roleMenuDao.saveBatch(entityList);
    }

    public List<RoleMenu> findHql(String hql, Object... param) {
        return roleMenuDao.findHql(hql, param);
    }

    public int deleteById(Serializable id) {
        return roleMenuDao.deleteById(id);
    }

    public int executeHql(String hql, Object... param) {
        return roleMenuDao.executeHql(hql, param);
    }

    public void updateByIdSelective(RoleMenu entity) {
        roleMenuDao.updateByIdSelective(entity);
    }

    public List<RoleMenu> query(HibernateQuery cq) {
        return roleMenuDao.query(cq);
    }

    public RoleMenu findById(Serializable id) {
        return roleMenuDao.findById(id);
    }

    public List<RoleMenu> findByExample(RoleMenu entity) {
        return roleMenuDao.findByExample(entity);
    }

    public List<RoleMenu> findAll() {
        return roleMenuDao.findAll();
    }

    public void delete(RoleMenu entity) {
        roleMenuDao.delete(entity);
    }


}
