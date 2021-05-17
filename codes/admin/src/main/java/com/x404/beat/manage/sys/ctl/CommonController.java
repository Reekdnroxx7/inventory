package com.x404.beat.manage.sys.ctl;

import com.x404.beat.core.controller.BaseController;
import com.x404.beat.core.util.PasswordUtil;
import com.x404.beat.manage.sys.utils.UserUtils;
import com.x404.beat.core.token.Token;
import com.x404.beat.manage.sys.entity.Menu;
import com.x404.beat.manage.sys.entity.User;
import com.x404.beat.manage.sys.service.IUserService;
import com.x404.beat.manage.sys.tools.UserInfo;
import com.x404.beat.manage.sys.utils.MenuUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 *         由于某些不需要权限验证的连接，分散在各个Controller不方便维护
 *         将不需要权限验证的，或自己做权限验证的请求统一放在CommonController中
 *         例如：setPassword; 因为跳过权限验证，所以必须仔细检查
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

    @Autowired
    private IUserService userService;


    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }


    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(params = "method=savenewpwd")
    @ResponseBody
    public void savenewpwd(HttpServletRequest request) {
        User user = UserUtils.getCurrentUser();
        String newpassword = request.getParameter("newpassword");
        user.setPassword(PasswordUtil.encrypt(user.getLoginName(), newpassword,
                PasswordUtil.getStaticSalt()));
        userService.updateByIdSelective(user);
    }

    /**
     * 修改风格
     *
     * @return
     */
    @RequestMapping(params = "method=saveStyle")
    @ResponseBody
    public void saveStyle(HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtils.getCurrentUser();
        if (user != null) {
            String indexType = request.getParameter("type");
            String indexStyle = request.getParameter("style");
            if (StringUtils.isNotEmpty(indexType)) {
                Cookie cookie = new Cookie("indexType", indexType);
                //设置cookie有效期为一个月
                cookie.setMaxAge(3600 * 24 * 30);
                response.addCookie(cookie);
            }
            if (StringUtils.isNotEmpty(indexStyle)) {
                Cookie cookie = new Cookie("indexStyle", indexStyle);
                //设置cookie有效期为一个月
                cookie.setMaxAge(3600 * 24 * 30);
                response.addCookie(cookie);
            }
        }
    }


    /**
     * 菜单树
     */
    @RequestMapping(params = "method=menuTree")
    @ResponseBody
    public List<Menu> menuTree(HttpServletRequest request) {
        String id = ServletRequestUtils.getStringParameter(request, "menuId",
                "0");
        UserInfo userInfo = UserUtils.getCurrentUserInfo();
        List<Menu> children = userInfo.getChildrenMenus(id);
        return children;
    }

    /**
     * 菜单树
     *
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping(params = "method=toMenu")
    //每个页面增加token,防止重复提交
    @Token(save = true)
    public String toMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String menuId = ServletRequestUtils.getStringParameter(request, "menuId");
        Menu menu = MenuUtils.getInstance().getMenuById(menuId);
        UserInfo userInfo = UserUtils.getCurrentUserInfo();
        if (userInfo.hasAuth(menuId)) {
            return menu.getTarget();
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/error/403.jsp").forward(request, response);
            return null;
        }
    }
}
