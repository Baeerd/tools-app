package com.app.generator.build.service;


import com.app.common.util.Util;
import com.app.generator.build.BuildJava;
import com.app.generator.build.BuildJavaAbstract;

public class BuildService extends BuildJavaAbstract implements BuildJava {

    public BuildService(String filePath, String tableName) {
        templateName = "service.ftl";
        templatePath = filePath+"/templates";
        classPath = filePath+"/result";
        this.tableName = tableName;
    }

    @Override
    public void generateDataList() {
        dataMap.put("package", propMap.get("package_service"));
        dataMap.put("entityClass", propMap.get("package_entity") + "." + Util.DBNameToJavaName(tableName));
        dataMap.put("entityName", Util.DBNameToJavaName(tableName));
        dataMap.put("serviceName", Util.DBNameToJavaName(tableName)+"Service");

        // 生成目录
        folder = Util.packToFolder(propMap.get("package_service"));
        // 生成文件
        generateFileName = Util.DBNameToJavaName(tableName) + "Service.java";
    }
}
