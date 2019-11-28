package com.ahok.cuber.controller;

import com.ahok.cuber.entity.Driver;
import com.ahok.cuber.pojo.DriverPojo;
import com.ahok.cuber.service.DriverService;
import com.ahok.cuber.util.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "drivers/all",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getAllDrivers() {
        List<Driver> driversList =
                driverService
                        .getAllDrivers();

        List<DriverPojo> drivers = new ArrayList<>();
        driversList.forEach(driver -> drivers.add(new DriverPojo(driver)));

        return Response.ok(driversList);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "drivers/get/{id}",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getDriver(@PathVariable("id") String driverId) {
        return Response.ok(new DriverPojo(driverService.getDriver(driverId)));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "drivers/add",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity createDriver(@RequestBody @Validated Driver driver) {
        String action = driverService.createDriver(driver);
        if (action == null) {
            return Response.badRequest(String.format("Driver with email (%s) already exists", driver.getEmail()));
        }
        return Response.ok(new DriverPojo(driver));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "drivers/update",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.PUT)
    public ResponseEntity updateDriver(@RequestBody @Validated Driver driver) {
        // TODO
        // handle IllegalArgumentException if `id` is missing.
        // handle NullPointerException if driver with given `id` not found
        return Response.ok(driverService.updateDriver(driver));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "drivers/delete/{id}",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteDriver(@PathVariable("id") String driverId) {
        return Response.ok(driverService.deleteDriver(driverId));
    }
}
