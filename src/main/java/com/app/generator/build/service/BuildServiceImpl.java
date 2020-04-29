package com.app.generator.build.service;


import com.app.common.util.Util;
import com.app.generator.build.BuildJava;
import com.app.generator.build.BuildJavaAbstract;

import java.util.Map;

public class BuildServiceImpl extends BuildJavaAbstract implements BuildJava {

    public BuildServiceImpl(String filePath, Map<String, String> params) {
        templateName = "serviceImpl.ftl";
        templatePath = filePath+"/templates";
        classPath = filePath+"/result";
        propMap.putAll(params);
        propMap.put("package_service", propMap.get("packageName")+".service");
        propMap.put("package_entity", propMap.get("packageName")+".entity");
    }

    @Override
    public void generateDataList() {
        dataMap.put("package", propMap.get("package_service")+".impl");
        dataMap.put("entityClass", propMap.get("package_entity") + "." + Util.DBNameToJavaName(propMap.get("tableName")));
        dataMap.put("entityName", Util.DBNameToJavaName(propMap.get("tableName")));
        dataMap.put("serviceName", Util.DBNameToJavaName(propMap.get("tableName"))+"Service");
        dataMap.put("serviceClass", propMap.get("package_service") + "." + Util.DBNameToJavaName(propMap.get("tableName"))+"Service");
        dataMap.put("serviceImplName", Util.DBNameToJavaName(propMap.get("tableName"))+"ServiceImpl");

        // 生成目录
        folder = Util.packToFolder(propMap.get("package_service")+".impl");
        // 生成文件
        generateFileName = Util.DBNameToJavaName(propMap.get("tableName")) + "ServiceImpl.java";
    }
}
