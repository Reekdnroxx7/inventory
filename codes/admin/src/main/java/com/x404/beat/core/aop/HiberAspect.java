package com.x404.beat.core.aop;

import com.x404.beat.manage.sys.entity.User;
import com.x404.beat.manage.sys.utils.UserUtils;
import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Date;

/**
 * Hiberate拦截器：实现创建人，创建时间，创建人名称自动注入; 修改人,修改时间,修改人名自动注入;
 *
 * @author 张代浩
 */
@Component("hiberAspect")
public class HiberAspect extends EmptyInterceptor {
    private static final Logger logger = Logger.getLogger(HiberAspect.class);
    private static final long serialVersionUID = 1L;

    public boolean onSave(Object entity, Serializable id, Object[] state,
                          String[] propertyNames, Type[] types) {
        User currentUser = null;
        try {
            currentUser = UserUtils.getCurrentUser();
        } catch (RuntimeException e) {
            logger.warn("当前session为空,无法获取用户");
        }
//		if (currentUser == null) {
//			return true;
//		}
        try {
            // 添加数据
            for (int index = 0; index < propertyNames.length; index++) {
                 /*找到名为"创建时间"的属性*/
                if ("createDate".equals(propertyNames[index]) && state[index] == null) {
				 /*使用拦截器将对象的"创建时间"属性赋上值*/
                    state[index] = new Date(System.currentTimeMillis());
                    continue;
                }
				 /*找到名为"创建人"的属性*/
                else if ("createBy".equals(propertyNames[index]) && state[index] == null) {
				 /*使用拦截器将对象的"创建人"属性赋上值*/
                    state[index] = currentUser != null ? currentUser.getId() : null;
                    continue;
                }
            }
        } catch (RuntimeException e) {
            logger.error(e, e);
        }
        return true;
    }

    public boolean onFlushDirty(Object entity, Serializable id,
                                Object[] currentState, Object[] previousState,
                                String[] propertyNames, Type[] types) {
        User currentUser = null;
        try {
            currentUser = UserUtils.getCurrentUser();
        } catch (RuntimeException e1) {
            logger.warn("当前session为空,无法获取用户");
        }
        if (currentUser == null) {
            return true;
        }
        // 添加数据
        for (int index = 0; index < propertyNames.length; index++) {
			/* 找到名为"修改时间"的属性 */
            if ("updateDate".equals(propertyNames[index]) && currentState[index] == null) {
				/* 使用拦截器将对象的"修改时间"属性赋上值 */
                currentState[index] = new Date(System.currentTimeMillis());
                continue;
            }
			/* 找到名为"修改人"的属性 */
            else if ("updateBy".equals(propertyNames[index]) && currentState[index] == null) {
				/* 使用拦截器将对象的"修改人"属性赋上值 */
                currentState[index] = currentUser != null ? currentUser.getId() : null;
                continue;
            }
        }
        return true;
    }

    @Override
    public int[] findDirty(Object entity, Serializable id,
                           Object[] currentState, Object[] previousState,
                           String[] propertyNames, Type[] types) {
        if (previousState != null || id == null) {
            return super.findDirty(entity, id, currentState, previousState, propertyNames, types);
        }
//		DynamicUpdate annotation = entity.getClass().getAnnotation(DynamicUpdate.class);
//		if(!annotation.value()){
//			return super.findDirty(entity, id, currentState, previousState, propertyNames,
//					types);
//		}
        int[] temp = new int[currentState.length];
        int i = 0;
        for (int j = 0; j < currentState.length; j++) {
            if (currentState[j] != null || "updateBy".equals(propertyNames[j]) || "updateDate".equals(propertyNames[j])) {
                temp[i++] = j;
            }
        }
        int[] result = new int[i];
        System.arraycopy(temp, 0, result, 0, i);
        return result;

    }


}
