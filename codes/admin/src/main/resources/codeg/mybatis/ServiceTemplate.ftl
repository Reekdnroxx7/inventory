package ${package}.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${package}.dao.I${entityName}Dao;
import ${package}.entity.${entityName};
@Service
public class ${entityName}Service {

	@Autowired
	private I${entityName}Dao ${entityName?uncap_first}Dao;

	public I${entityName}Dao get${entityName}Dao() {
		return ${entityName?uncap_first}Dao;
	}

	public void set${entityName}Dao(I${entityName}Dao ${entityName?uncap_first}Dao) {
		this.${entityName?uncap_first}Dao = ${entityName?uncap_first}Dao;
	}

   <#if primaryKey??>
	public ${entityName} selectByKey(Serializable ${primaryKey.javaFieldName}) {
		return ${entityName?uncap_first}Dao.selectByKey(${primaryKey.javaFieldName});
	}
	</#if>

	public int countByExample(MybatisExample example) {
		return ${entityName?uncap_first}Dao.countByExample(example);
	}

	public List<${entityName}> selectByExample(MybatisExample example) {
		return ${entityName?uncap_first}Dao.selectByExample(example);
	}

	public List<${entityName}> selectAll() {
		return ${entityName?uncap_first}Dao.selectAll();
	}


	public void insert(${entityName} entity) {
		${entityName?uncap_first}Dao.insert(entity);
	}

	public void insertSelective(${entityName} entity) {
		${entityName?uncap_first}Dao.insertSelective(entity);
	}

	public void insertBatch(List<${entityName}> entityList) {
        ${entityName?uncap_first}Dao.insertBatch(entityList);
    }

    <#if primaryKey??>
    public int deleteByKey(Serializable ${primaryKey.javaFieldName}) {
        return ${entityName?uncap_first}Dao.deleteByKey(${primaryKey.javaFieldName});
    }
    </#if>

	public int deleteByExample(MybatisExample example) {
		return ${entityName?uncap_first}Dao.deleteByExample(example);
	}

	<#if primaryKey??>
	public void updateByKey(${entityName} t) {
		${entityName?uncap_first}Dao.updateByKey(t);
	}


	public void updateByKeySelective(${entityName} t) {
		${entityName?uncap_first}Dao.updateByKeySelective(t);
	}
	</#if>

	public void updateByExample(${entityName} t, MybatisExample example) {
		${entityName?uncap_first}Dao.updateByExample(t, example);
	}

	public void updateByExampleSelective(${entityName} t, MybatisExample example) {
		${entityName?uncap_first}Dao.updateByExampleSelective(t, example);
	}


	public PageList<${entityName}> pageList(MybatisExample example) {
		PageBounds pageBounds = new PageBounds((example.getStart()/example.getLimit() +1),example.getLimit());
		return pageList(example,pageBounds);
	}

	/**分页查询
	 * @param example
	 * @param pageBounds
	 * @return
	 */
	public PageList<${entityName}> pageList(MybatisExample example,PageBounds pageBounds){
		return (PageList<${entityName}>) ${entityName?uncap_first}Dao.pageList(example, pageBounds);
	}
}
