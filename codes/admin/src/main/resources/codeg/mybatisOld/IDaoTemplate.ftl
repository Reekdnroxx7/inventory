package  ${package}.dao;

import com.beasy.web.core.common.mybatis.annotation.MyBatisRepository;
import com.beasy.web.core.common.mybatis.dao.IMybatisBaseDao;
import ${package}.entity.${entityName};
@MyBatisRepository
public interface I${entityName}Dao extends IMybatisBaseDao<${entityName}>{

}
