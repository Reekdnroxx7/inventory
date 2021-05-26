package com.x404.admin.manage.sys.utils;

import com.x404.admin.core.util.ContextHolderUtils;
import com.x404.admin.core.util.SpringContextHolder;
import com.x404.admin.manage.sys.dao.IOrgDao;
import com.x404.admin.manage.sys.entity.Org;
import com.x404.admin.manage.sys.tools.OrgTree;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author xiechao
 *         权限安全类，只能看到本机构和下级机构
 *         机构缓存。
 */
public class OrgUtils {

    private IOrgDao orgDao;

    private static OrgUtils instance = new OrgUtils();

    private Map<String, Org> orgs;

    private OrgTree orgTree;

    private List<Org> allOrgs;

    private OrgUtils() {
        init();
    }

    private void init() {
        refresh();
    }

    public static OrgUtils getInstance() {
        return instance;
    }

    public synchronized void refresh() {
        if (orgDao == null) {
            orgDao = SpringContextHolder.getBean("orgDao");
            orgDao.getSession().flush();
        }
        orgs = new HashMap<String, Org>();
        orgTree = new OrgTree();
        this.allOrgs = orgDao.findAll();
        for (Org org : allOrgs) {
            add(org);
        }

    }

    private void add(Org org) {
        orgTree.addOrg(org);
        if (StringUtils.isNotBlank(org.getId())) {
            orgs.put(org.getId(), org);
        }
    }


    public Org getOrg(String orgId) {
        if (StringUtils.isBlank(orgId)) {
            return null;
        }
        if (orgs == null) {
            refresh();
        }
        return orgs.get(orgId);
    }

    /**
     * @param orgId
     * @return 下级机构。
     */
    @SuppressWarnings("unchecked")
    public List<Org> getChildList(String orgId) {
        if (StringUtils.isBlank(orgId)) {
            return null;
        }
        if (orgs == null) {
            refresh();
        }
        return (List<Org>) orgTree.getExOrg(orgId).getChildren();
    }


    public OrgTree.ExOrg getExOrg(String id) {
        return orgTree.getExOrg(id);
    }

    /**
     * @return 请求参数为orgId对应的机构号，
     * 如果请求的机构不属于用户所在机构，则返回用户所在机构
     */
    public Org getRequestOrg() {
        String orgId = ContextHolderUtils.getRequest().getParameter("orgId");
        this.getOrg(orgId);
        Org userOrg = UserUtils.getCurrentUserInfo().getOrg();

        if (StringUtils.isEmpty(orgId)) {
            return userOrg;
        }
        Org requestOrg = this.getOrg(orgId);
        if (requestOrg == null) {
            return userOrg;
        }
        return userOrg.getLevel() > requestOrg.getLevel() ? userOrg : requestOrg;
    }

    /**
     * @return 所有下级机构
     */
    public List<Org> getAllChildList(String orgId) {
        List<Org> list = new ArrayList<Org>();
        List<Org> childList = this.orgTree.getChildList(orgId);
        for (Org org : childList) {
            list.add(org);
            list.addAll(getAllChildList(org.getId()));
        }
        return list;
    }

    /**
     * @return 所有下级机构id
     */
    public List<String> getAllChildIds(String orgId) {
        List<String> list = new ArrayList<String>();
        List<Org> childList = this.orgTree.getChildList(orgId);
        for (Org org : childList) {
            list.add(org.getId());
            list.addAll(getAllChildIds(org.getId()));
        }
        return list;
    }

    public static class ExOrg extends Org {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public boolean leaf;

        public List<ExOrg> children;

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public List<ExOrg> getChildren() {
            return children;
        }

        public void setChildren(List<ExOrg> children) {
            this.children = children;
        }
    }

    /**
     * @param big   上级机构
     * @param small 下级机构
     * @return 0 同一机构， 1，上下级关系成立，-1 不成立
     */
    public int isLower(Org big, Org small) {
        if (big.getId().equals(small.getId())) {
            return 0;
        }
        String parentIds = small.getParentIds();
        String[] split = parentIds.split(",");
        if (Arrays.binarySearch(split, big.getId()) > 0) {
            return 1;
        }
        return -1;
    }

}
