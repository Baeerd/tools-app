package com.app.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 工具类
 */
public class Util {

    /**
     * json字符串转Map
     * @param json
     * @return
     */
    public static Map<String, String> jsonToMap(String json) {
        Map<String, String> resultMap = new HashMap<>();
        JSONObject jsonObject = JSONObject.fromObject(json);
        return (Map)jsonObject;
    }

    /** json字符串转换为对象
     * @param json
     * @param type
     * @return
     */
    public  static <T>  T jsonToBean(Object json, Class<T> type) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        T obj =(T)JSONObject.toBean(jsonObject, type);
        return obj;
    }

    /**
     * json字符串转换为对象集合
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonArrToList(Object json, Class<T> type) {
        List resultList = new ArrayList();
        if(json == null || StringUtils.isEmpty(json.toString())) {
            return null;
        }
        String tempJson = json.toString().replace("[", "").replace("]", "");
        String[] jsonList = tempJson.trim().split("},");
        for (String s : jsonList) {
            T t = Util.jsonToBean(s+"}", type);
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * map转换成json
     * @param StringMap
     * @return
     */
    public static String mapToJson(Map StringMap) {
        JSONObject json = JSONObject.fromObject(StringMap);
        return json==null?"":json.toString();
    }

    /**
     * 实体类转换成json
     * @param bean
     * @return
     */
    public static String beanToJson(Object bean) {
        // 处理日期类型格式（用自定义的date处理器）
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
        JSONObject json = JSONObject.fromObject(bean,config);
        return json==null?"":json.toString();
    }

    /**
     * 集合转换成json
     * @param bean
     * @return
     */
    public static String arrayToJson(Object bean) {
        // 处理日期类型格式（用自定义的date处理器）
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
        JSONArray json = JSONArray.fromObject(bean,config);
        return json==null?"":json.toString();
    }

    /**
     * 将数据库名称转换成驼峰命名
     * @param DBName
     * @return
     */
    public static String DBNameToJavaName(String DBName) {
        StringBuilder javaName = new StringBuilder();
        String[] nameList = DBName.split("_");
        for (String name : nameList) {
            javaName.append(name.substring(0,1).toUpperCase()).append(name.substring(1).toLowerCase());
        }
        return javaName.toString();
    }

    /**
     * 将数据库类型转换成java类型
     * @param DBType
     * @return
     */
    public static String DBTypeToJavaType(String DBType) {
        String javaType = "";
        switch (DBType) {
            case "VARCHAR2" : javaType = "String"; break;
            case "VARCHAR" : javaType = "String"; break;
            case "NUMBER" : javaType = "BigDecimal"; break;
            case "DATE" : javaType = "Date"; break;
            case "NUMBER_" : javaType = "BigDecimal"; break;
            default:javaType = "String";break;
        }
        return javaType;
    }

    /**
     * 将数据库类型转换成java类型
     * @param DBType
     * @return
     */
    public static String DBTypeToMapperType(String DBType) {
        String javaType = "";
        switch (DBType) {
            case "VARCHAR2" : javaType = "VARCHAR"; break;
            case "VARCHAR" : javaType = "VARCHAR"; break;
            case "NUMBER" : javaType = "DECIMAL"; break;
            case "DATE" : javaType = "TIMESTAMP"; break;
            case "NUMBER_" : javaType = "DECIMAL"; break;
            default:javaType = "VARCHAR";break;
        }
        return javaType;
    }

    /**
     * 将第一个字母变成小写
     * @param javaName
     * @return
     */
    public static String lower1(String javaName) {
        return javaName.substring(0,1).toLowerCase() + javaName.substring(1);
    }

    /**
     * 包路径转换成文件路径
     * @param packStr
     * @return
     */
    public static String packToFolder(String packStr) {
        String folderPath = "";
        if(packStr != null && !"".equals(packStr)) {
            folderPath = packStr.replaceAll("\\.", "/");
        }
        return folderPath;
    }

    /**
     * 获取Map中第一个key
     * @return
     */
    public static String getFirstKey(Map<String,String> map) {
        String key="";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            key = entry.getKey();
            if (key != null) {
                break;
            }
        }
        return key;
    }

    /**
     * 获取Map中前count个键值对(包含第count个)
     * @param map
     * @param count
     * @return
     */
    public static Map<String, String> getBeforeMap(Map<String,String> map, Integer count) {
        Map<String, String> cutMap = new LinkedHashMap<>();
        int i = 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if(i <= count) {
                String key = entry.getKey();
                cutMap.put(key, map.get(key));
            }
            i++;
        }
        return cutMap;
    }

    /**
     * 获取Map中去除前count个key之后的Map(不包含第count个)
     * @param map
     * @param count
     * @return
     */
    public static Map<String, String> getAfterMap(Map<String,String> map, Integer count) {
        Map<String, String> cutMap = new LinkedHashMap<>();

        int i = 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if(i > count) {
                String key = entry.getKey();
                cutMap.put(key, map.get(key));
            }
            i++;
        }
        return cutMap;
    }

    public static String fieldName(String dbaName) {
        return Util.lower1(Util.DBNameToJavaName(dbaName));
    }

    public static String fieldNameUpper(String dbaName) {
        return Util.DBNameToJavaName(dbaName);
    }

}
