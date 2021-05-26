package com.x404.admin.manage.sys.ctl;

import com.x404.admin.core.controller.BaseController;
import com.x404.admin.core.json.AjaxJson;
import com.x404.admin.manage.sys.utils.OrgUtils;
import com.x404.admin.manage.sys.service.IOrgService;
import com.x404.admin.manage.sys.utils.UserUtils;
import com.x404.admin.manage.sys.entity.Org;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/org")
public class OrgController extends BaseController {
    @Autowired
    private IOrgService orgService;

    public IOrgService getOrgService() {
        return orgService;
    }

    public void setOrgService(IOrgService orgService) {
        this.orgService = orgService;
    }


    @RequestMapping(params = "method=list")
    @ResponseBody
    public List<Org> list(HttpServletRequest request) {
        String orgId = OrgUtils.getInstance().getRequestOrg().getId();
        ArrayList<Org> arrayList = new ArrayList<Org>(1);
        arrayList.add(OrgUtils.getInstance().getExOrg(orgId));
        return arrayList;
//		return OrgUtils.getInstance().getChildList(orgId);
    }

    @RequestMapping(params = "method=save")
    @ResponseBody
    public AjaxJson save(HttpServletRequest request, Org org) {
        String saveType = ServletRequestUtils.getStringParameter(request,
                "_saveType", "add");
        if ("add".equals(saveType)) {
            Org cuOrg = UserUtils.getCurrentUserInfo().getOrg();
            String parentId = org.getParentId();
            if ("-1".equals(parentId) || cuOrg.getId().equals(parentId)) {
                parentId = cuOrg.getId();
            } else {
                if (!isChildOrg(parentId)) {
                    return new AjaxJson(false, "没有该机构操作权限");
                }
            }
            Org parent = OrgUtils.getInstance().getOrg(parentId);
            org.setParentId(parentId);
            org.setParentIds(parent.getParentId() + "," + parent.getId());
            this.orgService.save(org);
        } else {
            if (!isChildOrg(org.getId())) {
                return new AjaxJson(false, "没有该机构操作权限");
            }
            Org old = OrgUtils.getInstance().getOrg(org.getId());
            org.setParentId(old.getParentId());
            org.setParentIds(org.getParentIds());
            this.orgService.updateByIdSelective(org);
        }
        AjaxJson ajaxJson = new AjaxJson(true, "操作成功");
        ajaxJson.setData(org);
        OrgUtils.getInstance().refresh();//刷新机构缓存
        return ajaxJson;
    }

    protected boolean isChildOrg(String parentId) {
        Org userOrg = UserUtils.getCurrentUserInfo().getOrg();
        List<String> allChildIds = OrgUtils.getInstance().getAllChildIds(userOrg.getId());
        for (String id : allChildIds) {
            if (id.equals(parentId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除
     */
    @RequestMapping(params = "method=delete")
    @ResponseBody
    public AjaxJson del(Org org, HttpServletRequest request) {
        if (!isChildOrg(org.getId())) {
            return new AjaxJson(false, "没有该机构操作权限");
        }
        List<Org> childOrgs = this.orgService.getChildOrgs(org.getId());
        if (childOrgs != null && childOrgs.size() > 0) {
            return new AjaxJson(false, "目录下有其他机构，不能删除！");
        }
        this.orgService.delete(org);
        OrgUtils.getInstance().refresh();//刷新机构缓存
        return new AjaxJson(true, "删除成功");
    }

    @RequestMapping(params = "method=refresh")
    @ResponseBody
    public AjaxJson refresh(HttpServletRequest request) {

        OrgUtils.getInstance().refresh();
        return new AjaxJson(true, "操作成功");
    }

}
