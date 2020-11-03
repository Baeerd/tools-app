package com.app.generator.build.mapper;


import com.app.common.util.Util;
import com.app.generator.build.BuildJava;
import com.app.generator.build.BuildJavaAbstract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildMapperXml extends BuildJavaAbstract implements BuildJava {

    public BuildMapperXml(String filePath, Map<String, String> params) {
        templateName = "mapperXml.ftl";
        templatePath = filePath+"/templates";
        classPath = filePath+"/result";
        propMap.putAll(params);
        propMap.put("package_mapper", propMap.get("packageName")+".mapper");
        propMap.put("package_entity", propMap.get("packageName")+".entity");
    }

    @Override
    public void generateDataList() {
        dataMap.put("package", propMap.get("package_mapper"));
        dataMap.put("mapperClass", propMap.get("package_mapper") + "." + Util.DBNameToJavaName(propMap.get("tableName")) + "Mapper");
        dataMap.put("entityClass", propMap.get("package_entity") + "." + Util.DBNameToJavaName(propMap.get("tableName")));

        dataMap.put("tableName", propMap.get("tableNameAll"));
        dataMap.put("insertSeq", propMap.get("insertSeq"));

        List<Map<String, String>> properlist = new ArrayList<>();

        for (Map<String, String> dbaDataMap : dbaList) {
            Map<String, String> map = new HashMap<>();
            map.put("fieldName", Util.fieldName(dbaDataMap.get("colName")));
            map.put("fieldType", Util.DBTypeToJavaType(dbaDataMap.get("dbType")));
            map.put("fieldNameUpper", Util.fieldNameUpper(dbaDataMap.get("colName")));
            map.put("fieldRemark", Util.fieldName(dbaDataMap.get("remarks")));

            map.put("dbaName", dbaDataMap.get("colName"));
            map.put("mapperType", Util.DBTypeToMapperType(dbaDataMap.get("dbType")));

            properlist.add(map);
        }

        dataMap.put("properlist",properlist);

        // 生成目录
        folder = Util.packToFolder(propMap.get("package_mapper"));
        // 生成文件
        generateFileName = Util.DBNameToJavaName(propMap.get("tableName")) + "Mapper.xml";
    }
}
