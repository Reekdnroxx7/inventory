package ${package};

import org.springframework.stereotype.Repository;

import com.levelappro.web.core.common.hibernate.dao.impl.HibernateDao;
import ${source_root_package}.${module}.dao.I${entityName}Dao;
import ${source_root_package}.${module}.entity.${entityName};


@Repository
public class ${entityName}Dao extends HibernateDao<${entityName}> implements I${entityName}Dao{

}