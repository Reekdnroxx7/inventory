package com.x404.module.basedao.query;

import com.x404.module.basedao.mybatis.dao.MybatisExample;
import com.x404.module.basedao.mybatis.dao.MybatisQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MybatisQueryHelper
{


    public static MybatisExample generateFromRequest(HttpServletRequest request) {
        return generateFromRequest(request, new HashMap<String, PropertyDescriptor>());
    }

    public static MybatisExample generateFromRequest(HttpServletRequest request, Class<?> entityClass) {
        Map<String, PropertyDescriptor> map = new HashMap<String, PropertyDescriptor>();
        if (entityClass != null) {
            PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(entityClass);

            for (PropertyDescriptor p : propertyDescriptors) {
                map.put(p.getName(), p);
            }
        }

        return generateFromRequest(request, map);
    }


    @SuppressWarnings("unchecked")
    protected static MybatisExample generateFromRequest(HttpServletRequest request, Map<String, PropertyDescriptor> propertyDescriptors) {

        @SuppressWarnings("rawtypes")
        Map<String, Object> requestMap = (Map) request.getParameterMap();
        int limit = -1;
        String temp = request.getParameter("limit");
        if (StringUtils.hasText(temp)) {
            limit = Integer.valueOf(temp);
        }
        int start = 0;
        temp = request.getParameter("start");
        if (StringUtils.hasText(temp)) {
            start = Integer.valueOf(temp);
        }
        MybatisExample example = new MybatisExample();
        example.setLimit(limit);
        example.setStart(start);

        String orderBy = request.getParameter(" order_by ");

        if (StringUtils.hasText(orderBy)) {
            example.setOrderByClause(orderBy);
        }

        MybatisQuery cq = new MybatisQuery();
        Set<Entry<String, Object>> entrySet = (Set<Entry<String, Object>>) requestMap.entrySet();
        for (Entry<String, Object> entry : entrySet) {
            QueryField field = getQueryField(entry, propertyDescriptors);
            if (field != null) {
                addFiled(cq, field);
            }
        }
        example.or(cq);
        return example;
    }

    private static void addFiled(MybatisQuery cq, QueryField field) {
        switch (field.getOp()) {
            case EQ:
                cq.eq(field.getName(), field.getValue());

                break;
            case GE:
                cq.ge(field.getName(), field.getValue());

                break;
            case GT:
                cq.gt(field.getName(), field.getValue());
                break;
            case IN:
                cq.in(field.getName(), field.getValue());
                break;
            case LE:
                cq.le(field.getName(), field.getValue());
                break;
            case LT:
                cq.lt(field.getName(), field.getValue());
                break;
            case LIKE:
                cq.like(field.getName(), field.getValue());
                break;

            default:
                break;
        }
    }

    private static QueryField getQueryField(Entry<String, Object> entry, Map<String, PropertyDescriptor> propertyDescriptors) {

        String[] split = entry.getKey().split("\\|");  // name-op
        Object vlaue0 = ((String[]) entry.getValue())[0];
        if (vlaue0 == null || !StringUtils.hasText(vlaue0.toString())) {
            return null;
        }
        String name = split[0];
        PropertyDescriptor p = propertyDescriptors.get(name);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        QueryFieldOP option = null;
        if (split.length == 1) {
            if (p == null) {
                return null;
            } else {
                option = QueryFieldOP.EQ;
                vlaue0 = converter.convertIfNecessary(vlaue0, p.getPropertyType());
            }
        } else {
            String op = split[1];
            option = QueryFieldOP.valueOf(op.toUpperCase());
            switch (option) {
                case IN:
                    String[] temp = (String[]) entry.getValue();
                    vlaue0 = new Object[temp.length];
                    for (int i = 0; i < temp.length; i++) {
                        ((Object[]) vlaue0)[i] = convertValue(vlaue0, p, converter);
                    }
                    break;
                case LIKE:

                    vlaue0 = "%" + vlaue0 + "%";
                    break;

                default:
                    vlaue0 = convertValue(vlaue0, p, converter);
                    break;
            }
        }
        QueryField qf = new QueryField(name, option, vlaue0);

        return qf;
    }

    private static Object convertValue(Object value, PropertyDescriptor p,
                                       SimpleTypeConverter converter) {
        if (p != null) {
            return converter.convertIfNecessary(value, p.getPropertyType());
        }
        return value;
    }


}
