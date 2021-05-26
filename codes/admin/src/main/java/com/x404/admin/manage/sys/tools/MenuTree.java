package com.x404.admin.manage.sys.tools;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.x404.admin.manage.sys.entity.Menu;
import com.x404.admin.manage.sys.entity.Operation;
import com.x404.admin.manage.sys.utils.OperationUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * @author xiechao
 *         菜单缓存维护 树形结构
 */
public class MenuTree implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String ROOT_MENU_ID = "0";

    private Map<String, List<Menu>> childrenMap = new HashMap<String, List<Menu>>();
    //	private  Map<String,Menu> urlKeyMap = new HashMap<String, Menu>();
    private Map<String, Menu> idKeyMap = new HashMap<String, Menu>();

    private Map<String, ExMenu> exMenuMap;

    public void addMenu(Menu menu) {
        if (idKeyMap.containsKey(menu.getId())) {
            return;
        }
        idKeyMap.put(menu.getId(), menu);
        String parentId = menu.getParentId();
        if (StringUtils.isEmpty(parentId)) {
            parentId = ROOT_MENU_ID;
        }
        addChild(parentId, menu);
    }

    private void addChild(String parentId, Menu m) {
        List<Menu> sst = childrenMap.get(parentId);
        if (sst == null) {
            sst = new ArrayList<Menu>();
            childrenMap.put(parentId, sst);
        }
        sst.add(m);
    }

    public List<Menu> getRootMenus() {
        return getChildList(ROOT_MENU_ID);
    }

    private List<Menu> getChildList0(String id) {
        List<Menu> list = childrenMap.get(id);
        if (list == null) {
            return new ArrayList<Menu>();
        }
        return list;
    }


    public List<Menu> getChildList(String id) {
        ExMenu exMenu = getExMenu(id);
        if (exMenu == null) {
            return new ArrayList<Menu>();
        }
        @SuppressWarnings("unchecked")
        List<Menu> childs = (List<Menu>) exMenu.getChildren();
        return childs;
    }

    public Menu getById(String id) {
        return this.idKeyMap.get(id);
    }

    public void decorateTree() {
        if (exMenuMap == null) {
            exMenuMap = new HashMap<String, MenuTree.ExMenu>();
        }
        decoreteNode("1");
    }

    private ExMenu decoreteNode(String id) {
        Menu menu = getById(id);
        if (menu == null) {
            return null;
        }
        ExMenu exMenu = new ExMenu(menu);
        List<Menu> childList = getChildList0(id);
        List<ExMenu> children = new ArrayList<MenuTree.ExMenu>();
        for (Menu m : childList) {
            children.add(decoreteNode(m.getId()));
        }
        exMenu.setChildren(children);
        this.exMenuMap.put(menu.getId(), exMenu);
        return exMenu;
    }

    public ExMenu getExMenu(String id) {
        if (exMenuMap == null) {
            decorateTree();
        }

        ExMenu exMenu = exMenuMap.get(id);
        return exMenu;
    }

    public static class ExMenu extends Menu {
        /**
         *
         */

        private List<Operation> operations;
        private static final long serialVersionUID = 1L;
        private Menu menu;
        private List<ExMenu> children;

        public ExMenu(Menu menu) {
            this.menu = menu;
            this.operations = OperationUtils.getInstance().getMenuOperation(menu.getId());
        }

        @JsonIgnore
        public Menu getMenu() {
            return menu;
        }

        public List<? extends Menu> getChildren() {
            return children;
        }

        public void setChildren(List<ExMenu> children) {
            this.children = children;
        }

        public String getId() {
            return menu.getId();
        }

        public void setId(String id) {
            menu.setId(id);
        }

        public String getRemarks() {
            return menu.getRemarks();
        }

        public void setRemarks(String remarks) {
            menu.setRemarks(remarks);
        }

        //		public void setCreateBy(User createBy) {
//			menu.setCreateBy(createBy);
//		}
        public String getParentId() {
            return menu.getParentId();
        }

        public void setCreateDate(Date createDate) {
            menu.setCreateDate(createDate);
        }

        public void setParentId(String parentId) {
            menu.setParentId(parentId);
        }

        public String getParentIds() {
            return menu.getParentIds();
        }

        public void setParentIds(String parentIds) {
            menu.setParentIds(parentIds);
        }

        public String getName() {
            return menu.getName();
        }

        public void setName(String name) {
            menu.setName(name);
        }

        public String getTranscode() {
            return menu.getTranscode();
        }

        public void setTranscode(String transcode) {
            menu.setTranscode(transcode);
        }

        public void setIcon(String icon) {
            menu.setIcon(icon);
        }

        public String getIcon() {
            return menu.getIcon();
        }

        public Integer getSort() {
            return menu.getSort();
        }

        public void setSort(Integer sort) {
            menu.setSort(sort);
        }

        public Integer getLevel() {
            return menu.getLevel();
        }

        public void setLevel(Integer level) {
            menu.setLevel(level);
        }

        public String getIsShow() {
            return menu.getIsShow();
        }

        public void setIsShow(String isShow) {
            menu.setIsShow(isShow);
        }

        public String getType() {
            return menu.getType();
        }

        public void setType(String type) {
            menu.setType(type);
        }

        public boolean isRoot() {
            return menu.isRoot();
        }

        public String getTarget() {
            return menu.getTarget();
        }

        public void setTarget(String target) {
            menu.setTarget(target);
        }

        public List<Operation> getOperations() {
            return operations;
        }

        public void setOperations(List<Operation> operations) {
            this.operations = operations;
        }


    }


}
