package com.x404.module.basedao.query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class MongoQueryHelper {


    public static Query generateFromRequest(HttpServletRequest request) {
        return generateFromRequest(request, new HashMap<String, PropertyDescriptor>());
    }

    public static Query generateFromRequest(HttpServletRequest request, Class<?> entityClass) {
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
    protected static Query generateFromRequest(HttpServletRequest request, Map<String, PropertyDescriptor> propertyDescriptors) {

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
        Query query = new Query();
        query.limit(limit);
        query.skip(start);

//        String orderBy = request.getParameter(" order_by ");
//        if (StringUtils.hasText(orderBy)) {
//            String[] split = orderBy.split(",");
//            Order.
//            Order orderBySource = new OrderBySource(orderBy);
//            query.with(orderBySource.toSort());
//        }

        Set<Entry<String, Object>> entrySet = requestMap.entrySet();
        Map<String, Criteria> criteriaMap = new HashMap<>();
        for (Entry<String, Object> entry : entrySet) {
            QueryField field = getQueryField(entry, propertyDescriptors);
            if (field != null) {
                createCriteria(criteriaMap, field);
            }
        }
        criteriaMap.values().forEach(query::addCriteria);
        return query;
    }

    private static void createCriteria(Map<String, Criteria> criteriaMap, QueryField field) {
        Criteria criteria = criteriaMap.get(field.getName());
        switch (field.getOp()) {
            case EQ:
                if (criteria != null) {
                    criteria.is(field.getValue());
                } else {
                    criteriaMap.put(field.getName(), Criteria.where(field.getName()).is(field.getValue()));
                }
                break;
            case GE:
                if (criteria != null) {
                    criteria.gte(field.getValue());
                } else {
                    criteriaMap.put(field.getName(), Criteria.where(field.getName()).gte(field.getValue()));
                }
                break;
            case GT:
                if (criteria != null) {
                    criteria.gt(field.getValue());
                } else {
                    criteriaMap.put(field.getName(), Criteria.where(field.getName()).gt(field.getValue()));
                }
                break;
            case IN:
                if (criteria != null) {
                    criteria.in(field.getValue());
                } else {
                    criteriaMap.put(field.getName(), Criteria.where(field.getName()).in(field.getValue()));
                }
                break;
            case LE:
                if (criteria != null) {
                    criteria.lte(field.getValue());
                } else {
                    criteriaMap.put(field.getName(), Criteria.where(field.getName()).lte(field.getValue()));
                }
                break;
            case LT:
                if (criteria != null) {
                    criteria.lt(field.getValue());
                } else {
                    criteriaMap.put(field.getName(), Criteria.where(field.getName()).lt(field.getValue()));
                }
                break;

            case LIKE:
                if (criteria != null) {
                    criteria.regex((String) field.getValue());
                } else {
                    criteriaMap.put(field.getName(), Criteria.where(field.getName()).regex((String) field.getValue()));
                }
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
                vlaue0 = convertValue(vlaue0, p, converter);
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
            if (Date.class.isAssignableFrom(p.getPropertyType())) {
                String dateStr = String.valueOf(value);
                String replace = dateStr.replaceAll("[:\\-\\s]", "");
                try {
                    if (replace.length() == 8) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                        return format.parse(replace);
                    }
                    if (replace.length() == 14) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

                        return format.parse(replace);

                    }
                    throw new TypeMismatchException(value, p.getPropertyType());
                } catch (ParseException e) {
                    throw new TypeMismatchException(value, p.getPropertyType(), e);
                }
            }
            return converter.convertIfNecessary(value, p.getPropertyType());
        }
        return value;
    }


}
