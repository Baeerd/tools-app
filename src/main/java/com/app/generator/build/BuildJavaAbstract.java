package com.app.generator.build;

import com.app.generator.util.JdbcUtil;
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

    protected String generateFileName;// 生成文件的名称

    protected Map<String, String> propMap = new HashMap<>();// 配置文件属性对应的Map

    protected List<Map<String, String>> dbaList;// 表字段集合，key 列名，value 列数据类型

    protected String folder;// 生成文件的路径

    public void init() {
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
            File parentDir = new File(classPath);
            if(!parentDir.exists() && !parentDir.isDirectory()) {
                parentDir.mkdirs();
            }
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
        dbaList = JdbcUtil.getTableInfo(propMap.get("tableNameAll").toUpperCase());
    }



    public abstract void generateDataList();

    public void generate() {
        this.init();// 初始化配置信息
        this.getDbaData();// 获取数据库表字段信息
        this.generateDataList();// 生成模板所需数据
        this.buildFile();// 生成文件
    }

}
