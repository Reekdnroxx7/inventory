package com.x404.admin.manage.codeg.utils;

import com.alibaba.druid.util.JdbcUtils;
import com.x404.admin.manage.codeg.entity.DatasourceConfig;
import com.x404.admin.manage.codeg.model.QualifiedType;
import com.x404.admin.manage.codeg.model.TableConfig;
import com.x404.admin.manage.codeg.model.FieldConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class CodegUtils {

    private static HashMap<Integer, QualifiedType> typeMap = new HashMap<Integer, QualifiedType>();

    static {
        typeMap.put(Types.ARRAY, new QualifiedType(Types.ARRAY, "ARRAY", Object.class.getName()));

        typeMap.put(Types.BIGINT, new QualifiedType(Types.BIGINT, "BIGINT", Long.class.getName()));

        typeMap.put(Types.BINARY, new QualifiedType(Types.BINARY, "BINARY", "byte[]"));

        typeMap.put(Types.BIT, new QualifiedType(Types.BIT, "BIT", Boolean.class.getName()));

        typeMap.put(Types.BLOB, new QualifiedType(Types.BLOB, "BLOB", "byte[]"));

        typeMap.put(Types.BOOLEAN, new QualifiedType(Types.BOOLEAN, "BOOLEAN", Boolean.class.getName()));

        typeMap.put(Types.CHAR, new QualifiedType(Types.CHAR, "CHAR", String.class.getName()));

        typeMap.put(Types.CLOB, new QualifiedType(Types.CLOB, "CLOB", String.class.getName()));

        typeMap.put(Types.DATALINK, new QualifiedType(Types.DATALINK, "DATALINK", Object.class.getName()));

        typeMap.put(Types.DATE, new QualifiedType(Types.DATE, "DATE", Date.class.getName()));

        typeMap.put(Types.DISTINCT, new QualifiedType(Types.DISTINCT, "DISTINCT", Object.class.getName()));

        typeMap.put(Types.DOUBLE, new QualifiedType(Types.DOUBLE, "DOUBLE", Double.class.getName()));

        typeMap.put(Types.FLOAT, new QualifiedType(Types.FLOAT, "FLOAT", Double.class.getName()));

        typeMap.put(Types.INTEGER, new QualifiedType(Types.INTEGER, "INTEGER", Integer.class.getName()));
        typeMap.put(Types.DECIMAL, new QualifiedType(Types.DECIMAL, "DECIMAL", Double.class.getName()));

        typeMap.put(Types.JAVA_OBJECT, new QualifiedType(Types.JAVA_OBJECT, "JAVA_OBJECT", Object.class.getName()));

        typeMap.put(Types.LONGVARBINARY, new QualifiedType(Types.LONGVARBINARY, "LONGVARBINARY", "byte[]"));

        typeMap.put(Types.LONGVARCHAR, new QualifiedType(Types.LONGVARCHAR, "LONGVARCHAR", String.class.getName()));

//	    typeMap.put(Types.NCHAR, new QualifiedType(Types.NCHAR, "NCHAR", String.class.getName()));
//
//	    typeMap.put(Types.NCLOB, new QualifiedType(Types.NCLOB, "NCLOB", String.class.getName()));
//
//	    typeMap.put(Types.NVARCHAR, new QualifiedType(Types.NVARCHAR, "NVARCHAR", String.class.getName()));

        typeMap.put(Types.NULL, new QualifiedType(Types.NULL, "NULL", Object.class.getName()));

        typeMap.put(Types.OTHER, new QualifiedType(Types.OTHER, "OTHER", Object.class.getName()));

        typeMap.put(Types.REAL, new QualifiedType(Types.REAL, "REAL", Float.class.getName()));

        typeMap.put(Types.REF, new QualifiedType(Types.REF, "REF", Object.class.getName()));

        typeMap.put(Types.SMALLINT, new QualifiedType(Types.SMALLINT, "SMALLINT", Short.class.getName()));

        typeMap.put(Types.STRUCT, new QualifiedType(Types.STRUCT, "STRUCT", Object.class.getName()));

        typeMap.put(Types.TIME, new QualifiedType(Types.TIME, "TIME", Date.class.getName()));

        typeMap.put(Types.TIMESTAMP, new QualifiedType(Types.TIMESTAMP, "TIMESTAMP", Date.class.getName()));

        typeMap.put(Types.TINYINT, new QualifiedType(Types.TINYINT, "TINYINT", Byte.class.getName()));

        typeMap.put(Types.VARBINARY, new QualifiedType(Types.VARBINARY, "VARBINARY", "byte[]"));

        typeMap.put(Types.VARCHAR, new QualifiedType(Types.VARCHAR, "VARCHAR", String.class.getName()));

    }

    private final static Logger logger = LoggerFactory.getLogger(CodegUtils.class);


    public static Connection getConnection(DatasourceConfig datasourceConfig) {
        String user = datasourceConfig.getUserName();
        String password = datasourceConfig.getPassword();
        String url = datasourceConfig.getUrl();
//		String driverName=properties.getProperty("diver_name");
        try {
            String driverClassName = JdbcUtils.getDriverClassName(url);
            Class.forName(driverClassName);
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("数据库配置有误", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("数据库配置有误", e);
        }
    }


    public static List<TableConfig> getAllTable(DatasourceConfig datasourceConfig) {
        List<TableConfig> tables = new ArrayList<TableConfig>();
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = getConnection(datasourceConfig);
            rs = connection.getMetaData().getTables("", "", "", null);

            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String displayName = getDisplayName(datasourceConfig, tableName);
                if (StringUtils.isBlank(displayName)) {
                    displayName = tableName;
                }
                TableConfig table = new TableConfig();
                table.setName(tableName);
                table.setDisplayName(displayName);
                tables.add(table);
            }
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("关闭数据集失败", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("关闭连接失败", e);
                }
            }

        }
        return tables;
    }


    public static String getDisplayName(DatasourceConfig datasourceConfig, String tablename) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet primaryKeys = null;
        try {
            connection = getConnection(datasourceConfig);
            String sql = "show table status like '" + tablename + "'";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString("Comment");
                return name;
            }
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            if (primaryKeys != null) {
                try {
                    primaryKeys.close();
                } catch (SQLException e) {
                    logger.error("", e);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("关闭数据集失败", e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.error("关闭数据集失败", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("关闭连接失败", e);
                }
            }

        }
        return null;
    }

    public static TableConfig getTable(DatasourceConfig datasourceConfig, String tableName) {
//		DataSource dataSource = DataSourceUtils.getDataSourceById("dataSource");
        Connection connection = null;
        ResultSet rs = null;
        ResultSet primaryKeys = null;
        try {
            connection = getConnection(datasourceConfig);
            rs = connection.getMetaData().getTables("", "", tableName, null);

            if (rs.next()) {
                tableName = rs.getString("TABLE_NAME");
                TableConfig table = new TableConfig();
                primaryKeys = connection.getMetaData().getPrimaryKeys("", "", tableName);
                Set<String> keys = new HashSet<String>();
                while (primaryKeys.next()) {
                    keys.add(primaryKeys.getString("COLUMN_NAME"));
                }
                table.setPrimaryKeys(keys);
                table.setName(tableName);
                return table;
            }
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            if (primaryKeys != null) {
                try {
                    primaryKeys.close();
                } catch (SQLException e) {
                    logger.error("", e);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("关闭数据集失败", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("关闭连接失败", e);
                }
            }

        }
        return null;
    }


    public static List<String> getPrimaryKeys(DatasourceConfig datasourceConfig, String tableName) {
        Connection connection = null;
        ResultSet rs = null;
        List<String> primaryKeys = new ArrayList<String>();
        try {
            connection = getConnection(datasourceConfig);
            rs = connection.getMetaData().getPrimaryKeys("", "", tableName);
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME").toLowerCase();
                primaryKeys.add(columnName);
            }
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("关闭数据集失败", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("关闭连接失败", e);
                }
            }

        }
        return primaryKeys;
    }

    public static List<FieldConfig> getAllFields(DatasourceConfig datasourceConfig, String tableName) {

        List<FieldConfig> codegFields = new ArrayList<FieldConfig>();
//		DataSource dataSource = DataSourceUtils.getDataSourceById("dataSource");
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = getConnection(datasourceConfig);
            rs = connection.getMetaData().getColumns("", "", tableName, "");
//			int columnCount = rs.getMetaData().getColumnCount();
//			for(int i=0;i < columnCount;i++){
//				System.out.println(rs.getMetaData().getColumnName(i+1));
//			}
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME").toLowerCase();
                String remarks = rs.getString("REMARKS");
                int type = rs.getInt("DATA_TYPE");
                int length = rs.getInt("COLUMN_SIZE");
//				 String actoInc = rs.getString("IS_GENERATEDCOLUMN");
                FieldConfig codegField = new FieldConfig();
                codegField.setTableName(tableName);
                codegField.setDbType(type);
                codegField.setFieldName(columnName);
                codegField.setDisplayName(remarks);
                codegField.setLength(length);
//				 codegField.setAutoInc("Y".equals(actoInc));
                codegFields.add(codegField);
            }
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("关闭数据集失败", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("关闭连接失败", e);
                }
            }

        }
        return codegFields;

    }

    public static String getPascalName(String dbName) {
        StringBuilder sb = new StringBuilder(dbName.toLowerCase());
        for (int i = 0; i < sb.length(); i++) {
            char charAt = sb.charAt(i);
            if ('_' == charAt) {
                if (i < sb.length() - 1) {
                    sb.setCharAt(i + 1, Character.toUpperCase(sb.charAt(i + 1)));
                }
                sb.deleteCharAt(i);
            }
        }
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    public static String getCamelName(String dbName) {
        StringBuilder sb = new StringBuilder(dbName.toLowerCase());
        for (int i = 0; i < sb.length(); i++) {
            char charAt = sb.charAt(i);
            if ('_' == charAt) {
                if (i < sb.length() - 1) {
                    sb.setCharAt(i + 1, Character.toUpperCase(sb.charAt(i + 1)));
                }
                sb.deleteCharAt(i);
            }
        }
        return sb.toString();
    }

    public static void decoreteFields(DatasourceConfig datasourceConfig, String tableName, List<FieldConfig> codegFields) {
        List<String> primaryKeys = getPrimaryKeys(datasourceConfig, tableName);
        for (FieldConfig field : codegFields) {
            if (primaryKeys.contains(field.getFieldName())) {
                field.setPrimaryKey(true);
            }
        }
    }

    public static QualifiedType getJavaType(int dbType) {
        return typeMap.get(dbType);
    }
}
