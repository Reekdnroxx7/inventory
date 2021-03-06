/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.admin.manage.sys.entity;

import com.sun.istack.internal.NotNull;
import com.x404.admin.core.entity.DataEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * 区域Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
@Entity
@Table(name = "sys_area")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Area extends DataEntity {

    private static final long serialVersionUID = 1L;
    private Area parent;    // 父级编号
    private String parentIds; // 所有父级编号
    private String code;    // 区域编码
    private String name;    // 区域名称
    private String type;    // 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）

    private List<Area> childList = new ArrayList<Area>();    // 拥有子区域列表

    public Area() {
        super();
    }

    public Area(String id) {
        this();
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @NotNull
    public Area getParent() {
        return parent;
    }

    public void setParent(Area parent) {
        this.parent = parent;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy(value = "code")
    @Fetch(FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public List<Area> getChildList() {
        return childList;
    }

    public void setChildList(List<Area> childList) {
        this.childList = childList;
    }

    @Transient
    public static void sortList(List<Area> list, List<Area> sourcelist, String parentId) {
        for (int i = 0; i < sourcelist.size(); i++) {
            Area e = sourcelist.get(i);
            if (e.getParent() != null && e.getParent().getId() != null
                    && e.getParent().getId().equals(parentId)) {
                list.add(e);
                // 判断是否还有子节点, 有则继续获取子节点
                for (int j = 0; j < sourcelist.size(); j++) {
                    Area childe = sourcelist.get(j);
                    if (childe.getParent() != null && childe.getParent().getId() != null
                            && childe.getParent().getId().equals(e.getId())) {
                        sortList(list, sourcelist, e.getId());
                        break;
                    }
                }
            }
        }
    }

    @Transient
    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    @Transient
    public static boolean isAdmin(String id) {
        return id != null && id.equals("1");
    }
}