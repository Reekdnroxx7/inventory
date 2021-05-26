package com.x404.admin.manage.sys.tools;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.x404.admin.manage.sys.entity.Org;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * @author xiechao
 *         机构缓存维护，树形结构
 */
public class OrgTree implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String ROOT_ORG_ID = "0";

    private Map<String, List<Org>> childrenMap = new HashMap<String, List<Org>>();
    private Map<String, Org> codeKeyMap = new HashMap<String, Org>();
    private Map<String, Org> idKeyMap = new HashMap<String, Org>();

    private Map<String, ExOrg> exOrgMap;


    public void addOrg(Org org) {
        if (idKeyMap.containsKey(org.getId())) {
            return;
        }
        idKeyMap.put(org.getId(), org);
        if (StringUtils.isNotBlank(org.getCode())) {
            codeKeyMap.put(org.getCode(), org);
        }
        String parentId = org.getParentId();
        if (StringUtils.isEmpty(parentId)) {
            parentId = ROOT_ORG_ID;
        }
        addChild(parentId, org);
    }

    private void addChild(String parentId, Org m) {
        List<Org> sst = childrenMap.get(parentId);
        if (sst == null) {
            sst = new ArrayList<Org>();
            childrenMap.put(parentId, sst);
        }
        sst.add(m);
    }

    public List<Org> getRootOrgs() {
        return getChildList(ROOT_ORG_ID);
    }

    private List<Org> getChildList0(String id) {
        List<Org> list = childrenMap.get(id);
        if (list == null) {
            return new ArrayList<Org>();
        }
        return (List<Org>) list;
    }

    @SuppressWarnings("unchecked")
    public List<Org> getChildList(String id) {
        ExOrg exOrg = getExOrg(id);
        if (exOrg == null) {
            return new ArrayList<Org>();
        }
        List<Org> childs = (List<Org>) exOrg.getChildren();
        return childs;
    }

    public Org getByUrl(String url) {
        return this.codeKeyMap.get(url);
    }

    public Org getById(String id) {
        return this.idKeyMap.get(id);
    }

    public void decorateTree() {
        if (exOrgMap == null) {
            exOrgMap = new HashMap<String, OrgTree.ExOrg>();
        }
        decoreteNode("1");
    }

    private ExOrg decoreteNode(String id) {
        Org org = getById(id);
        if (org == null) {
            return null;
        }
        ExOrg exOrg = new ExOrg(org);
        List<Org> childList = getChildList0(id);
        List<ExOrg> children = new ArrayList<OrgTree.ExOrg>();
        for (Org m : childList) {
            children.add(decoreteNode(m.getId()));
        }
        exOrg.setChildren(children);
        this.exOrgMap.put(org.getId(), exOrg);
        return exOrg;
    }


    public ExOrg getExOrg(String id) {
        if (exOrgMap == null) {
            decorateTree();
        }

        ExOrg exOrg = exOrgMap.get(id);
        return exOrg;
    }

    public static class ExOrg extends Org {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private Org org;
        private List<ExOrg> children;

        public ExOrg(Org org) {
            this.org = org;
        }

        @JsonIgnore
        public Org getOrg() {
            return org;
        }

        public List<? extends Org> getChildren() {
            return children;
        }

        public void setChildren(List<ExOrg> children) {
            this.children = children;
        }

        public String getId() {
            return org.getId();
        }

        public void setId(String id) {
            org.setId(id);
        }

        public String getRemarks() {
            return org.getRemarks();
        }

        public void setRemarks(String remarks) {
            org.setRemarks(remarks);
        }

        //		public void setCreateBy(User createBy) {
//			org.setCreateBy(createBy);
//		}
        public String getParentId() {
            return org.getParentId();
        }

        public void setCreateDate(Date createDate) {
            org.setCreateDate(createDate);
        }

        public void setParentId(String parentId) {
            org.setParentId(parentId);
        }

        public String getParentIds() {
            return org.getParentIds();
        }

        public void setParentIds(String parentIds) {
            org.setParentIds(parentIds);
        }

        public String getName() {
            return org.getName();
        }

        public void setName(String name) {
            org.setName(name);
        }

        public String getType() {
            return org.getType();
        }

        public void setType(String type) {
            org.setType(type);
        }

        public Integer getLevel() {
            return org.getLevel();
        }

        public void setLevel(Integer level) {
            org.setLevel(level);
        }

        public String getAddress() {
            return org.getAddress();
        }

        public void setAddress(String address) {
            org.setAddress(address);
        }

        public String getZipCode() {
            return org.getZipCode();
        }

        public void setZipCode(String zipCode) {
            org.setZipCode(zipCode);
        }

        public String getMaster() {
            return org.getMaster();
        }

        public void setMaster(String master) {
            org.setMaster(master);
        }

        public String getPhone() {
            return org.getPhone();
        }

        public void setPhone(String phone) {
            org.setPhone(phone);
        }

        public String getFax() {
            return org.getFax();
        }

        public void setFax(String fax) {
            org.setFax(fax);
        }

        public String getEmail() {
            return org.getEmail();
        }

        public void setEmail(String email) {
            org.setEmail(email);
        }

        public String getCode() {
            return org.getCode();
        }

        public void setCode(String code) {
            org.setCode(code);
        }

        public boolean isRoot() {
            return org.isRoot();
        }

        public String toString() {
            return org.toString();
        }

    }


}
