package ${package}.entity;

import java.io.Serializable;

/**   
 * @Title: ${entityName} 
 * @author 
 * @version V1.0   
 *
 */
public class ${entityName} implements Serializable {
    private static final long serialVersionUID = 1L;
	<#list fields as field>
	/**${field.displayName}*/
	private ${field.qualifiedType.javaType} ${field.javaFieldName};	
	</#list>
	public ${entityName}(){
		super();
	}
	<#if primaryKey??>
	public ${entityName}(${primaryKey.qualifiedType.javaType} ${primaryKey.javaFieldName}){
		this();
		this.${primaryKey.javaFieldName}= ${primaryKey.javaFieldName};
	}
	</#if>
	<#list fields as field>
	
	public ${field.qualifiedType.javaType} get${field.javaFieldName?cap_first}(){
		return ${field.javaFieldName};
	}
	
	public void set${field.javaFieldName?cap_first}(${field.qualifiedType.javaType} ${field.javaFieldName}){
		this.${field.javaFieldName} = ${field.javaFieldName};
	}
	</#list>
}
