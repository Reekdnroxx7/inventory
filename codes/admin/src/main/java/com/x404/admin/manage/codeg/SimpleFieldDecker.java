package com.x404.admin.manage.codeg;

import com.x404.admin.manage.codeg.model.FieldConfig;

import java.util.List;

public class SimpleFieldDecker implements FieldDecker {

    @Override
    public void deckFields(List<FieldConfig> fieldConfigs) {
        int i = 0;
        for (FieldConfig fieldConfig : fieldConfigs) {
            if (i == 1) {
                fieldConfig.setQueryType("like");
            }
            deckField(fieldConfig);
            i++;
        }
    }

    public void deckField(FieldConfig fieldConfig) {
        try {
            if (fieldConfig.getFieldName().endsWith("date")
                    || fieldConfig.getQualifiedType().getJavaType().equals("java.util.Date")) {
                fieldConfig.setXtype("datefield");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (fieldConfig.getQualifiedType().getJavaType().equals("java.lang.Integer") ||
                fieldConfig.getQualifiedType().getJavaType().equals("java.lang.Double") ||
                fieldConfig.getQualifiedType().getJavaType().equals("java.lang.Float")
                ) {
            fieldConfig.setXtype("numberfield");
        }

        if (fieldConfig.isPrimaryKey()) {
            fieldConfig.setShowType(1);
            fieldConfig.setEditable(false);
        }
        if (fieldConfig.getFieldName().equals("update_by")
                || fieldConfig.getFieldName().equals("create_by") ||
                fieldConfig.getFieldName().equals("update_date") ||
                fieldConfig.getFieldName().equals("create_date") ||
                fieldConfig.getFieldName().equals("remarks")) {
            fieldConfig.setShowType(1);
            fieldConfig.setEditable(false);
        }
        if (fieldConfig.getFieldName().endsWith("type")) {
            String dictCode = fieldConfig.getTableName() + "." + fieldConfig.getFieldName();
            fieldConfig.setDictGroupCode(dictCode);
            fieldConfig.setXtype("dictcombobox");
            fieldConfig.setQueryType("eq");
        }
    }

}
