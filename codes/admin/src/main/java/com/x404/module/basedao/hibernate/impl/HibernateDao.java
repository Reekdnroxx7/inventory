package com.x404.module.basedao.hibernate.impl;

import com.x404.module.basedao.hibernate.HibernateQuery;
import com.x404.module.basedao.hibernate.IHibernateDao;
import com.x404.module.basedao.query.PageList;
import com.x404.module.utils.Reflections;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述： DAO层泛型基类
 * <p>
 * 张代浩
 *
 * @param <T>
 * @version 1.0
 * @date： 日期：2012-12-7 时间：上午10:16:48
 */
@Component
public class HibernateDao<T>
        implements IHibernateDao<T> {
    private static final Logger logger = Logger
            .getLogger(HibernateDao.class);
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;
    private Class<T> entityClass;


    public HibernateDao() {
        entityClass = Reflections.getClassGenricType(getClass());
    }

    @Override
    public Session getSession() {
        // 事务必须是开启的(Required)，否则获取不到
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        return getSession().createCriteria(entityClass).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByExample(T entity) {
        Assert.notNull(entity, "Example entity must not be null");
        Criteria executableCriteria = getSession()
                .createCriteria(entityClass);
        executableCriteria.add(Example.create(entity));
        return executableCriteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(Serializable id) {
        T t = (T) getSession().get(entityClass, id);
        return t;
    }

    @Override
    public void saveBatch(List<T> entityList) {
        for (int i = 0; i < entityList.size(); i++) {
            getSession().save(entityList.get(i));
            if (i % 20 == 0) {
                // 20个对象后才清理缓存，写入数据库
                getSession().flush();
                getSession().clear();
            }
        }
        // 最后清理一下----防止大于20小于40的不保存
        getSession().flush();
        getSession().clear();
    }

    @Override
    public void updateByIdSelective(T entity) {
        getSession().update(entity);
        getSession().flush();

    }

    //	@Override
//	public void updateBatch(List<T> entityList) {
//		for (int i = 0; i < entityList.size(); i++) {
//			getSession().update(entityList.get(i));
//			if (i % 20 == 0) {
//				// 20个对象后才清理缓存，写入数据库
//				getSession().flush();
//				getSession().clear();
//			}
//		}
//		// 最后清理一下----防止大于20小于40的不保存
//		getSession().flush();
//		getSession().clear();
//	}
    @Override
    public int deleteById(Serializable id) {
        return executeHql("delete from  " + entityClass.getSimpleName() + " where id = ?", id);
    }

    /**
     * 根据传入的实体持久化对象
     *
     * @return
     */
    @Override
    public Serializable save(T entity) {
        try {
            Serializable id = getSession().save(entity);
            getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("保存实体成功," + entity.getClass().getName());
            }
            return id;
        } catch (RuntimeException e) {
            logger.error("保存实体异常", e);
            throw e;
        }

    }


    @Override
    public void saveOrUpdate(T entity) {
        try {
            getSession().saveOrUpdate(entity);
            getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("添加或更新成功," + entity.getClass().getName());
            }
        } catch (RuntimeException e) {
            logger.error("添加或更新异常", e);
            throw e;
        }
    }

    /**
     * 根据传入的实体删除对象
     */
    @Override
    public void delete(T entity) {
        try {
            getSession().delete(entity);
            getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("删除成功," + entity.getClass().getName());
            }
        } catch (RuntimeException e) {
            logger.error("删除异常", e);
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public PageList<T> getPageList(final HibernateQuery cq, final PageList<T> page) {

        Criteria criteria = getSession().createCriteria(entityClass).add(cq);
        if (page.getLimit() == -1) {
            page.setResultList(criteria.list());
            return page;
        }
        if (page.getTotalCount() == -1) {
            CriteriaImpl impl = (CriteriaImpl) criteria;
            // 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
            Projection projection = impl.getProjection();
            final int allCounts = ((Number) criteria.setProjection(
                    Projections.rowCount()).uniqueResult()).intValue();
            criteria.setProjection(projection);
            if (projection == null) {
                criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
            }
            page.setTotalCount(allCounts);
        }
        criteria.setFirstResult(page.getStart());
        criteria.setMaxResults(page.getLimit());
        page.setResultList(criteria.list());
        return page;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findHql(String hql, Object... param) {
        Query q = getSession().createQuery(hql);
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                q.setParameter(i, param[i]);
            }
        }
        return q.list();
    }

    @Override
    public int executeHql(String hql, Object... param) {
        Query q = getSession().createQuery(hql);
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                q.setParameter(i, param[i]);
            }
        }
        return q.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> query(HibernateQuery cq) {
        Criteria criteria = getSession().createCriteria(entityClass).add(cq);
        return criteria.list();
    }

}
