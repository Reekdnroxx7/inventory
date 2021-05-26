package com.x404.busi.entity;

import com.xc350.web.base.mybatis.dao.MybatisExample;

import java.util.List;



public class MaterielExample extends MybatisExample
{

	public MaterielExample andIdIsNull() {
		addCriterion("id is null");
		return this;
	}

	public MaterielExample andIdIsNotNull() {
		addCriterion("id is not null");
		return  this;
	}

	public MaterielExample andIdEqualTo(String value) {
		eq("id", value);
		return this;
	}

	public MaterielExample andIdGreaterThan(String value) {
		gt("id", value);
		return  this;
	}

	public MaterielExample andIdGreaterThanOrEqualTo(String value) {
		ge("id", value);
		return  this;
	}

	public MaterielExample andIdLessThan(String value) {
		lt("id", value);
		return  this;
	}

	public MaterielExample andIdLessThanOrEqualTo(String value) {
		le("id", value);
		return  this;
	}

	public MaterielExample andIdLike(String value) {
		like("id", value);
		return  this;
	}

	public MaterielExample andIdIn(List<Object> values) {
		in("id", values);
		return this;
	}

	public MaterielExample andProcedure_nameIsNull() {
		addCriterion("procedure_name is null");
		return this;
	}

	public MaterielExample andProcedure_nameIsNotNull() {
		addCriterion("procedure_name is not null");
		return  this;
	}

	public MaterielExample andProcedure_nameEqualTo(String value) {
		eq("procedure_name", value);
		return this;
	}

	public MaterielExample andProcedure_nameGreaterThan(String value) {
		gt("procedure_name", value);
		return  this;
	}

	public MaterielExample andProcedure_nameGreaterThanOrEqualTo(String value) {
		ge("procedure_name", value);
		return  this;
	}

	public MaterielExample andProcedure_nameLessThan(String value) {
		lt("procedure_name", value);
		return  this;
	}

	public MaterielExample andProcedure_nameLessThanOrEqualTo(String value) {
		le("procedure_name", value);
		return  this;
	}

	public MaterielExample andProcedure_nameLike(String value) {
		like("procedure_name", value);
		return  this;
	}

	public MaterielExample andProcedure_nameIn(List<Object> values) {
		in("procedure_name", values);
		return this;
	}

	public MaterielExample andMaterial_codeIsNull() {
		addCriterion("material_code is null");
		return this;
	}

	public MaterielExample andMaterial_codeIsNotNull() {
		addCriterion("material_code is not null");
		return  this;
	}

	public MaterielExample andMaterial_codeEqualTo(String value) {
		eq("material_code", value);
		return this;
	}

	public MaterielExample andMaterial_codeGreaterThan(String value) {
		gt("material_code", value);
		return  this;
	}

	public MaterielExample andMaterial_codeGreaterThanOrEqualTo(String value) {
		ge("material_code", value);
		return  this;
	}

	public MaterielExample andMaterial_codeLessThan(String value) {
		lt("material_code", value);
		return  this;
	}

	public MaterielExample andMaterial_codeLessThanOrEqualTo(String value) {
		le("material_code", value);
		return  this;
	}

	public MaterielExample andMaterial_codeLike(String value) {
		like("material_code", value);
		return  this;
	}

	public MaterielExample andMaterial_codeIn(List<Object> values) {
		in("material_code", values);
		return this;
	}

	public MaterielExample andMaterial_nameIsNull() {
		addCriterion("material_name is null");
		return this;
	}

	public MaterielExample andMaterial_nameIsNotNull() {
		addCriterion("material_name is not null");
		return  this;
	}

	public MaterielExample andMaterial_nameEqualTo(String value) {
		eq("material_name", value);
		return this;
	}

	public MaterielExample andMaterial_nameGreaterThan(String value) {
		gt("material_name", value);
		return  this;
	}

	public MaterielExample andMaterial_nameGreaterThanOrEqualTo(String value) {
		ge("material_name", value);
		return  this;
	}

	public MaterielExample andMaterial_nameLessThan(String value) {
		lt("material_name", value);
		return  this;
	}

	public MaterielExample andMaterial_nameLessThanOrEqualTo(String value) {
		le("material_name", value);
		return  this;
	}

	public MaterielExample andMaterial_nameLike(String value) {
		like("material_name", value);
		return  this;
	}

	public MaterielExample andMaterial_nameIn(List<Object> values) {
		in("material_name", values);
		return this;
	}

	public MaterielExample andQuantityIsNull() {
		addCriterion("quantity is null");
		return this;
	}

	public MaterielExample andQuantityIsNotNull() {
		addCriterion("quantity is not null");
		return  this;
	}

	public MaterielExample andQuantityEqualTo(Integer value) {
		eq("quantity", value);
		return this;
	}

	public MaterielExample andQuantityGreaterThan(Integer value) {
		gt("quantity", value);
		return  this;
	}

	public MaterielExample andQuantityGreaterThanOrEqualTo(Integer value) {
		ge("quantity", value);
		return  this;
	}

	public MaterielExample andQuantityLessThan(Integer value) {
		lt("quantity", value);
		return  this;
	}

	public MaterielExample andQuantityLessThanOrEqualTo(Integer value) {
		le("quantity", value);
		return  this;
	}

	public MaterielExample andQuantityLike(Integer value) {
		like("quantity", value);
		return  this;
	}

	public MaterielExample andQuantityIn(List<Object> values) {
		in("quantity", values);
		return this;
	}

	public MaterielExample andUnitIsNull() {
		addCriterion("unit is null");
		return this;
	}

	public MaterielExample andUnitIsNotNull() {
		addCriterion("unit is not null");
		return  this;
	}

	public MaterielExample andUnitEqualTo(String value) {
		eq("unit", value);
		return this;
	}

	public MaterielExample andUnitGreaterThan(String value) {
		gt("unit", value);
		return  this;
	}

	public MaterielExample andUnitGreaterThanOrEqualTo(String value) {
		ge("unit", value);
		return  this;
	}

	public MaterielExample andUnitLessThan(String value) {
		lt("unit", value);
		return  this;
	}

	public MaterielExample andUnitLessThanOrEqualTo(String value) {
		le("unit", value);
		return  this;
	}

	public MaterielExample andUnitLike(String value) {
		like("unit", value);
		return  this;
	}

	public MaterielExample andUnitIn(List<Object> values) {
		in("unit", values);
		return this;
	}

	public MaterielExample andPositionIsNull() {
		addCriterion("position is null");
		return this;
	}

	public MaterielExample andPositionIsNotNull() {
		addCriterion("position is not null");
		return  this;
	}

	public MaterielExample andPositionEqualTo(String value) {
		eq("position", value);
		return this;
	}

	public MaterielExample andPositionGreaterThan(String value) {
		gt("position", value);
		return  this;
	}

	public MaterielExample andPositionGreaterThanOrEqualTo(String value) {
		ge("position", value);
		return  this;
	}

	public MaterielExample andPositionLessThan(String value) {
		lt("position", value);
		return  this;
	}

	public MaterielExample andPositionLessThanOrEqualTo(String value) {
		le("position", value);
		return  this;
	}

	public MaterielExample andPositionLike(String value) {
		like("position", value);
		return  this;
	}

	public MaterielExample andPositionIn(List<Object> values) {
		in("position", values);
		return this;
	}

	public MaterielExample andCreate_dateIsNull() {
		addCriterion("create_date is null");
		return this;
	}

	public MaterielExample andCreate_dateIsNotNull() {
		addCriterion("create_date is not null");
		return  this;
	}

	public MaterielExample andCreate_dateEqualTo(java.util.Date value) {
		eq("create_date", value);
		return this;
	}

	public MaterielExample andCreate_dateGreaterThan(java.util.Date value) {
		gt("create_date", value);
		return  this;
	}

	public MaterielExample andCreate_dateGreaterThanOrEqualTo(java.util.Date value) {
		ge("create_date", value);
		return  this;
	}

	public MaterielExample andCreate_dateLessThan(java.util.Date value) {
		lt("create_date", value);
		return  this;
	}

	public MaterielExample andCreate_dateLessThanOrEqualTo(java.util.Date value) {
		le("create_date", value);
		return  this;
	}

	public MaterielExample andCreate_dateLike(java.util.Date value) {
		like("create_date", value);
		return  this;
	}

	public MaterielExample andCreate_dateIn(List<Object> values) {
		in("create_date", values);
		return this;
	}

	public MaterielExample andCreate_byIsNull() {
		addCriterion("create_by is null");
		return this;
	}

	public MaterielExample andCreate_byIsNotNull() {
		addCriterion("create_by is not null");
		return  this;
	}

	public MaterielExample andCreate_byEqualTo(String value) {
		eq("create_by", value);
		return this;
	}

	public MaterielExample andCreate_byGreaterThan(String value) {
		gt("create_by", value);
		return  this;
	}

	public MaterielExample andCreate_byGreaterThanOrEqualTo(String value) {
		ge("create_by", value);
		return  this;
	}

	public MaterielExample andCreate_byLessThan(String value) {
		lt("create_by", value);
		return  this;
	}

	public MaterielExample andCreate_byLessThanOrEqualTo(String value) {
		le("create_by", value);
		return  this;
	}

	public MaterielExample andCreate_byLike(String value) {
		like("create_by", value);
		return  this;
	}

	public MaterielExample andCreate_byIn(List<Object> values) {
		in("create_by", values);
		return this;
	}

	public MaterielExample andUpdate_dateIsNull() {
		addCriterion("update_date is null");
		return this;
	}

	public MaterielExample andUpdate_dateIsNotNull() {
		addCriterion("update_date is not null");
		return  this;
	}

	public MaterielExample andUpdate_dateEqualTo(java.util.Date value) {
		eq("update_date", value);
		return this;
	}

	public MaterielExample andUpdate_dateGreaterThan(java.util.Date value) {
		gt("update_date", value);
		return  this;
	}

	public MaterielExample andUpdate_dateGreaterThanOrEqualTo(java.util.Date value) {
		ge("update_date", value);
		return  this;
	}

	public MaterielExample andUpdate_dateLessThan(java.util.Date value) {
		lt("update_date", value);
		return  this;
	}

	public MaterielExample andUpdate_dateLessThanOrEqualTo(java.util.Date value) {
		le("update_date", value);
		return  this;
	}

	public MaterielExample andUpdate_dateLike(java.util.Date value) {
		like("update_date", value);
		return  this;
	}

	public MaterielExample andUpdate_dateIn(List<Object> values) {
		in("update_date", values);
		return this;
	}

	public MaterielExample andUpdate_byIsNull() {
		addCriterion("update_by is null");
		return this;
	}

	public MaterielExample andUpdate_byIsNotNull() {
		addCriterion("update_by is not null");
		return  this;
	}

	public MaterielExample andUpdate_byEqualTo(String value) {
		eq("update_by", value);
		return this;
	}

	public MaterielExample andUpdate_byGreaterThan(String value) {
		gt("update_by", value);
		return  this;
	}

	public MaterielExample andUpdate_byGreaterThanOrEqualTo(String value) {
		ge("update_by", value);
		return  this;
	}

	public MaterielExample andUpdate_byLessThan(String value) {
		lt("update_by", value);
		return  this;
	}

	public MaterielExample andUpdate_byLessThanOrEqualTo(String value) {
		le("update_by", value);
		return  this;
	}

	public MaterielExample andUpdate_byLike(String value) {
		like("update_by", value);
		return  this;
	}

	public MaterielExample andUpdate_byIn(List<Object> values) {
		in("update_by", values);
		return this;
	}

	public MaterielExample andRemarksIsNull() {
		addCriterion("remarks is null");
		return this;
	}

	public MaterielExample andRemarksIsNotNull() {
		addCriterion("remarks is not null");
		return  this;
	}

	public MaterielExample andRemarksEqualTo(String value) {
		eq("remarks", value);
		return this;
	}

	public MaterielExample andRemarksGreaterThan(String value) {
		gt("remarks", value);
		return  this;
	}

	public MaterielExample andRemarksGreaterThanOrEqualTo(String value) {
		ge("remarks", value);
		return  this;
	}

	public MaterielExample andRemarksLessThan(String value) {
		lt("remarks", value);
		return  this;
	}

	public MaterielExample andRemarksLessThanOrEqualTo(String value) {
		le("remarks", value);
		return  this;
	}

	public MaterielExample andRemarksLike(String value) {
		like("remarks", value);
		return  this;
	}

	public MaterielExample andRemarksIn(List<Object> values) {
		in("remarks", values);
		return this;
	}


}
