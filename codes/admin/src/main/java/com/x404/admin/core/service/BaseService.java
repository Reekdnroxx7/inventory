package com.x404.admin.core.service;

import java.io.Serializable;
import java.util.List;

/**
 * DAO支持类实现
 *
 * @param <T>
 * @author ThinkGem
 * @version 2013-05-15
 */
public interface BaseService<T> {
    public Serializable save(T entity);

    public void saveBatch(List<T> entityList);

    public int deleteById(Serializable id);

    public void updateByIdSelective(T entity);

    public T findById(Serializable id);

    public List<T> findAll();
}