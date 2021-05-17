package ${package}.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beasy.web.core.common.mybatis.query.MybatisExample;
import com.beasy.web.core.common.page.PageList;
import ${package}.dao.I${entityName}Dao;
import ${package}.entity.${entityName};
import ${package}.service.I${entityName}Service;
@Service
public class ${entityName}Service implements I${entityName}Service{
	
	@Autowired
	private I${entityName}Dao ${entityName?uncap_first}Dao;

	public I${entityName}Dao get${entityName}Dao() {
		return ${entityName?uncap_first}Dao;
	}

	public void set${entityName}Dao(I${entityName}Dao ${entityName?uncap_first}Dao) {
		this.${entityName?uncap_first}Dao = ${entityName?uncap_first}Dao;
	}

	
    <#if primaryKey??>
    public int deleteById(Serializable ${primaryKey.javaFieldName}) {
        return ${entityName?uncap_first}Dao.deleteById(${primaryKey.javaFieldName});
    }
   
    public void updateByIdSelective(${entityName} entity) {
        ${entityName?uncap_first}Dao.updateByIdSelective(entity);
    }
    
	public ${entityName} selectById(Serializable ${primaryKey.javaFieldName}) {
		return ${entityName?uncap_first}Dao.selectById(${primaryKey.javaFieldName});
	}
	 </#if>
	
	public void insert(${entityName} entity) {
		${entityName?uncap_first}Dao.insert(entity);
	}
	
	public void insertSelective(${entityName} entity) {
		${entityName?uncap_first}Dao.insertSelective(entity);
	}
	
	public void insertBatch(List<${entityName}> entityList) {
        ${entityName?uncap_first}Dao.insertBatch(entityList);
    }

	public List<${entityName}> selectAll() {
		return ${entityName?uncap_first}Dao.selectAll();
	}

	public int countByExample(MybatisExample example) {
		return ${entityName?uncap_first}Dao.countByExample(example);
	}

	public int deleteByExample(MybatisExample example) {
		return ${entityName?uncap_first}Dao.deleteByExample(example);
	}

	public List<${entityName}> selectByExample(MybatisExample example) {
		return ${entityName?uncap_first}Dao.selectByExample(example);
	}

	public void updateByExampleSelective(${entityName} t, MybatisExample example) {
		${entityName?uncap_first}Dao.updateByExampleSelective(t, example);
	}

	@Override
	public PageList<${entityName}> PageList(
			MybatisExample example) {
		PageList<${entityName}> list = new PageList<${entityName}>();
		List<${entityName}> selectByExample = this.${entityName?uncap_first}Dao.selectByExample(example);
		int countByExample = this.${entityName?uncap_first}Dao.countByExample(example);
		list.setResultList(selectByExample);
		list.setTotalCount(countByExample);
		return list;
	}
}
