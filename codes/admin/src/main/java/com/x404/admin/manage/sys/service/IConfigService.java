package com.x404.admin.manage.sys.service;

import com.x404.admin.core.service.IHibernateService;
import com.x404.admin.manage.sys.entity.Config;

public interface IConfigService extends IHibernateService<Config> {

    void updateByKey(Config config);
}
