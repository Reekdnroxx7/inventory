package ${package};

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.levelappro.web.core.common.entity.DataEntity;

/**   
 * @Title: Entity 
 * @author 
 * @version V1.0   
 *
 */
@Entity
@Table(name = "${tableName}")
@DynamicUpdate(true) @DynamicInsert(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ${entityName} extends DataEntity {
    private static final long serialVersionUID = 1L;
	<#list originalColumns as po>
	<#if po.fieldName !="id" 
	&& po.fieldName !="remarks"	
	&& po.fieldName !="createBy"
	&& po.fieldName !="createDate"
	&& po.fieldName !="updateBy"
	&& po.fieldName !="updateDate"
	>
	/**${po.displayName}*/
	private ${po.fieldType} ${po.fieldName};
	</#if>
	</#list>
	public ${entityName}(){
		super();
	}
	public ${entityName}(String id){
		this();
		this.id = id;
	}
	<#list originalColumns as po>
	<#if po.fieldName !="id" 
	&& po.fieldName !="remarks"	
	&& po.fieldName !="createBy"
	&& po.fieldName !="createDate"
	&& po.fieldName !="updateBy"
	&& po.fieldName !="updateDate"
	>
	public ${po.fieldType} get${po.fieldName?cap_first}(){
		return ${po.fieldName};
	}
	
	public void set${po.fieldName?cap_first}(${po.fieldType} ${po.fieldName}){
		this.${po.fieldName} = ${po.fieldName};
	}
	</#if>
	</#list>
}
