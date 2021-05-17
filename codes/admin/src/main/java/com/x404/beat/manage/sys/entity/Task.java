package com.x404.beat.manage.sys.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;

/**
 * @author
 * @version V1.0
 * @Title: Task
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    /***/
    private String id;
    /***/
    private String name;
    /***/
    private String beanName;
    /***/
    private String methodName;
    /***/
    private String cronExpression;
    /***/
    private String status;
    /***/
    @CreatedDate
    private java.util.Date createTime;
    /***/
    @LastModifiedDate
    private java.util.Date updateTime;

    public Task() {
        super();
    }

    public Task(String id) {
        this();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
}
