package com.ahok.cuber.pojo;

import java.io.Serializable;

public class AuthResponsePojo <T> implements Serializable {
    private T entity;
    private String token;

    public AuthResponsePojo(T entity, String token) {
        this.entity = entity;
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthResponsePojo{" +
                "entity=" + entity +
                ", token='" + token + '\'' +
                '}';
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
