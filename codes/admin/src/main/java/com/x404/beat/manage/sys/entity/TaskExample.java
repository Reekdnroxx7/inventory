package com.x404.beat.manage.sys.entity;

import com.xc350.web.base.mybatis.dao.MybatisExample;

import java.util.List;


public class TaskExample extends MybatisExample {

    public TaskExample andIdIsNull() {
        addCriterion("id is null");
        return this;
    }

    public TaskExample andIdIsNotNull() {
        addCriterion("id is not null");
        return this;
    }

    public TaskExample andIdEqualTo(String value) {
        eq("id", value);
        return this;
    }

    public TaskExample andIdGreaterThan(String value) {
        gt("id", value);
        return this;
    }

    public TaskExample andIdGreaterThanOrEqualTo(String value) {
        ge("id", value);
        return this;
    }

    public TaskExample andIdLessThan(String value) {
        lt("id", value);
        return this;
    }

    public TaskExample andIdLessThanOrEqualTo(String value) {
        le("id", value);
        return this;
    }

    public TaskExample andIdLike(String value) {
        like("id", value);
        return this;
    }

    public TaskExample andIdIn(List<Object> values) {
        in("id", values);
        return this;
    }

    public TaskExample andNameIsNull() {
        addCriterion("name is null");
        return this;
    }

    public TaskExample andNameIsNotNull() {
        addCriterion("name is not null");
        return this;
    }

    public TaskExample andNameEqualTo(String value) {
        eq("name", value);
        return this;
    }

    public TaskExample andNameGreaterThan(String value) {
        gt("name", value);
        return this;
    }

    public TaskExample andNameGreaterThanOrEqualTo(String value) {
        ge("name", value);
        return this;
    }

    public TaskExample andNameLessThan(String value) {
        lt("name", value);
        return this;
    }

    public TaskExample andNameLessThanOrEqualTo(String value) {
        le("name", value);
        return this;
    }

    public TaskExample andNameLike(String value) {
        like("name", value);
        return this;
    }

    public TaskExample andNameIn(List<Object> values) {
        in("name", values);
        return this;
    }

    public TaskExample andBeanNameIsNull() {
        addCriterion("bean_name is null");
        return this;
    }

    public TaskExample andBeanNameIsNotNull() {
        addCriterion("bean_name is not null");
        return this;
    }

    public TaskExample andBeanNameEqualTo(String value) {
        eq("bean_name", value);
        return this;
    }

    public TaskExample andBeanNameGreaterThan(String value) {
        gt("bean_name", value);
        return this;
    }

    public TaskExample andBeanNameGreaterThanOrEqualTo(String value) {
        ge("bean_name", value);
        return this;
    }

    public TaskExample andBeanNameLessThan(String value) {
        lt("bean_name", value);
        return this;
    }

    public TaskExample andBeanNameLessThanOrEqualTo(String value) {
        le("bean_name", value);
        return this;
    }

    public TaskExample andBeanNameLike(String value) {
        like("bean_name", value);
        return this;
    }

    public TaskExample andBeanNameIn(List<Object> values) {
        in("bean_name", values);
        return this;
    }

    public TaskExample andMethodNameIsNull() {
        addCriterion("method_name is null");
        return this;
    }

    public TaskExample andMethodNameIsNotNull() {
        addCriterion("method_name is not null");
        return this;
    }

    public TaskExample andMethodNameEqualTo(String value) {
        eq("method_name", value);
        return this;
    }

    public TaskExample andMethodNameGreaterThan(String value) {
        gt("method_name", value);
        return this;
    }

    public TaskExample andMethodNameGreaterThanOrEqualTo(String value) {
        ge("method_name", value);
        return this;
    }

    public TaskExample andMethodNameLessThan(String value) {
        lt("method_name", value);
        return this;
    }

    public TaskExample andMethodNameLessThanOrEqualTo(String value) {
        le("method_name", value);
        return this;
    }

    public TaskExample andMethodNameLike(String value) {
        like("method_name", value);
        return this;
    }

    public TaskExample andMethodNameIn(List<Object> values) {
        in("method_name", values);
        return this;
    }

    public TaskExample andCronExpressionIsNull() {
        addCriterion("cron_expression is null");
        return this;
    }

    public TaskExample andCronExpressionIsNotNull() {
        addCriterion("cron_expression is not null");
        return this;
    }

    public TaskExample andCronExpressionEqualTo(String value) {
        eq("cron_expression", value);
        return this;
    }

    public TaskExample andCronExpressionGreaterThan(String value) {
        gt("cron_expression", value);
        return this;
    }

    public TaskExample andCronExpressionGreaterThanOrEqualTo(String value) {
        ge("cron_expression", value);
        return this;
    }

    public TaskExample andCronExpressionLessThan(String value) {
        lt("cron_expression", value);
        return this;
    }

    public TaskExample andCronExpressionLessThanOrEqualTo(String value) {
        le("cron_expression", value);
        return this;
    }

    public TaskExample andCronExpressionLike(String value) {
        like("cron_expression", value);
        return this;
    }

    public TaskExample andCronExpressionIn(List<Object> values) {
        in("cron_expression", values);
        return this;
    }

    public TaskExample andStatusIsNull() {
        addCriterion("status is null");
        return this;
    }

    public TaskExample andStatusIsNotNull() {
        addCriterion("status is not null");
        return this;
    }

    public TaskExample andStatusEqualTo(String value) {
        eq("status", value);
        return this;
    }

    public TaskExample andStatusGreaterThan(String value) {
        gt("status", value);
        return this;
    }

    public TaskExample andStatusGreaterThanOrEqualTo(String value) {
        ge("status", value);
        return this;
    }

    public TaskExample andStatusLessThan(String value) {
        lt("status", value);
        return this;
    }

    public TaskExample andStatusLessThanOrEqualTo(String value) {
        le("status", value);
        return this;
    }

    public TaskExample andStatusLike(String value) {
        like("status", value);
        return this;
    }

    public TaskExample andStatusIn(List<Object> values) {
        in("status", values);
        return this;
    }

    public TaskExample andCreateTimeIsNull() {
        addCriterion("create_time is null");
        return this;
    }

    public TaskExample andCreateTimeIsNotNull() {
        addCriterion("create_time is not null");
        return this;
    }

    public TaskExample andCreateTimeEqualTo(java.util.Date value) {
        eq("create_time", value);
        return this;
    }

    public TaskExample andCreateTimeGreaterThan(java.util.Date value) {
        gt("create_time", value);
        return this;
    }

    public TaskExample andCreateTimeGreaterThanOrEqualTo(java.util.Date value) {
        ge("create_time", value);
        return this;
    }

    public TaskExample andCreateTimeLessThan(java.util.Date value) {
        lt("create_time", value);
        return this;
    }

    public TaskExample andCreateTimeLessThanOrEqualTo(java.util.Date value) {
        le("create_time", value);
        return this;
    }

    public TaskExample andCreateTimeLike(java.util.Date value) {
        like("create_time", value);
        return this;
    }

    public TaskExample andCreateTimeIn(List<Object> values) {
        in("create_time", values);
        return this;
    }

    public TaskExample andUpdateTimeIsNull() {
        addCriterion("update_time is null");
        return this;
    }

    public TaskExample andUpdateTimeIsNotNull() {
        addCriterion("update_time is not null");
        return this;
    }

    public TaskExample andUpdateTimeEqualTo(java.util.Date value) {
        eq("update_time", value);
        return this;
    }

    public TaskExample andUpdateTimeGreaterThan(java.util.Date value) {
        gt("update_time", value);
        return this;
    }

    public TaskExample andUpdateTimeGreaterThanOrEqualTo(java.util.Date value) {
        ge("update_time", value);
        return this;
    }

    public TaskExample andUpdateTimeLessThan(java.util.Date value) {
        lt("update_time", value);
        return this;
    }

    public TaskExample andUpdateTimeLessThanOrEqualTo(java.util.Date value) {
        le("update_time", value);
        return this;
    }

    public TaskExample andUpdateTimeLike(java.util.Date value) {
        like("update_time", value);
        return this;
    }

    public TaskExample andUpdateTimeIn(List<Object> values) {
        in("update_time", values);
        return this;
    }


}
