package com.x404.beat.manage.sys.service.impl;

import com.x404.beat.manage.sys.service.IOrgService;
import com.x404.beat.core.hibernate.query.HibernateQuery;
import com.x404.beat.core.page.ExPageList;
import com.x404.beat.manage.sys.dao.IOrgDao;
import com.x404.beat.manage.sys.entity.Org;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;


@Service
@Transactional
public class OrgService implements IOrgService {
    @Autowired
    private IOrgDao orgDao;


    public IOrgDao getOrgDao() {
        return orgDao;
    }

    public void setOrgDao(IOrgDao orgDao) {
        this.orgDao = orgDao;
    }

    public int deleteById(Serializable id) {
        return orgDao.deleteById(id);
    }

    public int executeHql(String hql, Object... param) {
        return orgDao.executeHql(hql, param);
    }

    public List<Org> findAll() {
        return orgDao.findAll();
    }

    public List<Org> findHql(String hql, Object... param) {
        return orgDao.findHql(hql, param);
    }

    public Org findById(Serializable id) {
        return orgDao.findById(id);
    }

    public ExPageList<Org> getPageList(HibernateQuery cq, ExPageList<Org> page) {
        return orgDao.getPageList(cq, page);
    }


    public Session getSession() {
        return orgDao.getSession();
    }

    public void saveOrUpdate(Org entity) {
        orgDao.saveOrUpdate(entity);
    }

    public Serializable save(Org entity) {
        return orgDao.save(entity);
    }

    public void saveBatch(List<Org> entityList) {
        orgDao.saveBatch(entityList);
    }

    public void updateByIdSelective(Org entity) {
        orgDao.updateByIdSelective(entity);
    }

    public List<Org> query(HibernateQuery cq) {
        return orgDao.query(cq);
    }

    public List<Org> findByExample(Org entity) {
        return orgDao.findByExample(entity);
    }

    @Override
    public List<Org> getChildOrgs(String parentId) {
        Org o = new Org();
        o.setParentId(parentId);
        return this.orgDao.findByExample(o);
    }
//

    public void delete(Org entity) {
        orgDao.delete(entity);
    }


}
