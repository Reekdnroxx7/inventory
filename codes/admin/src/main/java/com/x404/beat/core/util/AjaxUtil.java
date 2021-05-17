package com.x404.beat.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AjaxUtil {

    private final static Logger LOGGER = LoggerFactory
            .getLogger(AjaxUtil.class);

    public static boolean isAjax(HttpServletRequest request) {
        if ("true".equalsIgnoreCase(request.getParameter("ajax"))) {
            return true;
        }
        if (request.getContentType() != null && request.getContentType().contains("json")) {
            return true;
        }
        if (request.getRequestURI().endsWith("ajax")) {
            return true;
        }
        if (request.getRequestURI().endsWith("json")) {
            return true;
        }
        return false;
    }

    public static void error(HttpServletResponse response, String message) {
        ajax(response, "{success:false,message:\"" + message + "\"}");
    }

    public static void success(HttpServletResponse response) {
        ajax(response, "{success:true}");
    }

    public static void ajax(HttpServletResponse response, String message) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            LOGGER.debug("ajax 返回：" + message);
            out = response.getWriter();
            out.write(message);
            out.flush();
        } catch (IOException e) {
            LOGGER.error("ajax 返回失败", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        if (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request
                .getHeader("X-Requested-With").toString())) {
            return true;
        }
        return false;
    }
}
