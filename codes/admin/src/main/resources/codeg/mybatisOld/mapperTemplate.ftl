<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package}.dao.I${entityName}Dao">
	<resultMap id="BaseResultMap" type="${package}.entity.${entityName}" >	
	<#list fields as field>
		<#if field.primaryKey>
		<id column="${field.fieldName}" property="${field.javaFieldName}" jdbcType="${field.qualifiedType.jdbcType}" />
		<#else>
		<result column="${field.fieldName}" property="${field.javaFieldName}" jdbcType="${field.qualifiedType.jdbcType}" />
		</#if>	
	</#list>  
  </resultMap>
  <sql id="Example_Where_Clause">
    <where>
      <foreach collection="oredCriteria" item="criteria" separator="or">
        <if test="criteria.valid">
          <trim prefix="(" prefixOverrides="and" suffix=")">
            <foreach collection="criteria.criteria" item="criterion">
              <choose>
                <when test="criterion.noValue">
                  and ${r"${criterion.condition}"}
                </when>
                <when test="criterion.singleValue">
                  and ${r"${criterion.condition}"} ${r"#"}{criterion.value}
                </when>
                <when test="criterion.betweenValue">
                  and ${r"${criterion.condition}"} ${r"#"}{criterion.value} and ${r"#"}{criterion.secondValue}
                </when>
                <when test="criterion.listValue">
                  and ${r"${criterion.condition}"}
                  <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
                       ${r"#"}{listItem}
                  </foreach>
                </when>
              </choose>
            </foreach>
          </trim>
        </if>
      </foreach>
    </where>
  </sql>
  <sql id="Update_By_Example_Where_Clause">
    <where>
      <foreach collection="example.oredCriteria" item="criteria" separator="or">
        <if test="criteria.valid">
          <trim prefix="(" prefixOverrides="and" suffix=")">
            <foreach collection="criteria.criteria" item="criterion">
              <choose>
                <when test="criterion.noValue">
                  and ${r"${criterion.condition}"}
                </when>
                <when test="criterion.singleValue">
                  and ${r"${criterion.condition}"} ${r"#"}{criterion.value}
                </when>
                <when test="criterion.betweenValue">
                  and ${r"${criterion.condition}"} ${r"#"}{criterion.value} and ${r"#"}{criterion.secondValue}
                </when>
                <when test="criterion.listValue">
                  and ${r"${criterion.condition}"}
                  <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
                      ${r"#"}{listItem}
                  </foreach>
                </when>
              </choose>
            </foreach>
          </trim>
        </if>
      </foreach>
    </where>
  </sql>
  <sql id="Base_Column_List">
  <#list fields as field>${field.fieldName}<#if field_has_next>,</#if></#list>
  </sql>
  
  <select id="selectByExample" parameterType="${package}.entity.${entityName}Example" 
  resultMap="BaseResultMap">
    select
    <if test="distinct">
      distinct
    </if>
    <include refid="Base_Column_List" />
    from ${table.name}
    <include refid="Example_Where_Clause" />
    <if test="orderByClause != null" >
      order by ${r'${orderByClause}'}
    </if>
   	<if test="limit > 0 " >
   	 	limit ${r"#"}{start},${r"#"}{limit}
   	</if>
  </select>
  
  <#if primaryKey??>
  <select id="selectById" parameterType="${primaryKey.qualifiedType.javaType}" 
     resultMap="BaseResultMap">
    select
    <include refid="Base_Column_List" />
    from ${table.name}
  	where
     ${primaryKey.fieldName}= ${r"#"}{${primaryKey.javaFieldName},jdbcType=${primaryKey.qualifiedType.jdbcType}} 
  </select>
  </#if>
  
  <select id="selectAll" 
    resultMap="BaseResultMap">
    select
    <include refid="Base_Column_List" />
    from ${table.name}
  </select>
  
 <#if primaryKey??>
  <delete id="deleteById" parameterType="${primaryKey.qualifiedType.javaType}">
    delete from ${table.name}
    where
	 ${primaryKey.fieldName}= ${r"#"}{${primaryKey.javaFieldName},jdbcType=${primaryKey.qualifiedType.jdbcType}} 
  </delete>
  </#if>
  
  <delete id="deleteByExample" parameterType="${package}.entity.${entityName}Example" >
    delete from ${table.name}
    <if test="_parameter != null">
      <include refid="Example_Where_Clause" />
    </if>
  </delete>
  
  <insert id="insert"    parameterType="${package}.entity.${entityName}" 
  <#if primaryKey?? &&  primaryKey.autoInc> useGeneratedKeys="true" keyProperty="${primaryKey.fieldName}"</#if>>
	  
	insert into ${table.name} 
	( 
	 <#list fields as field>${field.fieldName}<#if field_has_next>,<#else></#if></#list> 
	 )
	values (
	 <#list fields as field>
          ${r"#"}{${field.javaFieldName},jdbcType=${field.qualifiedType.jdbcType}}<#if field_has_next>,<#else></#if>
          
  	 </#list>
	)
	</insert>
	
	 <insert id="insertSelective"  
            parameterType="${package}.entity.${entityName}" <#if primaryKey?? &&  primaryKey.autoInc> useGeneratedKeys="true" keyProperty="${primaryKey.fieldName}"</#if> >
	  
	insert into ${table.name} 
	 <trim prefix="(" suffix=")" suffixOverrides="," >
		 <#list fields as field>
		 <if test="${field.fieldName} != null" >${field.fieldName},</if>
		 </#list> 
	</trim>
	values 
	 <trim prefix="(" suffix=")" suffixOverrides="," >
	 <#list fields as field>
          <if test="${field.fieldName} != null" >
          ${r"#"}{${field.javaFieldName},jdbcType=${field.qualifiedType.jdbcType}},
         </if>
  	 </#list>
	</trim>
	</insert>
	
   <insert id="insertBatch" parameterType="java.util.List">
  	insert into ${table.name}(
	   <#list fields as field>${field.fieldName}<#if field_has_next>,<#else></#if></#list> 
	) values
  <foreach collection="list" item="item" index="index"
  	separator=",">
  (
  <#list fields as field>
          ${r"#"}{${field.javaFieldName},jdbcType=${field.qualifiedType.jdbcType}}
 </#list>
 	)
  </foreach>
  </insert>
  
  <select id="countByExample" parameterType="${package}.entity.${entityName}Example" resultType="java.lang.Integer">
    select count(*) from ${table.name}
      <include refid="Example_Where_Clause" />
  </select>
 <#if primaryKey??> 
 <update id="updateByIdSelective" parameterType="${package}.entity.${entityName}">
    update ${table.name}
     <set >
      <#list fields as field>
      <#if field.primaryKey>
      <#else>
      <if test="${field.javaFieldName} != null" >
         ${field.fieldName}=${r"#"}{${field.javaFieldName},jdbcType=${field.qualifiedType.jdbcType}}<#if field_has_next>,</#if>
      </if>
      </#if>
      </#list>
    </set>	 
    	where
	 ${primaryKey.fieldName}= ${r"#"}{${primaryKey.javaFieldName},jdbcType=${primaryKey.qualifiedType.jdbcType}} 
 </update>
</#if>
    <update id="updateByExampleSelective" parameterType="map">
        update ${table.name}
        <set >
            <#list fields as field>
            <#if field.primaryKey>
            <#else>
            <if test="${field.javaFieldName} != null" >
        		${field.fieldName}=${r"#"}{${field.javaFieldName},jdbcType=${field.qualifiedType.jdbcType}}<#if field_has_next>,</#if>
      		</if>
            </#if>
      	    </#list>
    	</set>	 
		<include refid="Update_By_Example_Where_Clause" />
	</update>
</mapper>