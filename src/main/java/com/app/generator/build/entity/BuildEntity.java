package com.app.generator.build.entity;


import com.app.common.util.Util;
import com.app.generator.build.BuildJava;
import com.app.generator.build.BuildJavaAbstract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildEntity extends BuildJavaAbstract implements BuildJava {

    public BuildEntity(String filePath, Map<String, String> params) {
        templateName = "entity.ftl";
        templatePath = filePath+"/templates";
        classPath = filePath+"/result";
        propMap.putAll(params);
        propMap.put("package_entity", propMap.get("packageName")+".entity");
    }

    @Override
    public void generateDataList() {
        dataMap.put("package", propMap.get("package_entity"));
        dataMap.put("class", Util.DBNameToJavaName(propMap.get("tableName")));

        Integer num = propMap.get("entityField")!=null?Integer.valueOf(propMap.get("entityField")):0;

        List<Map<String, String>> properlist = new ArrayList<>();
        int i = 0;
        for (Map<String, String> dbaDataMap : dbaList) {
            if(i++<num) {
                continue;
            }
            Map<String, String> map = new HashMap<>();
            map.put("fieldName", Util.fieldName(dbaDataMap.get("colName")));
            map.put("fieldRemark", Util.fieldName(dbaDataMap.get("remarks")));
            map.put("fieldType", Util.DBTypeToJavaType(dbaDataMap.get("dbType")));
            map.put("fieldNameUpper", Util.fieldNameUpper(dbaDataMap.get("colName")));
            properlist.add(map);
        }

        dataMap.put("properlist",properlist);
        // 生成目录
        folder = Util.packToFolder(propMap.get("package_entity"));
        // 生成文件
        generateFileName = Util.DBNameToJavaName(propMap.get("tableName")) + ".java";

    }

}
