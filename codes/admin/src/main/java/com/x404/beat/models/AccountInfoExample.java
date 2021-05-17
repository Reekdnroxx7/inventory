package com.x404.beat.models;

import com.xc350.web.base.mybatis.dao.MybatisExample;

import java.util.List;



public class AccountInfoExample extends MybatisExample {

	public AccountInfoExample andIdIsNull() {
		addCriterion("id is null");
		return this;
	}

	public AccountInfoExample andIdIsNotNull() {
		addCriterion("id is not null");
		return  this;
	}

	public AccountInfoExample andIdEqualTo(Long value) {
		eq("id", value);
		return this;
	}

	public AccountInfoExample andIdGreaterThan(Long value) {
		gt("id", value);
		return  this;
	}

	public AccountInfoExample andIdGreaterThanOrEqualTo(Long value) {
		ge("id", value);
		return  this;
	}

	public AccountInfoExample andIdLessThan(Long value) {
		lt("id", value);
		return  this;
	}

	public AccountInfoExample andIdLessThanOrEqualTo(Long value) {
		le("id", value);
		return  this;
	}

	public AccountInfoExample andIdLike(Long value) {
		like("id", value);
		return  this;
	}

	public AccountInfoExample andIdIn(List<Object> values) {
		in("id", values);
		return this;
	}

	public AccountInfoExample andBalanceIsNull() {
		addCriterion("balance is null");
		return this;
	}

	public AccountInfoExample andBalanceIsNotNull() {
		addCriterion("balance is not null");
		return  this;
	}

	public AccountInfoExample andBalanceEqualTo(Double value) {
		eq("balance", value);
		return this;
	}

	public AccountInfoExample andBalanceGreaterThan(Double value) {
		gt("balance", value);
		return  this;
	}

	public AccountInfoExample andBalanceGreaterThanOrEqualTo(Double value) {
		ge("balance", value);
		return  this;
	}

	public AccountInfoExample andBalanceLessThan(Double value) {
		lt("balance", value);
		return  this;
	}

	public AccountInfoExample andBalanceLessThanOrEqualTo(Double value) {
		le("balance", value);
		return  this;
	}

	public AccountInfoExample andBalanceLike(Double value) {
		like("balance", value);
		return  this;
	}

	public AccountInfoExample andBalanceIn(List<Object> values) {
		in("balance", values);
		return this;
	}

	public AccountInfoExample andInfoIsNull() {
		addCriterion("info is null");
		return this;
	}

	public AccountInfoExample andInfoIsNotNull() {
		addCriterion("info is not null");
		return  this;
	}

	public AccountInfoExample andInfoEqualTo(String value) {
		eq("info", value);
		return this;
	}

	public AccountInfoExample andInfoGreaterThan(String value) {
		gt("info", value);
		return  this;
	}

	public AccountInfoExample andInfoGreaterThanOrEqualTo(String value) {
		ge("info", value);
		return  this;
	}

	public AccountInfoExample andInfoLessThan(String value) {
		lt("info", value);
		return  this;
	}

	public AccountInfoExample andInfoLessThanOrEqualTo(String value) {
		le("info", value);
		return  this;
	}

	public AccountInfoExample andInfoLike(String value) {
		like("info", value);
		return  this;
	}

	public AccountInfoExample andInfoIn(List<Object> values) {
		in("info", values);
		return this;
	}

	public AccountInfoExample andNameIsNull() {
		addCriterion("name is null");
		return this;
	}

	public AccountInfoExample andNameIsNotNull() {
		addCriterion("name is not null");
		return  this;
	}

	public AccountInfoExample andNameEqualTo(String value) {
		eq("name", value);
		return this;
	}

	public AccountInfoExample andNameGreaterThan(String value) {
		gt("name", value);
		return  this;
	}

	public AccountInfoExample andNameGreaterThanOrEqualTo(String value) {
		ge("name", value);
		return  this;
	}

	public AccountInfoExample andNameLessThan(String value) {
		lt("name", value);
		return  this;
	}

	public AccountInfoExample andNameLessThanOrEqualTo(String value) {
		le("name", value);
		return  this;
	}

	public AccountInfoExample andNameLike(String value) {
		like("name", value);
		return  this;
	}

	public AccountInfoExample andNameIn(List<Object> values) {
		in("name", values);
		return this;
	}

	public AccountInfoExample andPasswordIsNull() {
		addCriterion("password is null");
		return this;
	}

	public AccountInfoExample andPasswordIsNotNull() {
		addCriterion("password is not null");
		return  this;
	}

	public AccountInfoExample andPasswordEqualTo(String value) {
		eq("password", value);
		return this;
	}

	public AccountInfoExample andPasswordGreaterThan(String value) {
		gt("password", value);
		return  this;
	}

	public AccountInfoExample andPasswordGreaterThanOrEqualTo(String value) {
		ge("password", value);
		return  this;
	}

	public AccountInfoExample andPasswordLessThan(String value) {
		lt("password", value);
		return  this;
	}

	public AccountInfoExample andPasswordLessThanOrEqualTo(String value) {
		le("password", value);
		return  this;
	}

	public AccountInfoExample andPasswordLike(String value) {
		like("password", value);
		return  this;
	}

	public AccountInfoExample andPasswordIn(List<Object> values) {
		in("password", values);
		return this;
	}

	public AccountInfoExample andPlatformIsNull() {
		addCriterion("platform is null");
		return this;
	}

	public AccountInfoExample andPlatformIsNotNull() {
		addCriterion("platform is not null");
		return  this;
	}

	public AccountInfoExample andPlatformEqualTo(String value) {
		eq("platform", value);
		return this;
	}

	public AccountInfoExample andPlatformGreaterThan(String value) {
		gt("platform", value);
		return  this;
	}

	public AccountInfoExample andPlatformGreaterThanOrEqualTo(String value) {
		ge("platform", value);
		return  this;
	}

	public AccountInfoExample andPlatformLessThan(String value) {
		lt("platform", value);
		return  this;
	}

	public AccountInfoExample andPlatformLessThanOrEqualTo(String value) {
		le("platform", value);
		return  this;
	}

	public AccountInfoExample andPlatformLike(String value) {
		like("platform", value);
		return  this;
	}

	public AccountInfoExample andPlatformIn(List<Object> values) {
		in("platform", values);
		return this;
	}

	public AccountInfoExample andTagIsNull() {
		addCriterion("tag is null");
		return this;
	}

	public AccountInfoExample andTagIsNotNull() {
		addCriterion("tag is not null");
		return  this;
	}

	public AccountInfoExample andTagEqualTo(String value) {
		eq("tag", value);
		return this;
	}

	public AccountInfoExample andTagGreaterThan(String value) {
		gt("tag", value);
		return  this;
	}

	public AccountInfoExample andTagGreaterThanOrEqualTo(String value) {
		ge("tag", value);
		return  this;
	}

	public AccountInfoExample andTagLessThan(String value) {
		lt("tag", value);
		return  this;
	}

	public AccountInfoExample andTagLessThanOrEqualTo(String value) {
		le("tag", value);
		return  this;
	}

	public AccountInfoExample andTagLike(String value) {
		like("tag", value);
		return  this;
	}

	public AccountInfoExample andTagIn(List<Object> values) {
		in("tag", values);
		return this;
	}


}
