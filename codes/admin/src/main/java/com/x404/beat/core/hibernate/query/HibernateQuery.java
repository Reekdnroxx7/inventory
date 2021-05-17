package com.x404.beat.core.hibernate.query;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：HibernateQuery类是对hibernate QBC查询方法的封装，需要的参数是当前操作的实体类
 * 张代浩
 *
 * @version 1.0
 * @date： 日期：2012-12-7 时间：上午10:22:15
 */
public class HibernateQuery implements Criterion {
    /**
     *
     */
    private static final long serialVersionUID = 7165072976628943550L;
    protected Criterion criterion;


    private List<Order> orders = new ArrayList<Order>();


    public HibernateQuery addOrder(Order order) {
        this.orders.add(order);
        return this;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public HibernateQuery eq(String keyname, Object keyvalue) {
        if (keyvalue != null && keyvalue != "") {
            and(Restrictions.eq(keyname, keyvalue));
        }
        return this;
    }

    /**
     * 设置notEq(不等)查询条件
     *
     * @param keyname
     * @param keyvalue1
     * @param keyvalue2
     */
    public HibernateQuery notEq(String keyname, Object keyvalue) {
        if (keyvalue != null && keyvalue != "") {
            and(Restrictions.ne(keyname, keyvalue));
        }
        return this;
    }

    /**
     * 设置like(模糊)查询条件
     *
     * @param keyname
     * @param keyvalue1
     * @param keyvalue2
     */
    public HibernateQuery like(String keyname, Object keyvalue) {
        if (keyvalue != null && keyvalue != "") {
            //criterionList.addPara(Restrictions.like(keyname, "%" + keyvalue+ "%"));
            and(Restrictions.like(keyname, keyvalue));
        }
        return this;
    }

    /**
     * 设置gt(>)查询条件
     *
     * @param keyname
     * @param keyvalue1
     * @param keyvalue2
     */
    public HibernateQuery gt(String keyname, Object keyvalue) {
        if (keyvalue != null && keyvalue != "") {
            and(Restrictions.gt(keyname, keyvalue));
        }
        return this;
    }

    /**
     * 设置lt(<)查询条件
     *
     * @param keyname
     * @param keyvalue1
     * @param keyvalue2
     */
    public HibernateQuery lt(String keyname, Object keyvalue) {
        if (keyvalue != null && keyvalue != "") {
            and(Restrictions.lt(keyname, keyvalue));
        }
        return this;
    }

    /**
     * 设置le(<=)查询条件
     *
     * @param keyname
     * @param keyvalue1
     * @param keyvalue2
     */
    public HibernateQuery le(String keyname, Object keyvalue) {
        if (keyvalue != null && keyvalue != "") {
            and(Restrictions.le(keyname, keyvalue));
        }
        return this;
    }

    /**
     * 设置ge(>=)查询条件
     *
     * @param keyname
     * @param keyvalue1
     * @param keyvalue2
     */
    public HibernateQuery ge(String keyname, Object keyvalue) {
        if (keyvalue != null && keyvalue != "") {
            and(Restrictions.ge(keyname, keyvalue));
        }
        return this;
    }

    /**
     * 设置in(包含)查询条件
     *
     * @param keyname
     * @param keyvalue1
     * @param keyvalue2
     */
    public HibernateQuery in(String keyname, Object[] keyvalue) {
        if (keyvalue != null && keyvalue[0] != "") {
            and(Restrictions.in(keyname, keyvalue));
        }
        return this;
    }

    /**
     * 设置isNull查询条件
     *
     * @param keyname
     * @param keyvalue1
     * @param keyvalue2
     */
    public HibernateQuery isNull(String keyname) {
        and(Restrictions.isNull(keyname));
        return this;
    }

    /**
     * 设置isNull查询条件
     *
     * @param keyname
     * @param keyvalue1
     * @param keyvalue2
     */
    public HibernateQuery isNotNull(String keyname) {
        and(Restrictions.isNotNull(keyname));
        return this;
    }


    /**
     * 设置between(之间)查询条件
     *
     * @param keyname
     * @param keyvalue1
     * @param keyvalue2
     */
    public HibernateQuery between(String keyname, Object keyvalue1, Object keyvalue2) {
        Criterion c = null;// 写入between查询条件

        if (keyvalue1 != null && keyvalue2 != null) {
            c = Restrictions.between(keyname, keyvalue1, keyvalue2);
        } else if (keyvalue1 != null) {
            c = Restrictions.ge(keyname, keyvalue1);
        } else if (keyvalue2 != null) {
            c = Restrictions.le(keyname, keyvalue2);
        }
        and(c);
        return this;
    }

    public HibernateQuery sql(String sql) {
        and(Restrictions.sqlRestriction(sql));
        return this;
    }

    public HibernateQuery sql(String sql, Object[] objects, Type[] type) {
        and(Restrictions.sqlRestriction(sql, objects, type));
        return this;
    }

    public HibernateQuery sql(String sql, Object objects, Type type) {
        and(Restrictions.sqlRestriction(sql, objects, type));
        return this;
    }

    public HibernateQuery or(Criterion criterion) {
        if (this.criterion == null) {
            this.criterion = criterion;
        } else {
            this.criterion = Restrictions.or(this.criterion, criterion);
        }
        return this;
    }

    public HibernateQuery or(HibernateQuery criteriaQuery) {
        if (this.criterion == null) {
            this.criterion = criteriaQuery.criterion;
        } else {
            this.criterion = Restrictions.or(this.criterion, criteriaQuery.criterion);
        }
        return this;
    }

    public HibernateQuery and(Criterion criterion) {
        if (this.criterion == null) {
            this.criterion = criterion;
        } else {
            this.criterion = Restrictions.and(this.criterion, criterion);
        }
        return this;
    }


    @Override
    public String toSqlString(Criteria criteria,
                              org.hibernate.criterion.CriteriaQuery criteriaQuery)
            throws HibernateException {
        if (this.criterion == null) {
            return "";
        } else {
            return criterion.toSqlString(criteria, criteriaQuery);
        }
    }

    @Override
    public TypedValue[] getTypedValues(Criteria criteria,
                                       org.hibernate.criterion.CriteriaQuery criteriaQuery)
            throws HibernateException {
        if (this.criterion == null) {
            return new TypedValue[0];
        } else {
            return criterion.getTypedValues(criteria, criteriaQuery);
        }

    }

}
