package com.x404.beat.manage.sys.service.impl;

import com.x404.beat.core.util.PasswordUtil;
import com.x404.beat.manage.sys.entity.RoleMenu;
import com.x404.beat.core.hibernate.query.HibernateQuery;
import com.x404.beat.core.page.ExPageList;
import com.x404.beat.manage.sys.dao.IUserDao;
import com.x404.beat.manage.sys.entity.User;
import com.x404.beat.manage.sys.service.IUserService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张代浩
 */
@Service("userService")
@Transactional
public class UserService implements IUserService {

//	@Autowired
//	private IUserRoleDao userRoleDao;
//
//
//
//	public IUserRoleDao getUserRoleDao() {
//		return userRoleDao;
//	}
//
//	public void setUserRoleDao(IUserRoleDao userRoleDao) {
//		this.userRoleDao = userRoleDao;
//	}

    @Autowired
    private IUserDao userDao;

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }


    public int deleteById(Serializable id) {
        return userDao.deleteById(id);
    }

    @Override
    public User checkUserExits(User user) {
        String password = PasswordUtil.encrypt(user.getLoginName(), user.getPassword(), PasswordUtil.getStaticSalt());
        User entity = new User();
        entity.setLoginName(user.getLoginName());
        entity.setPassword(password);
        List<User> users = this.userDao.findByExample(entity);
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public List<RoleMenu> getAccessMenus(User u) {
        return userDao.getAccessMenus(u);
    }

    public Session getSession() {
        return userDao.getSession();
    }

    public void saveOrUpdate(User entity) {
        userDao.saveOrUpdate(entity);
    }

    public ExPageList<User> getPageList(HibernateQuery cq, ExPageList<User> page) {
        return userDao.getPageList(cq, page);
    }

    public Serializable save(User entity) {
        return userDao.save(entity);
    }

    public void saveBatch(List<User> entityList) {
        userDao.saveBatch(entityList);
    }

    public List<User> findHql(String hql, Object... param) {
        return userDao.findHql(hql, param);
    }

    public int executeHql(String hql, Object... param) {
        return userDao.executeHql(hql, param);
    }

    public void updateByIdSelective(User entity) {
        userDao.updateByIdSelective(entity);
    }

    public List<User> query(HibernateQuery cq) {
        return userDao.query(cq);
    }

    public User findById(Serializable id) {
        return userDao.findById(id);
    }

    public List<User> findByExample(User entity) {
        return userDao.findByExample(entity);
    }

    public void delete(User entity) {
        userDao.delete(entity);
    }


}
