package com.ahok.cuber.pojo;

import com.ahok.cuber.entity.Client;

import java.io.Serializable;
import java.util.Date;

public class ClientPojo implements Serializable {
    private String id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    private Client.Gender gender;
    private Date created_at;
    private Date updated_at;

    public ClientPojo(Client client) {
        this.id = client.getId();
        this.first_name = client.getFirst_name();
        this.last_name = client.getLast_name();
        this.phone_number = client.getPhone_number();
        this.email = client.getEmail();
        this.gender = client.getGender();
        this.created_at = client.getCreated_at();
        this.updated_at = client.getUpdated_at();
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

    public Client.Gender getGender() {
        return gender;
    }

    public void setGender(Client.Gender gender) {
        this.gender = gender;
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
