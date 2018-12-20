package com.app.image.entity;

import com.app.common.entity.AbstractEntity;

import java.math.BigDecimal;

public class Image extends AbstractEntity {

    private String conveId;
    private BigDecimal taskType;
    private BigDecimal warehouseSid;
    private BigDecimal hallSid;
    private BigDecimal maxLoad;
    private BigDecimal taskState;
    private String taskDesc;

    public BigDecimal getWarehouseSid() {
        return warehouseSid;
    }
    public void setWarehouseSid(BigDecimal warehouseSid) {
        this.warehouseSid = warehouseSid;
    }
    public String getConveId() {
        return conveId;
    }
    public void setConveId(String conveId) {
        this.conveId = conveId;
    }
    public BigDecimal getTaskType() {
        return taskType;
    }
    public void setTaskType(BigDecimal taskType) {
        this.taskType = taskType;
    }
    public BigDecimal getHallSid() {
        return hallSid;
    }
    public void setHallSid(BigDecimal hallSid) {
        this.hallSid = hallSid;
    }
    public BigDecimal getMaxLoad() {
        return maxLoad;
    }
    public void setMaxLoad(BigDecimal maxLoad) {
        this.maxLoad = maxLoad;
    }
    public BigDecimal getTaskState() {
        return taskState;
    }
    public void setTaskState(BigDecimal taskState) {
        this.taskState = taskState;
    }
    public String getTaskDesc() {
        return taskDesc;
    }
    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }
}
