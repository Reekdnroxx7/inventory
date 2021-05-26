package com.x404.admin.manage.codeg.model;

import com.x404.admin.manage.codeg.utils.CodegUtils;

import java.util.Set;

public class TableConfig {
    private String name;
    private String displayName;
    private Set<String> primaryKeys;


    public TableConfig(String name, String displayName, Set<String> primaryKeys) {
        super();
        this.name = name;
        this.displayName = displayName;
        this.primaryKeys = primaryKeys;
    }


    public TableConfig() {
        super();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Set<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(Set<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public boolean isPrimaryKey(FieldConfig field) {
        return this.primaryKeys.contains(field.getFieldName());
    }

    public String getEntityName() {
        String entityName = this.name;
        if (entityName.indexOf("_") > 0) {
            entityName = entityName.substring(entityName.indexOf("_") + 1);
        }
        entityName = CodegUtils.getPascalName(entityName);
        return entityName;
    }

}
