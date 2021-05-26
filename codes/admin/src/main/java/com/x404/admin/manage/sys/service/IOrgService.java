package com.x404.admin.manage.sys.service;

import com.x404.admin.core.service.IHibernateService;
import com.x404.admin.manage.sys.entity.Org;

import java.util.List;

public interface IOrgService extends IHibernateService<Org> {

    List<Org> getChildOrgs(String parentId);

}
