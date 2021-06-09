package com.x404.module.basedao.mongo.dao;

import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.List;


/**
 * DAO支持类
 *
 * @param <T>
 */
public interface IMongoDao<T> {

    /**
     * 主键查询
     *
     * @param key
     * @return
     */
    public T selectByKey(Serializable key);

    /**
     * 查询所有
     *
     * @return
     */
    public List<T> selectAll();

    /**
     * 如果带有分页 此分页仅支持mysql 建议用pageList
     *
     * @param query
     * @return
     */
    List<T> selectByQuery(Query query);

    /**
     * 查总数
     *
     * @param query
     * @return
     */
    int countByQuery(Query query);

    /**
     * 插入 @see {@link #insertSelective}
     *
     * @param entity
     */
    public void insert(T entity);


    public void upsert(T entity);

    /**
     * 选择插入
     *
     * @param entity
     */
    public void insertSelective(T entity);

    /**
     * 批量插入
     *
     * @param entityList
     */
    public void insertBatch(List<T> entityList);

    /**
     * 主键删除
     *
     * @param key
     * @return
     */
    public void deleteByKey(Serializable key);

    /**
     * 条件删除
     *
     * @param query
     * @return
     */
    void deleteByQuery(Query query);

    /**
     * 主键更新
     *
     * @param t
     */
    void updateByKey(T t);

    /**
     * 主键更新
     *
     * @param t
     * @return
     */
    void updateByKeySelective(T t);

    /**
     * 条件更新
     *
     * @param t
     * @param query
     */
    void updateByQuery(T t, Query query);

    /**
     * 条件更新
     *
     * @param t
     * @param query
     * @return
     */
    void updateByQuerySelective(T t, Query query);
}
