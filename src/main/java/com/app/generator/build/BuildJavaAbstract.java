package com.app.generator.build;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.sql.*;
import java.util.*;

public abstract class BuildJavaAbstract implements BuildJava {

    protected String templatePath = "src/main/templates";
    protected String classPath = "src/main/result";

    protected Map<String, Object> dataMap = new HashMap<>();

    protected String templateName;// 模板名称

    protected String tableName;// 表名称

    protected String generateFileName;// 生成文件的名称

    protected Map<String, String> propMap;// 配置文件属性对应的Map

    protected List<Map<String, String>> dbaList;// 表字段集合，key 列名，value 列数据类型

    protected String folder;// 生成文件的路径

    public void init() {
        propMap = new HashMap<String, String>();
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
    }

    public void buildFile() {
        // 创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
            // 加载模版文件
            Template template = configuration.getTemplate(templateName);
            // 生成目录
            File folderDir = new File(classPath + "/" + folder);
            if(!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }
            // 生成数据
            File docFile = new File(classPath + "/" + folder + "/" + generateFileName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // 输出文件
            template.process(dataMap, out);
            System.out.println("生成文件................."+classPath + "/" + folder + "/" + generateFileName);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 根据数据库配置信息，连接数据库，对应表所有属性及类型
     * @return key 列名，value 列数据类型
     */
    private void getDbaData() {
        dbaList = new ArrayList<>();
        try {
            Class.forName(propMap.get("jdbcName"));
            String url = propMap.get("jdbcUrl");
            String user = propMap.get("jdbcUser");
            String password = propMap.get("jdbcPassword");

            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "select * from " + tableName;
            PreparedStatement stmt;
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData data = rs.getMetaData();
            for (int i = 1; i <= data.getColumnCount(); i++) {
                // 获得所有列的数目及实际列数
                int columnCount = data.getColumnCount();
                // 获得指定列的列名
                String columnName = data.getColumnName(i);
                // 获得指定列的列值
                int columnType = data.getColumnType(i);
                // 获得指定列的数据类型名
                String columnTypeName = data.getColumnTypeName(i);
                // 所在的Catalog名字
                String catalogName = data.getCatalogName(i);
                // 对应数据类型的类
                String columnClassName = data.getColumnClassName(i);
                // 在数据库中类型的最大字符个数
                int columnDisplaySize = data.getColumnDisplaySize(i);
                // 默认的列的标题
                String columnLabel = data.getColumnLabel(i);
                // 获得列的模式
                String schemaName = data.getSchemaName(i);
                // 某列类型的精确度(类型的长度)
                int precision = data.getPrecision(i);
                // 小数点后的位数
                int scale = data.getScale(i);
                // 获取某列对应的表名
                String tableName = data.getTableName(i);
                // 是否自动递增
                boolean isAutoInctement = data.isAutoIncrement(i);
                // 在数据库中是否为货币型
                boolean isCurrency = data.isCurrency(i);
                // 是否为空
                int isNullable = data.isNullable(i);
                // 是否为只读
                boolean isReadOnly = data.isReadOnly(i);
                // 能否出现在where中
                boolean isSearchable = data.isSearchable(i);
                Map<String, String> field = new HashMap<>();
                field.put("name", columnName);
                field.put("type", columnTypeName);
                dbaList.add(field);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void generateDataList();

    public void generate() {
        this.init();// 初始化配置信息
        this.getDbaData();// 获取数据库表字段信息
        this.generateDataList();// 生成模板所需数据
        this.buildFile();// 生成文件
    }

}
