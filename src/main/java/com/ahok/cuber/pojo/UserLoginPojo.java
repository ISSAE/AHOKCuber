package com.ahok.cuber.pojo;

import java.io.Serializable;

public class UserLoginPojo implements Serializable {
    private String email;
    private String password;

    @Override
    public String toString() {
        return "UserLoginPojo{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
