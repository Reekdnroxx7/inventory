package com.x404.admin.manage.sys.service.impl;

import com.x404.admin.manage.sys.dao.ITaskDao;
import com.x404.admin.manage.sys.entity.Task;
import com.xc350.web.base.mybatis.dao.MybatisExample;
import com.xc350.web.base.query.PageList;
import com.x404.admin.manage.sys.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private ITaskDao taskDao;

    public ITaskDao getTaskDao() {
        return taskDao;
    }

    public void setTaskDao(ITaskDao taskDao) {
        this.taskDao = taskDao;
    }


    public Task selectByKey(Serializable id) {
        return taskDao.selectByKey(id);
    }

    public int countByExample(MybatisExample example) {
        return taskDao.countByExample(example);
    }

    public List<Task> selectByExample(MybatisExample example) {
        return taskDao.selectByExample(example);
    }

    public List<Task> selectAll() {
        return taskDao.selectAll();
    }


    public void insert(Task entity) {
        taskDao.insert(entity);
    }

    public void insertSelective(Task entity) {
        taskDao.insertSelective(entity);
    }

    public void insertBatch(List<Task> entityList) {
        taskDao.insertBatch(entityList);
    }

    public int deleteByKey(Serializable id) {
        return taskDao.deleteByKey(id);
    }

    public int deleteByExample(MybatisExample example) {
        return taskDao.deleteByExample(example);
    }

    public void updateByKey(Task t) {
        taskDao.updateByKey(t);
    }


    public void updateByKeySelective(Task t) {
        taskDao.updateByKeySelective(t);
    }

    public void updateByExample(Task t, MybatisExample example) {
        taskDao.updateByExample(t, example);
    }

    public void updateByExampleSelective(Task t, MybatisExample example) {
        taskDao.updateByExampleSelective(t, example);
    }


    /**
     * 分页查询
     *
     * @param example
     * @return
     */
    public PageList<Task> page(MybatisExample example) {
        return new PageList<Task>(taskDao.countByExample(example), taskDao.selectByExample(example));
    }
}
