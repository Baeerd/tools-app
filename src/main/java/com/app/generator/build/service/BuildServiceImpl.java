package com.app.generator.build.service;


import com.app.common.util.Util;
import com.app.generator.build.BuildJava;
import com.app.generator.build.BuildJavaAbstract;

public class BuildServiceImpl extends BuildJavaAbstract implements BuildJava {

    public BuildServiceImpl(String filePath, String tableName) {
        templateName = "serviceImpl.ftl";
        templatePath = filePath+"/templates";
        classPath = filePath+"/result";
        this.tableName = tableName;
    }

    @Override
    public void generateDataList() {
        dataMap.put("package", propMap.get("package_service")+".impl");
        dataMap.put("entityClass", propMap.get("package_entity") + "." + Util.DBNameToJavaName(tableName));
        dataMap.put("entityName", Util.DBNameToJavaName(tableName));
        dataMap.put("serviceName", Util.DBNameToJavaName(tableName)+"Service");
        dataMap.put("serviceClass", propMap.get("package_service") + "." + Util.DBNameToJavaName(tableName)+"Service");
        dataMap.put("serviceImplName", Util.DBNameToJavaName(tableName)+"ServiceImpl");

        // 生成目录
        folder = Util.packToFolder(propMap.get("package_service")+".impl");
        // 生成文件
        generateFileName = Util.DBNameToJavaName(tableName) + "ServiceImpl.java";
    }
}
