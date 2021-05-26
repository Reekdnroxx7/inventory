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

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 用户Entity
 * @author ThinkGem
 * @version 2013-5-15
 */
@Entity
@Table(name = "sys_user")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends DataEntity {

    private static final long serialVersionUID = 1L;

    private String orgId;    // 归属部门
    private String loginName;// 登录名
    private String password;// 密码
    private String name;    // 姓名
    private String email;    // 邮箱
    private String mobile;    // 手机
    private String locale;
    private String userType;// 用户类型
    private String loginIp;    // 最后登陆IP
    private Date loginDate;    // 最后登陆日期

    public User() {
        super();
    }

    public User(String id) {
        this();
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

//	@Length(min=1, max=100)
//	public String getNo() {
//		return no;
//	}
//
//	public void setNo(String no) {
//		this.no = no;
//	}

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//	@Length(min=0, max=200)
//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Transient
    public String getRemarks() {
        return remarks;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Transient
    public Date getCreateDate() {
        return createDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
//	@OrderBy("id") @Fetch(FetchMode.SUBSELECT)
//	@NotFound(action = NotFoundAction.IGNORE)
//	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//	@Where(clause = "sys_user_role.role_type in ('0','2')")
//	@JsonIgnore
//	public List<Role> getAccessRoleList() {
//		return accessRoleList;
//	}
//
//
//
//	public void setAccessRoleList(List<Role> accessRoleList) {
//		this.accessRoleList = accessRoleList;
//	}


//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
//	@OrderBy("id") @Fetch(FetchMode.SUBSELECT)
//	@NotFound(action = NotFoundAction.IGNORE)
//	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//	@Where(clause = "sys_user_role.role_type in ('1','2')")
//	@JsonIgnore
//	public List<Role> getAssignRoleList() {
//		return assignRoleList;
//	}

//	public void setAssignRoleList(List<Role> assignRoleList) {
//		this.assignRoleList = assignRoleList;
//	}
//
//	@Transient
//	@JsonIgnore
//	public List<String> getRoleIdList() {
//		List<String> roleIdList = Lists.newArrayList();
//		for (Role role : accessRoleList) {
//			roleIdList.add(role.getId());
//		}
//		return roleIdList;
//	}
//
//	@Transient
//	public void setRoleIdList(List<String> roleIdList) {
//		accessRoleList = Lists.newArrayList();
//		for (String roleId : roleIdList) {
//			Role role = new Role();
//			role.setId(roleId);
//			accessRoleList.add(role);
//		}
//	}


}