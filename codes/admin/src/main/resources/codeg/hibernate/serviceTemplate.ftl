package ${package};

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.levelappro.web.core.common.hibernate.query.CriteriaQuery;
import com.levelappro.web.core.common.page.PageList;
import ${source_root_package}.${module}.dao.I${entityName}Dao;
import ${source_root_package}.${module}.entity.${entityName};
import ${source_root_package}.${module}.service.I${entityName}Service;
@Service
@Transactional
public class ${entityName}Service implements I${entityName}Service{
	@Autowired
	private I${entityName}Dao ${entityName?uncap_first}Dao;

	public I${entityName}Dao get${entityName}Dao() {
		return ${entityName?uncap_first}Dao;
	}

	public void set${entityName}Dao(I${entityName}Dao ${entityName?uncap_first}Dao) {
		this.${entityName?uncap_first}Dao = ${entityName?uncap_first}Dao;
	}

	public Session getSession() {
		return ${entityName?uncap_first}Dao.getSession();
	}

	public void saveOrUpdate(${entityName} entity) {
		${entityName?uncap_first}Dao.saveOrUpdate(entity);
	}

	public void delete(${entityName} entity) {
		${entityName?uncap_first}Dao.delete(entity);
	}

	public List<${entityName}> findAll() {
		return ${entityName?uncap_first}Dao.findAll();
	}

	public PageList<${entityName}> getPageList(CriteriaQuery cq,
			PageList<${entityName}> page) {
		return ${entityName?uncap_first}Dao.getPageList(cq, page);
	}

	public List<${entityName}> selectByExample(${entityName} entity) {
		return ${entityName?uncap_first}Dao.selectByExample(entity);
	}

	public ${entityName} getById(Serializable id) {
		return ${entityName?uncap_first}Dao.getById(id);
	}

	public List<${entityName}> findHql(String hql, Object... param) {
		return ${entityName?uncap_first}Dao.findHql(hql, param);
	}

	public Serializable save(${entityName} entity) {
		return ${entityName?uncap_first}Dao.save(entity);
	}

	public int executeHql(String hql, Object... param) {
		return ${entityName?uncap_first}Dao.executeHql(hql, param);
	}

	public void saveBatch(List<${entityName}> entityList) {
		${entityName?uncap_first}Dao.saveBatch(entityList);
	}

	public void update(${entityName} entity) {
		${entityName?uncap_first}Dao.update(entity);
	}

	public void updateBatch(List<${entityName}> entityList) {
		${entityName?uncap_first}Dao.updateBatch(entityList);
	}

	public int deleteById(Serializable id) {
		return ${entityName?uncap_first}Dao.deleteById(id);
	}
}
