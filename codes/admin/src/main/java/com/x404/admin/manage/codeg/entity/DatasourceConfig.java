package com.x404.admin.manage.codeg.entity;


import com.x404.module.basedao.IdEntity;

/**
 * @author
 * @version V1.0
 * @Title: DatasourceConfig
 */
public class DatasourceConfig implements IdEntity
{
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private java.lang.String id;
    /**
     * 数据库url
     */
    private java.lang.String url;
    /**
     * 数据库类型
     */
    private java.lang.String dbType;
    /**
     * 数据库用户名
     */
    private java.lang.String userName;
    /**
     * 数据库密码
     */
    private java.lang.String password;
    /**
     * 用户端ip
     */
    private java.lang.String host;

    public DatasourceConfig() {
        super();
    }

    public DatasourceConfig(String id) {
        this();
        this.id = id;
    }

    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    public java.lang.String getUrl() {
        return url;
    }

    public void setUrl(java.lang.String url) {
        this.url = url;
    }

    public java.lang.String getDbType() {
        return dbType;
    }

    public void setDbType(java.lang.String dbType) {
        this.dbType = dbType;
    }

    public java.lang.String getUserName() {
        return userName;
    }

    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    public java.lang.String getPassword() {
        return password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    public java.lang.String getHost() {
        return host;
    }

    public void setHost(java.lang.String host) {
        this.host = host;
    }
}
