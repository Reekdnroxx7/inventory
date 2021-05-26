package com.x404.admin.core.exception;

import com.x404.admin.core.util.AjaxUtil;
import com.x404.admin.core.util.ExceptionUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * spring mvc异常捕获类
 */
public class MyExceptionHandler implements HandlerExceptionResolver {

    private static final Logger logger = Logger
            .getLogger(MyExceptionHandler.class);

    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
        String exceptionMessage = ExceptionUtil.getExceptionMessage(ex);
        logger.error("发生异常：", ex);
        if (AjaxUtil.isAjax(request)) {
            AjaxUtil.error(response, ex.getMessage());
            return new ModelAndView();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("exceptionMessage", exceptionMessage);
        model.put("ex", ex);
        return new ModelAndView("error/500", model);
    }
}
