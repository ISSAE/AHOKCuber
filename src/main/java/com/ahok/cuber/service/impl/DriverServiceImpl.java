package com.ahok.cuber.service.impl;

import com.ahok.cuber.dao.DriverDao;
import com.ahok.cuber.entity.Driver;
import com.ahok.cuber.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private DriverDao driverDao;

    @Override
    public List<Driver> getAllDrivers() {
        return driverDao.getAll();
    }

    @Override
    public Driver getDriver(String driverId) {
        return driverDao.get(driverId);
    }

    @Override
    public String createDriver(Driver driver) {
        return driverDao.insertNew(driver);
    }

    @Override
    public String updateDriver(Driver driver) {
        return driverDao.update(driver);
    }

    @Override
    public String deleteDriver(String driverId) {
        return driverDao.remove(driverId);
    }

    @Override
    public Driver getAuth(String email, String password) {
        return driverDao.getAuth(email, password);
    }
}
