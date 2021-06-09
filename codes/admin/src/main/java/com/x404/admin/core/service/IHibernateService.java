package com.x404.admin.core.service;

import com.x404.module.basedao.hibernate.HibernateQuery;
import com.x404.module.basedao.query.PageList;

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

    PageList<T> getPageList(HibernateQuery cq, PageList<T> page);

    List<T> findHql(String hql, Object... param);

    int executeHql(String hql, Object... param);

    List<T> query(HibernateQuery cq);

    public List<T> findByExample(T entity);
}