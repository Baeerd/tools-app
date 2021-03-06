package com.app.common.entity;

import java.util.Date;

/**
 * 所有表的公共字段，用于其他实体类继承
 */
public abstract class AbstractEntity {
	
	/** 序列号 */
	protected Long sid;
	
	/** 版本号 */
	private Integer version=new Integer(1);
	
	/** 创建者*/
	private String createdBy;

	/** 创建时间 */
	private Date createdDt = new Date();

	/** 最后更新者*/
	private String updatedBy;

	/** 最后更新时间 */
	private Date updatedDt;
	
	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public Date getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}


}
