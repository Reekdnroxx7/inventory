package com.x404.beat.manage.sys.ctl;


import com.x404.beat.manage.sys.entity.Operation;
import com.x404.beat.manage.sys.entity.RoleMenu;
import com.x404.beat.manage.sys.entity.User;
import com.x404.beat.manage.sys.tools.UserInfo;
import com.x404.beat.manage.sys.utils.UserUtils;
import com.x404.beat.manage.sys.entity.Menu;
import com.x404.beat.manage.sys.service.IMenuService;
import com.x404.beat.manage.sys.service.IUserService;
import com.x404.beat.manage.sys.utils.OperationUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "main.do")
    public String login(HttpServletRequest request) {
        User user = UserUtils.getCurrentUser();
        // String roles = "";

        if (user != null) {
            logger.info("用户{}登录成功", user.getLoginName());

            UserInfo userInfo = UserUtils.getCurrentUserInfo();
            userInfo.clear();
            if ("admin".equals(userInfo.getUser().getLoginName())) {
                List<Menu> allMenus = (List<Menu>) this.menuService.findAll();
                for (Menu m : allMenus) {
                    if ("1".equals(m.getIsShow())) {
                        userInfo.add(m);
                    }
                }
                Set<Operation> operations = OperationUtils.getInstance().getOperations();
                for (Operation operation : operations) {
                    userInfo.add(operation);
                }
            } else {
                List<RoleMenu> accessMenus = userService
                        .getAccessMenus(userInfo.getUser());
                for (RoleMenu rm : accessMenus) {
                    if ("1".equals(rm.getMenu().getIsShow())) { // 不显示
                        userInfo.add(rm.getOperation());
                        userInfo.add(rm.getMenu());
                    }
                }
            }
            // 默认风格
            String indexStyle = "desktop";
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
                    continue;
                }
                if (cookie.getName().equalsIgnoreCase("indexStyle")) {
                    indexStyle = cookie.getValue();
                }
            }
            if ("classical".equalsIgnoreCase(indexStyle)) {
                return "main/extjs/main";
            }
//			return "main/desktop/desktop";
            return "main/extjs/main";
        } else {
            return "login/login";
        }

    }

}
