package com.x404.admin.manage.sys.ctl;

import com.x404.admin.core.controller.BaseController;
import com.x404.admin.core.json.AjaxJson;
import com.x404.admin.core.util.IpUtil;
import com.x404.admin.manage.sys.entity.User;
import com.x404.admin.manage.sys.service.IMenuService;
import com.x404.admin.manage.sys.tools.UserManager;
import com.x404.admin.manage.sys.tools.UserInfo;
import com.x404.admin.core.util.ContextHolderUtils;
import com.x404.admin.manage.sys.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 登陆初始化控制器
 */
@Controller
public class LoginController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public IMenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping("index.do")
    public String init() {
        return "login/login";
    }

    /**
     * 检查用户名称
     *
     * @param user
     * @param req
     * @return
     */

    @RequestMapping(value = "login.sdict", params = "method=checkuser")
    @ResponseBody
    public AjaxJson checkuser(User user, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        HttpSession session = req.getSession();
        Integer inputtimes;
        Integer userinputtimes = (Integer) req.getSession().getAttribute(
                "loginTimes");
        if (userinputtimes == null) {
            inputtimes = 0;
        } else {
            inputtimes = userinputtimes;
        }
//		if (inputtimes > 2) {
//
//			String checkcodestr = (String) session.getAttribute("checkcode");
//			String inputcheckcode = req.getParameter("checkcode");
//			if (!checkcodestr.equalsIgnoreCase(inputcheckcode)) {
//				inputtimes++;
//				session.setAttribute("loginTimes", inputtimes);
//				j.setMessage("验证码错误!");
//				j.setSuccess(false);
//				j.setObj(inputtimes);
//				return j;
//			}
//		}
        User u = userService.checkUserExits(user);
        if (u != null) {
            session.removeAttribute("loginTimes");
            UserInfo client = initUserInfo(req, u);
            UserManager.getInstance().addUser(session.getId(), client);
        } else {
            inputtimes++;
            session.setAttribute("loginTimes", inputtimes);
            j.setMessage("用户名或密码错误!");
            j.setSuccess(false);
            j.setData(inputtimes);
        }
        return j;
    }

    private UserInfo initUserInfo(HttpServletRequest req, User u) {
        UserInfo userInfo = new UserInfo();
        userInfo.setIp(IpUtil.getIpAddr(req));
        userInfo.setLogindatetime(new Date());
        userInfo.setUser(u);
        return userInfo;
    }


    /**
     * 退出系统
     *
     * @return
     */
    @RequestMapping(value = "login.do", params = "method=logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = ContextHolderUtils.getSession();
        UserManager.getInstance().removeUser(session.getId());
        ModelAndView modelAndView = new ModelAndView(new RedirectView("_login.do?method=_login"));
        return modelAndView;
    }


}
