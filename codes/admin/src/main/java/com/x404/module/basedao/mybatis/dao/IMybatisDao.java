/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.module.basedao.mybatis.dao;

import java.io.Serializable;
import java.util.List;


/**
 * DAO支持类
 *
 * @param <T>
 */
public interface IMybatisDao<T> {

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
     * @param example
     * @return
     */
    List<T> selectByExample(MybatisExample example);

    /**
     * 查总数
     *
     * @param example
     * @return
     */
    int countByExample(MybatisExample example);

    /**
     * 插入 @see {@link #insertSelective}
     *
     * @param entity
     */
    public void insert(T entity);

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
    public int deleteByKey(Serializable key);

    /**
     * 条件删除
     *
     * @param example
     * @return
     */
    int deleteByExample(MybatisExample example);

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
     */
    void updateByKeySelective(T t);

    /**
     * 条件更新
     *
     * @param t
     * @param example
     */
    void updateByExample(T t, MybatisExample example);

    /**
     * 条件更新
     *
     * @param t
     * @param example
     */
    void updateByExampleSelective(T t, MybatisExample example);
}
