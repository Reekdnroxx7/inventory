package com.x404.module.basedao.mybatis.dao;

import java.util.ArrayList;
import java.util.List;


public class MybatisExample {

    protected boolean page = false;

    protected int start = 0;

    protected int limit = -1;

    protected String orderByClause;

    protected boolean distinct;

    protected List<MybatisQuery> oredCriteria;


    public MybatisExample() {
        oredCriteria = new ArrayList<MybatisQuery>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }


    public boolean isPage() {
        return page;
    }

    public void setPage(boolean page) {
        this.page = page;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<MybatisQuery> getOredCriteria() {
        return oredCriteria;
    }

    public MybatisExample or(MybatisQuery criteria) {
        oredCriteria.add(criteria);
        return this;
    }


    public MybatisExample or(MybatisCriterion criterion) {
        MybatisQuery criteria = createCriteriaInternal();
        criteria.getAllCriteria().add(criterion);
        oredCriteria.add(criteria);
        return this;
    }


    protected MybatisQuery createCriteriaInternal() {
        MybatisQuery criteria = new MybatisQuery();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected MybatisQuery getCurrentQuery() {
        if (oredCriteria.size() == 0) {
            oredCriteria.add(createCriteriaInternal());
        }
        return oredCriteria.get(oredCriteria.size() - 1);
    }

    public MybatisExample like(String property, Object value) {
        getCurrentQuery().like(property, value);
        return this;
    }

    public MybatisExample eq(String property, Object value) {
        getCurrentQuery().eq(property, value);
        return this;
    }

    public MybatisExample gt(String property, Object value) {
        getCurrentQuery().gt(property, value);
        return this;
    }

    public MybatisExample ge(String property, Object value) {
        getCurrentQuery().ge(property, value);
        return this;
    }

    public MybatisExample lt(String property, Object value) {
        getCurrentQuery().lt(property, value);
        return this;
    }

    public MybatisExample le(String property, Object value) {
        getCurrentQuery().le(property, value);
        return this;
    }

    public MybatisExample in(String property, Object value) {
        getCurrentQuery().in(property, value);
        return this;
    }

    public MybatisExample addCriterion(String condition) {
        getCurrentQuery().addCriterion(condition);
        return this;
    }

    public MybatisExample addCriterion(String condition, Object value) {
        getCurrentQuery().addCriterion(condition, value);
        return this;
    }

    public MybatisExample addCriterion(String condition, Object value1,
                                       Object value2, String property) {
        getCurrentQuery().addCriterion(condition, value1, value2, property);
        return this;
    }

}