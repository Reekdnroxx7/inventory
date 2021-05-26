/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.admin.manage.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.x404.admin.core.entity.DataEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 角色Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
@Entity
@Table(name = "sys_role")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends DataEntity {

    private static final long serialVersionUID = 1L;
    private String name;    // 角色名称

    private List<RoleMenu> roleMenus;

    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    @JsonIgnore
    public List<RoleMenu> getRoleMenus() {
        return roleMenus;
    }

    public void setRoleMenus(List<RoleMenu> roleMenus) {
        this.roleMenus = roleMenus;
    }


}
