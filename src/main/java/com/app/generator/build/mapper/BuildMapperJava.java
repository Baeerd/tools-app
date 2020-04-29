package com.app.generator.build.mapper;

import com.app.common.util.Util;
import com.app.generator.build.BuildJava;
import com.app.generator.build.BuildJavaAbstract;

import java.util.Map;

public class BuildMapperJava extends BuildJavaAbstract implements BuildJava {

    public BuildMapperJava(String filePath, Map<String, String> params) {
        templateName = "mapperJava.ftl";
        templatePath = filePath+"/templates";
        classPath = filePath+"/result";
        propMap.putAll(params);
        propMap.put("package_mapper", propMap.get("packageName")+".mapper");
        propMap.put("package_entity", propMap.get("packageName")+".entity");
    }

    @Override
    public void generateDataList() {
        dataMap.put("package", propMap.get("package_mapper"));
        dataMap.put("entityClass", propMap.get("package_entity") + "." + Util.DBNameToJavaName(propMap.get("tableName")));
        dataMap.put("entityName", Util.DBNameToJavaName(propMap.get("tableName")));
        dataMap.put("mapperName", Util.DBNameToJavaName(propMap.get("tableName"))+"Mapper");

        // 生成目录
        folder = Util.packToFolder(propMap.get("package_mapper"));
        // 生成文件
        generateFileName = Util.DBNameToJavaName(propMap.get("tableName")) + "Mapper.java";
    }
}
