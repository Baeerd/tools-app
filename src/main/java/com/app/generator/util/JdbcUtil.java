package com.app.generator.util;

import com.app.common.exception.MessageException;
import com.app.generator.entity.Generator;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class JdbcUtil {

    public static String templatePath = "src/main/webapp/templates";

    private static String driver;
    private static String url;
    protected static String user;
    private static String pwd;

    private static void init() {
        Map<String, String> propMap = new HashMap<String, String>();
        InputStream in = null;
        Properties p = new Properties();
        try {
            in = new BufferedInputStream(new FileInputStream(new File(
                    templatePath+"/config.properties")));
            p.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<Map.Entry<Object, Object>> entrySet = p.entrySet();
        for (Map.Entry<Object, Object> entry : entrySet) {
            propMap.put((String) entry.getKey(), (String) entry.getValue());
        }
        url = propMap.get("jdbcUrl");
        user = propMap.get("jdbcUser");
        pwd = propMap.get("jdbcPassword");
        driver = propMap.get("jdbcName");
    }

    /**
     * 根据数据库的连接参数，获取指定表的基本信息：字段名、字段类型、字段注释
     * @param table 表名
     * @return Map集合
     */
    public static List<Map<String,String>> getTableInfo(String table){
        init();
        List<Map<String,String>> result = new ArrayList<>();

        Connection conn = null;
        DatabaseMetaData dbmd = null;

        try {
            conn = getConnections(driver,url,user,pwd);
            dbmd = conn.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, "%", table, new String[] { "TABLE" });

            while (resultSet.next()) {
                String tableName=resultSet.getString("TABLE_NAME");

                if(tableName.equals(table)){
                    ResultSet rs = conn.getMetaData().getColumns(null, getSchema(conn),tableName.toUpperCase(), "%");

                    while(rs.next()){
                        //System.out.println("字段名："+rs.getString("COLUMN_NAME")+"--字段注释："+rs.getString("REMARKS")+"--字段数据类型："+rs.getString("TYPE_NAME"));
                        Map<String,String> map = new HashMap<>();
                        String colName = rs.getString("COLUMN_NAME");
                        map.put("colName", colName);

                        String remarks = rs.getString("REMARKS");
                        if(remarks == null || remarks.equals("")){
                            remarks = colName;
                        }
                        map.put("remarks",remarks);

                        String dbType = rs.getString("TYPE_NAME");
                        map.put("dbType",dbType);

                        map.put("valueType", changeDbType(dbType));
                        result.add(map);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private static String changeDbType(String dbType) {
        dbType = dbType.toUpperCase();
        switch(dbType){
            case "VARCHAR":
            case "VARCHAR2":
            case "CHAR":
                return "1";
            case "NUMBER":
            case "DECIMAL":
                return "4";
            case "INT":
            case "SMALLINT":
            case "INTEGER":
                return "2";
            case "BIGINT":
                return "6";
            case "DATETIME":
            case "TIMESTAMP":
            case "DATE":
                return "7";
            default:
                return "1";
        }
    }

    //获取连接
    private static Connection getConnections(String driver, String url, String user, String pwd) throws Exception {
        Connection conn = null;
        try {
            Properties props = new Properties();
            props.put("remarksReporting", "true");
            props.put("user", user);
            props.put("password", pwd);
            Class.forName(driver);
            conn = DriverManager.getConnection(url, props);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return conn;
    }

    //其他数据库不需要这个方法 oracle和db2需要
    private static String getSchema(Connection conn) throws Exception {
        String schema;
        schema = conn.getMetaData().getUserName();
        if ((schema == null) || (schema.length() == 0)) {
            throw new Exception("ORACLE数据库模式不允许为空");
        }
        return schema.toUpperCase().toString();
    }

    public static List<Generator> getTableData(String tableName) {
        init();
        Connection conn = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        List<Generator> generators = new ArrayList<>();

        try {
            conn = getConnections(driver, url, user, pwd);
            if(StringUtils.isNotEmpty(tableName)) {
                p = conn.prepareStatement("select TABLE_NAME, LAST_ANALYZED, NUM_ROWS, TABLESPACE_NAME from USER_TABLES where TABLE_NAME like ? and LAST_ANALYZED is not null order by LAST_ANALYZED desc");
                p.setString(1, "%"+tableName+"%");
            } else {
                p = conn.prepareStatement("select TABLE_NAME, LAST_ANALYZED, NUM_ROWS, TABLESPACE_NAME from USER_TABLES where LAST_ANALYZED is not null order by LAST_ANALYZED desc");
            }
            resultSet = p.executeQuery();
            while (resultSet.next()) {
                Generator generator = new Generator();
                generator.setTableName(resultSet.getString(1));
                generator.setLastAnalyzed(resultSet.getDate(2));
                generator.setNumRows(resultSet.getLong(3));
                generator.setTablespaceName(resultSet.getString(4));
                generators.add(generator);
            }
            System.out.println(generators);
        } catch (Exception e) {
            throw new MessageException(e.getMessage());
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return generators;
    }
}
