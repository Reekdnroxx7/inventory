package com.x404.beat.manage.sys.model;

import com.x404.beat.manage.sys.entity.Role;

public class RoleEx {

    private String userRoleId;
    private String roleId;
    private String roleName;
    private String roleType;
    public boolean auth;


    public RoleEx() {
        super();
    }

    public RoleEx(Role role) {
        this.roleId = role.getId();
        this.roleName = role.getName();
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }
}
