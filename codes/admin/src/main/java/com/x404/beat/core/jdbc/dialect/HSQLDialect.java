package com.x404.beat.core.jdbc.dialect;


public class HSQLDialect extends Dialect {
    public boolean supportsLimit() {
        return true;
    }

    public boolean supportsLimitOffset() {
        return true;
    }

    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        return new StringBuffer(sql.length() + 10).append(sql).insert(sql.toLowerCase().indexOf("select") + 6, " top " + limitPlaceholder).toString();
    }
}