package com.x404.admin.manage.sys.service.impl;

import com.x404.admin.core.hibernate.query.HibernateQuery;
import com.x404.admin.core.page.ExPageList;
import com.x404.admin.manage.sys.entity.Icon;
import com.x404.admin.manage.sys.dao.IIconDao;
import com.x404.admin.manage.sys.service.IIconService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class IconService implements IIconService {
    @Autowired
    private IIconDao iconDao;

    public IIconDao getIconDao() {
        return iconDao;
    }

    public void setIconDao(IIconDao iconDao) {
        this.iconDao = iconDao;
    }

    public List<Icon> findAll() {
        return iconDao.findAll();
    }

    public List<Icon> findByExample(Icon entity) {
        return iconDao.findByExample(entity);
    }

    public Icon findById(Serializable id) {
        return iconDao.findById(id);
    }

    public Session getSession() {
        return iconDao.getSession();
    }

    public void saveOrUpdate(Icon entity) {
        iconDao.saveOrUpdate(entity);
    }

    public ExPageList<Icon> getPageList(HibernateQuery cq, ExPageList<Icon> page) {
        return iconDao.getPageList(cq, page);
    }

    public Serializable save(Icon entity) {
        return iconDao.save(entity);
    }

    public void saveBatch(List<Icon> entityList) {
        iconDao.saveBatch(entityList);
    }

    public List<Icon> findHql(String hql, Object... param) {
        return iconDao.findHql(hql, param);
    }

    public int deleteById(Serializable id) {
        return iconDao.deleteById(id);
    }

    public int executeHql(String hql, Object... param) {
        return iconDao.executeHql(hql, param);
    }

    public void updateByIdSelective(Icon entity) {
        iconDao.updateByIdSelective(entity);
    }

    public List<Icon> query(HibernateQuery cq) {
        return iconDao.query(cq);
    }

    public void delete(Icon entity) {
        iconDao.delete(entity);
    }


}
