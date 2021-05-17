package com.x404.beat.manage.sys.service.impl;

import com.x404.beat.manage.sys.dao.IDictDao;
import com.x404.beat.manage.sys.entity.Dict;
import com.x404.beat.core.hibernate.query.HibernateQuery;
import com.x404.beat.core.page.ExPageList;
import com.x404.beat.manage.sys.service.IDictService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class DictService implements IDictService {
    @Autowired
    private IDictDao dictDao;

    public IDictDao getDictDao() {
        return dictDao;
    }

    public void setDictDao(IDictDao dictDao) {
        this.dictDao = dictDao;
    }

    public Session getSession() {
        return dictDao.getSession();
    }

    public void saveOrUpdate(Dict entity) {
        dictDao.saveOrUpdate(entity);
    }

    public ExPageList<Dict> getPageList(HibernateQuery cq, ExPageList<Dict> page) {
        return dictDao.getPageList(cq, page);
    }

    public Serializable save(Dict entity) {
        return dictDao.save(entity);
    }

    public void saveBatch(List<Dict> entityList) {
        dictDao.saveBatch(entityList);
    }

    public List<Dict> findHql(String hql, Object... param) {
        return dictDao.findHql(hql, param);
    }

    public int deleteById(Serializable id) {
        return dictDao.deleteById(id);
    }

    public int executeHql(String hql, Object... param) {
        return dictDao.executeHql(hql, param);
    }

    public void updateByIdSelective(Dict entity) {
        dictDao.updateByIdSelective(entity);
    }

    public List<Dict> query(HibernateQuery cq) {
        return dictDao.query(cq);
    }

    public Dict findById(Serializable id) {
        return dictDao.findById(id);
    }

    public List<Dict> findByExample(Dict entity) {
        return dictDao.findByExample(entity);
    }

    public List<Dict> findAll() {
        return dictDao.findAll();
    }

    public void delete(Dict entity) {
        dictDao.delete(entity);
    }


}
