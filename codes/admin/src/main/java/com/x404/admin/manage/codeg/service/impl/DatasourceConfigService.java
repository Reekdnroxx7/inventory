package com.x404.admin.manage.codeg.service.impl;

import com.x404.admin.manage.codeg.dao.IDatasourceConfigDao;
import com.x404.admin.manage.codeg.entity.DatasourceConfig;
import com.x404.admin.manage.codeg.service.IDatasourceConfigService;
import com.xc350.web.base.mybatis.dao.MybatisExample;
import com.xc350.web.base.query.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class DatasourceConfigService implements IDatasourceConfigService {


    @Autowired
    private IDatasourceConfigDao datasourceConfigDao;


    public DatasourceConfig selectByKey(Serializable serializable) {
        return datasourceConfigDao.selectByKey(serializable);
    }

    public List<DatasourceConfig> selectAll() {
        return datasourceConfigDao.selectAll();
    }

    public List<DatasourceConfig> selectByExample(MybatisExample mybatisExample) {
        return datasourceConfigDao.selectByExample(mybatisExample);
    }

    public PageList<DatasourceConfig> page(MybatisExample mybatisExample) {

        return new PageList<DatasourceConfig>(countByExample(mybatisExample), selectByExample(mybatisExample));
    }

    public int countByExample(MybatisExample mybatisExample) {
        return datasourceConfigDao.countByExample(mybatisExample);
    }

    public void insert(DatasourceConfig datasourceConfig) {
        datasourceConfigDao.insert(datasourceConfig);
    }

    public void insertSelective(DatasourceConfig datasourceConfig) {
        datasourceConfigDao.insertSelective(datasourceConfig);
    }

    public void insertBatch(List<DatasourceConfig> list) {
        datasourceConfigDao.insertBatch(list);
    }

    public int deleteByKey(Serializable serializable) {
        return datasourceConfigDao.deleteByKey(serializable);
    }

    public int deleteByExample(MybatisExample mybatisExample) {
        return datasourceConfigDao.deleteByExample(mybatisExample);
    }

    public void updateByKey(DatasourceConfig datasourceConfig) {
        datasourceConfigDao.updateByKey(datasourceConfig);
    }

    public void updateByKeySelective(DatasourceConfig datasourceConfig) {
        datasourceConfigDao.updateByKeySelective(datasourceConfig);
    }

    public void updateByExample(DatasourceConfig datasourceConfig, MybatisExample mybatisExample) {
        datasourceConfigDao.updateByExample(datasourceConfig, mybatisExample);
    }

    public void updateByExampleSelective(DatasourceConfig datasourceConfig, MybatisExample mybatisExample) {
        datasourceConfigDao.updateByExampleSelective(datasourceConfig, mybatisExample);
    }
}
