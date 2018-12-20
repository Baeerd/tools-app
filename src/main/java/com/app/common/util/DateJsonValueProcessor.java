package com.app.common.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类说明
 * Date 处理器,用于JSONObject bean转换为json字符串时，格式化日期属性
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

    public static final String Default_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private DateFormat dateFormat;

    public DateJsonValueProcessor(String datePattern) {
        try {
            dateFormat = new SimpleDateFormat(datePattern);
        } catch (Exception e) {
            dateFormat = new SimpleDateFormat(Default_DATE_PATTERN);
        }
    }

    public DateJsonValueProcessor() {
        dateFormat = new SimpleDateFormat(Default_DATE_PATTERN);
    }

    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    @Override
    public Object processObjectValue(String key, Object value,JsonConfig jsonConfig) {
        return process(value);
    }

    private Object process(Object value) {
        if (value == null) {
            return "";
        } else {
            return dateFormat.format((Date) value);
        }
    }

}