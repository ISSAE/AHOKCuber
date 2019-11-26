package com.ahok.cuber.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "driver")
public class Driver {
	private String id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    @JsonIgnore
    private String password;
    private Gender gender;
    private String car_model;
    private String car_registration_number;

    @OneToMany(mappedBy = "driver", targetEntity = Trip.class, fetch=FetchType.EAGER)
    private List<Trip> trips;

    @Column(columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private Date created_at;

    @Column(columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private Date updated_at;

    public enum Gender {
        MALE (0){
            @Override
            public boolean isMale() {
                return true;
            }
        },
        FEMALE (1){
            @Override
            public boolean isFemale() {
                return true;
            }
        };

        public int sex;

        public boolean isMale() {return false;}

        public boolean isFemale() {return false;}

        Gender (int sex) {
            this.sex = sex;
        }
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id='" + id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", car_model='" + car_model + '\'' +
                ", car_registration_number='" + car_registration_number + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }

    @PrePersist
    private void preInsert () {
        this.created_at = new Date();
        this.updated_at = new Date();
    }

    @PreUpdate
    private void preUpdate () {
        this.updated_at = new Date();
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "com.ahok.cuber.util.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String lastname) {
        this.last_name = lastname;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String firstname) {
        this.first_name = firstname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone) {
        this.phone_number = phone;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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
}
