package com.app.generator.build.controller;


import com.app.common.util.Util;
import com.app.generator.build.BuildJava;
import com.app.generator.build.BuildJavaAbstract;

public class BuildController extends BuildJavaAbstract implements BuildJava {

    public BuildController(String filePath, String tableName) {
        templateName = "controller.ftl";
        templatePath = filePath+"/templates";
        classPath = filePath+"/result";
        this.tableName = tableName;
    }

    @Override
    public void generateDataList() {
        dataMap.put("package", propMap.get("package_controller"));
        dataMap.put("entityClass", propMap.get("package_entity") + "." + Util.DBNameToJavaName(tableName));
        dataMap.put("entityName", Util.DBNameToJavaName(tableName));
        dataMap.put("controllerName", Util.DBNameToJavaName(tableName)+"Controller");

        dataMap.put("requestUrl", tableName.toLowerCase().replaceAll("_", "-"));

        // 生成目录
        folder = Util.packToFolder(propMap.get("package_controller"));
        // 生成文件
        generateFileName = Util.DBNameToJavaName(tableName) + "Controller.java";
    }
}
