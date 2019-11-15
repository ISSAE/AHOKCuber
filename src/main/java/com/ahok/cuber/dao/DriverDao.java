package com.ahok.cuber.dao;

import com.ahok.cuber.entity.Driver;

import java.util.List;

public interface DriverDao {
    List<Driver> getAll();
    Driver get(String driverId);
    String insertNew(Driver driver);
    String update(Driver driver);
    String remove(String driverId);
    Driver getAuth(String email, String password);
}
