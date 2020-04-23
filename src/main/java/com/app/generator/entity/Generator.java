package com.app.generator.entity;

import com.app.common.entity.AbstractEntity;

import java.util.Date;

/**
 * 数据库表
 */
public class Generator extends AbstractEntity{

   private String tableName;

   private Date lastAnalyzed;

   private Long numRows;

   private String tablespaceName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Date getLastAnalyzed() {
        return lastAnalyzed;
    }

    public void setLastAnalyzed(Date lastAnalyzed) {
        this.lastAnalyzed = lastAnalyzed;
    }

    public Long getNumRows() {
        return numRows;
    }

    public void setNumRows(Long numRows) {
        this.numRows = numRows;
    }

    public String getTablespaceName() {
        return tablespaceName;
    }

    public void setTablespaceName(String tablespaceName) {
        this.tablespaceName = tablespaceName;
    }
}
