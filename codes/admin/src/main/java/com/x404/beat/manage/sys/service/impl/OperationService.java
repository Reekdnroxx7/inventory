package com.x404.beat.manage.sys.service.impl;

import com.x404.beat.core.hibernate.query.HibernateQuery;
import com.x404.beat.core.page.ExPageList;
import com.x404.beat.manage.sys.dao.IOprationDao;
import com.x404.beat.manage.sys.entity.Operation;
import com.x404.beat.manage.sys.service.IOperationService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张代浩
 */
@Transactional
@Service
public class OperationService implements IOperationService {
    @Autowired
    private IOprationDao oprationDao;

    public IOprationDao getOprationDao() {
        return oprationDao;
    }

    public void setOprationDao(IOprationDao oprationDao) {
        this.oprationDao = oprationDao;
    }

    public List<Operation> findAll() {
        return oprationDao.findAll();
    }

    public List<Operation> findByExample(Operation entity) {
        return oprationDao.findByExample(entity);
    }

    public Operation findById(Serializable id) {
        return oprationDao.findById(id);
    }


    public Session getSession() {
        return oprationDao.getSession();
    }

    public void saveOrUpdate(Operation entity) {
        oprationDao.saveOrUpdate(entity);
    }

    public ExPageList<Operation> getPageList(HibernateQuery cq,
                                             ExPageList<Operation> page) {
        return oprationDao.getPageList(cq, page);
    }

    public Serializable save(Operation entity) {
        return oprationDao.save(entity);
    }

    public void saveBatch(List<Operation> entityList) {
        oprationDao.saveBatch(entityList);
    }

    public List<Operation> findHql(String hql, Object... param) {
        return oprationDao.findHql(hql, param);
    }

    public int deleteById(Serializable id) {
        return oprationDao.deleteById(id);
    }

    public int executeHql(String hql, Object... param) {
        return oprationDao.executeHql(hql, param);
    }

    public void updateByIdSelective(Operation entity) {
        oprationDao.updateByIdSelective(entity);
    }

    public List<Operation> query(HibernateQuery cq) {
        return oprationDao.query(cq);
    }

    @Override
    public List<Operation> findByMenu(String menuId) {
        Operation p = new Operation();
        p.setMenuId(menuId);
        return this.findByExample(p);
    }

    public void delete(Operation entity) {
        oprationDao.delete(entity);
    }

}
