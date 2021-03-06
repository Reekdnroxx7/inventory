/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.admin.core.controller;

import com.x404.admin.core.databind.DateConvertEditor;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * 控制器支持类
 * @author ThinkGem
 * @version 2013-3-23
 */
public abstract class BaseController {

    /**
     * 日志对象
     */
    protected Log logger = LogFactory.getLog(getClass());

//	/**
//	 * 验证Bean实例对象
//	 */
//	@Autowired
//	protected Validator validator;
//
//	/**
//	 * 服务端参数有效性验证
//	 * @param object 验证的实体对象
//	 * @param groups 验证组
//	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
//	 */
//	protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
//		try{
//			BeanValidators.validateWithException(validator, object, groups);
//		}catch(ConstraintViolationException ex){
//			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
//			list.add(0, "数据验证失败：");
//			addMessage(model, list.toArray(new String[]{}));
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * 服务端参数有效性验证
//	 * @param object 验证的实体对象
//	 * @param groups 验证组
//	 * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
//	 */
//	protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
//		try{
//			BeanValidators.validateWithException(validator, object, groups);
//		}catch(ConstraintViolationException ex){
//			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
//			list.add(0, "数据验证失败：");
//			addMessage(redirectAttributes, list.toArray(new String[]{}));
//			return false;
//		}
//		return true;
//	}

    /**
     * 添加Model消息
     * @param messages 消息
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute("message", sb.toString());
    }

    /**
     * 添加Flash消息
     * @param messages 消息
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        redirectAttributes.addFlashAttribute("message", sb.toString());
    }

    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new DateConvertEditor());
    }

}
