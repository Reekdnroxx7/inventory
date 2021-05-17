package com.x404.beat.manage.sys.tools;

import com.x404.beat.manage.sys.entity.Operation;
import com.x404.beat.manage.sys.utils.OrgUtils;
import com.x404.beat.manage.sys.entity.Menu;
import com.x404.beat.manage.sys.entity.Org;
import com.x404.beat.manage.sys.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 在线用户对象
 *
 * @author JueYue
 * @version 1.0
 * @date 2013-9-28
 */
public class UserInfo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private User user;

    private Org org;

    private List<Menu> rootMenus;

    private Set<Operation> operations = new HashSet<Operation>();


    private MenuTree menuTree;
    /**
     * 用户IP
     */
    private java.lang.String ip;
    /**
     * 登录时间
     */
    private java.util.Date logindatetime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public java.lang.String getIp() {
        return ip;
    }

    public void setIp(java.lang.String ip) {
        this.ip = ip;
    }

    public java.util.Date getLogindatetime() {
        return logindatetime;
    }

    public void setLogindatetime(java.util.Date logindatetime) {
        this.logindatetime = logindatetime;
    }

//	public Map<String, Set<Operation>> getOperations() {
//		return operations;
//	}

    public List<Menu> getRootMenus() {
        if (rootMenus == null) {
            rootMenus = this.menuTree.getRootMenus();
        }
        return rootMenus;
    }


    public void setRootMenus(List<Menu> rootMenus) {
        this.rootMenus = rootMenus;
    }

    public List<Menu> getChildrenMenus(String menuId) {
        return menuTree.getChildList(menuId);
    }

//	public void setOperations(Map<String, Set<String>> operations) {
//		this.operations = operations;
//	}

    public void add(Menu menu) {
        menuTree.addMenu(menu);
    }

    public void add(Operation operation) {
        operations.add(operation);
    }


    public boolean hasAuth(Operation operation) {

        return operations.contains(operation);
    }

    public boolean hasAuth(String menuId) {
        return menuTree.getById(menuId) != null;
    }

    public void clear() {
        menuTree = new MenuTree();
        this.operations.clear();
    }

    public Org getOrg() {
        if (org == null) {
            org = OrgUtils.getInstance().getOrg(user.getOrgId());
        }
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }


    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}
