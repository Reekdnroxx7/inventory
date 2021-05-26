package com.x404.busi.dao;

import com.x404.busi.entity.Materiel;
import com.xc350.web.base.mybatis.dao.MybatisCriterion;
import com.xc350.web.base.mybatis.dao.MybatisExample;
import com.xc350.web.base.mybatis.dao.MybatisQuery;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class MaterielSqlProvider {

    public String countByExample(MybatisExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("bom_materiel");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(MybatisExample example) {
        BEGIN();
        DELETE_FROM("bom_materiel");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(Materiel record) {
        BEGIN();
        INSERT_INTO("bom_materiel");

        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=VARCHAR}");
        }
        if (record.getProcedure_name() != null) {
            VALUES("procedure_name", "#{procedure_name,jdbcType=VARCHAR}");
        }
        if (record.getMaterial_code() != null) {
            VALUES("material_code", "#{material_code,jdbcType=VARCHAR}");
        }
        if (record.getMaterial_name() != null) {
            VALUES("material_name", "#{material_name,jdbcType=VARCHAR}");
        }
        if (record.getQuantity() != null) {
            VALUES("quantity", "#{quantity,jdbcType=INTEGER}");
        }
        if (record.getUnit() != null) {
            VALUES("unit", "#{unit,jdbcType=CHAR}");
        }
        if (record.getPosition() != null) {
            VALUES("position", "#{position,jdbcType=VARCHAR}");
        }
        if (record.getCreate_date() != null) {
            VALUES("create_date", "#{create_date,jdbcType=TIMESTAMP}");
        }
        if (record.getCreate_by() != null) {
            VALUES("create_by", "#{create_by,jdbcType=VARCHAR}");
        }
        if (record.getUpdate_date() != null) {
            VALUES("update_date", "#{update_date,jdbcType=TIMESTAMP}");
        }
        if (record.getUpdate_by() != null) {
            VALUES("update_by", "#{update_by,jdbcType=VARCHAR}");
        }
        if (record.getRemarks() != null) {
            VALUES("remarks", "#{remarks,jdbcType=VARCHAR}");
        }
        return SQL();
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String insertBatch(Map map) {
        List<Materiel> users = (List<Materiel>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO bom_materiel ");
        sb.append("(id,procedure_name,material_code,material_name,quantity,unit,position,create_date,create_by,update_date,update_by,remarks )");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].id},#'{'list[{0}].procedure_name},#'{'list[{0}].material_code},#'{'list[{0}].material_name},#'{'list[{0}].quantity},#'{'list[{0}].unit},#'{'list[{0}].position},#'{'list[{0}].create_date},#'{'list[{0}].create_by},#'{'list[{0}].update_date},#'{'list[{0}].update_by},#'{'list[{0}].remarks} )");
        for (int i = 0; i < users.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < users.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }


    public String pageList(Map<String, Object> parameter) {

    	MybatisExample example = (MybatisExample) parameter.get("example");
        BEGIN();
        SELECT("id");
        SELECT("procedure_name");
        SELECT("material_code");
        SELECT("material_name");
        SELECT("quantity");
        SELECT("unit");
        SELECT("position");
        SELECT("create_date");
        SELECT("create_by");
        SELECT("update_date");
        SELECT("update_by");
        SELECT("remarks");
        FROM("bom_materiel");
        applyWhere(example, true);
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        return SQL();
    }


     public String selectByExample(MybatisExample example) {
        BEGIN();

        SELECT("id");
        SELECT("procedure_name");
        SELECT("material_code");
        SELECT("material_name");
        SELECT("quantity");
        SELECT("unit");
        SELECT("position");
        SELECT("create_date");
        SELECT("create_by");
        SELECT("update_date");
        SELECT("update_by");
        SELECT("remarks");
        FROM("bom_materiel");
        applyWhere(example, false);

        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }

        StringBuilder sb = new StringBuilder(SQL());
        if (example != null && example.getLimit() > 0) {
           sb.append(" limit ").append( " #{start},#{limit} ");
        }
        return sb.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Materiel record = (Materiel) parameter.get("record");
        MybatisExample example = (MybatisExample) parameter.get("example");

        BEGIN();
        UPDATE("bom_materiel");

        if (record.getId() != null) {
        	SET("id = #{record.id,jdbcType=VARCHAR}");
        }
        if (record.getProcedure_name() != null) {
        	SET("procedure_name = #{record.procedure_name,jdbcType=VARCHAR}");
        }
        if (record.getMaterial_code() != null) {
        	SET("material_code = #{record.material_code,jdbcType=VARCHAR}");
        }
        if (record.getMaterial_name() != null) {
        	SET("material_name = #{record.material_name,jdbcType=VARCHAR}");
        }
        if (record.getQuantity() != null) {
        	SET("quantity = #{record.quantity,jdbcType=INTEGER}");
        }
        if (record.getUnit() != null) {
        	SET("unit = #{record.unit,jdbcType=CHAR}");
        }
        if (record.getPosition() != null) {
        	SET("position = #{record.position,jdbcType=VARCHAR}");
        }
        if (record.getCreate_date() != null) {
        	SET("create_date = #{record.create_date,jdbcType=TIMESTAMP}");
        }
        if (record.getCreate_by() != null) {
        	SET("create_by = #{record.create_by,jdbcType=VARCHAR}");
        }
        if (record.getUpdate_date() != null) {
        	SET("update_date = #{record.update_date,jdbcType=TIMESTAMP}");
        }
        if (record.getUpdate_by() != null) {
        	SET("update_by = #{record.update_by,jdbcType=VARCHAR}");
        }
        if (record.getRemarks() != null) {
        	SET("remarks = #{record.remarks,jdbcType=VARCHAR}");
        }

        applyWhere(example, true);
        return SQL();
    }


    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("bom_materiel");

        	SET("id = #{record.id,jdbcType=VARCHAR}");
        	SET("procedure_name = #{record.procedure_name,jdbcType=VARCHAR}");
        	SET("material_code = #{record.material_code,jdbcType=VARCHAR}");
        	SET("material_name = #{record.material_name,jdbcType=VARCHAR}");
        	SET("quantity = #{record.quantity,jdbcType=INTEGER}");
        	SET("unit = #{record.unit,jdbcType=CHAR}");
        	SET("position = #{record.position,jdbcType=VARCHAR}");
        	SET("create_date = #{record.create_date,jdbcType=TIMESTAMP}");
        	SET("create_by = #{record.create_by,jdbcType=VARCHAR}");
        	SET("update_date = #{record.update_date,jdbcType=TIMESTAMP}");
        	SET("update_by = #{record.update_by,jdbcType=VARCHAR}");
        	SET("remarks = #{record.remarks,jdbcType=VARCHAR}");

        MybatisExample example = (MybatisExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }
    public String updateByKey(Materiel record) {
        BEGIN();
        UPDATE("bom_materiel");

        	SET("id = #{id,jdbcType=VARCHAR}");
        	SET("procedure_name = #{procedure_name,jdbcType=VARCHAR}");
        	SET("material_code = #{material_code,jdbcType=VARCHAR}");
        	SET("material_name = #{material_name,jdbcType=VARCHAR}");
        	SET("quantity = #{quantity,jdbcType=INTEGER}");
        	SET("unit = #{unit,jdbcType=CHAR}");
        	SET("position = #{position,jdbcType=VARCHAR}");
        	SET("create_date = #{create_date,jdbcType=TIMESTAMP}");
        	SET("create_by = #{create_by,jdbcType=VARCHAR}");
        	SET("update_date = #{update_date,jdbcType=TIMESTAMP}");
        	SET("update_by = #{update_by,jdbcType=VARCHAR}");
        	SET("remarks = #{remarks,jdbcType=VARCHAR}");

        WHERE("id = #{id,jdbcType=VARCHAR}");
        return SQL();
    }

    public String updateByKeySelective(Materiel record) {
        BEGIN();
        UPDATE("bom_materiel");

        if (record.getId() != null) {
        	SET("id = #{id,jdbcType=VARCHAR}");
        }
        if (record.getProcedure_name() != null) {
        	SET("procedure_name = #{procedure_name,jdbcType=VARCHAR}");
        }
        if (record.getMaterial_code() != null) {
        	SET("material_code = #{material_code,jdbcType=VARCHAR}");
        }
        if (record.getMaterial_name() != null) {
        	SET("material_name = #{material_name,jdbcType=VARCHAR}");
        }
        if (record.getQuantity() != null) {
        	SET("quantity = #{quantity,jdbcType=INTEGER}");
        }
        if (record.getUnit() != null) {
        	SET("unit = #{unit,jdbcType=CHAR}");
        }
        if (record.getPosition() != null) {
        	SET("position = #{position,jdbcType=VARCHAR}");
        }
        if (record.getCreate_date() != null) {
        	SET("create_date = #{create_date,jdbcType=TIMESTAMP}");
        }
        if (record.getCreate_by() != null) {
        	SET("create_by = #{create_by,jdbcType=VARCHAR}");
        }
        if (record.getUpdate_date() != null) {
        	SET("update_date = #{update_date,jdbcType=TIMESTAMP}");
        }
        if (record.getUpdate_by() != null) {
        	SET("update_by = #{update_by,jdbcType=VARCHAR}");
        }
        if (record.getRemarks() != null) {
        	SET("remarks = #{remarks,jdbcType=VARCHAR}");
        }

        WHERE("id = #{id,jdbcType=VARCHAR}");
        return SQL();
    }


    protected void applyWhere(MybatisExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }

        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }

        StringBuilder sb = new StringBuilder();
        List<MybatisQuery> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            MybatisQuery criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }

                sb.append('(');
                List<MybatisCriterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    MybatisCriterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }

                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }

        if (sb.length() > 0) {
            WHERE(sb.toString());
        }
    }
}