package com.x404.beat.manage.codeg.entity;

import com.xc350.web.base.mybatis.dao.MybatisExample;

import java.util.List;


public class DatasourceConfigExample extends MybatisExample {

    public MybatisExample andIdIsNull() {
        addCriterion("id is null");
        return this;
    }

    public MybatisExample andIdIsNotNull() {
        addCriterion("id is not null");
        return (MybatisExample) this;
    }

    public MybatisExample andIdEqualTo(String value) {
        eq("id", value);
        return (MybatisExample) this;
    }

    public MybatisExample andIdGreaterThan(String value) {
        gt("id", value);
        return (MybatisExample) this;
    }

    public MybatisExample andIdGreaterThanOrEqualTo(String value) {
        ge("id", value);
        return (MybatisExample) this;
    }

    public MybatisExample andIdLessThan(String value) {
        lt("id", value);
        return (MybatisExample) this;
    }

    public MybatisExample andIdLessThanOrEqualTo(String value) {
        le("id", value);
        return (MybatisExample) this;
    }

    public MybatisExample andIdLike(String value) {
        like("id", value);
        return (MybatisExample) this;
    }

    public MybatisExample andIdIn(List<Object> values) {
        in("id", values);
        return this;
    }

    public MybatisExample andUrlIsNull() {
        addCriterion("url is null");
        return this;
    }

    public MybatisExample andUrlIsNotNull() {
        addCriterion("url is not null");
        return (MybatisExample) this;
    }

    public MybatisExample andUrlEqualTo(String value) {
        eq("url", value);
        return (MybatisExample) this;
    }

    public MybatisExample andUrlGreaterThan(String value) {
        gt("url", value);
        return (MybatisExample) this;
    }

    public MybatisExample andUrlGreaterThanOrEqualTo(String value) {
        ge("url", value);
        return (MybatisExample) this;
    }

    public MybatisExample andUrlLessThan(String value) {
        lt("url", value);
        return (MybatisExample) this;
    }

    public MybatisExample andUrlLessThanOrEqualTo(String value) {
        le("url", value);
        return (MybatisExample) this;
    }

    public MybatisExample andUrlLike(String value) {
        like("url", value);
        return (MybatisExample) this;
    }

    public MybatisExample andUrlIn(List<Object> values) {
        in("url", values);
        return this;
    }

    public MybatisExample andDbTypeIsNull() {
        addCriterion("db_type is null");
        return this;
    }

    public MybatisExample andDbTypeIsNotNull() {
        addCriterion("db_type is not null");
        return (MybatisExample) this;
    }

    public MybatisExample andDbTypeEqualTo(String value) {
        eq("db_type", value);
        return (MybatisExample) this;
    }

    public MybatisExample andDbTypeGreaterThan(String value) {
        gt("db_type", value);
        return (MybatisExample) this;
    }

    public MybatisExample andDbTypeGreaterThanOrEqualTo(String value) {
        ge("db_type", value);
        return (MybatisExample) this;
    }

    public MybatisExample andDbTypeLessThan(String value) {
        lt("db_type", value);
        return (MybatisExample) this;
    }

    public MybatisExample andDbTypeLessThanOrEqualTo(String value) {
        le("db_type", value);
        return (MybatisExample) this;
    }

    public MybatisExample andDbTypeLike(String value) {
        like("db_type", value);
        return (MybatisExample) this;
    }

    public MybatisExample andDbTypeIn(List<Object> values) {
        in("db_type", values);
        return this;
    }

    public MybatisExample andUserNameIsNull() {
        addCriterion("user_name is null");
        return this;
    }

    public MybatisExample andUserNameIsNotNull() {
        addCriterion("user_name is not null");
        return (MybatisExample) this;
    }

    public MybatisExample andUserNameEqualTo(String value) {
        eq("user_name", value);
        return (MybatisExample) this;
    }

    public MybatisExample andUserNameGreaterThan(String value) {
        gt("user_name", value);
        return (MybatisExample) this;
    }

    public MybatisExample andUserNameGreaterThanOrEqualTo(String value) {
        ge("user_name", value);
        return (MybatisExample) this;
    }

    public MybatisExample andUserNameLessThan(String value) {
        lt("user_name", value);
        return (MybatisExample) this;
    }

    public MybatisExample andUserNameLessThanOrEqualTo(String value) {
        le("user_name", value);
        return (MybatisExample) this;
    }

    public MybatisExample andUserNameLike(String value) {
        like("user_name", value);
        return (MybatisExample) this;
    }

    public MybatisExample andUserNameIn(List<Object> values) {
        in("user_name", values);
        return this;
    }

    public MybatisExample andPasswordIsNull() {
        addCriterion("password is null");
        return this;
    }

    public MybatisExample andPasswordIsNotNull() {
        addCriterion("password is not null");
        return (MybatisExample) this;
    }

    public MybatisExample andPasswordEqualTo(String value) {
        eq("password", value);
        return (MybatisExample) this;
    }

    public MybatisExample andPasswordGreaterThan(String value) {
        gt("password", value);
        return (MybatisExample) this;
    }

    public MybatisExample andPasswordGreaterThanOrEqualTo(String value) {
        ge("password", value);
        return (MybatisExample) this;
    }

    public MybatisExample andPasswordLessThan(String value) {
        lt("password", value);
        return (MybatisExample) this;
    }

    public MybatisExample andPasswordLessThanOrEqualTo(String value) {
        le("password", value);
        return (MybatisExample) this;
    }

    public MybatisExample andPasswordLike(String value) {
        like("password", value);
        return (MybatisExample) this;
    }

    public MybatisExample andPasswordIn(List<Object> values) {
        in("password", values);
        return this;
    }

    public MybatisExample andHostIsNull() {
        addCriterion("host is null");
        return this;
    }

    public MybatisExample andHostIsNotNull() {
        addCriterion("host is not null");
        return (MybatisExample) this;
    }

    public MybatisExample andHostEqualTo(String value) {
        eq("host", value);
        return (MybatisExample) this;
    }

    public MybatisExample andHostGreaterThan(String value) {
        gt("host", value);
        return (MybatisExample) this;
    }

    public MybatisExample andHostGreaterThanOrEqualTo(String value) {
        ge("host", value);
        return (MybatisExample) this;
    }

    public MybatisExample andHostLessThan(String value) {
        lt("host", value);
        return (MybatisExample) this;
    }

    public MybatisExample andHostLessThanOrEqualTo(String value) {
        le("host", value);
        return (MybatisExample) this;
    }

    public MybatisExample andHostLike(String value) {
        like("host", value);
        return (MybatisExample) this;
    }

    public MybatisExample andHostIn(List<Object> values) {
        in("host", values);
        return this;
    }


}
