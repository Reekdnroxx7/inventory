package com.x404.module.basedao.query;

import com.x404.module.basedao.hibernate.HibernateQuery;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.SimpleTypeConverter;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HibernateQueryHelper {

    @SuppressWarnings("unchecked")
    public static HibernateQuery generateFromRequest(HttpServletRequest request, Class<?> entityClass) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(entityClass);
        Map<String, PropertyDescriptor> map = new HashMap<String, PropertyDescriptor>();
        for (PropertyDescriptor p : propertyDescriptors) {
            map.put(p.getName(), p);
        }
        return generateFromRequest(parameterMap, map);
    }

    @SuppressWarnings("unchecked")
    public static HibernateQuery generateFromRequest(HttpServletRequest request) {
        return generateFromRequest(request.getParameterMap(), new HashMap<String, PropertyDescriptor>());
    }

    protected static HibernateQuery generateFromRequest(Map<String, String[]> requestMap, Map<String, PropertyDescriptor> propertyDescriptors) {

        HibernateQuery cq = new HibernateQuery();
        Set<Entry<String, String[]>> entrySet = requestMap.entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            QueryField field = getQueryField(entry, propertyDescriptors);
            if (field != null) {
                addFiled(cq, field);
            }
        }
        return cq;
    }

    private static void addFiled(HibernateQuery cq, QueryField field) {
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
                cq.in(field.getName(), (Object[]) field.getValue());
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

    private static QueryField getQueryField(Entry<String, String[]> entry, Map<String, PropertyDescriptor> propertyDescriptors) {

        String[] split = entry.getKey().split("\\|");  // name|op
        Object vlaue0 = ((String[]) entry.getValue())[0];
        if (vlaue0 == null || StringUtils.isBlank(vlaue0.toString())) {
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
