package com.x404.beat.manage.codeg.model;

public class QualifiedType {
    private int dbType;
    private String jdbcType;
    private String javaType;


    public QualifiedType() {
        super();
    }

    public QualifiedType(int dbType, String jdbcType, String javaType) {
        super();
        this.dbType = dbType;
        this.jdbcType = jdbcType;
        this.javaType = javaType;
    }

    public int getDbType() {
        return dbType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    @Override
    public String toString() {
        return "QualifiedType [dbType=" + dbType + ", jdbcType=" + jdbcType
                + ", javaType=" + javaType + "]";
    }


}