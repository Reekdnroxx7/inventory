package com.x404.admin.core.service;

import com.x404.admin.core.hibernate.query.HibernateQuery;
import com.x404.admin.core.page.ExPageList;

import java.io.Serializable;
import java.util.List;

/**
 * DAO支持类实现
 *
 * @param <T>
 * @author ThinkGem
 * @version 2013-05-15
 */
public interface IHibernateService<T> extends BaseService<T> {
    public Serializable save(T entity);

    void saveOrUpdate(T entity);

    void delete(T entity);

    ExPageList<T> getPageList(HibernateQuery cq, ExPageList<T> page);

    List<T> findHql(String hql, Object... param);

    int executeHql(String hql, Object... param);

    List<T> query(HibernateQuery cq);

    public List<T> findByExample(T entity);
}