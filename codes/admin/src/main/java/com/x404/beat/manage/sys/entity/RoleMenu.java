/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.beat.manage.sys.entity;

import com.x404.beat.core.entity.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色Entity
 *
 * @author ThinkGem
 * @version 2013-05-15
 */
@Entity
@Table(name = "sys_role_menu")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleMenu extends IdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Role role;
    private Menu menu;
    private Operation operation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operation_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

}
