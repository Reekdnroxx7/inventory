package com.x404.beat.manage.sys.service;

import com.x404.beat.manage.sys.entity.Operation;
import com.x404.beat.core.service.IHibernateService;

import java.util.List;

/**
 * @author 张代浩
 */
public interface IOperationService extends IHibernateService<Operation> {

    public List<Operation> findByMenu(String menuId);
}
