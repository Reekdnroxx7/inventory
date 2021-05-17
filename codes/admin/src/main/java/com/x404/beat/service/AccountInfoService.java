package com.x404.beat.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.x404.beat.dao.IAccountInfoDao;
import com.x404.beat.models.AccountInfo;
import com.xc350.web.base.mybatis.dao.MybatisExample;
import com.xc350.web.base.query.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
@Service
public class AccountInfoService {

	@Autowired
	private IAccountInfoDao accountInfoDao;

	public IAccountInfoDao getAccountInfoDao() {
		return accountInfoDao;
	}

	public void setAccountInfoDao(IAccountInfoDao accountInfoDao) {
		this.accountInfoDao = accountInfoDao;
	}

	public AccountInfo selectByKey(Serializable id) {
		return accountInfoDao.selectByKey(id);
	}

	public int countByExample(MybatisExample example) {
		return accountInfoDao.countByExample(example);
	}

	public List<AccountInfo> selectByExample(MybatisExample example) {
		return accountInfoDao.selectByExample(example);
	}

	public List<AccountInfo> selectAll() {
		return accountInfoDao.selectAll();
	}


	public void insert(AccountInfo entity) {
		accountInfoDao.insert(entity);
	}

	public void insertSelective(AccountInfo entity) {
		accountInfoDao.insertSelective(entity);
	}

	public void insertBatch(List<AccountInfo> entityList) {
        accountInfoDao.insertBatch(entityList);
    }

    public int deleteByKey(Serializable id) {
        return accountInfoDao.deleteByKey(id);
    }

	public int deleteByExample(MybatisExample example) {
		return accountInfoDao.deleteByExample(example);
	}

	public void updateByKey(AccountInfo t) {
		accountInfoDao.updateByKey(t);
	}


	public void updateByKeySelective(AccountInfo t) {
		accountInfoDao.updateByKeySelective(t);
	}

	public void updateByExample(AccountInfo t, MybatisExample example) {
		accountInfoDao.updateByExample(t, example);
	}

	public void updateByExampleSelective(AccountInfo t, MybatisExample example) {
		accountInfoDao.updateByExampleSelective(t, example);
	}


	public PageList<AccountInfo> pageList(MybatisExample example) {
		PageBounds pageBounds = new PageBounds((example.getStart()/example.getLimit() +1),example.getLimit());
		return pageList(example,pageBounds);
	}

	/**分页查询
	 * @param example
	 * @param pageBounds
	 * @return
	 */
	public PageList<AccountInfo> pageList(MybatisExample example,PageBounds pageBounds){
		com.github.miemiedev.mybatis.paginator.domain.PageList<AccountInfo> accountInfoList = (com.github.miemiedev.mybatis.paginator.domain.PageList<AccountInfo>) accountInfoDao.pageList(example, pageBounds);
		return  new PageList<>(accountInfoList.getPaginator().getTotalCount(),accountInfoList);
	}
}
