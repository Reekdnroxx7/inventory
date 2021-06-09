package com.x404.module.basedao.hibernate;

import com.x404.module.basedao.query.PageList;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;


public interface IHibernateDao<T> {

    public void saveBatch(List<T> entityList);

    public int deleteById(Serializable id);

    public T findById(Serializable id);

    public List<T> findAll();


    Session getSession();

    Serializable save(T entity);

    void saveOrUpdate(T entity);

    public void updateByIdSelective(T entity);

    void delete(T entity);

    PageList<T> getPageList(HibernateQuery cq, PageList<T> page);

    List<T> findHql(String hql, Object... param);

    int executeHql(String hql, Object... param);

    List<T> query(HibernateQuery cq);

    public List<T> findByExample(T entity);

}
