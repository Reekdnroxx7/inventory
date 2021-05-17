/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.beat.manage.sys.entity;

import com.x404.beat.core.entity.DataEntity;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

/**
 * 菜单Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
@Entity
@Table(name = "sys_menu")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Menu extends DataEntity {

    private static final long serialVersionUID = 1L;


    //	private Menu parent;	// 父级菜单
    private String parentId;
    private String parentIds; // 所有父级编号
    private String name;    // 名称
    private String transcode;
    private String target;    // 目标
    private String icon;    // 图标
    private Integer sort;    // 排序
    private Integer level; //级别
    private String isShow;    // 是否在菜单中显示（1：显示；0：不显示）
    //	private String isActiviti; 	// 是否同步到工作流（1：同步；0：不同步）
//	private String permission; // 权限标识
    private String type; // 0 功能菜单，1 父菜单
//	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表
//	private List<Operation> operationList ;

    public Menu() {
        super();
    }

    public Menu(String id) {
        this();
        this.id = id;
    }

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="parent_id")
//	@NotFound(action = NotFoundAction.IGNORE)
//	@NotNull
//	public Menu getParent() {
//		return parent;
//	}


    @Column(name = "parent_id")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

//	public void setParent(Menu parent) {
//		this.parent = parent;
//	}

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

    public String getTranscode() {
        return this.transcode;
    }

    public void setTranscode(String transcode) {
        this.transcode = transcode;
    }


    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

//	@Length(min=0, max=20)
//	public String getTarget() {
//		return target;
//	}
//
//	public void setTarget(String target) {
//		this.target = target;
//	}

    public String getIcon() {
        return icon;
    }


    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//	@OneToMany(mappedBy = "parentId", fetch=FetchType.LAZY)
//	@OrderBy(value="sort") @Fetch(FetchMode.SUBSELECT)
//	@NotFound(action = NotFoundAction.IGNORE)
//	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//	@JsonIgnore
//	public List<Menu> getChildList() {
//		return childList;
//	}
//
//	public void setChildList(List<Menu> childList) {
//		this.childList = childList;
//	}
//
//	@ManyToMany(mappedBy = "menuList", fetch=FetchType.LAZY)
//	@OrderBy("id") @Fetch(FetchMode.SUBSELECT)
//	@NotFound(action = NotFoundAction.IGNORE)
//	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//	public List<Role> getRoleList() {
//		return roleList;
//	}
//
//	public void setRoleList(List<Role> roleList) {
//		this.roleList = roleList;
//	}


//	@OneToMany(fetch=FetchType.LAZY,mappedBy="menuId")
//	@NotFound(action = NotFoundAction.IGNORE)
//	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//	@JsonIgnore
//	public List<Operation> getOperationList() {
//		return operationList;
//	}
//
//	public void setOperationList(List<Operation> operationList) {
//		this.operationList = operationList;
//	}

//	@Transient
//	public static void sortList(List<Menu> list, List<Menu> sourcelist, String parentId){
//		for (int i=0; i<sourcelist.size(); i++){
//			Menu e = sourcelist.get(i);
//			if (e.getParent()!=null && e.getParent().getId()!=null
//					&& e.getParent().getId().equals(parentId)){
//				list.add(e);
//				// 判断是否还有子节点, 有则继续获取子节点
//				for (int j=0; j<sourcelist.size(); j++){
//					Menu child = sourcelist.get(j);
//					if (child.getParent()!=null && child.getParent().getId()!=null
//							&& child.getParent().getId().equals(e.getId())){
//						sortList(list, sourcelist, e.getId());
//						break;
//					}
//				}
//			}
//		}
//	}

    @Transient
    public boolean isRoot() {
        return isRoot(this.id);
    }

    @Transient
    public static boolean isRoot(String id) {
        return id != null && id.equals("1");
    }

    private Set<String> transcodeSet = null;

    @Transient
    public Set<String> getTranscodes() {
        if (transcodeSet == null) {
            transcodeSet = new HashSet<String>();
            String[] split = this.getTranscode().split(",");
            for (String str : split) {
                transcodeSet.add(StringUtils.trim(str));
            }
        }
        return transcodeSet;
    }


//	private HashMap<String,Set<Operation>> operationMap = new HashMap<String, Set<Operation>>();
//
//	@Transient
//	public HashMap<String, Set<Operation>> getOperationMap() {
//		return operationMap;
//	}
//
//
//	public void addOperation(Operation operation){
//
//	}


}