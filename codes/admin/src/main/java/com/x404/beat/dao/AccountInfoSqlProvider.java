package com.x404.beat.dao;

import com.x404.beat.models.AccountInfo;
import com.xc350.web.base.mybatis.dao.MybatisCriterion;
import com.xc350.web.base.mybatis.dao.MybatisExample;
import com.xc350.web.base.mybatis.dao.MybatisQuery;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class AccountInfoSqlProvider {

    public String countByExample(MybatisExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("account_info");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(MybatisExample example) {
        BEGIN();
        DELETE_FROM("account_info");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(AccountInfo record) {
        BEGIN();
        INSERT_INTO("account_info");

        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        if (record.getBalance() != null) {
            VALUES("balance", "#{balance,jdbcType=DOUBLE}");
        }
        if (record.getInfo() != null) {
            VALUES("info", "#{info,jdbcType=VARCHAR}");
        }
        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        if (record.getPassword() != null) {
            VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        if (record.getPlatform() != null) {
            VALUES("platform", "#{platform,jdbcType=VARCHAR}");
        }
        if (record.getTag() != null) {
            VALUES("tag", "#{tag,jdbcType=VARCHAR}");
        }
        return SQL();
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String insertBatch(Map map) {
        List<AccountInfo> users = (List<AccountInfo>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO account_info ");
        sb.append("(id,balance,info,name,password,platform,tag )");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].id},#'{'list[{0}].balance},#'{'list[{0}].info},#'{'list[{0}].name},#'{'list[{0}].password},#'{'list[{0}].platform},#'{'list[{0}].tag} )");
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
        SELECT("balance");
        SELECT("info");
        SELECT("name");
        SELECT("password");
        SELECT("platform");
        SELECT("tag");
        FROM("account_info");
        applyWhere(example, true);
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        return SQL();
    }


     public String selectByExample(MybatisExample example) {
        BEGIN();

        SELECT("id");
        SELECT("balance");
        SELECT("info");
        SELECT("name");
        SELECT("password");
        SELECT("platform");
        SELECT("tag");
        FROM("account_info");
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
        AccountInfo record = (AccountInfo) parameter.get("record");
        MybatisExample example = (MybatisExample) parameter.get("example");

        BEGIN();
        UPDATE("account_info");

        if (record.getId() != null) {
        	SET("id = #{record.id,jdbcType=BIGINT}");
        }
        if (record.getBalance() != null) {
        	SET("balance = #{record.balance,jdbcType=DOUBLE}");
        }
        if (record.getInfo() != null) {
        	SET("info = #{record.info,jdbcType=VARCHAR}");
        }
        if (record.getName() != null) {
        	SET("name = #{record.name,jdbcType=VARCHAR}");
        }
        if (record.getPassword() != null) {
        	SET("password = #{record.password,jdbcType=VARCHAR}");
        }
        if (record.getPlatform() != null) {
        	SET("platform = #{record.platform,jdbcType=VARCHAR}");
        }
        if (record.getTag() != null) {
        	SET("tag = #{record.tag,jdbcType=VARCHAR}");
        }

        applyWhere(example, true);
        return SQL();
    }


    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("account_info");

        	SET("id = #{record.id,jdbcType=BIGINT}");
        	SET("balance = #{record.balance,jdbcType=DOUBLE}");
        	SET("info = #{record.info,jdbcType=VARCHAR}");
        	SET("name = #{record.name,jdbcType=VARCHAR}");
        	SET("password = #{record.password,jdbcType=VARCHAR}");
        	SET("platform = #{record.platform,jdbcType=VARCHAR}");
        	SET("tag = #{record.tag,jdbcType=VARCHAR}");

        MybatisExample example = (MybatisExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }
    public String updateByKey(AccountInfo record) {
        BEGIN();
        UPDATE("account_info");

        	SET("id = #{id,jdbcType=BIGINT}");
        	SET("balance = #{balance,jdbcType=DOUBLE}");
        	SET("info = #{info,jdbcType=VARCHAR}");
        	SET("name = #{name,jdbcType=VARCHAR}");
        	SET("password = #{password,jdbcType=VARCHAR}");
        	SET("platform = #{platform,jdbcType=VARCHAR}");
        	SET("tag = #{tag,jdbcType=VARCHAR}");

        WHERE("id = #{id,jdbcType=BIGINT}");
        return SQL();
    }

    public String updateByKeySelective(AccountInfo record) {
        BEGIN();
        UPDATE("account_info");

        if (record.getId() != null) {
        	SET("id = #{id,jdbcType=BIGINT}");
        }
        if (record.getBalance() != null) {
        	SET("balance = #{balance,jdbcType=DOUBLE}");
        }
        if (record.getInfo() != null) {
        	SET("info = #{info,jdbcType=VARCHAR}");
        }
        if (record.getName() != null) {
        	SET("name = #{name,jdbcType=VARCHAR}");
        }
        if (record.getPassword() != null) {
        	SET("password = #{password,jdbcType=VARCHAR}");
        }
        if (record.getPlatform() != null) {
        	SET("platform = #{platform,jdbcType=VARCHAR}");
        }
        if (record.getTag() != null) {
        	SET("tag = #{tag,jdbcType=VARCHAR}");
        }

        WHERE("id = #{id,jdbcType=BIGINT}");
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