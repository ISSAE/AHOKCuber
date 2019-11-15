package com.ahok.cuber.service;

import com.ahok.cuber.entity.Driver;

import java.util.List;

public interface DriverService {

    List<Driver> getAllDrivers();

    Driver getDriver(String driverId);

    String createDriver(Driver driver);

    String updateDriver(Driver driver);

    String deleteDriver(String driverId);

    Driver getAuth(String email, String password);
}
