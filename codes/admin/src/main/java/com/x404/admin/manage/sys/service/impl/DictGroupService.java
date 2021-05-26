package com.x404.admin.manage.sys.service.impl;

import com.x404.admin.manage.sys.dao.IDictGroupDao;
import com.x404.admin.manage.sys.service.IDictGroupService;
import com.x404.admin.core.hibernate.query.HibernateQuery;
import com.x404.admin.core.page.ExPageList;
import com.x404.admin.manage.sys.entity.DictGroup;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class DictGroupService implements IDictGroupService {
    @Autowired
    private IDictGroupDao dictGroupDao;

    public IDictGroupDao getDictGroupDao() {
        return dictGroupDao;
    }

    public void setDictGroupDao(IDictGroupDao dictGroupDao) {
        this.dictGroupDao = dictGroupDao;
    }

    public Session getSession() {
        return dictGroupDao.getSession();
    }

    public void saveOrUpdate(DictGroup entity) {
        dictGroupDao.saveOrUpdate(entity);
    }


    public List<DictGroup> findAll() {
        return dictGroupDao.findAll();
    }

    public ExPageList<DictGroup> getPageList(HibernateQuery cq,
                                             ExPageList<DictGroup> page) {
        return dictGroupDao.getPageList(cq, page);
    }

    public List<DictGroup> findByExample(DictGroup entity) {
        return dictGroupDao.findByExample(entity);
    }

    public DictGroup findById(Serializable id) {
        return dictGroupDao.findById(id);
    }

    public List<DictGroup> findHql(String hql, Object... param) {
        return dictGroupDao.findHql(hql, param);
    }

    public Serializable save(DictGroup entity) {
        return dictGroupDao.save(entity);
    }

    public void saveBatch(List<DictGroup> entityList) {
        dictGroupDao.saveBatch(entityList);
    }

    public int deleteById(Serializable id) {
        return dictGroupDao.deleteById(id);
    }

    public int executeHql(String hql, Object... param) {
        return dictGroupDao.executeHql(hql, param);
    }

    public void updateByIdSelective(DictGroup entity) {
        dictGroupDao.updateByIdSelective(entity);
    }

    public List<DictGroup> query(HibernateQuery cq) {
        return dictGroupDao.query(cq);
    }

    public void delete(DictGroup entity) {
        dictGroupDao.delete(entity);
    }


}
