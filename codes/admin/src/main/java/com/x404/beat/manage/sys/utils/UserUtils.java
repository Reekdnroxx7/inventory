/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.beat.manage.sys.utils;

import com.x404.beat.core.util.ContextHolderUtils;
import com.x404.beat.manage.sys.entity.User;
import com.x404.beat.manage.sys.tools.UserInfo;
import com.x404.beat.manage.sys.tools.UserManager;

import javax.servlet.http.HttpSession;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class UserUtils {

    public static UserInfo getCurrentUserInfo() {
        HttpSession session = ContextHolderUtils.getSession();
        UserInfo userInfo = UserManager.getInstance().getUserInfo(session.getId());
        return userInfo;
    }

    public static User getCurrentUser() {
        HttpSession session = ContextHolderUtils.getSession();
        UserInfo userInfo = UserManager.getInstance().getUserInfo(session.getId());
        if (userInfo != null) {
            return userInfo.getUser();
        }
        return null;
    }


}
