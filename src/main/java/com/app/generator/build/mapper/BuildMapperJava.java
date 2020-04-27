package com.app.generator.build.mapper;

import com.app.common.util.Util;
import com.app.generator.build.BuildJava;
import com.app.generator.build.BuildJavaAbstract;

public class BuildMapperJava extends BuildJavaAbstract implements BuildJava {

    public BuildMapperJava(String filePath, String tableName) {
        templateName = "mapperJava.ftl";
        templatePath = filePath+"/templates";
        classPath = filePath+"/result";
        this.tableName = tableName;
    }

    @Override
    public void generateDataList() {
        dataMap.put("package", propMap.get("package_mapper"));
        dataMap.put("entityClass", propMap.get("package_entity") + "." + Util.DBNameToJavaName(tableName));
        dataMap.put("entityName", Util.DBNameToJavaName(tableName));
        dataMap.put("mapperName", Util.DBNameToJavaName(tableName)+"Mapper");

        // 生成目录
        folder = Util.packToFolder(propMap.get("package_mapper"));
        // 生成文件
        generateFileName = Util.DBNameToJavaName(tableName) + "Mapper.java";
    }
}
