package com.x404.admin.manage.sys.service.impl;

import com.x404.admin.manage.sys.entity.Role;
import com.x404.module.basedao.hibernate.HibernateQuery;
import com.x404.module.basedao.query.PageList;
import com.x404.admin.manage.sys.dao.IRoleDao;
import com.x404.admin.manage.sys.service.IRoleService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class RoleService implements IRoleService {
    @Autowired
    private IRoleDao roleDao;

    public IRoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Session getSession() {
        return roleDao.getSession();
    }

    public void saveOrUpdate(Role entity) {
        roleDao.saveOrUpdate(entity);
    }

    public PageList<Role> getPageList(HibernateQuery cq, PageList<Role> page) {
        return roleDao.getPageList(cq, page);
    }

    public Serializable save(Role entity) {
        return roleDao.save(entity);
    }

    public void saveBatch(List<Role> entityList) {
        roleDao.saveBatch(entityList);
    }

    public List<Role> findHql(String hql, Object... param) {
        return roleDao.findHql(hql, param);
    }

    public int deleteById(Serializable id) {
        return roleDao.deleteById(id);
    }

    public int executeHql(String hql, Object... param) {
        return roleDao.executeHql(hql, param);
    }

    public void updateByIdSelective(Role entity) {
        roleDao.updateByIdSelective(entity);
    }

    public List<Role> query(HibernateQuery cq) {
        return roleDao.query(cq);
    }

    public Role findById(Serializable id) {
        return roleDao.findById(id);
    }

    public List<Role> findByExample(Role entity) {
        return roleDao.findByExample(entity);
    }

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    public void delete(Role entity) {
        roleDao.delete(entity);
    }


}
