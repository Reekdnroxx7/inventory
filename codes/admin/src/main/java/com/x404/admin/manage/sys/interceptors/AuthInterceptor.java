package com.x404.admin.manage.sys.interceptors;

import com.x404.admin.core.util.AjaxUtil;
import com.x404.admin.core.util.ResourceUtil;
import com.x404.admin.manage.sys.entity.Operation;
import com.x404.admin.manage.sys.utils.MenuUtils;
import com.x404.admin.manage.sys.utils.UserUtils;
import com.x404.admin.manage.sys.entity.Menu;
import com.x404.admin.manage.sys.tools.UserInfo;
import com.x404.admin.manage.sys.utils.OperationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 权限拦截器
 *
 * @author 张代浩
 */
public class AuthInterceptor implements HandlerInterceptor, InitializingBean {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);
    private List<String> excludeUrls;
    protected UrlPathHelper urlPathHelper = new UrlPathHelper();

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }


    /**
     * 在controller后拦截
     */
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object object, Exception exception)
            throws Exception {
    }

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object object,
                           ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在controller前拦截
     */
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {
        boolean result = validate(request, response);
        return result;
    }

    private boolean validate(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException {
        String requestPath = ResourceUtil.getRequestPath(request);
//		requestPath = requestPath.substring(beginIndex)\
        if (true) {
            return true;
        }
        if ("/_login".equals(requestPath) ||
                "/checkcode".equals(requestPath) ||
                "/twodimenCode".equals(requestPath)) {
            return true;
        }

        String uri = urlPathHelper.getLookupPathForRequest(request);

        UserInfo userInfo = UserUtils.getCurrentUserInfo();
        if (userInfo == null) {
            toTimeout(request, response);
            return false;
        }
        AntPathMatcher matcher = new AntPathMatcher();
        for (String url : excludeUrls) {
            if (matcher.match(url, uri)) {
                return true;
            }
        }
        Menu menu = MenuUtils.getInstance().getCurrentMenu();
        if (menu == null) {
            toResoucesNotFound(request, response);
            return false;
        }
        if (!menu.getTranscodes().contains(requestPath)) {  // 可以访问的类
            toNoAuth(request, response);
            return false;
        }
        if (userInfo.getUser() != null) {
            if (!userInfo.hasAuth(menu.getId())) {
                toNoAuth(request, response);
                return false;
            }
            String oprationCode = request.getParameter("method");
            Operation p = new Operation(requestPath, oprationCode);
            if (!OperationUtils.getInstance().contains(p)) { //访问需要权限的方法
                return true;
            }
            if (userInfo.hasAuth(p)) {              // 是否已经有权限访问该方法
                return true;
            } else {
                toNoAuth(request, response);
                return false;
            }

        } else {
            toTimeout(request, response);
            return false;
        }
    }

    private void toResoucesNotFound(HttpServletRequest request,
                                    HttpServletResponse response) throws ServletException, IOException {
        LOGGER.error("资源不存在");
        if (AjaxUtil.isAjax(request)) {
            AjaxUtil.error(response, "资源不存在");
        } else {
            response.sendRedirect("/WEB-INF/jsp/error/404.jsp");
        }
    }

    private void toTimeout(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        LOGGER.error("会话超时");
        if (AjaxUtil.isAjax(request)) {
            AjaxUtil.error(response, "回话超时，请重新登陆！");
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/error/timeout.jsp").forward(request, response);
        }
    }

    private void toNoAuth(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        LOGGER.error("权限不足");
        if (AjaxUtil.isAjax(request)) {
            AjaxUtil.error(response, "权限不足，请联系统管理员");
        } else {
            response.sendRedirect("/WEB-INF/jsp/error/403.jsp");
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (excludeUrls != null) {
            for (int i = 0; i < excludeUrls.size(); i++) {
                String path = excludeUrls.get(i);

                excludeUrls.set(i, path);

            }
        }
    }

    public static void main(String[] args) {
        AntPathMatcher matcher = new AntPathMatcher();
        System.out.println("sdfdasfsasdfasfa");
        boolean match = matcher.match("/admin/*.sdict", "/admin/sys.sdict");
        System.out.println(match);
    }

}
