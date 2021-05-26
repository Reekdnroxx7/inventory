package com.x404.admin.manage.sys.entity;

import com.x404.admin.core.entity.DataEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author
 * @version V1.0
 * @Title: Entity
 */
@Entity
@Table(name = "sys_config")
@DynamicUpdate(true)
@DynamicInsert(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Config extends DataEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 键
     */
    private java.lang.String key;
    /**
     * 值
     */
    private java.lang.String value;

    public Config() {
        super();
    }

    public Config(String id) {
        this();
        this.id = id;
    }

    @Column(name = "[key]")
    public java.lang.String getKey() {
        return key;
    }

    public void setKey(java.lang.String key) {
        this.key = key;
    }

    @Column(name = "[value]")
    public java.lang.String getValue() {
        return value;
    }

    public void setValue(java.lang.String value) {
        this.value = value;
    }
}
