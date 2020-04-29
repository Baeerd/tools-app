package com.app.generator.build.controller;


import com.app.common.util.Util;
import com.app.generator.build.BuildJava;
import com.app.generator.build.BuildJavaAbstract;

import java.util.Map;

public class BuildController extends BuildJavaAbstract implements BuildJava {

    public BuildController(String filePath, Map<String, String> params) {
        templateName = "controller.ftl";
        templatePath = filePath+"/templates";
        classPath = filePath+"/result";
        propMap.putAll(params);
        propMap.put("package_controller", propMap.get("packageName")+".controller");
        propMap.put("package_entity", propMap.get("packageName")+".entity");
    }

    @Override
    public void generateDataList() {
        dataMap.put("package", propMap.get("package_controller"));
        dataMap.put("entityClass", propMap.get("package_entity") + "." + Util.DBNameToJavaName(propMap.get("tableName")));
        dataMap.put("entityName", Util.DBNameToJavaName(propMap.get("tableName")));
        dataMap.put("controllerName", Util.DBNameToJavaName(propMap.get("tableName"))+"Controller");

        dataMap.put("requestUrl", propMap.get("tableName").toLowerCase().replaceAll("_", "-"));

        // 生成目录
        folder = Util.packToFolder(propMap.get("package_controller"));
        // 生成文件
        generateFileName = Util.DBNameToJavaName(propMap.get("tableName")) + "Controller.java";
    }
}
