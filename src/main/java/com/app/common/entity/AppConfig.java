package com.app.common.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private String index;

    private String value;

    private String exceptionMessage1;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExceptionMessage1() {
        return exceptionMessage1;
    }

    public void setExceptionMessage1(String exceptionMessage1) {
        this.exceptionMessage1 = exceptionMessage1;
    }
}
