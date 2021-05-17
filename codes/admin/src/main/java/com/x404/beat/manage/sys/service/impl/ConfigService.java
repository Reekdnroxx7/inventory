package com.x404.beat.manage.sys.service.impl;

import com.x404.beat.core.hibernate.query.HibernateQuery;
import com.x404.beat.core.page.ExPageList;
import com.x404.beat.manage.sys.dao.IConfigDao;
import com.x404.beat.manage.sys.entity.Config;
import com.x404.beat.manage.sys.service.IConfigService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class ConfigService implements IConfigService {
    @Autowired
    private IConfigDao configDao;

    public IConfigDao getConfigDao() {
        return configDao;
    }

    public void setConfigDao(IConfigDao configDao) {
        this.configDao = configDao;
    }

    public Session getSession() {
        return configDao.getSession();
    }

    public void saveOrUpdate(Config entity) {
        configDao.saveOrUpdate(entity);
    }


    public List<Config> findAll() {
        return configDao.findAll();
    }

    public ExPageList<Config> getPageList(HibernateQuery cq,
                                          ExPageList<Config> page) {
        return configDao.getPageList(cq, page);
    }

    public List<Config> findByExample(Config entity) {
        return configDao.findByExample(entity);
    }

    public Config findById(Serializable id) {
        return configDao.findById(id);
    }

    public List<Config> findHql(String hql, Object... param) {
        return configDao.findHql(hql, param);
    }

    public Serializable save(Config entity) {
        return configDao.save(entity);
    }

    public int executeHql(String hql, Object... param) {
        return configDao.executeHql(hql, param);
    }

    public void saveBatch(List<Config> entityList) {
        configDao.saveBatch(entityList);
    }


    public int deleteById(Serializable id) {
        return configDao.deleteById(id);
    }

    public void updateByIdSelective(Config entity) {
        configDao.updateByIdSelective(entity);
    }

    public List<Config> query(HibernateQuery cq) {
        return configDao.query(cq);
    }

    public void delete(Config entity) {
        configDao.delete(entity);
    }


    @Override
    public void updateByKey(Config config) {
        this.configDao.updateByKey(config);
    }
}
