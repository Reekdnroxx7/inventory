package com.x404.admin.manage.sys.ctl;

import com.x404.admin.core.json.AjaxJson;
import com.x404.admin.core.query.HibernateQueryHelper;
import com.x404.admin.manage.sys.entity.Operation;
import com.x404.admin.manage.sys.model.MenuRoleForm;
import com.x404.admin.manage.sys.service.IRoleService;
import com.x404.admin.manage.sys.service.IUserRoleService;
import com.x404.admin.manage.sys.utils.MenuUtils;
import com.x404.admin.manage.sys.entity.Role;
import com.x404.admin.manage.sys.entity.RoleMenu;
import com.x404.admin.manage.sys.service.IRoleMenuService;
import com.x404.admin.manage.sys.tools.MenuTree;
import com.x404.admin.core.hibernate.query.HibernateQuery;
import com.x404.admin.core.page.ExPageList;
import com.x404.admin.manage.sys.entity.Menu;
import com.x404.admin.manage.sys.service.IMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private IUserRoleService userRoleService;

    public IRoleMenuService getRoleMenuService() {
        return roleMenuService;
    }

    public void setRoleMenuService(IRoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    public IMenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    public IRoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

//	@RequestMapping(params="method=init")
//	public String init(HttpServletRequest request) {
//		String menuHref = ResourceUtil.getRequestPath(request);
//		Menu menu = MenuUtils.getInstance().getMenuByUrl(menuHref);
//		return menu.getTarget();
//	}


    @RequestMapping(params = "method=listMenu")
    @ResponseBody
    public List<MenuRoleForm> listMenu(HttpServletRequest request, Role role) {
        MenuTree.ExMenu exMenu = MenuUtils.getInstance().getExMenu("1");
        List<RoleMenu> roleMenus = roleMenuService.getRoleMenus(role.getId());
//		Set<String> menu_ids = new HashSet<String>();
//		for (RoleMenu rm : roleMenu) {
//			menu_ids.add(rm.getMenu().getId());
//		}
        MenuRoleForm menuEx = MenuRoleForm.decorateMenu(exMenu, roleMenus);

        return menuEx.getChildren();
    }

    @RequestMapping(params = "method=listRole")
    @ResponseBody
    public ExPageList<Role> listRole(HttpServletRequest request,
                                     ExPageList<Role> page) {
        HibernateQuery cq = HibernateQueryHelper.generateFromRequest(request);
        return roleService.getPageList(cq, page);
    }

    @RequestMapping(params = "method=save")
    @ResponseBody
    public AjaxJson save(HttpServletRequest request, Role role) {
        String saveType = ServletRequestUtils.getStringParameter(request,
                "_saveType", "add");
        System.out.println(saveType);
        if ("add".equals(saveType)) {
            roleService.save(role);
        } else {
            roleService.updateByIdSelective(role);
        }
        return new AjaxJson(true, "添加成功");
    }

    @RequestMapping(params = "method=delete")
    @ResponseBody
    public AjaxJson delRole(Role role, HttpServletRequest request) {
        /** 删除角色需要删除对应的用户角色 */
        userRoleService.deleteUserRoleByRoleId(role.getId());
        roleService.delete(role);
        return new AjaxJson(true, "删除成功");
    }

    @RequestMapping(params = "method=saveRoleMenu")
    @ResponseBody
    public AjaxJson saveRoleMenu(HttpServletRequest request) {
        String roleId = request.getParameter("roleId");
        Role r = new Role();
        r.setId(roleId);
        String[] mAndops = request.getParameterValues("mAndops");
        roleMenuService.deleteRoleMenus(roleId);
        if (mAndops == null) { // 全部删除
            return new AjaxJson(true, "添加成功");
        }
        List<RoleMenu> rmList = new ArrayList<RoleMenu>();
        for (int i = 0; i < mAndops.length; i++) {
            RoleMenu rom = new RoleMenu();
            rom.setRole(r);
            String[] ss = mAndops[i].split("\\|");
            Menu m = new Menu(ss[0]);
            rom.setMenu(m);
            if (ss.length > 1 && StringUtils.isNotBlank(ss[1])) {
                Operation op = new Operation();
                op.setId(ss[1]);
                rom.setOperation(op);
            }
            rmList.add(rom);

        }
        roleMenuService.saveBatch(rmList);
        return new AjaxJson(true, "添加成功");
    }

}
