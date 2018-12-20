package com.app.common.entity;

/**
 * 下拉框等数据配置实体[DATA_CONFIG]
 */
public class DataConfig extends AbstractEntity {

    private String typeId;

    private String name;

    private String value;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
