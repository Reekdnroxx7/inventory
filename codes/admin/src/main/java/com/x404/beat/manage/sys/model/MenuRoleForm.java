package com.x404.beat.manage.sys.model;

import com.x404.beat.manage.sys.entity.RoleMenu;
import com.x404.beat.manage.sys.tools.MenuTree;
import com.x404.beat.manage.sys.entity.Operation;

import java.util.ArrayList;
import java.util.List;

public class MenuRoleForm {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private boolean checked;
    private String name;
    private String menuId;
    private String icon;
    private boolean leaf = false;
    private List<MenuRoleForm> children;
    private String operationId;

    public static MenuRoleForm decorateMenu(MenuTree.ExMenu menu, List<RoleMenu> roleMenus) {
        List<HashRoleMenu> hashRoleMenus = new ArrayList<MenuRoleForm.HashRoleMenu>();
        for (RoleMenu rm : roleMenus) {
            hashRoleMenus.add(new HashRoleMenu(rm.getMenu().getId(), rm.getOperation() != null ? rm.getOperation().getId() : null));
        }

        return decorateMenu0(menu, hashRoleMenus);

    }

    private static MenuRoleForm decorateMenu0(MenuTree.ExMenu menu,
                                              List<HashRoleMenu> hashRoleMenus) {

        MenuRoleForm mex = new MenuRoleForm();
        mex.checked = hashRoleMenus.contains(new HashRoleMenu(menu.getId(), null));
        mex.name = menu.getName();
        mex.menuId = menu.getId();
        mex.icon = menu.getIcon();
//		mex.operationId
        @SuppressWarnings("unchecked")
        List<MenuTree.ExMenu> exmList = (List<MenuTree.ExMenu>) menu.getChildren();
        List<MenuRoleForm> temp = new ArrayList<MenuRoleForm>(exmList.size());
        if (exmList != null) {
            for (MenuTree.ExMenu m : exmList) {
                temp.add(decorateMenu0(m, hashRoleMenus));
            }
        }
        List<Operation> operations = menu.getOperations();
        if (operations != null) {
            for (Operation p : operations) {
                temp.add(decorateMenu1(p, hashRoleMenus));
            }
        }
        mex.children = temp;
        if (mex.children.size() == 0) {
            mex.leaf = true;
        }
        return mex;
    }

    private static MenuRoleForm decorateMenu1(Operation p,
                                              List<HashRoleMenu> hashRoleMenus) {
        MenuRoleForm mex = new MenuRoleForm();
        mex.checked = hashRoleMenus.contains(new HashRoleMenu(p.getMenuId(), p.getId()));
        mex.leaf = true;
        mex.name = p.getOperationName();
        mex.menuId = p.getMenuId();
        mex.operationId = p.getId();
        mex.icon = null;
        return mex;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuRoleForm> getChildren() {
        return children;
    }

    public void setChildren(List<MenuRoleForm> children) {
        this.children = children;
    }


    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }


    private static class HashRoleMenu {
        private String menuId;
        private String operationId;

        public HashRoleMenu(String menuId, String operationId) {
            super();
            this.menuId = menuId;
            this.operationId = operationId;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((menuId == null) ? 0 : menuId.hashCode());
            result = prime * result
                    + ((operationId == null) ? 0 : operationId.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            HashRoleMenu other = (HashRoleMenu) obj;
            if (menuId == null) {
                if (other.menuId != null)
                    return false;
            } else if (!menuId.equals(other.menuId))
                return false;
            if (operationId == null) {
                if (other.operationId != null)
                    return false;
            } else if (!operationId.equals(other.operationId))
                return false;
            return true;
        }


    }


}
