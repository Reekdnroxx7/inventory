package com.x404.admin.manage.sys.ctl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x404.admin.core.controller.BaseController;
import com.x404.admin.core.json.AjaxJson;
import com.x404.admin.core.query.HibernateQueryHelper;
import com.x404.admin.core.util.PasswordUtil;
import com.x404.admin.manage.sys.entity.User;
import com.x404.admin.manage.sys.model.RoleEx;
import com.x404.admin.manage.sys.service.IRoleService;
import com.x404.admin.manage.sys.service.IUserRoleService;
import com.x404.admin.manage.sys.utils.OrgUtils;
import com.x404.admin.manage.sys.utils.UserUtils;
import com.x404.admin.manage.sys.entity.Role;
import com.x404.admin.manage.sys.entity.UserRole;
import com.x404.admin.core.hibernate.query.HibernateQuery;
import com.x404.admin.core.page.ExPageList;
import com.x404.admin.manage.sys.entity.Org;
import com.x404.admin.manage.sys.service.IUserService;
import com.x404.admin.manage.sys.tools.UserInfo;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 字典处理类
 *
 * @author 谢超
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    private IUserRoleService userRoleService;

    public IUserRoleService getUserRoleService() {
        return userRoleService;
    }

    public void setUserRoleService(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    private IRoleService roleService;

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


    @RequestMapping(params = "method=listUser")
    @ResponseBody
    public ExPageList<User> listUser(HttpServletRequest request,
                                     ExPageList<User> page) {
        HibernateQuery cq = HibernateQueryHelper.generateFromRequest(request);
        String orgId = OrgUtils.getInstance().getRequestOrg().getId();
        List<String> orgIds = OrgUtils.getInstance().getAllChildIds(orgId);
        orgIds.add(orgId);
        cq.and(Restrictions.in("orgId", orgIds.toArray())); // 选出下级的人员
        return userService.getPageList(cq, page);
    }

    @RequestMapping(params = "method=saveUser")
    @ResponseBody
    public AjaxJson saveUser(HttpServletRequest request, User u) {
        String saveType = ServletRequestUtils.getStringParameter(request,
                "_saveType", "add");
        ensureAuth(u);
        if ("add".equals(saveType)) {
            if (u.getPassword() == null) {
                String passwd;
                try {
                    passwd = PasswordUtil.encrypt(u.getLoginName(), "111111",
                            PasswordUtil.getStaticSalt());
                    u.setPassword(passwd);
                } catch (Exception e) {
                    logger.error("生成密码失败", e);
                    return new AjaxJson(false, "操作失败");
                }
            }
            this.userService.save(u);
        } else {
            this.userService.updateByIdSelective(u);
        }
        AjaxJson ajaxJson = new AjaxJson(true, "操作成功");
        ajaxJson.setData(u);
        return ajaxJson;
    }

    @RequestMapping(params = "method=deleteUser")
    @ResponseBody
    public AjaxJson deleteUser(HttpServletRequest request, User u) {
        this.userService.delete(u);
        return new AjaxJson(true, "操作成功");
    }

    @RequestMapping(params = "method=resetPassword")
    @ResponseBody
    public AjaxJson resetPassword(HttpServletRequest request, User u) {
//		this.userService.delete(u);
        String passwd = PasswordUtil.encrypt(u.getLoginName(), "111111",
                PasswordUtil.getStaticSalt());
        u.setPassword(passwd);
        userService.updateByIdSelective(u);
        return new AjaxJson(true, "操作成功");
    }

    @RequestMapping(params = "method=listUserRole")
    @ResponseBody
    public ExPageList<RoleEx> listUserRole(HttpServletRequest request, User u) {

        // 第一步 修改用户的机构是否在 当前用户机构下。
        ensureAuth(u);
        // 当前用户可分配的角色.
        List<Role> assginRoles = getAssginRoles();
        // 用户的拥有角色
        List<UserRole> userRoles = userRoleService.getAuthRoles(u);

        List<RoleEx> roleExs = new ArrayList<RoleEx>(assginRoles.size());
        // userRoles.toArray(array);
        for (Role src : assginRoles) {
            UserRole ur = new UserRole();
            ur.setRole(src);
            RoleEx role = new RoleEx(src);
            int index = Collections.binarySearch(userRoles, ur,
                    new UserRoleComparator());
            if (index >= 0) { // 如果已经分配
                role.setUserRoleId(userRoles.get(index).getId());
                role.setAuth(true);
                role.setRoleType(userRoles.get(index).getRoleType());
            } else {
                role.setRoleType("1");
            }
            roleExs.add(role);
        }
        ExPageList<RoleEx> pageList = new ExPageList<RoleEx>();
        pageList.setResultList(roleExs);
        pageList.setTotalCount(roleExs.size());
        return pageList;
    }

    private List<Role> getAssginRoles() {
        UserInfo userInfo = UserUtils.getCurrentUserInfo();
        List<Role> assginRoles;
        if ("admin".equals(userInfo.getUser().getLoginName())) {
            assginRoles = roleService.findAll();
        } else {
            assginRoles = userRoleService.getAssginRoles(userInfo.getUser());
        }
        return assginRoles;
    }

    @RequestMapping(params = "method=saveUserRoles")
    @ResponseBody
    public AjaxJson saveUserRoles(HttpServletRequest request, User u) {
        // 第一步 修改用户的机构是否在 当前用户机构下。
        ensureAuth(u);
        // 当前用户可分配的角色.
        String[] roles = request.getParameterValues("roles");
        ObjectMapper om = new ObjectMapper();

        List<UserRole> saveRoles = new ArrayList<UserRole>();
        List<UserRole> updateRoles = new ArrayList<UserRole>();
        List<UserRole> delRoles = new ArrayList<UserRole>();
        List<Role> userRoles = getAssginRoles();
        for (String str : roles) {
            try {
                RoleEx ex = om.readValue(str, RoleEx.class);
                if (!ex.isAuth() && ex.getRoleId() == null) {
                    continue;
                }
                UserRole createUserRole = createUserRole(ex, u, userRoles);
                if (!ex.isAuth()) { // 如果不授权 删除
                    delRoles.add(createUserRole);
                } else {
                    if (ex.getUserRoleId() != null) {
                        updateRoles.add(createUserRole);
                    } else {
                        saveRoles.add(createUserRole);
                    }
                }
            } catch (JsonParseException e) {
                logger.error("保存角色失败", e);
                return new AjaxJson(false, "失败");
            } catch (JsonMappingException e) {
                logger.error("保存角色失败", e);
                return new AjaxJson(false, "失败");
            } catch (IOException e) {
                logger.error("保存角色失败", e);
                return new AjaxJson(false, "失败");
            }
        }
        this.userRoleService.addSaveDelBatch(delRoles, saveRoles, updateRoles);
        return new AjaxJson(true, "操作成功");
    }

    private UserRole createUserRole(RoleEx ex, User u, List<Role> roles) {
        UserRole ur = new UserRole();
        ur.setId(ex.getUserRoleId());
        Role role = new Role();
        role.setId(ex.getRoleId());
        ur.setRole(role);
        ur.setUser(u);
        ur.setRoleType(ex.getRoleType());
        ensureHasRole(role, roles);
        return ur;
    }

    private void ensureAuth(User u) {
        UserInfo userInfo = UserUtils.getCurrentUserInfo();
        Org small = OrgUtils.getInstance().getOrg(u.getOrgId());
        Org big = userInfo.getOrg();
        if (OrgUtils.getInstance().isLower(big, small) < 0) {
            throw new RuntimeException("权限不够");
        }
    }

    private void ensureHasRole(Role role, List<Role> roles) {
        // 用户的拥有角色
        int binarySearch = Collections.binarySearch(roles, role,
                new RoleComparator());
        if (binarySearch < 0) {
            throw new RuntimeException("权限不够");
        }
    }

    private static final class UserRoleComparator implements
            Comparator<UserRole>, Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(UserRole o1, UserRole o2) {
            return o1.getRole().getId().compareTo(o2.getRole().getId());
        }
    }

    private static final class RoleComparator implements Comparator<Role>, Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Role o1, Role o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }

//	/**
//	 * 修改密码
//	 *
//	 * @return
//	 */
//	@RequestMapping(params = "method=savenewpwd")
//	@ResponseBody
//	public void savenewpwd(HttpServletRequest request) {
//		User user = UserUtils.getCurrentUser();
//		String newpassword = request.getParameter("newpassword");
//		user.setPassword(PasswordUtil.encrypt(user.getLoginName(), newpassword,
//				PasswordUtil.getStaticSalt()));
//		userService.update(user);
//	}
//
//	/**
//	 * 修改风格
//	 *
//	 * @return
//	 */
//	@RequestMapping(params = "method=saveStyle")
//	@ResponseBody
//	public void saveStyle(HttpServletRequest request,HttpServletResponse response) {
//		User user = UserUtils.getCurrentUser();
//		if (user != null) {
//			String indexType = request.getParameter("type");
//			String indexStyle = request.getParameter("style");
//			if(StringUtils.isNotEmpty(indexType)){
//				Cookie cookie=new Cookie("indexType", indexType);
//				//设置cookie有效期为一个月
//				cookie.setMaxAge(3600*24*30);
//				response.addCookie(cookie);
//			}
//			if(StringUtils.isNotEmpty(indexStyle)){
//				Cookie cookie = new Cookie("indexStyle", indexStyle);
//				//设置cookie有效期为一个月
//				cookie.setMaxAge(3600*24*30);
//				response.addCookie(cookie);
//			}
//		}
//	}

}
