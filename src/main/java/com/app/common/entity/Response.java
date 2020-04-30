package com.app.common.entity;

/**
 * 响应对象
 */
public class Response {

    private static final String OK = "操作成功!";
    private static final String ERROR = "操作失败!";

    private Object data;
    private boolean success;
    private Object message;

    public Response success() {
        this.success=true;
        this.data = OK;
        return this;
    }

    public Response success(Object data) {
        this.success=true;
        this.data = data;
        return this;
    }

    public Response failure() {
        this.success=false;
        this.data = ERROR;
        return this;
    }

    public Response failure(String message) {
        this.success=false;
        this.data = message;
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

	public boolean isSuccess() {
		return success;
	}

    public Object getMessage() {
        return message;
    }
}
