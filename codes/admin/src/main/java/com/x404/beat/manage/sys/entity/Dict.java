/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.x404.beat.manage.sys.entity;

import com.x404.beat.core.entity.DataEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 字典Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
@Entity
@Table(name = "sys_dict")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dict extends DataEntity {

    private static final long serialVersionUID = 1L;
    private String groupCode;//类型分组
    private String dictValue;//字典值
    private String dictCode;//类型编码

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Column(name = "dict_value")
    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    @Column(name = "dict_code")
    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }


}