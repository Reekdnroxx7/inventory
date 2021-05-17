package com.x404.beat.manage.sys.service.impl;

import com.x404.beat.core.hibernate.dao.impl.HibernateDao;
import com.x404.beat.core.hibernate.query.HibernateQuery;
import com.x404.beat.core.page.ExPageList;
import com.x404.beat.manage.sys.dao.IMenuDao;
import com.x404.beat.manage.sys.entity.Menu;
import com.x404.beat.manage.sys.service.IMenuService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张代浩
 */
@Service
@Transactional
public class MenuService extends HibernateDao<Menu> implements IMenuService {
    @Autowired
    private IMenuDao menuDao;


    public IMenuDao getMenuDao() {
        return menuDao;
    }

    public void setMenuDao(IMenuDao menuDao) {
        this.menuDao = menuDao;
    }


    public Session getSession() {
        return menuDao.getSession();
    }

    public void saveOrUpdate(Menu entity) {
        menuDao.saveOrUpdate(entity);
    }

    public ExPageList<Menu> getPageList(HibernateQuery cq, ExPageList<Menu> page) {
        return menuDao.getPageList(cq, page);
    }

    public Serializable save(Menu entity) {
        return menuDao.save(entity);
    }

    public void saveBatch(List<Menu> entityList) {
        menuDao.saveBatch(entityList);
    }

    public List<Menu> findHql(String hql, Object... param) {
        return menuDao.findHql(hql, param);
    }

    public int deleteById(Serializable id) {
        return menuDao.deleteById(id);
    }

    public int executeHql(String hql, Object... param) {
        return menuDao.executeHql(hql, param);
    }

    public void updateByIdSelective(Menu entity) {
        menuDao.updateByIdSelective(entity);
    }

    public List<Menu> query(HibernateQuery cq) {
        return menuDao.query(cq);
    }

    public Menu findById(Serializable id) {
        return menuDao.findById(id);
    }

    public List<Menu> findByExample(Menu entity) {
        return menuDao.findByExample(entity);
    }

    public List<Menu> findAll() {
        return menuDao.findAll();
    }

    @Override
    public List<Menu> getChildMenus(String parentId) {
        Menu m = new Menu();
        m.setParentId(parentId);
        return this.menuDao.findByExample(m);
    }


}
