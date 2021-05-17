package ${package}.entity;

import java.util.List;

import com.beasy.web.core.common.mybatis.query.MybatisExample;

public class ${entityName}Example extends MybatisExample {
	<#list fields as field>
	
	public ${entityName}Example and${field.javaFieldName?cap_first}IsNull() {
		addCriterion("${field.fieldName} is null");
		return this;
	}
	
	public ${entityName}Example and${field.javaFieldName?cap_first}IsNotNull() {
		addCriterion("${field.fieldName} is not null");
		return  this;
	}

	public ${entityName}Example and${field.javaFieldName?cap_first}EqualTo(${field.qualifiedType.javaType} value) {
		eq("${field.fieldName}", value);
		return this;
	}

	public ${entityName}Example and${field.javaFieldName?cap_first}GreaterThan(${field.qualifiedType.javaType} value) {
		gt("${field.fieldName}", value);
		return  this;
	}

	public ${entityName}Example and${field.javaFieldName?cap_first}GreaterThanOrEqualTo(${field.qualifiedType.javaType} value) {
		ge("${field.fieldName}", value);
		return  this;
	}

	public ${entityName}Example and${field.javaFieldName?cap_first}LessThan(${field.qualifiedType.javaType} value) {
		lt("${field.fieldName}", value);
		return  this;
	}

	public ${entityName}Example and${field.javaFieldName?cap_first}LessThanOrEqualTo(${field.qualifiedType.javaType} value) {
		le("${field.fieldName}", value);
		return  this;
	}

	public ${entityName}Example and${field.javaFieldName?cap_first}Like(${field.qualifiedType.javaType} value) {
		like("${field.fieldName}", value);
		return  this;
	}

	public ${entityName}Example and${field.javaFieldName?cap_first}In(List<Object> values) {
		in("${field.fieldName}", values);
		return this;
	}
	</#list>
	

}
