package com.ahok.cuber.pojo;

import com.ahok.cuber.entity.Driver;

import java.io.Serializable;
import java.util.Date;

public class DriverPojo implements Serializable {
    private String id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    private Driver.Gender gender;
    private String car_model;
    private String car_registration_number;
    private Date created_at;
    private Date updated_at;

    public DriverPojo(Driver driver) {
        this.id = driver.getId();
        this.first_name = driver.getFirst_name();
        this.last_name = driver.getLast_name();
        this.phone_number = driver.getPhone_number();
        this.email = driver.getEmail();
        this.gender = driver.getGender();
        this.car_model = driver.getCar_model();
        this.car_registration_number = driver.getCar_registration_number();
        this.created_at = driver.getCreated_at();
        this.updated_at = driver.getUpdated_at();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Driver.Gender getGender() {
        return gender;
    }

    public void setGender(Driver.Gender gender) {
        this.gender = gender;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getCar_registration_number() {
        return car_registration_number;
    }

    public void setCar_registration_number(String car_registration_number) {
        this.car_registration_number = car_registration_number;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
