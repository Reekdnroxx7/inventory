package com.x404.busi.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.x404.busi.dao.IMaterielDao;
import com.x404.busi.entity.Materiel;
import com.xc350.web.base.mybatis.dao.MybatisExample;
import com.xc350.web.base.query.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Service
public class MaterielService {

	@Autowired
	private IMaterielDao materielDao;

	public IMaterielDao getMaterielDao() {
		return materielDao;
	}

	public void setMaterielDao(IMaterielDao materielDao) {
		this.materielDao = materielDao;
	}

	public Materiel selectByKey(Serializable id) {
		return materielDao.selectByKey(id);
	}

	public int countByExample(MybatisExample example) {
		return materielDao.countByExample(example);
	}

	public List<Materiel> selectByExample(MybatisExample example) {
		return materielDao.selectByExample(example);
	}

	public List<Materiel> selectAll() {
		return materielDao.selectAll();
	}


	public void insert(Materiel entity) {
		materielDao.insert(entity);
	}

	public void insertSelective(Materiel entity) {
		materielDao.insertSelective(entity);
	}

	public void insertBatch(List<Materiel> entityList) {
        materielDao.insertBatch(entityList);
    }

    public int deleteByKey(Serializable id) {
        return materielDao.deleteByKey(id);
    }

	public int deleteByExample(MybatisExample example) {
		return materielDao.deleteByExample(example);
	}

	public void updateByKey(Materiel t) {
		materielDao.updateByKey(t);
	}


	public void updateByKeySelective(Materiel t) {
		materielDao.updateByKeySelective(t);
	}

	public void updateByExample(Materiel t, MybatisExample example) {
		materielDao.updateByExample(t, example);
	}

	public void updateByExampleSelective(Materiel t, MybatisExample example) {
		materielDao.updateByExampleSelective(t, example);
	}


	public PageList<Materiel> pageList(MybatisExample example) {
		PageBounds pageBounds = new PageBounds((example.getStart()/example.getLimit() +1),example.getLimit());
		return pageList(example,pageBounds);
	}

	/**分页查询
	 * @param example
	 * @param pageBounds
	 * @return
	 */
	public PageList<Materiel> pageList(MybatisExample example,PageBounds pageBounds){
		com.github.miemiedev.mybatis.paginator.domain.PageList<Materiel> materiels = (com.github.miemiedev.mybatis.paginator.domain.PageList<Materiel>) materielDao.pageList(example, pageBounds);

		return new PageList<>(materiels.getPaginator().getTotalCount(),new ArrayList<>(materiels));

	}
}
