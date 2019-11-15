package com.ahok.cuber.util.http;

import org.springframework.http.HttpStatus;

public class ResponseBody {
    private boolean success;
    private Object body;
    private int status;

    ResponseBody(boolean success, Object body, HttpStatus status) {
        this.success = success;
        this.body = body;
        this.status = status.value();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }
}
