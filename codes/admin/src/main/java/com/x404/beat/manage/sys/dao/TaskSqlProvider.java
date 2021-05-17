package com.x404.beat.manage.sys.dao;

import com.x404.beat.manage.sys.entity.Task;
import com.xc350.web.base.mybatis.dao.MybatisCriterion;
import com.xc350.web.base.mybatis.dao.MybatisExample;
import com.xc350.web.base.mybatis.dao.MybatisQuery;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class TaskSqlProvider {

    public String countByExample(MybatisExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("sys_task");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(MybatisExample example) {
        BEGIN();
        DELETE_FROM("sys_task");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(Task record) {
        BEGIN();
        INSERT_INTO("sys_task");

        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=VARCHAR}");
        }
        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        if (record.getBeanName() != null) {
            VALUES("bean_name", "#{beanName,jdbcType=VARCHAR}");
        }
        if (record.getMethodName() != null) {
            VALUES("method_name", "#{methodName,jdbcType=VARCHAR}");
        }
        if (record.getCronExpression() != null) {
            VALUES("cron_expression", "#{cronExpression,jdbcType=VARCHAR}");
        }
        if (record.getStatus() != null) {
            VALUES("status", "#{status,jdbcType=CHAR}");
        }
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        return SQL();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public String insertBatch(Map map) {
        List<Task> users = (List<Task>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO sys_task ");
        sb.append("(id,name,bean_name,method_name,cron_expression,status,create_time,update_time )");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].id},#'{'list[{0}].name},#'{'list[{0}].beanName},#'{'list[{0}].methodName},#'{'list[{0}].cronExpression},#'{'list[{0}].status},#'{'list[{0}].createTime},#'{'list[{0}].updateTime} )");
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
        SELECT("name");
        SELECT("bean_name");
        SELECT("method_name");
        SELECT("cron_expression");
        SELECT("status");
        SELECT("create_time");
        SELECT("update_time");
        FROM("sys_task");
        applyWhere(example, true);
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        return SQL();
    }


    public String selectByExample(MybatisExample example) {
        BEGIN();

        SELECT("id");
        SELECT("name");
        SELECT("bean_name");
        SELECT("method_name");
        SELECT("cron_expression");
        SELECT("status");
        SELECT("create_time");
        SELECT("update_time");
        FROM("sys_task");
        applyWhere(example, false);

        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }

        StringBuilder sb = new StringBuilder(SQL());
        if (example != null && example.getLimit() > 0) {
            sb.append(" limit ").append(" #{start},#{limit} ");
        }
        return sb.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Task record = (Task) parameter.get("record");
        MybatisExample example = (MybatisExample) parameter.get("example");

        BEGIN();
        UPDATE("sys_task");

        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=VARCHAR}");
        }
        if (record.getName() != null) {
            SET("name = #{record.name,jdbcType=VARCHAR}");
        }
        if (record.getBeanName() != null) {
            SET("bean_name = #{record.beanName,jdbcType=VARCHAR}");
        }
        if (record.getMethodName() != null) {
            SET("method_name = #{record.methodName,jdbcType=VARCHAR}");
        }
        if (record.getCronExpression() != null) {
            SET("cron_expression = #{record.cronExpression,jdbcType=VARCHAR}");
        }
        if (record.getStatus() != null) {
            SET("status = #{record.status,jdbcType=CHAR}");
        }
        if (record.getCreateTime() != null) {
            SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        if (record.getUpdateTime() != null) {
            SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }

        applyWhere(example, true);
        return SQL();
    }


    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("sys_task");

        SET("id = #{record.id,jdbcType=VARCHAR}");
        SET("name = #{record.name,jdbcType=VARCHAR}");
        SET("bean_name = #{record.beanName,jdbcType=VARCHAR}");
        SET("method_name = #{record.methodName,jdbcType=VARCHAR}");
        SET("cron_expression = #{record.cronExpression,jdbcType=VARCHAR}");
        SET("status = #{record.status,jdbcType=CHAR}");
        SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");

        MybatisExample example = (MybatisExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByKey(Task record) {
        BEGIN();
        UPDATE("sys_task");

        SET("id = #{id,jdbcType=VARCHAR}");
        SET("name = #{name,jdbcType=VARCHAR}");
        SET("bean_name = #{beanName,jdbcType=VARCHAR}");
        SET("method_name = #{methodName,jdbcType=VARCHAR}");
        SET("cron_expression = #{cronExpression,jdbcType=VARCHAR}");
        SET("status = #{status,jdbcType=CHAR}");
        SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");

        WHERE("id = #{id,jdbcType=VARCHAR}");
        return SQL();
    }

    public String updateByKeySelective(Task record) {
        BEGIN();
        UPDATE("sys_task");

        if (record.getId() != null) {
            SET("id = #{id,jdbcType=VARCHAR}");
        }
        if (record.getName() != null) {
            SET("name = #{name,jdbcType=VARCHAR}");
        }
        if (record.getBeanName() != null) {
            SET("bean_name = #{beanName,jdbcType=VARCHAR}");
        }
        if (record.getMethodName() != null) {
            SET("method_name = #{methodName,jdbcType=VARCHAR}");
        }
        if (record.getCronExpression() != null) {
            SET("cron_expression = #{cronExpression,jdbcType=VARCHAR}");
        }
        if (record.getStatus() != null) {
            SET("status = #{status,jdbcType=CHAR}");
        }
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
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
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j, criterion.getTypeHandler()));
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