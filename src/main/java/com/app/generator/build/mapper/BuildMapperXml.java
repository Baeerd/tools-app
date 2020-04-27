package com.app.generator.build.mapper;


import com.app.common.util.Util;
import com.app.generator.build.BuildJava;
import com.app.generator.build.BuildJavaAbstract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildMapperXml extends BuildJavaAbstract implements BuildJava {

    public BuildMapperXml(String filePath, String tableName) {
        templateName = "mapperXml.ftl";
        templatePath = filePath+"/templates";
        classPath = filePath+"/result";
        this.tableName = tableName;
    }

    @Override
    public void generateDataList() {
        dataMap.put("package", propMap.get("package_mapper"));
        dataMap.put("mapperClass", propMap.get("package_mapper") + "." + Util.DBNameToJavaName(tableName) + "Mapper");
        dataMap.put("entityClass", propMap.get("package_entity") + "." + Util.DBNameToJavaName(tableName));

        dataMap.put("tableName", tableName);
        dataMap.put("find", propMap.get("find"));
        dataMap.put("insert", propMap.get("insert"));
        dataMap.put("update", propMap.get("update"));
        dataMap.put("delete", propMap.get("delete"));
        dataMap.put("insertSeq", "SEQ_"+tableName);

        List<Map<String, String>> properlist = new ArrayList<>();

        for (Map<String, String> dbaDataMap : dbaList) {
            Map<String, String> map = new HashMap<>();
            map.put("fieldName", Util.fieldName(dbaDataMap.get("name")));
            map.put("fieldType", Util.DBTypeToJavaType(dbaDataMap.get("type")));
            map.put("fieldNameUpper", Util.fieldNameUpper(dbaDataMap.get("name")));

            map.put("dbaName", dbaDataMap.get("name"));
            map.put("mapperType", Util.DBTypeToMapperType(dbaDataMap.get("type")));

            properlist.add(map);
        }

        dataMap.put("properlist",properlist);

        // 生成目录
        folder = Util.packToFolder(propMap.get("package_mapper"));
        // 生成文件
        generateFileName = Util.DBNameToJavaName(tableName) + "Mapper.xml";
    }
}
