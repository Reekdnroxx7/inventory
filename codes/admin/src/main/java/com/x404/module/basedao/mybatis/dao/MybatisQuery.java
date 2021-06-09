package com.x404.module.basedao.mybatis.dao;

import java.util.ArrayList;
import java.util.List;


public class MybatisQuery {

    protected List<MybatisCriterion> criteria;

    public MybatisQuery() {
        super();
        criteria = new ArrayList<MybatisCriterion>();
    }

    public boolean isValid() {
        return criteria.size() > 0;
    }

    public List<MybatisCriterion> getAllCriteria() {
        return criteria;
    }

    public List<MybatisCriterion> getCriteria() {
        return criteria;
    }

    public MybatisQuery like(String property, Object value) {
        return addCriterion(property + " like ", value);
    }

    public MybatisQuery eq(String property, Object value) {
        return addCriterion(property + " = ", value);
    }

    public MybatisQuery gt(String property, Object value) {
        return addCriterion(property + " > ", value);
    }

    public MybatisQuery ge(String property, Object value) {
        return addCriterion(property + " >= ", value);
    }

    public MybatisQuery lt(String property, Object value) {
        return addCriterion(property + " < ", value);
    }

    public MybatisQuery le(String property, Object value) {
        return addCriterion(property + " <= ", value);
    }

    public MybatisQuery in(String property, Object value) {
        return addCriterion(property + " in ", value);
    }

    public MybatisQuery addCriterion(String condition) {
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        criteria.add(new MybatisCriterion(condition));
        return this;
    }

    public MybatisQuery addCriterion(String condition, Object value) {
        if (value == null) {
            throw new RuntimeException("Value for " + condition
                    + " cannot be null");
        }
        criteria.add(new MybatisCriterion(condition, value));
        return this;
    }

    public MybatisQuery addCriterion(String condition, Object value1, Object value2,
                                     String property) {
        if (value1 == null || value2 == null) {
            throw new RuntimeException("Between values for " + property
                    + " cannot be null");
        }
        criteria.add(new MybatisCriterion(condition, value1, value2));
        return this;
    }


}