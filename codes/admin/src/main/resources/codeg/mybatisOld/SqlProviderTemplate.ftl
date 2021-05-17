package  ${package}.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import cn.bp.earth.web.basedao.mybatis.dao.MybatisCriterion;
import cn.bp.earth.web.basedao.mybatis.dao.MybatisQuery;
import ${package}.entity.${entityName};

public class ${entityName}SqlProvider {

    public String countByExample(MybatisExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("${table.name}");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(MybatisExample example) {
        BEGIN();
        DELETE_FROM("${table.name}");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(${entityName} record) {
        BEGIN();
        INSERT_INTO("${table.name}");
        
        <#list fields as field>
        if (record.get${field.javaFieldName?cap_first}() != null) {
            VALUES("${field.fieldName}", "${r"#"}{${field.javaFieldName},jdbcType=${field.qualifiedType.jdbcType}}");
        }
        </#list>
        return SQL();
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String insertBatch(Map map) {  
        List<${entityName}> users = (List<${entityName}>) map.get("list");  
        StringBuilder sb = new StringBuilder();  
        sb.append("INSERT INTO ${table.name} ");  
        sb.append("(<#list fields as field>${field.fieldName}<#if field_has_next>,</#if></#list> )");  
        sb.append("VALUES ");  
        MessageFormat mf = new MessageFormat("(<#list fields as field>${r"#"}'{'list[{0}].${field.javaFieldName}}<#if field_has_next>,</#if></#list> )");  
        for (int i = 0; i < users.size(); i++) {  
            sb.append(mf.format(new Object[]{i}));  
            if (i < users.size() - 1) {  
                sb.append(",");  
            }  
        }  
        return sb.toString();  
    }  
    

    public String selectByExample(MybatisExample example) {
        BEGIN();
       
        <#list fields as field>
        SELECT("${field.fieldName}");
        </#list>       
        FROM("${table.name}");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        } 
        return SQL();
    }
    
    
     public String selectByExample(MybatisExample example) {
        BEGIN();
       
        <#list fields as field>
        SELECT("${field.fieldName}");
        </#list>       
        FROM("${table.name}");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        StringBuilder sb = new StringBuilder(SQL());
        if (example != null && example.getLimit() > 0) {
           sb.append(" limit ").append( " ${r"#"}{start},${r"#"}{limit} ")
        }
        return sb.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        ${entityName} record = (${entityName}) parameter.get("record");
        MybatisExample example = (MybatisExample) parameter.get("example");
        
        BEGIN();
        UPDATE("${table.name}");
        
        <#list fields as field>
        if (record.get${field.javaFieldName?cap_first}() != null) {
        	SET("${field.fieldName} = ${r"#"}{record.${field.javaFieldName},jdbcType=${field.qualifiedType.jdbcType}}");
        }
        </#list>       
        
        applyWhere(example, true);
        return SQL();
    }

   
    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("${table.name}");
        
        <#list fields as field>
        	SET("${field.fieldName} = ${r"#"}{record.${field.javaFieldName},jdbcType=${field.qualifiedType.jdbcType}}");
        </#list> 
        
        MybatisExample example = (MybatisExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }
<#if primaryKey??>
    public String updateById(${entityName} record) {
        BEGIN();
        UPDATE("${table.name}");
        
        <#list fields as field>
        	SET("${field.fieldName} = ${r"#"}{${field.javaFieldName},jdbcType=${field.qualifiedType.jdbcType}}");
        </#list> 
		        
        WHERE("${primaryKey.fieldName} = ${r"#"}{${primaryKey.javaFieldName},jdbcType=${primaryKey.qualifiedType.jdbcType}}");
        return SQL();
    }
    
    public String updateByIdSelective(${entityName} record) {
        BEGIN();
        UPDATE("${table.name}");
        
        <#list fields as field>
        if (record.get${field.javaFieldName?cap_first}() != null) {
        	SET("${field.fieldName} = ${r"#"}{${field.javaFieldName},jdbcType=${field.qualifiedType.jdbcType}}");
        }
        </#list>       
        
        WHERE("${primaryKey.fieldName} = ${r"#"}{${primaryKey.javaFieldName},jdbcType=${primaryKey.qualifiedType.jdbcType}}");
        return SQL();
    }
    
</#if>

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
            parmPhrase1 = "%s ${r"#"}{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s ${r"#"}{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s ${r"#"}{example.oredCriteria[%d].allCriteria[%d].value} and ${r"#"}{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s ${r"#"}{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and ${r"#"}{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "${r"#"}{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "${r"#"}{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s ${r"#"}{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s ${r"#"}{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s ${r"#"}{oredCriteria[%d].allCriteria[%d].value} and ${r"#"}{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s ${r"#"}{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and ${r"#"}{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "${r"#"}{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "${r"#"}{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
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