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


import ${package}.entity.${entityName};
@MyBatisRepository
public interface I${entityName}Dao {


<#if primaryKey??>
	/**主键查询
	 * @param key
	 * @return
	 */
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
	public ${entityName} selectByKey(Serializable key);
</#if>

	/**查询所有
	 * @return
	 */
	@Select("select * from ${table.name} ")
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


	/**
	 * 如果带有分页 此分页仅支持mysql 建议用pageList
	 * @param example
	 * @return
	 */
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

	/**分页查询，使用此方法，注意pageBounds ，example不要同时有排序
	 * @param example
	 * @param pageBounds
	 * @return
	 */
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
	List<${entityName}> pageList(@Param("example")MybatisExample example,PageBounds pageBounds);

	/**查总数
	 * @param example
	 * @return
	 */
	@SelectProvider(type = ${entityName}SqlProvider.class, method = "countByExample")
	int countByExample(MybatisExample example);

	/**插入 @see {@link #insertSelective}
	 * @param entity
	 */
	@Insert({ "insert into ${table.name}",
		"( <#list fields as field>${field.fieldName}<#if field_has_next>,<#else></#if></#list> )",
		"values (",
	    "<#list fields as field>${r"#"}{${field.javaFieldName},jdbcType=${field.qualifiedType.jdbcType}}<#if field_has_next>,<#else></#if> </#list>",
	")"})
<#if primaryKey?? &&  primaryKey.autoInc>
	@Options(useGeneratedKeys=true keyProperty="${primaryKey.fieldName}")
</#if>
	public void insert(${entityName} entity);

	/**选择插入
	 * @param entity
	 */
	@InsertProvider(type = ${entityName}SqlProvider.class, method = "insertSelective")
<#if primaryKey?? &&  primaryKey.autoInc>
	@Options(useGeneratedKeys=true keyProperty="${primaryKey.fieldName}")
</#if>
	public void insertSelective(${entityName} entity);

	/**批量插入
	 * @param entityList
	 */
	@InsertProvider(type = ${entityName}SqlProvider.class, method = "insertBatch")
	public void insertBatch(List<${entityName}> entityList);


<#if primaryKey??>

	/**主键删除
	 * @param key
	 * @return
	 */
	@Delete({ "delete from ${table.name}", "where ${primaryKey.fieldName}= ${r"#"}{${primaryKey.javaFieldName},jdbcType=${primaryKey.qualifiedType.jdbcType}}" })
	public int deleteByKey(Serializable key);
</#if>


	/**条件删除
	 * @param example
	 * @return
	 */
	@DeleteProvider(type = ${entityName}SqlProvider.class, method = "deleteByExample")
	int deleteByExample(MybatisExample example);

<#if primaryKey??>

	/**主键更新
     * @param t
     */
    @UpdateProvider(type=${entityName}SqlProvider.class, method="updateByKey")
	void updateByKey(${entityName} t);

	/**主键更新
     * @param t
     */
	@UpdateProvider(type=${entityName}SqlProvider.class, method="updateByKeySelective")
	void updateByKeySelective(${entityName} t);
</#if>

	 /**条件更新
     * @param t
     * @param example
     */
    @UpdateProvider(type=${entityName}SqlProvider.class, method="updateByExample")
    void updateByExample(${entityName} t, MybatisExample example);

	/**条件更新
     * @param t
     * @param example
     */
	@UpdateProvider(type=${entityName}SqlProvider.class, method="updateByExampleSelective")
	void updateByExampleSelective(@Param("record")${entityName} t, @Param("example")MybatisExample example);

}
