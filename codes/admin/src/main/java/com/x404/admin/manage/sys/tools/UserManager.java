package com.x404.admin.manage.sys.tools;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 对在线用户的管理
 *
 * @author JueYue
 * @version 1.0
 * @date 2013-9-28
 */
public class UserManager {

    private static UserManager instance = new UserManager();


    public static UserManager getInstance() {
        return instance;
    }

    private Map<String, UserInfo> cache;

    private UserManager() {
        cache = new HashMap<String, UserInfo>();

        //CacheUtils.getCacheManager().addCacheIfAbsent(UserManager.class.getName());
    }


    /**
     * @param sessionId
     * @param client
     */
    public void addUser(String sessionId, UserInfo userInfo) {
//		Element element = new Element(sessionId, userInfo);
//		element.setTimeToIdle(1800);
        cache.put(sessionId, userInfo);
    }

    /**
     * sessionId
     */
    public void removeUser(String sessionId) {
        cache.remove(sessionId);
    }

    /**
     * @param sessionId
     * @return
     */
    public UserInfo getUserInfo(String sessionId) {
        return (UserInfo) cache.get(sessionId);
//		 Element element = cache.get(sessionId);
//		 if(element != null){
//			 return (UserInfo) element.getObjectValue();
//		 }else {
//			 return null;
//		 }
    }

    /**
     * @return
     */
    public Collection<UserInfo> getAllUser() {
        return (Collection<UserInfo>) cache.values();
    }

}
