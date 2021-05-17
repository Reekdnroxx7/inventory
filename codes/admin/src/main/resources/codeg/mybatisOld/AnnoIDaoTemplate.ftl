package  ${package}.dao;


import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import cn.bp.earth.web.basedao.mybatis.dao.MyBatisRepository;
import cn.bp.earth.web.basedao.mybatis.dao.MybatisExample;
import ${package}.entity.${entityName};
@MyBatisRepository
public interface I${entityName}Dao {
	@SelectProvider(type = ${entityName}SqlProvider.class, method = "countByExample")
	int countByExample(MybatisExample example);

	@DeleteProvider(type = ${entityName}SqlProvider.class, method = "deleteByExample")
	int deleteByExample(MybatisExample example);
<#if primaryKey??>
	@Delete({ "delete from ${table.name}", "where ${primaryKey.fieldName}= ${r"#"}{${primaryKey.javaFieldName},jdbcType=${primaryKey.qualifiedType.jdbcType}}" })
	public int deleteById(Serializable id);
</#if>	
	@Insert({ "insert into ${table.name}",
		"<#list fields as field>${field.fieldName}<#if field_has_next>,<#else></#if></#list> ",
		"values (",
	    "<#list fields as field>${r"#"}{${field.javaFieldName},jdbcType=${field.qualifiedType.jdbcType}}<#if field_has_next>,<#else></#if> </#list>",       
	")"})
<#if primaryKey?? &&  primaryKey.autoInc>
	@Options(useGeneratedKeys=true keyProperty="${primaryKey.fieldName}")
</#if>
	public void insert(${entityName} entity);
	
	@InsertProvider(type = ${entityName}SqlProvider.class, method = "insertSelective")
<#if primaryKey?? &&  primaryKey.autoInc>
	@Options(useGeneratedKeys=true keyProperty="${primaryKey.fieldName}")
</#if>
	public void insertSelective(${entityName} entity);
	
	@InsertProvider(type = ${entityName}SqlProvider.class, method = "insertBatch")
	public void insertBatch(List<${entityName}> entityList);
<#if primaryKey??>
	 @Select({
        "select",
        "<#list fields as field>${field.fieldName}<#if field_has_next>,</#if></#list>",
        "from ${table.name}",
        "where ${primaryKey.fieldName}= ${r"#"}{${primaryKey.javaFieldName},jdbcType=${primaryKey.qualifiedType.jdbcType}}"
    })
	@Results({
		<#list fields as field>
		<#if field.primaryKey>
		@Result(column="${field.fieldName}", property="${field.javaFieldName}", jdbcType=JdbcType.${field.qualifiedType.jdbcType}, id=true)<#if field_has_next>,</#if>
		<#else>
		@Result(column="${field.fieldName}", property="${field.javaFieldName}", jdbcType=JdbcType.${field.qualifiedType.jdbcType})<#if field_has_next>,</#if>
		</#if>	
		</#list>  
	})
	public ${entityName} selectById(Serializable id);
</#if>	
	@Select("select id,parent_id,name,level,l1_id,l2_id,descrip,cover,is_hot,order_flag,create_time,update_time,name_en,lng,lat from ${table.name} ")
	@Results({
		<#list fields as field>
		<#if field.primaryKey>
		@Result(column="${field.fieldName}", property="${field.javaFieldName}", jdbcType=JdbcType.${field.qualifiedType.jdbcType}, id=true)<#if field_has_next>,</#if>
		<#else>
		@Result(column="${field.fieldName}", property="${field.javaFieldName}", jdbcType=JdbcType.${field.qualifiedType.jdbcType})<#if field_has_next>,</#if>
		</#if>	
		</#list>  
	})
	public List<${entityName}> selectAll();
	
	
	
	@SelectProvider(type = ${entityName}SqlProvider.class, method = "selectByExample")
	@Results({
		<#list fields as field>
		<#if field.primaryKey>
		@Result(column="${field.fieldName}", property="${field.javaFieldName}", jdbcType=JdbcType.${field.qualifiedType.jdbcType}, id=true)<#if field_has_next>,</#if>
		<#else>
		@Result(column="${field.fieldName}", property="${field.javaFieldName}", jdbcType=JdbcType.${field.qualifiedType.jdbcType})<#if field_has_next>,</#if>
		</#if>	
		</#list>  
	})
	List<${entityName}> selectByExample(MybatisExample example);
	
	
	@SelectProvider(type = ${entityName}SqlProvider.class, method = "pageList")
	@Results({
		<#list fields as field>
		<#if field.primaryKey>
		@Result(column="${field.fieldName}", property="${field.javaFieldName}", jdbcType=JdbcType.${field.qualifiedType.jdbcType}, id=true)<#if field_has_next>,</#if>
		<#else>
		@Result(column="${field.fieldName}", property="${field.javaFieldName}", jdbcType=JdbcType.${field.qualifiedType.jdbcType})<#if field_has_next>,</#if>
		</#if>	
		</#list>  
	})
	List<T> pageList(@Param("example")MybatisExample example,PageBounds pageBounds);
	
	@UpdateProvider(type=${entityName}SqlProvider.class, method="updateByExampleSelective")
	void updateByExampleSelective(@Param("record")${entityName} t, @Param("example")MybatisExample example);

	@UpdateProvider(type=${entityName}SqlProvider.class, method="updateByIdSelective")
	void updateByIdSelective(${entityName} t);
	
	
	
}
