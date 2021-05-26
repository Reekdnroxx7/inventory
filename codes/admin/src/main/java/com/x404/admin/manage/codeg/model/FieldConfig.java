package com.x404.admin.manage.codeg.model;

import com.x404.admin.manage.codeg.utils.CodegUtils;


public class FieldConfig {
    private String tableName;
    private String fieldName;
    private String displayName;
    private int dbType;
    private int showType = 0; // int 0 显示 ；1 不显示；
    private int length;
    private String dictGroupCode;
    private boolean isPrimaryKey;
    private boolean editable = true;
    protected boolean autoInc;
    //编辑器类型 textfield,datefield,numblefield
    private String xtype = "textfield";
    private String queryType = "no"; // 查询方式

    public FieldConfig() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getJavaFieldName() {
        return fieldName;
//		return CodegUtils.getCamelName(this.fieldName);
    }

    public void setJavaFieldName(String fieldName) {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getDbType() {
        return dbType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public String getDictGroupCode() {
        return dictGroupCode;
    }

    public void setDictGroupCode(String dictGroupCode) {
        this.dictGroupCode = dictGroupCode;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public int getLength() {
        return length;
    }

    public QualifiedType getQualifiedType() {
        return CodegUtils.getJavaType(this.dbType);
    }

    public void setQualifiedType(QualifiedType qualifiedType) {
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }


    public String getXtype() {
        return xtype;
    }

    public void setXtype(String xtype) {
        this.xtype = xtype;
    }


    public boolean isAutoInc() {
        return autoInc;
    }

    public void setAutoInc(boolean autoInc) {
        this.autoInc = autoInc;
    }

    @Override
    public String toString() {
        return "CodegField [tableName=" + tableName + ", fieldName="
                + fieldName + ", displayName=" + displayName + ", dbType="
                + dbType + ", showType=" + showType + ", length=" + length
                + ", dictGroupCode=" + dictGroupCode + ", isPrimaryKey="
                + isPrimaryKey + ", editable=" + editable + ", queryType="
                + queryType + "]";
    }


}
